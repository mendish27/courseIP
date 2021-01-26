package forUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Oplata")
public class Oplata extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>"
                    + "<title>Оплата проката</title>"
                    + "<link href='style3.css' type='text/css' rel='stylesheet'/>"
                    + "</head><body>"
                    + "<h2>Введите данные карты: </h2>"

                    //make sure method="post" so that the servlet service method
                    //calls doPost in the response to this form submit
                    +
                    "<form method=\"post\" action =\"" + request.getContextPath() +
                    "/main\" >"
                    + "<table border=\"10\"><tr><td valign=\"top\">"
                    + "Введите номер карты: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"text\" size=\"20\">"
                    + "</td></tr><tr><td valign=\"top\">"
                    + "Введите ФИО: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"kolZvezd\" size=\"20\">"
                    + "</td></tr><tr><td valign=\"top\">"
                    + "Введите код с карты: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"kolZvezd2\" size=\"20\">"
                    + "</td></tr><tr><td valign=\"top\">"
                    + "<input type=\"submit\" name=davay value=\"Оплатить\"></td></tr>"
                    + "</table></form>"
                    + "</body></html>");
            if (request.getParameter("davay") != null){
                response.sendRedirect("main");
            }

        }
    }
}
