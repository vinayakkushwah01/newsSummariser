package com.newsSummeriser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.newsSummeriser.model.NewsDetails;

@Repository
public interface NewsDetailsRepository extends JpaRepository<NewsDetails, Long> {
    NewsDetails findByNewsHeadlineId(Long newsHeadlineId);
}
