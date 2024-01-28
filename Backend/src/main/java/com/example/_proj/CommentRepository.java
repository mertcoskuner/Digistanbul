package com.example._proj;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Optional<Comment> findCommentById(String id);
    Optional<Comment> findCommentByUser(String username);
    Optional<Comment> deleteCommentById(String id);

}