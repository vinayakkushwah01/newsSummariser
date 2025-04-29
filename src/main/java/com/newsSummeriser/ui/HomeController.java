package com.newsSummeriser.ui;

import com.newsSummeriser.model.BreakingNews;
import com.newsSummeriser.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;
    @GetMapping("/api/news/")
    public String showIndexPage(Model model) {
        return "index";  // This will map to templates/index.html
    }
    @GetMapping("/")
    public String showHomePage(Model model) {
        List<BreakingNews> breakingNews = newsService.fetchBreakingNews();
        model.addAttribute("breakingNews", breakingNews);
        return "index"; // maps to templates/index.html
    }
    
}
