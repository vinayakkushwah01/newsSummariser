package com.newsSummeriser.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.newsSummeriser.model.Category;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String categoryName);
    List<Category> findByUrl(String url);
    
}
