package com.newsSummeriser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsSummeriser.exception.DuplicateNewsException;
import com.newsSummeriser.model.HashTags;
import com.newsSummeriser.model.NewsCard;
import com.newsSummeriser.model.Tags;
import com.newsSummeriser.model.TrendingNews;
import com.newsSummeriser.repository.HashTagsRepo;
import com.newsSummeriser.repository.TagsRepo;
import com.newsSummeriser.repository.NewsCardRepo;

import java.io.IOException;
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
 

    
    public void  getMainNews(String url) {
       // String url = "https://www.amarujala.com/";
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



                Optional<NewsCard> existingNews = newsCardRepo.findByHeadlineAndImageUrlAndArticleLink(
                    ns.getHeadline(), ns.getImageUrl(), ns.getArticleLink());

                    if (existingNews.isPresent()) {
                         throw new DuplicateNewsException("Duplicate entry! This news article already exists.");
                    }
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





}
