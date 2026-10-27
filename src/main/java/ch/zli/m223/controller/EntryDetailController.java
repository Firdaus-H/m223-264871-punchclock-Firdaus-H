package ch.zli.m223.controller;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.dto.EntryDetailDto;
import ch.zli.m223.mapper.EntryMapper;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entry-details")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Entry Details", description = "Detailed representation of entries")
public class EntryDetailController {

    @Inject
    EntryService entryService;

    @Inject
    EntryMapper entryMapper;

    @GET
    @Transactional 
    @Operation(
        summary = "Returns all entry details.",
        description = "Returns all entries with their Category and Tags."
    )
    public List<EntryDetailDto> index() {

        return entryService.findAll()
                .stream()
                .map(entryMapper::toDetailDto)
                .toList();
    }

    @GET
    @Transactional 
    @Path("/{id}")
    @Operation(
        summary = "Returns entry details by ID.",
        description = "Returns an entry with its Category and Tags."
    )
    public EntryDetailDto show(@PathParam("id") Long id) {

        Entry entry = entryService.findById(id);

        if (entry == null) {
            throw new NotFoundException(
                "Entry with ID " + id + " not found."
            );
        }

        return entryMapper.toDetailDto(entry);
    }
}