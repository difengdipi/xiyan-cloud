package com.ruoyi.ai.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
// 消息结构
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@Data
@Getter
@Setter
public class ChatRequest  implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @JsonProperty("model")
    private String model;
    
    @JsonProperty("messages")
    private List<Message> messages = new ArrayList<>();
    
    @JsonProperty("stream")
    private boolean stream;

    // 静态内部类定义消息结构
    @Builder
    public static class Message {
        @JsonProperty("role")
        private String role;
        
        @JsonProperty("content")
        private String content;

        // 无参构造器
        public Message() {}

        // 全参构造器
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
        // Getter & Setter
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    // 构造方法
    public ChatRequest() {}

    public ChatRequest(String model, boolean stream) {
        this.model = model;
        this.stream = stream;
    }

    // 添加消息的便捷方法
    public ChatRequest addMessage(String role, String content) {
        this.messages.add(new Message(role, content));
        return this;
    }

    // Getter & Setter
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
    public boolean isStream() { return stream; }
    public void setStream(boolean stream) { this.stream = stream; }
}