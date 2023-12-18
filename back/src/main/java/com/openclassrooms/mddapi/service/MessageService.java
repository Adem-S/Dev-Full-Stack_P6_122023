package com.openclassrooms.mddapi.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exceptions.MessageApiException;
import com.openclassrooms.mddapi.model.Message;
import com.openclassrooms.mddapi.payload.MessageApiResponse;
import com.openclassrooms.mddapi.payload.MessageRequest;
import com.openclassrooms.mddapi.payload.MessageWithAdditionalData;
import com.openclassrooms.mddapi.payload.MessagesResponse;
import com.openclassrooms.mddapi.repository.MessageRepository;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

@Service
public class MessageService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private PostService postService;

	public MessageApiResponse sendMessage(MessageRequest messageRequest) {

		boolean postExists = postService.postExists(messageRequest.getPostId());

		if (!postExists) {
			throw new MessageApiException("No post found");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		Timestamp date = new Timestamp(System.currentTimeMillis());

		Message message = new Message(messageRequest.getPostId(), userId, messageRequest.getMessage(), date);

		messageRepository.save(message);

		return new MessageApiResponse("Message send with success");

	}

	public MessagesResponse getMessagesByPost(Long postId) {

		List<MessageWithAdditionalData> messages = messageRepository.findMessagesWithUsernameByPostId(postId);

		return new MessagesResponse(messages);

	}

}