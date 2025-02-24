package com.newsSummeriser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsSummeriser.model.NewsCard;
@Repository
public interface NewsCardRepo  extends JpaRepository <NewsCard , Long> {
     
}
