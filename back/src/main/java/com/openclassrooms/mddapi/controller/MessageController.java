package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.payload.MessageApiResponse;
import com.openclassrooms.mddapi.payload.MessageRequest;
import com.openclassrooms.mddapi.payload.MessagesResponse;
import com.openclassrooms.mddapi.service.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/message")
@CrossOrigin("*")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PostMapping("")
	public ResponseEntity<MessageApiResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest) {
		return new ResponseEntity<>(messageService.sendMessage(messageRequest), HttpStatus.OK);
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<MessagesResponse> getMessagesByPost(@PathVariable("id") final Long id) {
		return new ResponseEntity<>(messageService.getMessagesByPost(id), HttpStatus.OK);
	}
}