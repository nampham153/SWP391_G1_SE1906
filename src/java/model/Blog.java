package model;
import java.sql.Timestamp;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namp0
 */
public class Blog {
    private int blogId;
    private String staffId;
    private String title;
    private String content;
    private String image;
    private Timestamp createdAt;
    private int status;

    public Blog() {
    }

    public Blog(int blogId, String staffId, String title, String content, String image, Timestamp createdAt, int status) {
        this.blogId = blogId;
        this.staffId = staffId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

