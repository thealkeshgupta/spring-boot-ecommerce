package com.ecommerce.app_server.repository;

import com.ecommerce.app_server.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName( String categoryName);
}