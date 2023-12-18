package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	List<Subject> findAllByIdIn(List<Long> ids);

}