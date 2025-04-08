package com.newsSummeriser.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface NewsHeadlineRepository extends JpaRepository<NewsHeadline, Long> {
    List<NewsHeadline> findByCategory(Category category);
    Optional<NewsHeadline> findByHeadlineAndImageUrlAndArticleLink(String headline, String imageUrl, String articleLink);
    List <NewsHeadline> findByFetchedFalse();
    boolean existsByHeadlineAndImageUrlAndArticleLink(String headline, String imageUrl, String articleLink);
    // List<NewsHeadline> findByCategoryAndFetchedTrueOrderByDateDesc(Category category);
    // @Query("SELECT n FROM NewsHeadline n WHERE n.category = :category AND n.fetched = true ORDER BY n.date DESC")
    // List<NewsHeadline> findTop100ByCategoryAndFetchedTrueOrderByDateDesc(@Param("category") Category category);
    @Query("SELECT n FROM NewsHeadline n WHERE n.category = :category AND n.fetched = true ORDER BY n.date DESC")
    Page<NewsHeadline> findByCategoryAndFetchedTrueOrderByDateDesc(@Param("category") Category category, Pageable pageable);
    
}
