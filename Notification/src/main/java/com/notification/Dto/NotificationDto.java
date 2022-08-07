package com.notification.Dto;

public class NotificationDto {

    private Integer id;

    private String to;

    private String title;

    private String text;

    private String type;

    public NotificationDto() {
    }

    public NotificationDto(Integer id, String to, String title, String text,String type) {
        this.id = id;
        this.to = to;
        this.title = title;
        this.text = text;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
