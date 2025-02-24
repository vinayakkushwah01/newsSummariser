package com.newsSummeriser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.service.ContentScraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/news") // Base path for your news-related endpoints
public class TempController {

@Autowired
    ContentScraper cs;

    @GetMapping("/")
    public String getMethodName() {
        cs.categoryScraper("https://navbharattimes.indiatimes.com/viral/articlelist/82150262.cms");
		//cs.articleBodyScraper("https://navbharattimes.indiatimes.com/india/conflict-between-bjp-and-congress-escalates-over-usaid-funding-presenting-evidence-against-each-other/articleshow/118476730.cms");
        return "ok";
    }
    
}
