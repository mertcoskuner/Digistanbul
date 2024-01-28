package com.example._proj;


import lombok.Data;

@Data
public class Comment {
    private String id;//userID
    private String content;//content
    private String user;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Comment(String content, String user) {
        super();
        this.user = user;
        this.content = content;
    }

    public Comment() {}

}