package com.newsSummeriser.service;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newsSummeriser.controller.BreakingNewsController;
import com.newsSummeriser.exception.ContentNotScarpedError;
import com.newsSummeriser.model.BreakingNews;
import com.newsSummeriser.model.Category;
import com.newsSummeriser.model.NewsDetails;
import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.repository.BreakingNewsRepo;
import com.newsSummeriser.repository.CategoryRepository;
import com.newsSummeriser.repository.NewsHeadlineRepository;


@Service 
public class NewsService {

   

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private NewsHeadlineRepository newsHeadlineRepo;

    @Autowired
    private BreakingNewsRepo breakingNewsRepo;



   


    public List<Category> getNewsCategory(String url){
       
        String temp = "/"+url ;
        if(url.equals("home")){temp = "/";}
        return(categoryRepo.findByUrl(temp));
    }
    
    public List<NewsHeadline> getNewsHeadlines(String url) throws Exception {
        try {
            List<Category> categories = getNewsCategory(url);
            
            if (categories.isEmpty()) {
                throw new Exception("Data not found or incorrect category");
            }
            
            return newsHeadlineRepo.findByCategoryAndFetchedTrueOrderByDateDesc(categories.get(0));
        } catch (Exception e) {
            throw new Exception("An unexpected error occurred", e);
        }
    }
    
    public List<BreakingNews> fetchBreakingNews(){
       return  breakingNewsRepo.findTop6ByOrderByLocalDateTimeDesc();
    }
    public NewsDetails fetchBreakingNews(String url ,Long id  ) {
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
                NewsDetails na = new NewsDetails();
                // na.setId(id);
                na.setHeadline(headline);
                na.setDetailedNews(detailedNews.toString().trim());
                na.setImageUrl(imgUrl);
                // na.setNewsHeadline(newsHeadline);
                return na;

            // System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            // System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            // System.out.println("Saved in news Details ");
            // System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            // System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            // // Print extracted and cleaned data
            //System.out.println("Headline: " + headline);
            // System.out.println("Image URL: " + imgUrl);
            // System.out.println("\nDetailed News:\n" + detailedNews.toString().trim());

            // return "Scraping Successful!";

        } catch (Exception e) {
            e.printStackTrace();
            throw new ContentNotScarpedError("Can Not Get Article Please try again");
        }
        
    }
}
