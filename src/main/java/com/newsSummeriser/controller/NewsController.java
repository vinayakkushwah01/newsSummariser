package com.newsSummeriser.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.model.Category;
import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService ;

    @GetMapping("/{category}")
    public ResponseEntity<?> getNewsByCategory(@PathVariable String category) {
        try {
            List<NewsHeadline> headlines = newsService.getNewsHeadlines(category);
            return ResponseEntity.ok(headlines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error ", e.getMessage()));
        }
    }

    //fetchBreakingNews
        

}

