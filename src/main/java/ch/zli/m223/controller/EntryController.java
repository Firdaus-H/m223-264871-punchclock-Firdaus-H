package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;
import ch.zli.m223.mapper.EntryMapper;

@Path("/entries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @Inject
    EntryMapper entryMapper;

    @GET
    @Operation(
        summary = "Index all Entries.",
        description = "Returns a list of all entries."
    )
    public List<EntryDto> index() {
        return entryService.findAll()
                .stream()
                .map(entryMapper::toDto)
                .toList();
    }

    @POST
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public EntryDto create(@NotNull @Valid EntryDto dto) {
        Entry entry = entryMapper.toEntity(dto);
        Entry createdEntry = entryService.createEntry(entry);
        return entryMapper.toDto(createdEntry);
    }

    @DELETE
    @Path("/delete/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes an entry by its ID.")
    public void delete(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
    }

    @PUT
    @Path("/update/{id}")
    @Operation(summary = "Updates an entry.", description = "Updates an entry by its ID.")
    public EntryDto update(@PathParam("id") Long id, @NotNull @Valid EntryDto dto) {
        Entry entry = entryMapper.toEntity(dto);
        Entry updatedEntry = entryService.updateEntry(id, entry);
        if (updatedEntry == null) {
            throw new NotFoundException("Entry with ID " + id + " not found.");
        }
        return entryMapper.toDto(updatedEntry);
    }

    @PATCH
    @Path("/{id}")
    @Operation(
        summary = "Partially updates an entry",
        description = "Partially updates an entry by its ID."
    )
    public EntryDto patch(@PathParam("id") Long id, @NotNull EntryDto dto) {

        Entry entry = entryMapper.toEntity(dto);
        Entry updatedEntry = entryService.patchEntry(id, entry);

        if (updatedEntry == null) {
            throw new NotFoundException("Entry with ID " + id + " not found.");
        }
        return entryMapper.toDto(updatedEntry);
    }

}
