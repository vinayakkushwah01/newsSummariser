package com.newsSummeriser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsSummeriser.model.NewsArticle;
@Repository
public interface NewsArticleRepo extends JpaRepository< NewsArticle, Long>  {
    
}
