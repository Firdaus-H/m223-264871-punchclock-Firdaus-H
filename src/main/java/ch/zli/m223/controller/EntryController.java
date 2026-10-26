package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<Entry> index() {
        return entryService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public Entry create(Entry entry) {
       return entryService.createEntry(entry);
    }

    @DELETE
    @Path("/delete/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes an entry by its ID.")
    public void delete(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
    }

    @PATCH
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry.", description = "Updates an entry by its ID.")
    public Entry update(@PathParam("id") Long id, Entry entry) {
        Entry existingEntry = entryService.findById(id);
        if (existingEntry != null) {
            if (entry.getCheckIn() != null) {
                existingEntry.setCheckIn(entry.getCheckIn());
            }
            if (entry.getCheckOut() != null) {
                existingEntry.setCheckOut(entry.getCheckOut());
            }
            return entryService.updateEntry(existingEntry);
        } else {
            throw new NotFoundException("Entry with ID " + id + " not found.");
        }
    }
}
