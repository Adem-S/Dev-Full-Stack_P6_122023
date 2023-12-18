package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Message;
import com.openclassrooms.mddapi.payload.MessageWithAdditionalData;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT NEW com.openclassrooms.mddapi.payload.MessageWithAdditionalData(m.id, m.postId, m.userId, m.message, m.date, u.username) FROM Message m JOIN m.user u WHERE m.postId = :postId")
	List<MessageWithAdditionalData> findMessagesWithUsernameByPostId(@Param("postId") Long postId);

}
