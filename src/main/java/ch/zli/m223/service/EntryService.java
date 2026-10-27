package ch.zli.m223.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Entry;
import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.model.Category;
import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.TagRepository;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntryRepository entryRepository;

    @Inject 
    private CategoryRepository categoryRepository;

    @Inject
    private TagRepository tagRepository;

    @Transactional
    public Entry createEntry(Entry entry, EntryDto dto) {
        assignRelationships(entry, dto);
        entryRepository.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        return entryRepository.listAll();
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id);
        if (entry == null) {
            throw new NotFoundException("Entry with ID " + id + " not found.");
        }
        entryRepository.delete(entry);
    }

    public Entry findById(Long id) {
        return entryRepository.findById(id);
    }

    @Transactional 
    public Entry updateEntry(Long id, Entry updatedEntry, EntryDto dto) {
        Entry existingEntry = entryRepository.findById(id);
        if (existingEntry == null) {
            throw new NotFoundException("Entry with ID " + id + " not found.");
        }

        assignRelationships(updatedEntry, dto);

        existingEntry.setCheckIn(updatedEntry.getCheckIn());
        existingEntry.setCheckOut(updatedEntry.getCheckOut());

        existingEntry.setCategory(updatedEntry.getCategory());
        existingEntry.setTags(updatedEntry.getTags());

        return existingEntry;
    }

    @Transactional
    public Entry patchEntry(Long id, Entry updatedEntry) {
        Entry existingEntry = entryRepository.findById(id);
        if (existingEntry == null) {
            throw new NotFoundException("Entry with ID " + id + " not found.");
        }

        // Determine the final values without modifying the database entity
        LocalDateTime checkIn = updatedEntry.getCheckIn() != null
                ? updatedEntry.getCheckIn()
                : existingEntry.getCheckIn();

        LocalDateTime checkOut = updatedEntry.getCheckOut() != null
                ? updatedEntry.getCheckOut()
                : existingEntry.getCheckOut();

        if (checkIn == null || checkOut == null) {
            throw new BadRequestException(
                "Check-in and check-out are required."
            );
        }

        if (!checkIn.isBefore(checkOut)) {
            throw new BadRequestException(
                "Check-in must be before check-out."
            );
        }

        existingEntry.setCheckIn(checkIn);
        existingEntry.setCheckOut(checkOut);

        return existingEntry;
    }

    private void assignRelationships(Entry entry, EntryDto dto) {

    // Resolve Category
    if (dto.getCategoryId() != null) {

        Category category = categoryRepository.findById(
            dto.getCategoryId()
        );

        if (category == null) {
            throw new NotFoundException(
                "Category with ID " + dto.getCategoryId() + " not found."
            );
        }

        entry.setCategory(category);

    } else {
        entry.setCategory(null);
    }

    // Resolve Tags
    List<Tag> tags = new ArrayList<>();

    if (dto.getTagIds() != null) {

        for (Long tagId : dto.getTagIds()) {

            Tag tag = tagRepository.findById(tagId);

            if (tag == null) {
                throw new NotFoundException(
                    "Tag with ID " + tagId + " not found."
                );
            }

            tags.add(tag);
        }
    }

    entry.setTags(tags);
}
}
