package com.openclassrooms.mddapi.payload;

import java.util.List;

public class PostsResponse {

	private List<PostWithAdditionalData> posts;

	public PostsResponse(List<PostWithAdditionalData> posts) {
		this.posts = posts;
	}

	public List<PostWithAdditionalData> getPosts() {
		return posts;
	}

	public void setPosts(List<PostWithAdditionalData> posts) {
		this.posts = posts;
	}

}
