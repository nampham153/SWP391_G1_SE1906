package model;

import java.util.Date;

public class Staff {

    private String staffId;
    private String staffName;
    private String staffTitle;
    private String staffAddress;
    private Date staffBirthDate;
    private boolean staffGender;
    private String supervisorId;
    private int departmentId;

    public Staff() {
    }

    public Staff(String staffId, String staffName, String staffTitle, String staffAddress,
            Date staffBirthDate, boolean staffGender, String supervisorId, int departmentId) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffTitle = staffTitle;
        this.staffAddress = staffAddress;
        this.staffBirthDate = staffBirthDate;
        this.staffGender = staffGender;
        this.supervisorId = supervisorId;
        this.departmentId = departmentId;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffTitle() {
        return staffTitle;
    }

    public String getStaffAddress() {
        return staffAddress;
    }

    public Date getStaffBirthDate() {
        return staffBirthDate;
    }

    public boolean isStaffGender() {
        return staffGender;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setStaffTitle(String staffTitle) {
        this.staffTitle = staffTitle;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }

    public void setStaffBirthDate(Date staffBirthDate) {
        this.staffBirthDate = staffBirthDate;
    }

    public void setStaffGender(boolean staffGender) {
        this.staffGender = staffGender;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

}
