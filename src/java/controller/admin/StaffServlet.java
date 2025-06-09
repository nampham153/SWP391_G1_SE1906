package controller.admin;

import dao.StaffDAO;
import dao.CustomerDAO;
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

@WebServlet("/admin/staff")
public class StaffServlet extends HttpServlet {

    private StaffDAO staffDao;
    private final CustomerDAO customerDao = new CustomerDAO();
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
            String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
            String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
            if ("delete".equals(action)) {
                dao.disableStaff(request.getParameter("id"));
                response.sendRedirect("staff");
                return;
            }
            List<Staff> staffList = dao.getAll(keyword, status);
            request.setAttribute("staffList", staffList);
            if ("edit".equals(action)) {
                Staff staff = dao.getById(request.getParameter("id"));
                if (staff == null) {
                    response.sendRedirect("staff");
                    return;
                }
                request.setAttribute("staff", staff);
            }
            List<String> staffIdOptions = getAvailableStaffIdsFromCustomersWithRole2();
            for (Staff st : staffList) {
                staffIdOptions.remove(st.getStaffId()); // loại id đã đăng ký
            }
            request.setAttribute("staffIdOptions", staffIdOptions);

            request.getRequestDispatcher("staff-manage.jsp").forward(request, response);
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
            String staffId = request.getParameter("staffId");

            String birthDateStr = request.getParameter("staffBirthDate");
            if (birthDateStr == null || birthDateStr.isBlank()) {
                request.setAttribute("error", "Ngày sinh không được để trống.");
                reloadFormWithError(request, response);
                return;
            }
            Date staffBirthDate;
            try {
                staffBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
            } catch (ParseException pe) {
                request.setAttribute("error", "Ngày sinh không hợp lệ! Định dạng: yyyy-MM-dd.");
                reloadFormWithError(request, response);
                return;
            }
            if (!isEdit) {
                List<String> validIds = getAvailableStaffIdsFromCustomersWithRole2();
                if (!validIds.contains(staffId)) {
                    request.setAttribute("error", "Mã nhân viên không hợp lệ. Chỉ được chọn từ danh sách khách hàng có tài khoản role 2.");
                    reloadFormWithError(request, response);
                    return;
                }
                if (dao.getById(staffId) != null) {
                    request.setAttribute("error", "Mã nhân viên đã tồn tại trong danh sách nhân viên.");
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
            staff.setStaffName(request.getParameter("staffName"));
            staff.setStaffTitle(request.getParameter("staffTitle"));
            staff.setStaffAddress(request.getParameter("staffAddress"));
            staff.setStaffBirthDate(staffBirthDate);
            staff.setStaffGender(Boolean.parseBoolean(request.getParameter("staffGender")));
            staff.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
            staff.setStatus(Boolean.parseBoolean(request.getParameter("status")));
            if (isEdit) {
                dao.update(staff);
            } else {
                dao.insert(staff);
            }
            response.sendRedirect("staff");
            System.out.println("Action: " + action);
System.out.println("staffId: " + staffId);


        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void reloadFormWithError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StaffDAO dao = getStaffDao();
            request.setAttribute("staffList", dao.getAll("", "all"));

            List<String> staffIdOptions = getAvailableStaffIdsFromCustomersWithRole2();
            for (Staff st : dao.getAll("", "all")) {
                staffIdOptions.remove(st.getStaffId());
            }
            request.setAttribute("staffIdOptions", staffIdOptions);

            request.getRequestDispatcher("staff-manage.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private List<String> getAvailableStaffIdsFromCustomersWithRole2() throws SQLException, ClassNotFoundException {
        List<Account> role2Accounts = accountDao.getByRole(2);
        List<String> ids = new ArrayList<>();
        for (Account acc : role2Accounts) {
            ids.add(acc.getPhone());
        }
        return ids;
    }

}
