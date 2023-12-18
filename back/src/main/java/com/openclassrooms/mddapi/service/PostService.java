package com.openclassrooms.mddapi.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exceptions.PostNotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.payload.MessageApiResponse;
import com.openclassrooms.mddapi.payload.PostRequest;
import com.openclassrooms.mddapi.payload.PostWithAdditionalData;
import com.openclassrooms.mddapi.payload.PostsResponse;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

@Service
public class PostService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private SubscriptionService subscriptionService;

	public MessageApiResponse addPost(PostRequest postRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		Timestamp date = new Timestamp(System.currentTimeMillis());

		Post post = new Post(postRequest.getTitle(), postRequest.getContent(), postRequest.getSubjectId(), userId,
				date);

		postRepository.save(post);

		return new MessageApiResponse("Post created !");

	}

	public PostsResponse getFeedPosts() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long userId = userDetails.getId();

		List<Long> subjectIds = subscriptionService.getIdOfSubjectsUserIsSubscribed(userId);

		List<PostWithAdditionalData> posts = postRepository.findAllBySubjectIdInWithDetails(subjectIds);

		return new PostsResponse(posts);

	}

	public PostWithAdditionalData getPost(Long id) {

		Optional<PostWithAdditionalData> post = postRepository.findPostByIdWithDetails(id);

		return post.orElseThrow(() -> new PostNotFoundException("No Post Found"));

	}

	public boolean postExists(Long id) {
		return postRepository.existsById(id);

	}

}
