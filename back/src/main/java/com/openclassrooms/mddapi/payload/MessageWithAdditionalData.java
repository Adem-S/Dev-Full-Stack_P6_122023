package com.openclassrooms.mddapi.payload;

import java.sql.Timestamp;

import com.openclassrooms.mddapi.model.Message;

public class MessageWithAdditionalData extends Message {
	
	private String username;

	public MessageWithAdditionalData(Long id, Long postId, Long userId, String message, Timestamp date, String username) {
		super(id, postId, userId, message, date);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}