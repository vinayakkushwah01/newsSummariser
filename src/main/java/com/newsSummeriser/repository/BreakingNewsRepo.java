package com.newsSummeriser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsSummeriser.model.BreakingNews;
@Repository
public interface BreakingNewsRepo extends JpaRepository<BreakingNews, Long>  {
    Optional<BreakingNews> findByBreakingHeadlineAndBreakingUrl(String breakingHeadline, String breakingUrl);
    boolean existsByBreakingHeadlineAndBreakingUrl(String breakingHeadline, String breakingUrl);
}
