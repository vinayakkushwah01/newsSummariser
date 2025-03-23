package com.newsSummeriser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.model.BreakingNews;
import com.newsSummeriser.service.BreakingNewsService;

@RestController
@RequestMapping("api/breaking-news")
public class BreakingNewsController {


    @Autowired
    private BreakingNewsService breakingNewsService;
    @GetMapping
    public ResponseEntity<?> getAllBreakingNews()
    {
        try{
            List<BreakingNews> newsList = breakingNewsService.getAllBreakingNews();
            if(newsList.isEmpty())
            {
         return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No breaking News available");
            }

            return ResponseEntity.ok(newsList);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching breaking news");
        }
    }

}
