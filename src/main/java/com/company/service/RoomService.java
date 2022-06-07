package com.company.service;

import com.company.dto.room.ChangeRoomDetailDTO;
import com.company.dto.room.RoomDTO;
import com.company.entity.RoomEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.RoomRepository;
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
public class RoomService {
    private final RoomRepository roomRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public RoomDTO create(RoomDTO dto) {
        var optional = roomRepository.findByName(dto.getName());
        if (optional.isPresent())
            throw new ItemAlreadyExistsException("room name already exists!");

        var entity = new RoomEntity();
        entity.setName(dto.getName());
        entity.setCapacity(dto.getCapacity());

        roomRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangeRoomDetailDTO dto) {
        roomRepository.findById(dto.getId())
                .orElseThrow(() -> new ItemNotFoundException("room not found!"));

        var optional = roomRepository.findByName(dto.getName());
        if (optional.isPresent())
            throw new ItemAlreadyExistsException("room name already exists!");

        roomRepository.updateRoomDetail(dto.getName(), dto.getCapacity(), dto.getId());
        roomRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "room detail updated successfully";
    }

    public RoomDTO getById(Long id) {
        return toDTO(roomRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("room not found!"))
        );
    }

    public List<RoomDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = roomRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("room list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = roomRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("room not found!"));
        roomRepository.delete(entity);

        return "room deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private RoomDTO toDTO(RoomEntity entity) {
        var dto = new RoomDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCapacity(entity.getCapacity());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
