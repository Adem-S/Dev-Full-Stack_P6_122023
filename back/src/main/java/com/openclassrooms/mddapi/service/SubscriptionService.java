package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exceptions.SubscriptionNotFoundException;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.payload.MessageApiResponse;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public List<Long> getIdOfSubjectsUserIsSubscribed(Long userId) {

		List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

		List<Long> listeIds = subscriptions.stream().map(Subscription::getSubjectId).collect(Collectors.toList());

		return listeIds;

	}

	public MessageApiResponse subscribe(Long userId, Long subjectId) {

		boolean subscriptionExist = subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId);

		if (subscriptionExist) {
			return new MessageApiResponse("New subscription !");
		}

		Subscription subscription = new Subscription(userId, subjectId);

		subscriptionRepository.save(subscription);

		return new MessageApiResponse("New subscription !");

	}

	public MessageApiResponse unsubscribe(Long userId, Long subjectId) {

		Optional<Subscription> optionalSubscription = subscriptionRepository.findByUserIdAndSubjectId(userId,
				subjectId);

		if (optionalSubscription.isPresent()) {

			Subscription subscription = optionalSubscription.get();

			subscriptionRepository.delete(subscription);

			return new MessageApiResponse("No longer subscribed !");
			
		} else {
			
			throw new SubscriptionNotFoundException("Not subscribed !");
		}

	}

}
