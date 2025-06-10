package controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.util.Properties;

@WebServlet(name = "ContactServlet", urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageContent", "contact-us.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        final String yourEmail = "namp04464@gmail.com";
        final String yourPassword = "bytb qsow teds bzxh";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(yourEmail, yourPassword);
                    }
                });

        String status;
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(yourEmail, "Shop PC"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(yourEmail));
            msg.setSubject(subject);
            msg.setText("Tên: " + name + "\nEmail: " + email + "\n\nNội dung:\n" + message);
            Transport.send(msg);

            status = "success";
        } catch (MessagingException e) {
            status = "error";
        }

        request.setAttribute("statusMessage", status);
        request.setAttribute("pageContent", "contact.jsp");
        request.getRequestDispatcher("contact-us.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Contact form submission and email handler";
    }
}
