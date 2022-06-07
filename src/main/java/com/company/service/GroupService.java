package com.company.service;

import com.company.dto.group.ChangeGroupDetailDTO;
import com.company.dto.group.GroupDTO;
import com.company.entity.GroupEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final CourseService courseService;
    private final RoomService roomService;
    private final TeacherService teacherService;
    private final StatusService statusService;
    private final GroupRepository groupRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public GroupDTO create(GroupDTO dto) {
        var optional = groupRepository.findByName(dto.getName());
        if (optional.isPresent()) throw new ItemAlreadyExistsException("group name already exists");

        courseService.getById(dto.getCourseId());
        roomService.getById(dto.getRoomId());
        teacherService.getTeacherById(dto.getTeacherId());
        statusService.getById(dto.getStatusId());

        var entity = toEntity(dto);
        groupRepository.save(entity);

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public GroupDTO getById(Long id) {
        return toDTO(
                groupRepository.findById(id).orElseThrow(
                        () -> new ItemNotFoundException("group not found!")
                )
        );
    }

    public List<GroupDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = groupRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("day list is empty!");

        return list;
    }

    public String update(ChangeGroupDetailDTO dto) {
        groupRepository.findById(dto.getId())
                .orElseThrow(() -> new ItemNotFoundException("group not found"));

        var optional = groupRepository.findByName(dto.getName());
        if (optional.isPresent()) throw new ItemAlreadyExistsException("group name already exists");

        courseService.getById(dto.getCourseId());
        roomService.getById(dto.getRoomId());
        teacherService.getTeacherById(dto.getTeacherId());
        statusService.getById(dto.getStatusId());

        groupRepository.save(toEntity(dto));
        groupRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "group updated successfully";
    }

    public String delete(Long id) {
        var entity = groupRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("group not found"));

        groupRepository.delete(entity);

        return "group deleted successfully";
    }

    /**
     * OTHER METHODS
     **/
    private GroupEntity toEntity(GroupDTO dto) {
        var entity = new GroupEntity();
        var formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        entity.setName(dto.getName());
        entity.setCourseId(dto.getCourseId());
        entity.setEndDate(LocalDate.parse(dto.getEndDate(), formatter));
        entity.setStartDate(LocalDate.parse(dto.getStartDate(), formatter));
        entity.setRoomId(dto.getRoomId());
        entity.setTeacherId(dto.getTeacherId());
        entity.setStatusId(dto.getStatusId());

        return entity;
    }

    private GroupEntity toEntity(ChangeGroupDetailDTO dto) {
        var entity = new GroupEntity();
        var formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        entity.setName(dto.getName());
        entity.setCourseId(dto.getCourseId());
        entity.setEndDate(LocalDate.parse(dto.getEndDate(), formatter));
        entity.setStartDate(LocalDate.parse(dto.getStartDate(), formatter));
        entity.setRoomId(dto.getRoomId());
        entity.setTeacherId(dto.getTeacherId());
        entity.setStatusId(dto.getStatusId());

        return entity;
    }

    private GroupDTO toDTO(GroupEntity entity) {
        var dto = new GroupDTO();

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setName(entity.getName());
        dto.setStartDate(String.valueOf(entity.getStartDate()));
        dto.setEndDate(String.valueOf(entity.getEndDate()));
        dto.setCourse(entity.getCourse());
        dto.setRoom(entity.getRoom());
        dto.setStatus(entity.getStatus());
        dto.setTeacher(entity.getTeacher());

        return dto;
    }
}
