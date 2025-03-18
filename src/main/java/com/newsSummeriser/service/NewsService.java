package com.newsSummeriser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsSummeriser.model.Category;
import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.repository.BreakingNewsRepo;
import com.newsSummeriser.repository.CategoryRepository;
import com.newsSummeriser.repository.NewsHeadlineRepository;


@Service 
public class NewsService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private NewsHeadlineRepository newsHeadlineRepo;


    public List<Category> getNewsCategory(String url){
       
        String temp = "/"+url ;
        if(url.equals("home")){temp = "/";}
        return(categoryRepo.findByUrl(temp));
    }
    
    public List<NewsHeadline> getNewsHeadlines(String url) throws Exception {
        try {
            List<Category> categories = getNewsCategory(url);
            
            if (categories.isEmpty()) {
                throw new Exception("Data not found or incorrect category");
            }
            
            return newsHeadlineRepo.findByCategoryAndFetchedTrueOrderByDateDesc(categories.get(0));
        } catch (Exception e) {
            throw new Exception("An unexpected error occurred", e);
        }
    }
    
    
    
}
