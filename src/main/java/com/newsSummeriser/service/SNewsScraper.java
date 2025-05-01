package com.newsSummeriser.service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsSummeriser.model.NewsDetails;
@Service 
public class SNewsScraper {

    public NewsDetails fetchBreakingNews(String url) {
        NewsDetails newsDetails = new NewsDetails();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements scriptTags = doc.select("script[type=application/ld+json]");

            ObjectMapper objectMapper = new ObjectMapper();

            for (Element scriptTag : scriptTags) {
                String jsonData = scriptTag.html();

                JsonNode jsonNode = objectMapper.readTree(jsonData);
                if (jsonNode.has("@type") && jsonNode.get("@type").asText().equals("NewsArticle")) {

                    String headline = jsonNode.path("headline").asText();
                    String articleBody = jsonNode.path("articleBody").asText();
                    String imageUrl = "";
                    JsonNode imageNode = jsonNode.path("image");
                    if (imageNode.isTextual()) {
                        imageUrl = imageNode.asText();
                    } else if (imageNode.isObject()) {
                        imageUrl = imageNode.path("url").asText();
                    }
                    newsDetails.setHeadline(headline);
                    newsDetails.setDetailedNews(articleBody);
                    newsDetails.setImageUrl(imageUrl);
                    break; 
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to scrape news from url: " + url);
        }

        return newsDetails;
    }
}
