package com.example._proj;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/get-all-comment")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/get-comment/{id}")
    public Optional<Comment> getCommentById(@PathVariable String id) {
        return commentService.getCommentsById(id);
    }

    @DeleteMapping("delete-comment-with-id/{id}")
    public Optional<Comment> deleteComment(@PathVariable String id) {
        return commentService.deleteCommentById(id);
    }

    @PostMapping("write-comment/")
    public String writeComment(@RequestParam String user, @RequestParam String content) {
        return commentService.writeComment(user, content);

    }
}
