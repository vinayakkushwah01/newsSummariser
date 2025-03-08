package com.newsSummeriser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsSummeriser.model.Tags;

public interface TagsRepo  extends JpaRepository<Tags, Long> {
}
