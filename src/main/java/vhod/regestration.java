package vhod;

import prosmotr.vspomogat.ParkPlace;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet("/regestration")
public class regestration extends HttpServlet {
    private interface HTMLGenerator{
        class Form{
            private static String makeForm(HttpServletRequest request) {
                return "<html><head>"
                        +"<title>Regestration</title><body>"
                        //+  "<link href='app.css' type='text/css' rel='stylesheet'/>"
                        + "<link href='style71.css' type='text/css' rel='stylesheet'/>"
                        //+ "<style> body { background-image: url(../../images/vel.png);}"
                        //+"<h2>Регистрация в системе: </h2>"
                        + "</style></head>"
                        //make sure method="post" so that the servlet service method
                        //calls doPost in the response to this form submit
                        +
                        "<form method=\"post\" action =\"" + request.getContextPath() +
                        "/regestration\" >"

                        +"<div align =\"center\"  class = \"col-md-6\">\n" +
                        "\n" +
                        "\t\t<h1 align=\"center\">Регистрация в системе: </h1>\n" +
                        "\n"
                        +"<div ><table style=\"margin-top: 10%\" border=1 align=center>"//bgcolor=#cbc4c4>
                        + "<tr style=\"opacity:1\"><td>"
                        + "\t\t<div class=\"form-group\" style=\"opacity:1\">\n" +
                        "Введите имя: " + "</td><td>"
                        +"<input type=\"text\" name=\"firstname\" size=\"20\">" +
                        "</div>"
                        + "</td></tr>"
                        + "<tr><td>"
                        +"\t\t<div class=\"form-group\" style=\"opacity:1\">\n" +
                        "Введите фамилию: " + "</td><td>"
                        +"<input type=\"text\" name=\"lastname\" size=\"20\">" +
                        "</div>"
                        + "</td></tr>"
                        + "<tr><td>"
                        + "\t\t<div class=\"form-group\" style=\"opacity:1\">\n" +
                        "Номер телефона: " + "</td><td>"
                        +"<input type=\"text\" name=\"phone\" size=\"20\">" +
                        "</div>"
                        + "</td></tr>"
                        + "<tr><td>"
                        + "\t\t<div class=\"form-group\" style=\"opacity:1\">\n" +
                        "Ваша почта: " + "</td><td>"
                        +"<input type=\"text\" name=\"email\" size=\"20\">" +
                        "</div>"
                        + "</td></tr>"
                        + "<tr><td>"
                        + "\t\t<div class=\"form-group\" style=\"opacity:1\">\n" +
                        "Введите пароль: " + "</td><td>"
                        +"<input type=\"text\" name=\"password\" size=\"20\">" +
                        "</div>"
                        + "</td></tr></table><table>"
                        + "<tr><td>"
                        + "\t\t<div class=\"form-group\" align=\"center\" style=\"opacity:1\">\n" +
                        "<input type=\"submit\" value=\"Зарегистрироваться\">" +
                        "</div>"
                        + "</td></tr> \n"
                        + "</table></div></form>"
                        +"</body></html>";
            }
        }
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("command: " + request.getParameter("command"));

        //set the MIME type of the response, "text/html"
        response.setContentType("text/html;charset=UTF-8");

        //use a PrintWriter send text data to the client who has requested the servlet
        PrintWriter out = response.getWriter();

        out.println(HTMLGenerator.Form.makeForm(request));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");


        try{
            String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                //out.println("Подкл");

                String sql = "INSERT INTO clients (name, lastName, email, phone, password) Values ( ?, ?, ?, ?, ?)";
                    try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){

                        //preparedStatement.setInt(1, 5);
                        preparedStatement.setString(1, request.getParameter("firstname"));
                        preparedStatement.setString(2, request.getParameter("lastname"));
                        preparedStatement.setString(3, request.getParameter("email"));
                        preparedStatement.setInt(4, Integer.parseInt(request.getParameter("phone")));
                        preparedStatement.setString(5, request.getParameter("password"));
                        preparedStatement.executeUpdate();
                        out.println("Успешно!");
                        out.println("<form action=vhod>"
                                + "Ввойти в систему -> "
                                + "<input type=submit name=od value=OK>"
                        + "</form>");
                    }
                    catch (Exception ex){
                        out.println("Не вышло");
                        out.println(ex);
                    }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }








        /*//display the parameter names and values
        Enumeration paramNames = request.getParameterNames();

        String parName;//this will hold the name of the parameter from the HTML form

        boolean emptyEnum = false;
        if (! paramNames.hasMoreElements())
            emptyEnum = true;


        //set the MIME type of the response, "text/html"
        response.setContentType("text/html");

        //use a PrintWriter send text data to the client who has requested the servlet
        PrintWriter out = response.getWriter();

        //Begin assembling the HTML content

        out.println("<html><head>");
        out.println("<title>Submitted Parameters</title></head><body>");

        if (emptyEnum){
            out.println("<h2>Sorry, the request does not contain any parameters</h2>");
        } else {
            out.println("<h2>Регистрация прошла успешно!</h2>");
        }

        while(paramNames.hasMoreElements()){
            request.setCharacterEncoding("UTF-8");
            parName = (String) paramNames.nextElement();
            out.println("<strong>" + parName + "</strong> : " + request.getParameter(parName));
            out.println("<br />");
        }//while

        out.println("</body></html>");*/

    }// doPost
}
