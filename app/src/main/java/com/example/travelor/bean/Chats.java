package com.example.travelor.bean;

import java.io.Serializable;

public class Chats implements Serializable {
    private String id;
    private String identity;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "chats{" +
                "id='" + id + '\'' +
                ", identity='" + identity + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
