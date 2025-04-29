package com.newsSummeriser.ui;

import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/category")
public class CategoryPageController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/{category}")
    public String showCategoryPage(@PathVariable String category,
                                   @RequestParam(defaultValue = "0") int page,
                                   Model model) throws Exception {
        List<NewsHeadline> headlines = newsService.getNewsHeadlines(category, page);
        model.addAttribute("headlines", headlines);
        model.addAttribute("categoryName", category);
        return "category"; // maps to templates/category.html
    }
    @GetMapping({"", "/"})
    public String showHomePage(Model model) {
        // Optionally load some breaking news if you want
        return "index"; // maps to templates/home.html
    }
}
