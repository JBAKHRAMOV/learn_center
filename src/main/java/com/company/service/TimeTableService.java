package com.company.service;

import com.company.dto.time.ChangeTimeTableDetailDTO;
import com.company.dto.time.TimeTableDTO;
import com.company.entity.TimeTableEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.TimeTableRepository;
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
public class TimeTableService {
    private final DayService dayService;
    private final TimeTableRepository timeTableRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public TimeTableDTO create(TimeTableDTO dto) {
        dayService.getById(dto.getDayId());

        var entity = new TimeTableEntity();
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setDayId(dto.getDayId());
        timeTableRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public TimeTableDTO getById(Long id) {
        return toDTO(timeTableRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("time table not found!")
        ));
    }

    public List<TimeTableDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = timeTableRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("time table list is empty!");

        return list;
    }

    public String update(ChangeTimeTableDetailDTO dto) {
        timeTableRepository.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException("time table not found!"));

        timeTableRepository.updateTimeTableDetail(dto.getStartTime(), dto.getEndTime(), dto.getId());
        timeTableRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "time table detail updated successfully";
    }

    public String delete(Long id) {
        var entity = timeTableRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("time table not found!"));

        timeTableRepository.delete(entity);

        return "time table deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private TimeTableDTO toDTO(TimeTableEntity entity) {
        var dto = new TimeTableDTO();

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setDay(entity.getDay());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());

        return dto;
    }

}
