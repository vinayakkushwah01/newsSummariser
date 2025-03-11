package com.newsSummeriser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.model.NewsCard;
import com.newsSummeriser.model.BreakingNews;
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
    // @Autowired
    // NewsScraper ns;
    // @Autowired
    // ContentScraper cs ;
    


    @Autowired
    HomePageService homePageService;

    // @GetMapping("/")
    // public  List<BreakingNews> getMethodName() {
    //     // //ns.getMainNews("https://www.amarujala.com/");
        
    //     // return ns.fetchBreakingNews();

    // }
    // @GetMapping("/p")
    // public String fechMethod() {
    //     // return cs.mapArticles();
        
    // }
    



    
    
}
