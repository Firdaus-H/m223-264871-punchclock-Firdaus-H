package ch.zli.m223.service;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;

import ch.zli.m223.repository.CategoryRepository;
import ch.zli.m223.repository.EntryRepository;
import ch.zli.m223.repository.TagRepository;

import io.quarkus.runtime.StartupEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ExampleDataService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    TagRepository tagRepository;

    @Inject
    EntryRepository entryRepository;

    @ConfigProperty(name = "punchclock.example-data.enabled", defaultValue = "false")
    boolean exampleDataEnabled;

    void onStart(@Observes StartupEvent event) {

        if (!exampleDataEnabled) {
            return;
        }

        createExampleData();
    }

    @Transactional
    public void createExampleData() {

        if (categoryRepository.count() > 0 ||
            tagRepository.count() > 0 ||
            entryRepository.count() > 0) {

            return;
        }

        Category school = new Category();
        school.setTitle("School");

        Category work = new Category();
        work.setTitle("Work");

        Category personal = new Category();
        personal.setTitle("Personal");

        categoryRepository.persist(school);
        categoryRepository.persist(work);
        categoryRepository.persist(personal);

        Tag java = new Tag();
        java.setTitle("Java");

        Tag quarkus = new Tag();
        quarkus.setTitle("Quarkus");

        Tag database = new Tag();
        database.setTitle("Database");

        Tag meeting = new Tag();
        meeting.setTitle("Meeting");

        tagRepository.persist(java);
        tagRepository.persist(quarkus);
        tagRepository.persist(database);
        tagRepository.persist(meeting);

        Entry entry1 = new Entry();
        entry1.setCheckIn(LocalDateTime.of(2026, 10, 20, 8, 0));
        entry1.setCheckOut(LocalDateTime.of(2026, 10, 20, 12, 0));
        entry1.setCategory(school);
        entry1.setTags(List.of(java, quarkus));

        Entry entry2 = new Entry();
        entry2.setCheckIn(LocalDateTime.of(2026, 10, 21, 9, 0));
        entry2.setCheckOut(LocalDateTime.of(2026, 10, 21, 17, 0));
        entry2.setCategory(work);
        entry2.setTags(List.of(database, meeting));

        Entry entry3 = new Entry();
        entry3.setCheckIn(LocalDateTime.of(2026, 10, 22, 13, 0));
        entry3.setCheckOut(LocalDateTime.of(2026, 10, 22, 16, 0));
        entry3.setCategory(school);
        entry3.setTags(List.of(java, database));

        Entry entry4 = new Entry();
        entry4.setCheckIn(LocalDateTime.of(2026, 10, 23, 10, 0));
        entry4.setCheckOut(LocalDateTime.of(2026, 10, 23, 12, 0));
        entry4.setCategory(personal);
        entry4.setTags(List.of());

        Entry entry5 = new Entry();
        entry5.setCheckIn(LocalDateTime.of(2026, 10, 24, 8, 30));
        entry5.setCheckOut(LocalDateTime.of(2026, 10, 24, 11, 30));
        entry5.setCategory(null);
        entry5.setTags(List.of(quarkus));

        entryRepository.persist(entry1);
        entryRepository.persist(entry2);
        entryRepository.persist(entry3);
        entryRepository.persist(entry4);
        entryRepository.persist(entry5);
    }
}