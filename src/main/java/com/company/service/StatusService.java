package com.company.service;

import com.company.dto.status.ChangeStatusDetailDTO;
import com.company.dto.status.StatusDTO;
import com.company.entity.StatusEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.StatusRepository;
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
public class StatusService {
    private final StatusRepository statusRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public StatusDTO create(StatusDTO dto) {
        var optional = statusRepository.findByName(dto.getName());
        if (optional.isPresent())
            throw new ItemAlreadyExistsException("status name already exists!");

        var entity = new StatusEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        statusRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangeStatusDetailDTO dto) {
        statusRepository.findById(dto.getId())
                .orElseThrow(() -> new ItemNotFoundException("status not found!"));

        var optional = statusRepository.findByName(dto.getName());
        if (optional.isPresent())
            throw new ItemAlreadyExistsException("status name already exists!");

        statusRepository.updateStatusDetail(dto.getName(), dto.getDescription(), dto.getId());
        statusRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "status detail updated successfully";
    }

    public StatusDTO getById(Long id) {
        return toDTO(statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("status not found!"))
        );
    }

    public List<StatusDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = statusRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("status list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = statusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("status not found!"));
        statusRepository.delete(entity);

        return "status deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private StatusDTO toDTO(StatusEntity entity) {
        var dto = new StatusDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
