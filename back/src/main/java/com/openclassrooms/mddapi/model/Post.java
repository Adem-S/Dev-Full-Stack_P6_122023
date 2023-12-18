package com.openclassrooms.mddapi.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	@Column(name = "subject_id")
	private Long subjectId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "subject_id", insertable = false, updatable = false)
	private Subject subject;

	@Column(name = "user_id")
	private Long userId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	private Timestamp date;

	public Post() {
	}

	public Post(String title, String content, Long subjectId, Long userId, Timestamp date) {
		this.title = title;
		this.content = content;
		this.subjectId = subjectId;
		this.userId = userId;
		this.date = date;

	}

	public Post(Long id, String title, String content, Long subjectId, Long userId, Timestamp date) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.subjectId = subjectId;
		this.userId = userId;
		this.date = date;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

}