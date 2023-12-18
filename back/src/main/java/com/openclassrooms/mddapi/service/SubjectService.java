
package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.payload.MessageApiResponse;
import com.openclassrooms.mddapi.payload.SubjectsResponse;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

@Service
public class SubjectService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private SubscriptionService subscriptionService;

	public SubjectsResponse getSubjects() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		List<Subject> subjects = subjectRepository.findAll();

		List<Long> subscribedSubjectIds = subscriptionService.getIdOfSubjectsUserIsSubscribed(userId);

		for (Subject subject : subjects) {
			subject.setSubscribed(subscribedSubjectIds.contains(subject.getId()));
		}

		return new SubjectsResponse(subjects);
	}

	public SubjectsResponse getSubjectSubscribed() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		List<Long> subjectIds = subscriptionService.getIdOfSubjectsUserIsSubscribed(userId);

		List<Subject> subjects = subjectRepository.findAllByIdIn(subjectIds);

		return new SubjectsResponse(subjects);

	}

	public MessageApiResponse subscribe(Long subjectId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		return subscriptionService.subscribe(userId, subjectId);

	}

	public MessageApiResponse unsubscribe(Long subjectId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		return subscriptionService.unsubscribe(userId, subjectId);

	}

}
