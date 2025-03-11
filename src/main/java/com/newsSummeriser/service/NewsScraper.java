package com.newsSummeriser.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.newsSummeriser.exception.DuplicateNewsException;
import com.newsSummeriser.model.HashTags;
import com.newsSummeriser.model.NewsCard;
import com.newsSummeriser.model.Tags;
import com.newsSummeriser.model.BreakingNews;
import com.newsSummeriser.repository.BreakingNewsRepo;
import com.newsSummeriser.repository.HashTagsRepo;
import com.newsSummeriser.repository.TagsRepo;

import jakarta.annotation.PostConstruct;

import com.newsSummeriser.repository.NewsCardRepo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class NewsScraper {
    @Autowired
    private  NewsCardRepo newsCardRepo;
    @Autowired
    private TagsRepo tegsRepo;
    @Autowired
    private HashTagsRepo hashTagsRepo;
    @Autowired
    private BreakingNewsRepo breakingNewsRepo;

 

     //  Run once when the server starts
    @PostConstruct
    @Scheduled(fixedRate = 30 * 60 * 1000) // Run every 30 minutes
    public void  getMainNews() {
       String url = "https://www.amarujala.com/";
       try {
        Document doc = Jsoup.connect(url).get();

        // Select all news blocks using their common class
        Elements newsBlocks = doc.select("section.__main_listing_content");

        for (Element newsBlock : newsBlocks) {
            // Extract Headline (Checking both h2 and h3)
            Element headlineElement = newsBlock.selectFirst("h2 a");
            if (headlineElement == null) {
                headlineElement = newsBlock.selectFirst("h3 a");
            }
            String headline = (headlineElement != null) ? headlineElement.text() : "N/A";

            // Extract Subheading (if available)
            Element subheadingElement = newsBlock.selectFirst(".summary a");
            String subheading = (subheadingElement != null) ? subheadingElement.text() : "N/A";

            // Extract Article Link
            String articleLink = (headlineElement != null) ? headlineElement.attr("href") : "N/A";

            // // Extract Image URL
            // Element imageElement = newsBlock.selectFirst(".image img");
            // String imageUrl = (imageElement != null) ? imageElement.attr("src") : "N/A";
            // Extract Image URL
            Element imageElement = newsBlock.selectFirst(".image img");
            String imageUrl = "N/A";

            if (imageElement != null) {
                imageUrl = imageElement.hasAttr("data-src") ? imageElement.attr("data-src") : imageElement.attr("src");
            }
            // Extract Tags
            Elements tagElements = newsBlock.select(".rel_tags a");
            List<Tags> tags = new ArrayList<>();
            List<HashTags> hashtags = new ArrayList<>();

            for (Element tag : tagElements) {
                String tagText = tag.text();
                Tags ts = new Tags();
                ts.setName(tagText);
                tags.add(ts);
                if (tagText.startsWith("#")) {
                    HashTags hs = new HashTags();
                    hs.setName(tagText);
                    hashtags.add(hs);
                }
            }
            if (subheading.length() <= 100) {
                subheading =  subheading;
            } else {
                subheading =  subheading.substring(0, 100);
            }

            // Extract Date
            Element dateElement = newsBlock.selectFirst(".date span");
            String date = (dateElement != null) ? dateElement.text() : "N/A";
                NewsCard ns = new NewsCard();
                ns.setHeadline(headline);
                ns.setSubheading(subheading);
                ns.setArticleLink(articleLink);
                ns.setImageUrl(imageUrl);
                ns.setTags(tags);
                ns.setHashtags(hashtags);
                ns.setDate(date);
                ns.setFetched(false);

                // Check for duplicate entry
                Optional<NewsCard> existingNews = newsCardRepo.findByHeadlineAndImageUrlAndArticleLink(
                    headline, imageUrl, articleLink);

                if (existingNews.isPresent()) {
                    System.out.println("Skipping duplicate news entry: " + headline);
                    continue; // Skip this entry and move to the next one
                }

                // Optional<NewsCard> existingNews = newsCardRepo.findByHeadlineAndImageUrlAndArticleLink(
                //     ns.getHeadline(), ns.getImageUrl(), ns.getArticleLink());

                //     if (existingNews.isPresent()) {
                //          throw new DuplicateNewsException("Duplicate entry! This news article already exists.");
                //   }
                newsCardRepo.save(ns);

            // Print extracted details for each news
            System.out.println("------------------------------------------------");
            // System.out.println("Headline: " + headline);
            // System.out.println("Subheading: " + subheading);
            // System.out.println("Article Link: " + articleLink);
            // System.out.println("Image URL: " + imageUrl);
            // System.out.println("Tags: " + tags);
            // System.out.println("Hashtags: " + hashtags);
            // System.out.println("Date: " + date);
        }

    } catch (IOException e) {
        System.out.println("Error fetching the webpage: " + e.getMessage());
    }
    }



    @Scheduled(fixedRate = 5 * 60 * 1000) // Run every 5 minutes
public List<BreakingNews> fetchBreakingNews() {
    String ajaxUrl = "https://www.amarujala.com/ajax/home-page-live-section";
    List<BreakingNews> breakingNewsList = new ArrayList<>();

    try {
        Connection.Response response = Jsoup.connect(ajaxUrl)
            .method(Connection.Method.GET)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
            .header("Referer", "https://www.amarujala.com/")
            .header("X-Requested-With", "XMLHttpRequest")
            .ignoreContentType(true)
            .execute();

        Document doc = Jsoup.parse(response.body());
        Elements newsElements = doc.select("div.__main_listing_content ul li");

        for (Element newsElement : newsElements) {
            BreakingNews news = new BreakingNews();
            Element linkElement = newsElement.selectFirst("a");

            if (linkElement != null) {
                String headline = linkElement.text();
                String newsUrl = "https://www.amarujala.com" + linkElement.attr("href");

                // Check if news already exists
                Optional<BreakingNews> existingNews = breakingNewsRepo.findByBreakingHeadlineAndBreakingUrl(headline, newsUrl);
                if (existingNews.isPresent()) {
                    continue; // Skip duplicate news
                }

                news.setBreakingHeadline(headline);
                news.setBreakingUrl(newsUrl);
            }

            Element timeElement = newsElement.selectFirst(".time");
            if (timeElement != null) {
                news.setBreakingTime(timeElement.text());
            }

            news.setLocalDateTime(LocalDateTime.now());
            breakingNewsList.add(news);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return breakingNewsRepo.saveAllAndFlush(breakingNewsList);
}


}
