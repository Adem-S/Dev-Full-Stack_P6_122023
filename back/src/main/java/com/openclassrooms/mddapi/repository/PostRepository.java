package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.payload.PostWithAdditionalData;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	boolean existsById(Long id);

	List<Post> findAllBySubjectIdIn(List<Long> ids, Sort sort);

	@Query("SELECT new com.openclassrooms.mddapi.payload.PostWithAdditionalData(p.id, p.title, p.content, p.subjectId, p.userId, p.date, u.username, s.title) FROM Post p "
			+ "JOIN p.user u " + "JOIN p.subject s " + "WHERE p.id = :postId")
	Optional<PostWithAdditionalData> findPostByIdWithDetails(@Param("postId") Long postId);

	@Query("SELECT new com.openclassrooms.mddapi.payload.PostWithAdditionalData(p.id, p.title, p.content, p.subjectId, p.userId, p.date, u.username, s.title) FROM Post p "
			+ "JOIN p.user u " + "JOIN p.subject s " + "WHERE p.subjectId IN :ids " + "ORDER BY p.date DESC")
	List<PostWithAdditionalData> findAllBySubjectIdInWithDetails(@Param("ids") List<Long> ids);

}
