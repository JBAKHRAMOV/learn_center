package com.company.service;

import com.company.dto.payment.ChangePaymentDetailDTO;
import com.company.dto.payment.PaymentDTO;
import com.company.entity.PaymentEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.PaymentRepository;
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
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentTypeService paymentTypeService;
    private final StudentService studentService;

    /**
     * ROLE_ADMIN related methods
     **/

    public PaymentDTO create(PaymentDTO dto) {
        studentService.getStudentById(dto.getStudentId());
        paymentTypeService.getById(dto.getPaymentTypeId());

        var entity = new PaymentEntity();
        entity.setPaymentTypeId(dto.getPaymentTypeId());
        entity.setStudentId(dto.getStudentId());
        entity.setDescription(dto.getDescription());
        entity.setSum(dto.getSum());

        paymentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public PaymentDTO getById(Long id) {
        return toDTO(paymentRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("payment not found!")
        ));
    }

    public List<PaymentDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = paymentRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("payment list is empty!");

        return list;
    }

    public String update(ChangePaymentDetailDTO dto) {
        var entity = paymentRepository.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException("payment not found!"));

        studentService.getStudentById(dto.getStudentId());
        paymentTypeService.getById(dto.getPaymentTypeId());

        /*paymentRepository.updatePaymentDetail(dto.getStudentId(), dto.getPaymentTypeId(),
                dto.getSum(), dto.getId());*/

        entity.setPaymentTypeId(dto.getPaymentTypeId());
        entity.setStudentId(dto.getStudentId());
        entity.setSum(dto.getSum());

        paymentRepository.save(entity);
        paymentRepository.updateLastModifiedDate(LocalDateTime.now(), dto.getId());

        return "payment detail updated successfully";
    }

    public String delete(Long id) {
        var entity = paymentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("payment not found!"));

        paymentRepository.delete(entity);

        return "payment deleted successfully";
    }


    /**
     * OTHER METHODS
     **/

    private PaymentDTO toDTO(PaymentEntity entity) {
        var dto = new PaymentDTO();

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setSum(entity.getSum());
        dto.setDescription(entity.getDescription());
        dto.setPaymentType(entity.getPaymentType());
        dto.setStudent(entity.getStudent());

        return dto;
    }
}
