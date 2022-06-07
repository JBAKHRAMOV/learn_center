package com.company.service;

import com.company.dto.group.ChangeGroupStudentDetailDTO;
import com.company.dto.group.GroupStudentDTO;
import com.company.entity.GroupStudentEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.GroupStudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupStudentService {
    private final GroupStudentRepository groupStudentRepository;
    private final GroupService groupService;
    private final StudentService studentService;

    /**
     * ROLE_ADMIN related methods
     **/

    public GroupStudentDTO create(GroupStudentDTO dto) {
        groupService.getById(dto.getGroupId());
        studentService.getStudentById(dto.getStudentId());

        var entity = new GroupStudentEntity();

        entity.setGroupId(dto.getGroupId());
        entity.setStudentId(entity.getStudentId());

        groupStudentRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangeGroupStudentDetailDTO dto) {
        groupStudentRepository.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException("group student table not found!"));
        groupService.getById(dto.getGroupId());
        studentService.getStudentById(dto.getStudentId());

        groupStudentRepository.updateGroupTimeTableDetail(dto.getGroupId(), dto.getStudentId(), dto.getId());
        groupStudentRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "group student table detail updated successfully";
    }

    public GroupStudentDTO getById(Long id) {
        return toDTO(groupStudentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("group student table not found!")));
    }

    public List<GroupStudentDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = groupStudentRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("group student table list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = groupStudentRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("group student table not found!"));
        groupStudentRepository.delete(entity);

        return "group student table deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private GroupStudentDTO toDTO(GroupStudentEntity entity) {
        var dto = new GroupStudentDTO();

        dto.setId(entity.getId());
        dto.setStudent(entity.getStudent());
        dto.setGroup(entity.getGroup());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
