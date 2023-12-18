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
import com.openclassrooms.mddapi.payload.PostRequest;
import com.openclassrooms.mddapi.payload.PostWithAdditionalData;
import com.openclassrooms.mddapi.payload.PostsResponse;
import com.openclassrooms.mddapi.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/post")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("")
	public ResponseEntity<MessageApiResponse> addPost(@Valid @RequestBody PostRequest postRequest) {
		System.out.println("OK");
		return new ResponseEntity<>(postService.addPost(postRequest), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<PostsResponse> getFeedPosts() {
		return new ResponseEntity<>(postService.getFeedPosts(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostWithAdditionalData> getPostById(@PathVariable("id") final Long id) {

		return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
	}

}
