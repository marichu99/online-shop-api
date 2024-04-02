package com.product.order.service;

import java.util.List;
import java.util.Optional;

import com.product.order.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category createCategory(Category category);
    Optional<Category> updateCategory(Long id, Category updatedCategory);
    boolean deleteCategory(Long id);
}
