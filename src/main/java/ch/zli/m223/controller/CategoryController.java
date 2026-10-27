package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.CategoryDto;
import ch.zli.m223.model.Category;
import ch.zli.m223.service.CategoryService;
import ch.zli.m223.mapper.CategoryMapper;

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Categories", description = "Handling of categories")
public class CategoryController {
    
    @Inject
    CategoryService categoryService;

    @Inject 
    CategoryMapper categoryMapper;

    @GET
    @Operation(
        summary = "Index all Categories.",
        description = "Returns a list of all categories."
    )
    public List<CategoryDto> index() {
        return categoryService.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @POST
    @Operation(summary = "Creates a new category.", description = "Creates a new category and returns the newly added category.")
    public CategoryDto create(@NotNull @Valid CategoryDto dto) {
        Category category = categoryMapper.toEntity(dto);
        Category createdCategory = categoryService.createCategory(category);
        return categoryMapper.toDto(createdCategory);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a category by ID.", description = "Deletes a category by its ID. If the category does not exist, a NotFoundException is thrown.")
    public void delete(@PathParam("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Updates a category by ID.", description = "Updates a category by its ID. If the category does not exist, a NotFoundException is thrown.")
    public CategoryDto update(@PathParam("id") Long id, @NotNull @Valid CategoryDto dto) {
        Category category = categoryMapper.toEntity(dto);
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory == null) {
            throw new NotFoundException("Category with ID " + id + " not found.");
        }
        return categoryMapper.toDto(updatedCategory);
    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "Patches a category by ID.", description = "Patches a category by its ID. If the category does not exist, a NotFoundException is thrown.")
    public CategoryDto patch(@PathParam("id") Long id, @NotNull CategoryDto dto) {

        Category category = categoryMapper.toEntity(dto);
        Category patchedCategory = categoryService.patchCategory(id, category);
        
        if (patchedCategory == null) {
            throw new NotFoundException("Category with ID " + id + " not found.");
        }
        return categoryMapper.toDto(patchedCategory);
    }

}
