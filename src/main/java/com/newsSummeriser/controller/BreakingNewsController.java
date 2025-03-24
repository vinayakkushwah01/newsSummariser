package com.newsSummeriser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.model.BreakingNews;
import com.newsSummeriser.model.NewsDetails;

import com.newsSummeriser.service.NewsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api") 
public class BreakingNewsController {
    // @Autowired
    // private ContentScraper cs ;
    @Autowired
    private NewsService ns;

  @GetMapping("/breakingnews/{id}")
  public NewsDetails getBreakingNewsArticle(@PathVariable  Long  id , @RequestParam String url) {
    return ns.fetchBreakingNews(url, id);
  }
  @GetMapping("/breakingnews")
  public List<BreakingNews> getBreakingNews() {
      return ns.fetchBreakingNews();
  }

 
}
