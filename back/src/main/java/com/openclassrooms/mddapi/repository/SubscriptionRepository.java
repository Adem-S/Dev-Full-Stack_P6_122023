package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	List<Subscription> findByUserId(Long userId);

	Optional<Subscription> findByUserIdAndSubjectId(Long userId, Long subjectId);

	boolean existsByUserIdAndSubjectId(Long userId, Long subjectId);

}
