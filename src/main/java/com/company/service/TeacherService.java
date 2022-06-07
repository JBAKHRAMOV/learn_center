package com.company.service;

import com.company.dto.teacher.ChangeTeacherSalaryDTO;
import com.company.dto.teacher.TeacherDTO;
import com.company.dto.user.UserDetailDTO;
import com.company.entity.TeacherEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.TeacherRepository;
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
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserService userService;


    /**
     * ROLE_ADMIN related methods
     **/

    public List<TeacherDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        var list = teacherRepository.findAll(pageable).stream().map(this::toDTO).toList();
        if (list.isEmpty()) throw new ItemNotFoundException("teacher list is empty!");

        return list;
    }

    public String updateSalary(ChangeTeacherSalaryDTO dto) {
        var user = userService.getUserByPhone(dto.getPhone());

        teacherRepository.updateTeacherSalary(dto.getSalary(), user);
        teacherRepository.updateLastModifiedDate(LocalDateTime.now(), user);

        return "teacher salary updated successfully!";
    }

    public String delete(Long id) {
        TeacherEntity entity = teacherRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("teacher not found!"));

        teacherRepository.deleteById(id);
        userService.deleteById(entity.getUserEntity().getId());

        return "teacher deleted successfully";
    }

    public TeacherDTO getTeacherByPhone(String phone) {
        var user = userService.getUserByPhone(phone);
        var entity = teacherRepository.findByUser(user);

        var dto = new TeacherDTO();
        dto.setPhone(user.getPhone());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setSalary(entity.getSalary());

        return dto;
    }

    public TeacherDTO getTeacherById(Long id) {
        return toDTO(
                teacherRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("teacher not found!"))
        );
    }

    /**
     * ROLE_TEACHER related methods
     **/

    public String updateTeacherDetail(UserDetailDTO dto, String username) {
        var user = userService.updateUserDetail(dto.getFirstName(), dto.getLastName(), username);
        teacherRepository.updateLastModifiedDate(LocalDateTime.now(), user);
        return "updated successfully";
    }

    /**
     * OTHER METHODS
     **/

    private TeacherDTO toDTO(TeacherEntity entity) {
        var dto = new TeacherDTO();

        dto.setFirstName(entity.getUserEntity().getFirstName());
        dto.setLastName(entity.getUserEntity().getFirstName());
        dto.setPhone(entity.getUserEntity().getPhone());
        dto.setSalary(entity.getSalary());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModifiedDate(entity.getLastModifiedDate());

        return dto;
    }

    public void saveTeacher(UserEntity userEntity) {
        var entity = new TeacherEntity();

        entity.setUserEntity(userEntity);
        teacherRepository.save(entity);
    }

}
