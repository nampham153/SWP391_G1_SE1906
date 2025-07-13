/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin;
import dao.StaffDAO;
import dao.AccountDAO;
import model.Staff;
import model.Account;
import context.DBContext;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author namp0
 */
@WebServlet("/admin/staff")
public class StaffServlet extends HttpServlet {

    private StaffDAO staffDao;
    private final AccountDAO accountDao = new AccountDAO();

    private StaffDAO getStaffDao() throws SQLException, ClassNotFoundException {
        if (staffDao == null) {
            staffDao = new StaffDAO(new DBContext().getConnection());
        }
        return staffDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StaffDAO dao = getStaffDao();
            String action = request.getParameter("action");

            if ("delete".equals(action)) {
                dao.disableStaff(request.getParameter("id"));
                response.sendRedirect("staff");
                return;
            }

            List<Staff> staffList = dao.getAll("", "all");
            request.setAttribute("staffList", staffList);

            if ("edit".equals(action)) {
                Staff staff = dao.getById(request.getParameter("id"));
                if (staff != null) {
                    request.setAttribute("staff", staff);
                }
            }

            List<String> staffIdOptions = getAvailableStaffIdsFromAccountsWithRole2();
            for (Staff s : staffList) {
                staffIdOptions.remove(s.getStaffId());
            }
            request.setAttribute("staffIdOptions", staffIdOptions);

            if ("add".equals(action) || "edit".equals(action)) {
                request.setAttribute("pageContent", "/admin/staff-form.jsp");
            } else {
                request.setAttribute("pageContent", "/admin/staff-manage.jsp");
            }

            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StaffDAO dao = getStaffDao();
            String action = request.getParameter("action");
            boolean isEdit = "edit".equals(action);

            String staffId = request.getParameter("staffId").trim();
            String staffName = request.getParameter("staffName").trim();
            String staffTitle = request.getParameter("staffTitle").trim();
            String staffAddress = request.getParameter("staffAddress").trim();
            String birthDateStr = request.getParameter("staffBirthDate");
            String genderStr = request.getParameter("staffGender");
            String departmentIdStr = request.getParameter("departmentId");
            String statusStr = request.getParameter("status");

            if (staffId.isEmpty() || staffName.isEmpty() || staffTitle.isEmpty()
                    || staffAddress.isEmpty() || birthDateStr == null || birthDateStr.isBlank()
                    || genderStr == null || departmentIdStr == null || statusStr == null) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc.");
                reloadFormWithError(request, response);
                return;
            }
            if (!staffName.matches("[\\p{L}\\s]{3,}")) {
                request.setAttribute("error", "Họ tên phải có ít nhất 3 ký tự chữ và không chứa ký tự đặc biệt.");
                reloadFormWithError(request, response);
                return;
            }

            if (!staffTitle.matches("[\\p{L}\\s]{3,}")) {
                request.setAttribute("error", "Chức danh phải có ít nhất 3 ký tự chữ và không chứa ký tự đặc biệt.");
                reloadFormWithError(request, response);
                return;
            }

            if (!staffAddress.matches("[\\p{L}\\d\\s,./-]{5,}")) {
                request.setAttribute("error", "Địa chỉ phải có ít nhất 5 ký tự hợp lệ.");
                reloadFormWithError(request, response);
                return;
            }

            Date staffBirthDate;
            try {
                staffBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
                Date today = new Date();
                if (staffBirthDate.after(today)) {
                    request.setAttribute("error", "Ngày sinh không được vượt quá ngày hiện tại.");
                    reloadFormWithError(request, response);
                    return;
                }
            } catch (ParseException e) {
                request.setAttribute("error", "Ngày sinh không đúng định dạng (yyyy-MM-dd).");
                reloadFormWithError(request, response);
                return;
            }

            int departmentId;
            try {
                departmentId = Integer.parseInt(departmentIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Mã phòng ban phải là số.");
                reloadFormWithError(request, response);
                return;
            }

            if (!isEdit) {
                List<String> validIds = getAvailableStaffIdsFromAccountsWithRole2();
                if (!validIds.contains(staffId)) {
                    request.setAttribute("error", "Mã nhân viên không hợp lệ. Chỉ được chọn từ tài khoản có role là nhân viên.");
                    reloadFormWithError(request, response);
                    return;
                }
                if (dao.getById(staffId) != null) {
                    request.setAttribute("error", "Mã nhân viên đã tồn tại.");
                    reloadFormWithError(request, response);
                    return;
                }
            } else {
                if (dao.getById(staffId) == null) {
                    request.setAttribute("error", "Không tìm thấy nhân viên để cập nhật.");
                    reloadFormWithError(request, response);
                    return;
                }
            }

            Staff staff = new Staff();
            staff.setStaffId(staffId);
            staff.setStaffName(staffName);
            staff.setStaffTitle(staffTitle);
            staff.setStaffAddress(staffAddress);
            staff.setStaffBirthDate(new java.sql.Date(staffBirthDate.getTime()));
            staff.setStaffGender(Boolean.parseBoolean(genderStr));
            staff.setDepartmentId(departmentId);
            staff.setStatus(Boolean.parseBoolean(statusStr));
            staff.setSupervisorId(null);

            if (isEdit) {
                dao.update(staff);
            } else {
                dao.insert(staff);
            }

            response.sendRedirect("staff");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void reloadFormWithError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StaffDAO dao = getStaffDao();
            request.setAttribute("staffList", dao.getAll("", "all"));
            List<String> staffIdOptions = getAvailableStaffIdsFromAccountsWithRole2();
            for (Staff s : dao.getAll("", "all")) {
                staffIdOptions.remove(s.getStaffId());
            }
            request.setAttribute("staffIdOptions", staffIdOptions);
            request.setAttribute("pageContent", "/admin/staff-form.jsp");
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private List<String> getAvailableStaffIdsFromAccountsWithRole2() throws SQLException, ClassNotFoundException {
        List<Account> role2Accounts = accountDao.getByRole(2);
        List<String> ids = new ArrayList<>();
        for (Account acc : role2Accounts) {
            ids.add(acc.getPhone());
        }
        return ids;
    }
}
