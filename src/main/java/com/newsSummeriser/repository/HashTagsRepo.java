package com.newsSummeriser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsSummeriser.model.HashTags;

public interface HashTagsRepo extends JpaRepository<HashTags, Long> {
}
