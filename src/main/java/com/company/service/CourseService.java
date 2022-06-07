package com.company.service;

import com.company.dto.course.ChangeCourseDetailDTO;
import com.company.dto.course.CourseDTO;
import com.company.entity.CourseEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public CourseDTO create(CourseDTO dto) {
        var optional = courseRepository.findByName(dto.getName());
        if (optional.isPresent())
            throw new ItemAlreadyExistsException("course name already exists!");

        var entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());

        courseRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangeCourseDetailDTO dto) {
        courseRepository.findById(dto.getId())
                .orElseThrow(() -> new ItemNotFoundException("course not found!"));

        var optional = courseRepository.findByName(dto.getName());
        if (optional.isPresent())
            throw new ItemAlreadyExistsException("course name already exists!");

        courseRepository.updateCourseDetail(dto.getName(), dto.getDuration(), dto.getPrice(), dto.getId());
        courseRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "course detail updated successfully";
    }

    public CourseDTO getById(Long id) {
        return toDTO(courseRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("course not found!"))
        );
    }

    public List<CourseDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = courseRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("course list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = courseRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("course not found!"));
        courseRepository.delete(entity);

        return "course deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private CourseDTO toDTO(CourseEntity entity) {
        var dto = new CourseDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
