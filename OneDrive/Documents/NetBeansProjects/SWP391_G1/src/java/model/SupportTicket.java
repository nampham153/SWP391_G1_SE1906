package model;

import java.util.Date;

public class SupportTicket {

    private int ticketId;
    private String ticketContent;
    private Date issueDate;
    private boolean ticketStatus;
    private byte[] attachment;
    private String customerId;
    private int categoryId;

    public SupportTicket() {
    }

    public SupportTicket(int ticketId, String ticketContent, Date issueDate,
            boolean ticketStatus, byte[] attachment, String customerId, int categoryId) {
        this.ticketId = ticketId;
        this.ticketContent = ticketContent;
        this.issueDate = issueDate;
        this.ticketStatus = ticketStatus;
        this.attachment = attachment;
        this.customerId = customerId;
        this.categoryId = categoryId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setTicketStatus(boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public boolean isTicketStatus() {
        return ticketStatus;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
