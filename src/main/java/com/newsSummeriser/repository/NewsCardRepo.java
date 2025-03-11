package com.newsSummeriser.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsSummeriser.model.NewsCard;
@Repository
public interface NewsCardRepo  extends JpaRepository <NewsCard , Long> {
   // List<NewsCard> findByTopSectionOrderByDateDesc(String topSection);
   Optional<NewsCard> findByHeadlineAndImageUrlAndArticleLink(String headline, String imageUrl, String articleLink);
   List <NewsCard> findByFetchedFalse();

}  
