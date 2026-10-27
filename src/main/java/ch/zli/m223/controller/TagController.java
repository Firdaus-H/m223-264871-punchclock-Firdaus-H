package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import ch.zli.m223.dto.TagDto;
import ch.zli.m223.model.Tag;
import ch.zli.m223.service.TagService;
import ch.zli.m223.mapper.TagMapper;

@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@org.eclipse.microprofile.openapi.annotations.tags.Tag(
    name = "Tags", 
    description = "Handling of tags"
)
public class TagController {

    @Inject
    TagService tagService;

    @Inject
    TagMapper tagMapper;

    // GET ALL
    @GET
    @Operation(
        summary = "Index all Tags.",
        description = "Returns a list of all tags."
    )
    public List<TagDto> index() {
        return tagService.findAll()
                .stream()
                .map(tagMapper::toDto)
                .toList();
    }

    // POST
    @POST
    @Operation(
        summary = "Creates a new tag.",
        description = "Creates a new tag and returns it."
    )
    public TagDto create(@NotNull @Valid TagDto dto) {

        Tag tag = tagMapper.toEntity(dto);

        Tag createdTag = tagService.createTag(tag);

        return tagMapper.toDto(createdTag);
    }

    // PUT
    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Updates a tag.",
        description = "Updates a tag by its ID."
    )
    public TagDto update(
            @PathParam("id") Long id,
            @NotNull @Valid TagDto dto) {

        Tag tag = tagMapper.toEntity(dto);

        Tag updatedTag = tagService.updateTag(id, tag);

        return tagMapper.toDto(updatedTag);
    }

    // PATCH
    @PATCH
    @Path("/{id}")
    @Operation(
        summary = "Partially updates a tag.",
        description = "Partially updates a tag by its ID."
    )
    public TagDto patch(
            @PathParam("id") Long id,
            @NotNull TagDto dto) {

        Tag tag = tagMapper.toEntity(dto);

        Tag patchedTag = tagService.patchTag(id, tag);

        return tagMapper.toDto(patchedTag);
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Deletes a tag.",
        description = "Deletes a tag by its ID."
    )
    public void delete(@PathParam("id") Long id) {
        tagService.deleteTag(id);
    }
}