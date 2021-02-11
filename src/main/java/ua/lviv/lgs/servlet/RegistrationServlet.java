package ua.lviv.lgs.servlet;

import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.domain.UserRole;
import ua.lviv.lgs.service.UserService;
import ua.lviv.lgs.service.impl.UserServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "registrationForm", urlPatterns = { "/registrationForm" })
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService  userService = UserServiceImpl.getUserService();


    public RegistrationServlet() {
        super();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        if (!email.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !password.isEmpty()) {

            userService.create(new User(email, firstName, lastName, UserRole.USER.toString(), password));

        }

        request.getRequestDispatcher("cabinet.jsp").forward(request, response);
    }

}