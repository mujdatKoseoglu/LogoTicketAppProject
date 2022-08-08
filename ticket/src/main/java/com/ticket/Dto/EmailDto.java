package com.ticket.Dto;

public class EmailDto {

    private String to;

    private String title;

    private String text;

    private String type;

    public EmailDto(String to, String title, String text,String type) {
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
