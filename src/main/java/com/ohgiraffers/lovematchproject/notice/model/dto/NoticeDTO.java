package com.ohgiraffers.lovematchproject.notice.model.dto;


import java.util.Date;

public class NoticeDTO {

    private int id;
    private String noticeTitle;
    private String noticeContent;
    private Date createDate;

    public NoticeDTO() {
    }

    public NoticeDTO(int id, String noticeTitle, String noticeContent, Date createDate) {
        this.id = id;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createDate = createDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "NoticeDTO{" +
                "id=" + id +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
