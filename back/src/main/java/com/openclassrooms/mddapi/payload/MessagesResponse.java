
package com.openclassrooms.mddapi.payload;

import java.util.List;

public class MessagesResponse {

	private List<MessageWithAdditionalData> messages;

	public MessagesResponse(List<MessageWithAdditionalData> messages) {
		this.messages = messages;
	}

	public List<MessageWithAdditionalData> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageWithAdditionalData> messages) {
		this.messages = messages;
	}

}
