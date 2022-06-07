package com.company.service;

import com.company.dto.group.ChangeGroupTimeTableDetailDTO;
import com.company.dto.group.GroupTimeTableDTO;
import com.company.entity.GroupTimeTableEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.GroupTimeTableRepository;
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
public class GroupTimeTableService {
    private final GroupTimeTableRepository groupTimeTableRepository;
    private final GroupService groupService;
    private final TimeTableService timeTableService;

    /**
     * ROLE_ADMIN related methods
     **/

    public GroupTimeTableDTO create(GroupTimeTableDTO dto) {
        groupService.getById(dto.getGroupId());
        timeTableService.getById(dto.getTimeTableId());

        var entity = new GroupTimeTableEntity();

        entity.setGroupId(dto.getGroupId());
        entity.setTimeTableId(entity.getTimeTableId());

        groupTimeTableRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangeGroupTimeTableDetailDTO dto) {
        groupTimeTableRepository.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException("group time table not found!"));
        groupService.getById(dto.getGroupId());
        timeTableService.getById(dto.getTimeTableId());

        groupTimeTableRepository.updateGroupTimeTableDetail(dto.getGroupId(), dto.getTimeTableId(), dto.getId());
        groupTimeTableRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "group time table detail updated successfully";
    }

    public GroupTimeTableDTO getById(Long id) {
        return toDTO(groupTimeTableRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("group time table not found!")));
    }

    public List<GroupTimeTableDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = groupTimeTableRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("group time table list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = groupTimeTableRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("group time table not found!"));
        groupTimeTableRepository.delete(entity);

        return "group time table deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private GroupTimeTableDTO toDTO(GroupTimeTableEntity entity) {
        var dto = new GroupTimeTableDTO();

        dto.setId(entity.getId());
        dto.setTimeTable(entity.getTimeTable());
        dto.setGroup(entity.getGroup());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
