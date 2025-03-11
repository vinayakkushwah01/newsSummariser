package com.newsSummeriser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.newsSummeriser.model.NewsArticle;
import com.newsSummeriser.repository.NewsArticleRepo;
import com.newsSummeriser.repository.NewsCardRepo;
import com.newsSummeriser.model.NewsCard;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;


@Service
public class ContentScraper {
    @Autowired
    private NewsArticleRepo newsArticleRepo;
   @Autowired
    private NewsCardRepo newsCardRepo;

 @PersistenceContext  // ✅ Inject EntityManager properly
    private EntityManager entityManager;

    public String fetchArticle(String url ,Long id ) {
        try {
            // Connect to the website and fetch the document
            Document doc = Jsoup.connect(url).get();

            // Extract the headline
            Element headlineElement = doc.selectFirst("h1");
            String headline = (headlineElement != null) ? headlineElement.text().trim() : "Headline not found";



            // Extract image element
            Element imageElement = doc.selectFirst("div.image img");

            // Extract the correct image URL from `data-src` or `src`
            String imgUrl = "Image not found";
            if (imageElement != null) {
                if (imageElement.hasAttr("data-src")) {
                    String dataSrcUrl = imageElement.absUrl("data-src");
                    if (dataSrcUrl.startsWith("https://staticimg")) {
                        imgUrl = dataSrcUrl;  // Use `data-src` if it's correct
                    }
                } 
                if (imgUrl.equals("Image not found") && imageElement.hasAttr("src")) {
                    String srcUrl = imageElement.absUrl("src");
                    if (srcUrl.startsWith("https://staticimg")) {
                        imgUrl = srcUrl;  // Use `src` only if `data-src` is missing
                    }
                }
            }

           // System.out.println("Extracted Image URL: " + imgUrl);

            // Select all possible sections containing the article
            Elements contentElements = doc.select("div.article-desc, div.vistaar, div.hide_for_metered_wall");

            StringBuilder detailedNews = new StringBuilder();
            List<String> unwantedTexts = Arrays.asList(
                    "विज्ञापन", "Trending Videos यह वीडियो/विज्ञापन हटाएं", "और पढ़ें", "Link Copied"," वॉट्सऐप चैनल फॉलो करें","Follow Us", "पहले जानें-"
            );

            for (Element content : contentElements) {
                Elements paragraphs = content.select("p, div");
                for (Element para : paragraphs) {
                    String text = para.text().trim();
                    
                    // Remove unwanted texts
                    if (!text.isEmpty() && unwantedTexts.stream().noneMatch(text::contains)) {
                        detailedNews.append(text).append("\n\n");
                    }
                }
            }
                NewsArticle na = new NewsArticle();
                na.setId(id);
                na.setHeadline(headline);
                na.setDetailedNews(detailedNews.toString().trim());
                na.setImageUrl(imgUrl);
                newsArticleRepo.save(na);

            // Print extracted and cleaned data
            //System.out.println("Headline: " + headline);
            // System.out.println("Image URL: " + imgUrl);
            // System.out.println("\nDetailed News:\n" + detailedNews.toString().trim());

            return "Scraping Successful!";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Scraping Failed!";
    }

         //  Run once when the server starts
    @PostConstruct
    @Scheduled(fixedRate = 1830000, initialDelay = 60000) // Run every 30.5 min and intial wait for 1 min  minutes
    public String mapArticles() {
        entityManager.clear(); //
        List<NewsCard> unfatchedNewsCards = newsCardRepo.findByFetchedFalse();
    
        Iterator<NewsCard> iterator = unfatchedNewsCards.iterator();
        while (iterator.hasNext()) {
            NewsCard entity = iterator.next();
    
            if (entity.getDate() == null) {
                iterator.remove();
                newsCardRepo.deleteById(entity.getId());  // Delete from DB
                continue;
            }
    
            Long id = entity.getId();
            String url = "https://www.amarujala.com/" + entity.getArticleLink();
    
            String res = fetchArticle(url, id);
    
            if ("Scraping Successful!".equals(res)) {  
                entity.setFetched(true);
                newsCardRepo.saveAndFlush(entity);  //  Saves each entity individually
            }
        }
    
        return "ok all Articles are added";
    }
    
}
