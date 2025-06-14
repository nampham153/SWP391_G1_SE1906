/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
import java.sql.Date;

public class SupportTicket {
    private int ticketId;
    private String ticketContent;
    private Date issueDate;
    private boolean ticketStatus;
    private byte[] attachment;
    private String customerId;
    private int categoryId;
    // constructors, getters, setters...

    public SupportTicket() {
    }

    public SupportTicket(int ticketId, String ticketContent, Date issueDate, boolean ticketStatus, byte[] attachment, String customerId, int categoryId) {
        this.ticketId = ticketId;
        this.ticketContent = ticketContent;
        this.issueDate = issueDate;
        this.ticketStatus = ticketStatus;
        this.attachment = attachment;
        this.customerId = customerId;
        this.categoryId = categoryId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "SupportTicket{" + "ticketId=" + ticketId + ", ticketContent=" + ticketContent + ", issueDate=" + issueDate + ", ticketStatus=" + ticketStatus + ", attachment=" + attachment + ", customerId=" + customerId + ", categoryId=" + categoryId + '}';
    }
    
}
