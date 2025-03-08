// package com.newsSummeriser.service;
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.newsSummeriser.model.NewsCard;
// import com.newsSummeriser.repository.NewsCardRepo;

// import org.jsoup.nodes.Node;

// import java.io.IOException;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// @Service
// public class ContentScraper {
//     @Autowired
//     private NewsCardRepo newsCardRepo;

    

// // public static void categoryScraper (String targetUrl) {
      
// //        String userAgent = "Mozilla/5.0";
// //        try {
// //            Document doc = Jsoup.connect(targetUrl)
// //                    .userAgent(userAgent)
// //                    .get();

// //            // Select content blocks with either "row" or "top_section" class
// //            Elements contentBlocks = doc.select("div.row, div.top_section");

// //            int rowIndex = 1;
// //            String topSection =null;
// //            for (Element block : contentBlocks) {
               

// //                if (block.classNames().contains("top_section")) {
// //                    System.out.println("----- Top Section -----");
// //                    topSection = (block.text().trim());
// //                    System.out.println(block.text().trim());
// //                } else if (block.classNames().contains("row")) {
// //                    System.out.println("----- Row " + rowIndex + " -----");
// //                    rowIndex++;

// //                    Elements newsCards = block.select("li[class*=news-card]");

// //                    if (newsCards.isEmpty()) {
// //                        System.out.println("No news cards found in this row block.");
// //                    } else {
// //                        for (Element card : newsCards) {
// //                            // Extract headline text from h2 or h3 elements
// //                            Element headlineTag = card.selectFirst("h2, h3");
// //                            String headline = (headlineTag != null) ? headlineTag.text().trim() : "No headline";

// //                            // Extract URL from the first <a> tag inside the headline tag (if available)
// //                            String url = "No URL";
// //                            if (headlineTag != null) {
// //                                Element urlTag = headlineTag.selectFirst("a[href]");
// //                                if (urlTag != null) {
// //                                    url = urlTag.attr("href");
// //                                }
// //                            }

// //                            // Extract image URL from an <img> tag if available
// //                            String imgUrl = "No image";
// //                            Element imgTag = card.selectFirst("img[src]");
// //                            if (imgTag != null) {
// //                                imgUrl = imgTag.attr("src");
// //                            }

// //                            // Output the extracted data
// //                            NewsCard nc = new NewsCard();
// //                            nc.setTopSection(topSection);
// //                            nc.setImageUrl(imgUrl);
// //                            nc.setHeadline(headline);
// //                            nc.setUrl(url);
// //                            System.out.println(nc.toString());

// //                            // System.out.println("Headline: " + headline);
// //                            // System.out.println("URL: " + url);
// //                            // System.out.println("Image URL: " + imgUrl);
// //                        }
// //                    }
// //                    System.out.println("----------------------------------------");
// //                }
// //            }
// //        } catch (IOException e) {
// //            System.out.println("Failed to fetch the page: " + e.getMessage());
// //        }
// //    } 

// //// re open from her 
// /// 
// /// 
// /// 
// /// 
// /// 
// /// 
// public  void categoryScraper(String targetUrl) {
//     String userAgent = "Mozilla/5.0";
//     try {
//         Document doc = Jsoup.connect(targetUrl)
//                 .userAgent(userAgent)
//                 .get();

//         // Extract all top_section and row elements in order of appearance
//         Elements contentBlocks = doc.select("div.top_section, div.row");

//         String currentTopSection = null;
//         int rowIndex = 1;

//         for (Element block : contentBlocks) {
//             if (block.classNames().contains("top_section")) {
//                 currentTopSection = block.text().trim();
//                 System.out.println("----- New Top Section -----");
//                 System.out.println(currentTopSection);
//             } 
//             else if (block.classNames().contains("row")) {
//                 System.out.println("\n----- Row " + rowIndex + " -----");
//                 rowIndex++;

//                 Elements newsCards = block.select("li[class*=news-card]");
//                 if (newsCards.isEmpty()) {
//                     System.out.println("No news cards found in this row block.");
//                 } else {
//                     for (Element card : newsCards) {
//                         // Extract headline text from h2 or h3 elements
//                         Element headlineTag = card.selectFirst("h2, h3");
//                         String headline = (headlineTag != null) ? headlineTag.text().trim() : "No headline";

//                         // Extract URL from the first <a> tag inside the headline tag (if available)
//                         String url = "No URL";
//                         if (headlineTag != null) {
//                             Element urlTag = headlineTag.selectFirst("a[href]");
//                             if (urlTag != null) {
//                                 url = urlTag.attr("href");
//                             }
//                         }

//                         // Extract image URL from an <img> tag if available
//                         String imgUrl = "No image";
//                         Element imgTag = card.selectFirst("img[src]");
//                         if (imgTag != null) {
//                             imgUrl = imgTag.attr("src");
//                         }
//                         else{
//                             continue;

//                         }

//                         // Store extracted data in NewsCard object
//                         NewsCard nc = new NewsCard();
//                         nc.setTopSection(currentTopSection); // Assign correct Top Section
//                         nc.setHeadline(headline);
//                         nc.setUrl(url);
//                         nc.setImageUrl(imgUrl);
//                         nc.setDate( LocalDateTime.now());
//                         newsCardRepo.save(nc);  

//                         System.out.println(nc.toString());
//                     }
//                 }
//                 System.out.println("----------------------------------------");
//             }
//         }
//     } catch (IOException e) {
//         System.out.println("Failed to fetch the page: " + e.getMessage());
//     }
// }

//     public static void articleBodyScraper(String articleUrl) {
//         try {
//             // Fetch the article page
//             Document doc = Jsoup.connect(articleUrl)
//                     .userAgent("Mozilla/5.0")
//                     .get();

//             // Extract main heading
//             Element mainHeadingTag = doc.selectFirst("h1");
//             String mainHeading = (mainHeadingTag != null) ? mainHeadingTag.text().trim() : "No Heading Found";

//             // Extract article body using itemprop="articleBody"
//             Element articleBody = doc.selectFirst("div[itemprop=articleBody]");

//             // List to hold subheadings with following content
//             List<SubheadingContent> subheadingsData = new ArrayList<>();
//             String directText = "";

//             if (articleBody != null) {
//                 // Extract all text from the article body for a complete view
//                 directText = articleBody.text().trim();

//                 // Find all relevant subheading tags (h1, h3, strong) within articleBody
//                 Elements subheadingTags = articleBody.select("h1, h3, strong");

//                 for (Element tag : subheadingTags) {
//                     String subheadingText = tag.text().trim();
//                     StringBuilder followingText = new StringBuilder();

//                     // Iterate through siblings after the subheading tag until we hit another subheading tag
//                     for (Node sibling = tag.nextSibling(); sibling != null; sibling = sibling.nextSibling()) {
//                         // If sibling is an Element and is one of the subheading tags, break out of loop
//                         if (sibling instanceof Element) {
//                             Element el = (Element) sibling;
//                             if (el.tagName().matches("h1|h2|h3|strong"))
//                                 break;
//                             followingText.append(el.text().trim()).append(" ");
//                         } else {
//                             // For text nodes, append the text content directly
//                             String text = sibling.toString().trim();
//                             if (!text.isEmpty()) {
//                                 followingText.append(text).append(" ");
//                             }
//                         }
//                     }
//                     subheadingsData.add(new SubheadingContent(subheadingText, followingText.toString().trim()));
//                 }
//             }

//             // Output the extracted data
//             System.out.println("----- Main Heading -----");
//             System.out.println(mainHeading);

//             System.out.println("\n----- Main Article Content -----");
//             int index = 1;
//             for (SubheadingContent item : subheadingsData) {
//                 System.out.println("\nSubheading " + index + ": " + item.getSubheading());
//                 System.out.println("Content following this subheading:");
//                 System.out.println(item.getContent());
//                 index++;
//             }

//             System.out.println("\n----- Direct Text from Article Body -----");
//             System.out.println(directText);

//         } catch (IOException e) {
//             System.out.println("Failed to fetch the article. Error: " + e.getMessage());
//         }
//     }
//     private static class SubheadingContent {
//         private final String subheading;
//         private final String content;

//         public SubheadingContent(String subheading, String content) {
//             this.subheading = subheading;
//             this.content = content;
//         }

//         public String getSubheading() {
//             return subheading;
//         }

//         public String getContent() {
//             return content;
//         }
//     }



// }
