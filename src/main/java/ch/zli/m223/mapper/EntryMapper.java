package ch.zli.m223.mapper;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.dto.EntryDto;

@ApplicationScoped
public class EntryMapper {

    public Entry toEntity(EntryDto dto) {
        Entry entry = new Entry();

        entry.setCheckIn(dto.getCheckIn());
        entry.setCheckOut(dto.getCheckOut());

        return entry;
    }

    public EntryDto toDto(Entry entry) {
        EntryDto dto = new EntryDto();

        dto.setId(entry.getId());
        dto.setCheckIn(entry.getCheckIn());
        dto.setCheckOut(entry.getCheckOut());

        if (entry.getCategory() != null) {
            dto.setCategoryId(entry.getCategory().getId());
        }
        
        if (entry.getTags() != null) {
            List<Long> tagIds = new ArrayList<>();

            for (Tag tag : entry.getTags()) {
                tagIds.add(tag.getId());
            }

            dto.setTagIds(tagIds);
        } else {
            dto.setTagIds(new ArrayList<>());
        }

        return dto;
    }
}