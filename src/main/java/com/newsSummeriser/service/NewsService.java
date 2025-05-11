package com.newsSummeriser.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.newsSummeriser.exception.ContentNotScarpedError;
import com.newsSummeriser.model.BreakingNews;
import com.newsSummeriser.model.Category;
import com.newsSummeriser.model.NewsDetails;
import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.repository.BreakingNewsRepo;
import com.newsSummeriser.repository.CategoryRepository;
import com.newsSummeriser.repository.NewsDetailsRepository;
import com.newsSummeriser.repository.NewsHeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;



@Service 
public class NewsService {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private NewsHeadlineRepository newsHeadlineRepo;

    @Autowired
    private BreakingNewsRepo breakingNewsRepo;

    @Autowired
    private SNewsScraper sNewsScraper;
    @Autowired
    private NewsDetailsRepository newsDetailsRepository;
    public List<Category> getNewsCategory(String url){
        String temp = "/"+url ;
        if(url.equals("home")){temp = "/";}
        return(categoryRepo.findByUrl(temp));
    }
    
    public List<NewsHeadline> getNewsHeadlines(String url, int pageNumber) throws Exception {
        try {
            List<Category> categories = getNewsCategory(url);
            if (categories.isEmpty()) {
                throw new Exception("Data not found or incorrect category");
            }
            Pageable pageable = PageRequest.of(pageNumber, 12);  
            return newsHeadlineRepo.findByCategoryAndFetchedTrueOrderByDateDesc(categories.get(0), pageable).getContent();
        } catch (Exception e) {
            throw new Exception("404 Category not found", e);
        }
    }
    public List<BreakingNews> fetchBreakingNews(){
       return  breakingNewsRepo.findTop6ByOrderByLocalDateTimeDesc();
    }
    public NewsDetails fetchBreakingNews(String url, Long id) {
       return sNewsScraper.fetchBreakingNews(url);
    }
    public NewsDetails getFullNewsArticle(Long id){
    
    return newsDetailsRepository.findByNewsHeadlineId(id);
    //return  sample;
    }

    
}
