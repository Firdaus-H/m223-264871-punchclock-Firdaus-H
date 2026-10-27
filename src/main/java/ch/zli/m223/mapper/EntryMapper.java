package ch.zli.m223.mapper;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.dto.EntryDetailDto;
import ch.zli.m223.dto.CategoryDto;
import ch.zli.m223.dto.TagDto;

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
    
    public EntryDetailDto toDetailDto(Entry entry) {

        EntryDetailDto detailDto = new EntryDetailDto();
        
        detailDto.setId(entry.getId());
        detailDto.setCheckIn(entry.getCheckIn());
        detailDto.setCheckOut(entry.getCheckOut());

        if (entry.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(entry.getCategory().getId());
            categoryDto.setTitle(entry.getCategory().getTitle());
            detailDto.setCategory(categoryDto);
        } else {
            detailDto.setCategory(null);
        }

        List<TagDto> tagDtos = new ArrayList<>();
        if (entry.getTags() != null) {
            for (Tag tag : entry.getTags()) {
                TagDto tagDto = new TagDto();
                tagDto.setId(tag.getId());
                tagDto.setTitle(tag.getTitle());
                tagDtos.add(tagDto);
            }
        }

        detailDto.setTags(tagDtos);

        return detailDto;
    }
}