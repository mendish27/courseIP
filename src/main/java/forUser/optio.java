package forUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/optio")
public class optio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");


        try (PrintWriter out = response.getWriter()) {

            /*out.println("<form action=changeEmail>"
                    + "Поменять почту -> "
                    + "<input type=submit name=email value=OK>"
                    + "</form>");
            out.println("<form action=changePassword>"
                    + "Поменять пароль -> "
                    + "<input type=submit name=password value=OK>"
                    + "</form>");
            *//*out.println("<form action=changePhoto>"
                    + "Добавить фото профиля -> "
                    + "<input type=submit name=photo value=OK>"
                    + "</form>");*//*
            out.println("<form action=main>"
                    + "Вернуться в основное меню -> "
                    + "<input type=submit name=osn value=OK>"
                    + "</form>");*/
            out.println("<html><head>"
                    +"<title></title><body>"
                    + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                    + "</head>");
            out.println("<h1 align=\"center\">Основная страница</h1>"
                    + "<table align=\"center\" style=\"margin-top: 10%\" border=\"1\">"
                    + "<tr><td>"
                    + "<form action=changeEmail>"
                    + "Поменять почту" + "</td><td>"
                    + "<input type=submit name=email value=\"Поменять почту\">"
                    +  "</form>"
                    + "</td></tr>"
                    + "<tr><td>"
                    + "<form action=changePassword>"
                    + "Поменять пароль"  + "</td><td>"
                    + "<input type=submit name=password value=\"Поменять пароль\">"
                    +  "</form>"
                    + "<tr><td>"
                    + "<form action=main>"
                    + "Вернуться в основное меню" + "</td><td>"
                    + "<input type=submit name=osn value=\"Вернуться в основное меню\">"
                    +  "</form>"
                    + "</td></tr>"
                    + "</table>");
            if (request.getParameter("email") != null) {
                response.sendRedirect("changeEmail");
            } else if (request.getParameter("password") != null){
                response.sendRedirect("changePassword");
            } else if (request.getParameter("photo") != null){
                response.sendRedirect("changePhoto");
            } else if (request.getParameter("osn") != null){
                response.sendRedirect("main");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
