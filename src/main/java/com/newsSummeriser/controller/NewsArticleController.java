package com.newsSummeriser.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.model.NewsDetails;
import com.newsSummeriser.service.NewsService;
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api") 
public class NewsArticleController {
   @Autowired
    private NewsService ns ;
    @GetMapping("/article/{id}")
  public NewsDetails getNewsArticle(@PathVariable  Long  id ) {
    return ns.getFullNewsArticle(id);
  }
}
