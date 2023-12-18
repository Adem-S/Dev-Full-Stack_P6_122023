package com.openclassrooms.mddapi.payload;

import java.sql.Timestamp;

import com.openclassrooms.mddapi.model.Post;

public class PostWithAdditionalData extends Post {

	private String username;
	private String subjectTitle;

	public PostWithAdditionalData(Long id, String title, String content, Long subjectId, Long userId, Timestamp date,
			String username, String subjectTitle) {
		super(id, title, content, subjectId, userId, date);
		this.username = username;
		this.subjectTitle = subjectTitle;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

}
