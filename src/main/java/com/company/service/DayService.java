package com.company.service;

import com.company.dto.day.ChangeDayDetailDTO;
import com.company.dto.day.DayDTO;
import com.company.entity.DayEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.DayRepository;
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
public class DayService {
    private final DayRepository dayRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public DayDTO create(DayDTO dto) {
        var optional = dayRepository.findByName(dto.getName());
        if (optional.isPresent()) throw new ItemAlreadyExistsException("day name already exists!");

        var entity = new DayEntity();
        entity.setName(dto.getName());

        dayRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangeDayDetailDTO dto) {
        dayRepository.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException("day not found!"));

        var optional = dayRepository.findByName(dto.getName());
        if (optional.isPresent()) throw new ItemAlreadyExistsException("day name already exists!");

        dayRepository.updateDayDetail(dto.getName(), dto.getId());
        dayRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "day detail updated successfully";
    }

    public DayDTO getById(Long id) {
        return toDTO(dayRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("day not found!")));
    }

    public List<DayDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = dayRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("day list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = dayRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("day not found!"));
        dayRepository.delete(entity);

        return "day deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private DayDTO toDTO(DayEntity entity) {
        var dto = new DayDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
