package ch.zli.m223.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.m223.dto.TimeSummary;
import ch.zli.m223.model.Entry;

public class TimeSummaryServiceTest {

    private TimeSummaryService timeSummaryService;

    @BeforeEach
    void setUp() {
        timeSummaryService = new TimeSummaryService();
    }

    private Entry createEntry(
            LocalDateTime checkIn,
            LocalDateTime checkOut) {

        Entry entry = new Entry();

        entry.setCheckIn(checkIn);
        entry.setCheckOut(checkOut);

        return entry;
    }

    // TEST 1: Empty list
    @Test
    void testEmptyList() {

        List<TimeSummary> result = timeSummaryService.calculateSummaryPerDay(List.of());

        assertTrue(result.isEmpty());
    }

    // TEST 2: Single entry
    @Test
    void testSingleEntry() {

        Entry entry = createEntry(
                LocalDateTime.of(2024, 8, 20, 8, 0),
                LocalDateTime.of(2024, 8, 20, 16, 30));

        List<TimeSummary> result = timeSummaryService.calculateSummaryPerDay(List.of(entry));

        assertEquals(1, result.size());

        assertEquals(
                LocalDate.of(2024, 8, 20),
                result.get(0).getDate());

        assertEquals(
                Duration.ofHours(8).plusMinutes(30),
                result.get(0).getDuration());
    }

    // TEST 3: Multiple entries on the same day
    @Test
    void testMultipleEntriesSameDay() {

        Entry entry1 = createEntry(
                LocalDateTime.of(2024, 8, 20, 8, 0),
                LocalDateTime.of(2024, 8, 20, 12, 0));

        Entry entry2 = createEntry(
                LocalDateTime.of(2024, 8, 20, 13, 0),
                LocalDateTime.of(2024, 8, 20, 17, 30));

        List<TimeSummary> result = timeSummaryService.calculateSummaryPerDay(
                List.of(entry1, entry2));

        assertEquals(1, result.size());

        assertEquals(
                Duration.ofHours(8).plusMinutes(30),
                result.get(0).getDuration());
    }

    // TEST 4: Entries on different days
    @Test
    void testDifferentDays() {

        Entry entry1 = createEntry(
                LocalDateTime.of(2024, 8, 20, 8, 0),
                LocalDateTime.of(2024, 8, 20, 12, 0));

        Entry entry2 = createEntry(
                LocalDateTime.of(2024, 8, 21, 9, 0),
                LocalDateTime.of(2024, 8, 21, 17, 0));

        List<TimeSummary> result = timeSummaryService.calculateSummaryPerDay(
                List.of(entry1, entry2));

        assertEquals(2, result.size());

        assertEquals(
                LocalDate.of(2024, 8, 20),
                result.get(0).getDate());

        assertEquals(
                Duration.ofHours(4),
                result.get(0).getDuration());

        assertEquals(
                LocalDate.of(2024, 8, 21),
                result.get(1).getDate());

        assertEquals(
                Duration.ofHours(8),
                result.get(1).getDuration());
    }

    // TEST 5: Results are sorted by date
    @Test
    void testSortedByDate() {

        Entry entry1 = createEntry(
                LocalDateTime.of(2024, 8, 22, 8, 0),
                LocalDateTime.of(2024, 8, 22, 10, 0));

        Entry entry2 = createEntry(
                LocalDateTime.of(2024, 8, 20, 8, 0),
                LocalDateTime.of(2024, 8, 20, 12, 0));

        List<TimeSummary> result = timeSummaryService.calculateSummaryPerDay(
                List.of(entry1, entry2));

        assertEquals(2, result.size());

        assertEquals(
                LocalDate.of(2024, 8, 20),
                result.get(0).getDate());

        assertEquals(
                LocalDate.of(2024, 8, 22),
                result.get(1).getDate());
    }
}