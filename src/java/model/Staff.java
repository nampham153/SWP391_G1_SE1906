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

public class Staff {
    private String staffId;
    private String staffName;
    private String staffTitle;
    private String staffAddress;
    private Date staffBirthDate;
    private boolean staffGender;
    private String supervisorId;
    private int departmentId;
    private Boolean status;

    public Staff() {}

    public Staff(String staffId, String staffName, String staffTitle, String staffAddress,
                 Date staffBirthDate, boolean staffGender, String supervisorId,
                 int departmentId, Boolean status) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffTitle = staffTitle;
        this.staffAddress = staffAddress;
        this.staffBirthDate = staffBirthDate;
        this.staffGender = staffGender;
        this.supervisorId = supervisorId;
        this.departmentId = departmentId;
        this.status = status;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffTitle() {
        return staffTitle;
    }

    public void setStaffTitle(String staffTitle) {
        this.staffTitle = staffTitle;
    }

    public String getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }

    public Date getStaffBirthDate() {
        return staffBirthDate;
    }

    public void setStaffBirthDate(Date staffBirthDate) {
        this.staffBirthDate = staffBirthDate;
    }

    public boolean isStaffGender() {
        return staffGender;
    }

    public void setStaffGender(boolean staffGender) {
        this.staffGender = staffGender;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
