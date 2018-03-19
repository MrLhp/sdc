package sdc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import sdc.model.Teacher;

/**
 * TeacherRepository
 */
public interface TeacherRepository extends Repository<Teacher, Long> {

    Page<Teacher> findAll(Pageable pageable);

    Teacher findOne(Long id);

    Teacher save(Teacher model);

    void delete(Long id);

}
