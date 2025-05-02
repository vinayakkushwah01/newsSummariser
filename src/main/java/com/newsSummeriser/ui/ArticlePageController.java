package com.newsSummeriser.ui;

import com.newsSummeriser.model.NewsDetails;
import com.newsSummeriser.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ArticlePageController {

    @Autowired
    private NewsService newsService;

    @GetMapping("article/{id}")
    public String showFullArticle(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        System.out.println(id);
        return "article"; // maps to templates/article.html
    }

    @GetMapping("breakingnews")
    public String breakingNewsPage(@RequestParam("id") Long id,
            @RequestParam("url") String url,
            Model model) {
        model.addAttribute("id", id);
        model.addAttribute("url", url);
        return "breakingnews"; // Resolves to breakingnews.html
    }
}
