package ch.zli.m223.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import ch.zli.m223.model.Entry;
import ch.zli.m223.repository.EntryRepository;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntryRepository entryRepository;

    @Transactional
    public Entry createEntry(Entry entry) {
        entryRepository.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        return entryRepository.listAll();
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id);
        if (entry != null) {
            entryRepository.delete(entry);
        }
    }

    public Entry findById(Long id) {
        return entryRepository.findById(id);
    }

    @Transactional 
    public Entry updateEntry(Long id, Entry updatedEntry) {
        Entry existingEntry = entryRepository.findById(id);
        if (existingEntry != null) {
            existingEntry.setCheckIn(updatedEntry.getCheckIn());
            existingEntry.setCheckOut(updatedEntry.getCheckOut());
        }
        return existingEntry;
    }

    @Transactional
    public Entry patchEntry(Long id, Entry updatedEntry) {
        Entry existingEntry = entryRepository.findById(id);
        if (existingEntry == null) {
            return null;
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
}
