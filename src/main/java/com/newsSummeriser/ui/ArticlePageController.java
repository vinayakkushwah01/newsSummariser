package com.newsSummeriser.ui;

import com.newsSummeriser.model.NewsDetails;
import com.newsSummeriser.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/article")
public class ArticlePageController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/{id}")
    public String showFullArticle(@PathVariable Long id, Model model) {
        Optional<NewsDetails> newsDetails = newsService.getFullNewsArticle(id);
        if (newsDetails.isPresent()) {
            model.addAttribute("article", newsDetails.get());
        } else {
            model.addAttribute("error", "Article not found.");
        }
        return "article"; // maps to templates/article.html
    }
}
