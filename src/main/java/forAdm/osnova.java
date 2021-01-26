package forAdm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@WebServlet("/osnova")
public class osnova extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>"
                    +"<title>Основная страница</title><body>"
                    + "<link href='app.css' type='text/css' rel='stylesheet'/>"
                    + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                    //+  "<link href='foundation.css' type='text/css' rel='stylesheet'/>"
                    //+ "<link href='style3.css' type='text/css' rel='stylesheet'/>"
                    + "</head>");
            out.println("<table><tr><td><img width=\"50%\" src= \"cof1.png\"></td>");
            out.println("<td><h2>Основная страница</h2></td></tr></table>"
                    + "<table align=\"center\" style=\"margin-top: 10%\">"// border=\"10\">"
                    + "<tr><td>"
                    + "<form action=iform>"
                    + "Просмотреть данные" + "</td><td>"
                    + "<input type=submit name=od value=Просмотреть данные>"
                    + "</form>"
                    + "</td></tr>"
                    + "<tr><td>"
                    + "<form action=vvod>"
                    + "Добавить данные" + "</td><td>"
                    + "<input type=submit name=dod value=\"Добавить данные\">"
                    + "</form>"
                    + "</td></tr>"
                    + "<tr><td>"
                    + "<form action=osnova>"
                    + "Выйти из системы" + "</td><td>"
                    + "<input type=submit name=out value=\"Выйти из системы\">"
                    + "</form>"
                    + "</td></tr>"
                    + "</table>");

            if (request.getParameter("out") != null){
                //response.sendRedirect("/homepage");
                //out.println("fisdadasdasrst");
                Cookie[] cookies = request.getCookies();
                if(cookies != null){
                    for(Cookie cookie : cookies){
                        if(cookie.getName().equals("JSESSIONID")){
                            System.out.println("JSESSIONID="+cookie.getValue());
                            break;
                        }
                    }
                }
                //invalidate the session if exists
                HttpSession session = request.getSession(false);
                System.out.println("User="+session.getAttribute("userId"));
                if(session != null){
                    session.invalidate();
                }
                out.println("first");
                response.sendRedirect("/homepage");
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
