package com.newsSummeriser.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.newsSummeriser.model.NewsHeadline;
import com.newsSummeriser.model.Category;

@Repository
public interface NewsHeadlineRepository extends JpaRepository<NewsHeadline, Long> {
    List<NewsHeadline> findByCategory(Category category);
    Optional<NewsHeadline> findByHeadlineAndImageUrlAndArticleLink(String headline, String imageUrl, String articleLink);
    List <NewsHeadline> findByFetchedFalse();
    boolean existsByHeadlineAndImageUrlAndArticleLink(String headline, String imageUrl, String articleLink);
    List<NewsHeadline> findByCategoryAndFetchedTrueOrderByDateDesc(Category category);

}
