package com.newsSummeriser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.model.NewsCard;
import com.newsSummeriser.model.TrendingNews;
import com.newsSummeriser.service.ContentScraper;
import com.newsSummeriser.service.HomePageService;
import com.newsSummeriser.service.NewsScraper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/news") // Base path for your news-related endpoints
public class TempController {
    @Autowired
    NewsScraper ns;
    @Autowired
    ContentScraper cs ;
    


    @Autowired
    HomePageService homePageService;

    @GetMapping("/")
    public String getMethodName() {
        ns.getMainNews("https://www.amarujala.com/uttar-pradesh");
        
        //cs.categoryScraper("https://navbharattimes.indiatimes.com/");
		//cs.articleBodyScraper("https://navbharattimes.indiatimes.com/india/conflict-between-bjp-and-congress-escalates-over-usaid-funding-presenting-evidence-against-each-other/articleshow/118476730.cms");
        return "ok";

    }
    @GetMapping("/p")
    public String fechMethod() {
        return cs.fetchArticle("https://www.amarujala.com/world/khalistan-supporter-extremists-breach-eam-s-jaishankar-security-in-london-uk-know-their-base-and-history-2025-03-08?src=tlh&position=1");
        
    }
    



    
    
}
