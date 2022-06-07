package com.company.service;

import com.company.dto.student.StudentDTO;
import com.company.dto.user.UserDetailDTO;
import com.company.entity.StudentEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.StudentRepository;
import com.company.entity.UserEntity;
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
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserService userService;

    /**
     * ROLE_ADMIN related methods
     **/

    public List<StudentDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = studentRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("student list is empty!");

        return list;
    }

    public StudentDTO getStudentByPhone(String phone) {
        var user = userService.getUserByPhone(phone);
        var entity = studentRepository.findByUser(user);
        return toDTO(entity);
    }

    public StudentDTO getStudentById(Long id) {
        return toDTO(studentRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("student not found!"))
        );
    }

    public String delete(Long id) {
        var entity = studentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("student not found!"));

        studentRepository.deleteById(id);
        userService.deleteById(entity.getUserEntity().getId());

        return "student deleted successfully!";
    }

    /**
     * ROLE_STUDENT related methods
     **/

    public String updateStudentDetail(UserDetailDTO dto, String username) {
        var user = userService.updateUserDetail(dto.getFirstName(), dto.getLastName(), username);
        studentRepository.updateLastModifiedDate(LocalDateTime.now(), user);
        return "updated successfully";
    }

    /**
     * OTHER METHODS
     **/

    public void saveStudent(UserEntity userEntity) {
        var entity = new StudentEntity();

        entity.setUserEntity(userEntity);
        studentRepository.save(entity);
    }

    public StudentDTO toDTO(StudentEntity entity) {
        var dto = new StudentDTO();

        dto.setPhone(entity.getUserEntity().getPhone());
        dto.setFirstName(entity.getUserEntity().getFirstName());
        dto.setLastName(entity.getUserEntity().getLastName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }
}
