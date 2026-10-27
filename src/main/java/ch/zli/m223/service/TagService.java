package ch.zli.m223.service;

import java.util.List;

import ch.zli.m223.model.Tag;
import ch.zli.m223.repository.TagRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    @Transactional
    public Tag createTag(Tag tag) {
        tagRepository.persist(tag);
        return tag;
    }

    public List<Tag> findAll() {
        return tagRepository.listAll();
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public Tag updateTag(Long id, Tag updatedTag) {

        Tag existingTag = tagRepository.findById(id);

        if (existingTag == null) {
            throw new NotFoundException(
                "Tag with ID " + id + " not found."
            );
        }
        
        if (updatedTag.getTitle() == null || updatedTag.getTitle().isBlank()) {
            throw new BadRequestException(
                "Tag title must not be blank."
            );
        }

        existingTag.setTitle(updatedTag.getTitle());

        return existingTag;
    }

    @Transactional
    public Tag patchTag(Long id, Tag updatedTag) {

        Tag existingTag = tagRepository.findById(id);

        if (existingTag == null) {
            throw new NotFoundException(
                "Tag with ID " + id + " not found."
            );
        }

        if (updatedTag.getTitle() != null) {

            if (updatedTag.getTitle().isBlank()) {
                throw new BadRequestException(
                    "Tag title must not be blank."
                );
            }

            existingTag.setTitle(updatedTag.getTitle());
        }

        return existingTag;
    }

    @Transactional
    public void deleteTag(Long id) {

        Tag tag = tagRepository.findById(id);

        if (tag == null) {
            throw new NotFoundException(
                "Tag with ID " + id + " not found."
            );
        }

        tagRepository.delete(tag);
    }
}