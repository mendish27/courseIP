package forUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main")
public class main extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html><head>"
                +"<title>Основная страница</title><body>"
                + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                + "</head>");
        out.println("<h1 align=\"center\">Основная страница</h1>"
                + "<table align=\"center\" style=\"margin-top: 10%\" border=\"1\">"
                + "<tr><td>"
                + "<form action=GetProducts>"
                + "Поиск товара" + "</td><td>"
                + "<input type=submit name=od value=\"Поиск товара\">"
                +  "</form>"
                + "</td></tr>"
                + "<tr><td>"
                + "<form action=optio>"
                + "Настройки" + "</td><td>"
                + "<input type=submit name=optio value=Настройки>"
                +  "</form>"
                + "</td></tr>"
                + "<tr><td>"
                + "<form action=Otziv>"
                + "Отзывы о работе проката" + "</td><td>"
                + "<input type=submit name=optio value=\"Отзывы\">"
                +  "</form>"
                + "</td></tr>"
                + "<tr><td>"
                + "<form action=main>"
                + "Выйти из системы" + "</td><td>"
                + "<input type=submit name=out value=\"Выйти из системы\">"
                + "</form>"
                + "</td></tr>"
                + "</table>");

        if (request.getParameter("od") != null){
            response.sendRedirect("/check");
        } else if(request.getParameter("dod") != null){
            response.sendRedirect("/order");
        } else if(request.getParameter("optio") != null){
          response.sendRedirect("optio");
        } else if(request.getParameter("out") != null){
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
