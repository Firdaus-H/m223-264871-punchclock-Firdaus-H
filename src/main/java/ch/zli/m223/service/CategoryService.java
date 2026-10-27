package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import ch.zli.m223.model.Category;
import ch.zli.m223.repository.CategoryRepository;

@ApplicationScoped 
public class CategoryService {
    @Inject CategoryRepository categoryRepository;

    @Transactional 
    public Category createCategory(Category category) {
        categoryRepository.persist(category);
        return category;
    }

    public List<Category> findAll() {
        return categoryRepository.listAll();
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new NotFoundException("Category with ID " + id + " not found.");
        }
        categoryRepository.delete(category);
    }
    
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(id);
        if (existingCategory != null) {
            existingCategory.setTitle(updatedCategory.getTitle());
        }
        return existingCategory;
    }

    @Transactional
    public Category patchCategory(Long id, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(id);
        if (existingCategory == null) {
            return null;
        }
        if (updatedCategory.getTitle() != null) {
            if (updatedCategory.getTitle().isEmpty()) {
                throw new BadRequestException("Title must not be blank.");
            }
            existingCategory.setTitle(updatedCategory.getTitle());
        }
        return existingCategory;
    }
}