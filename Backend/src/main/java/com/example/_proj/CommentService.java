package com.example._proj;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository CommentRepository;
    public List<Comment> getAllComments() { return CommentRepository.findAll(); }

    public Optional<Comment> getCommentsByUsername(String username) {
        return CommentRepository.findCommentByUser(username);
    }

    public Optional<Comment> getCommentsById(String id) {
        return CommentRepository.findCommentById(id);
    }

    public Optional<Comment> deleteCommentById(String id) {
        return CommentRepository.deleteCommentById(id);
    }

    public String writeComment(String user, String content) {
        Comment comment = new Comment(content, user);
        CommentRepository.insert(comment);
        return comment.getId();
    }
}
