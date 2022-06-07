package com.company.service;

import com.company.dto.payment.ChangePaymentTypeDetailDTO;
import com.company.dto.payment.PaymentTypeDTO;
import com.company.entity.PaymentTypeEntity;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.PaymentTypeRepository;
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
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    /**
     * ROLE_ADMIN related methods
     **/

    public PaymentTypeDTO create(PaymentTypeDTO dto) {
        var optional = paymentTypeRepository.findByName(dto.getName());
        if (optional.isPresent()) throw new ItemAlreadyExistsException("payment type name already exists!");

        var entity = new PaymentTypeEntity();
        entity.setName(dto.getName());

        paymentTypeRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public String update(ChangePaymentTypeDetailDTO dto) {
        paymentTypeRepository.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException("payment type not found!"));

        var optional = paymentTypeRepository.findByName(dto.getName());
        if (optional.isPresent()) throw new ItemAlreadyExistsException("payment type name already exists!");

        paymentTypeRepository.updateDayDetail(dto.getName(), dto.getId());
        paymentTypeRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "payment type detail updated successfully";
    }

    public PaymentTypeDTO getById(Long id) {
        return toDTO(paymentTypeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("payment type not found!")));
    }

    public List<PaymentTypeDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = paymentTypeRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("payment type list is empty!");

        return list;
    }

    public String delete(Long id) {
        var entity = paymentTypeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("payment type not found!"));
        paymentTypeRepository.delete(entity);

        return "payment type deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private PaymentTypeDTO toDTO(PaymentTypeEntity entity) {
        var dto = new PaymentTypeDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }

}
