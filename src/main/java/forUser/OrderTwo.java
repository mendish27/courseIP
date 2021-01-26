package forUser;

import vhod.regestration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/OrderTwo")
public class OrderTwo extends HttpServlet {
    private interface HTMLGenerator{
        class Form{
            private static String makeForm(HttpServletRequest request) {
                return "<html><head><title>Work Hunter</title><link href='app.css' type='text/css' rel='stylesheet'/>" +
                        "<link href='style01.css' type='text/css' rel='stylesheet'/>" +
                        "</head>"
                        + "<body><nav class='navbar navbar-default'><div class='container-fluid'>"
                        + "<div class='collapse navbar-collapse'>"
                        + "<ul class='nav navbar-nav navbar-right'>"
                        +"<li><a href='regestration' style=\"font-size:20px\">Регистрация</a></li>"
                        + "<li><a href=\"getClient\" style=\"font-size:20px\">Вывод всех клиентов</a></li></ul></div></div></nav>"
//make sure method="post" so that the servlet service method
//calls doPost in the response to this form submit
                        +"<form method=\"post\" action =\"" + request.getContextPath() +
                        "/regestration\" >"
                        +"<div align =\"center\" class = \"col-md-6\">\n" +
                        "\n" +
                        "\t\t<h1>Создать аккаунт</h1>\n" +
                        "\n" +
                        "\t\t<div class=\"form-group\">\n" +
                        "Введите ФИО:"
                        +"<input type=\"text\" name=\"fio\" size=\"20\">" +
                        "</div>" +
                        "\t\t<div class=\"form-group\">\n" +
                        "__BithDay__:"
                        +"<input type=\"text\" name=\"bithday\" size=\"20\">" +
                        "</div>" +
                        "\t\t<div class=\"form-group\">\n" +
                        "__Телефон__:"
                        +"<input type=\"text\" name=\"phone\" size=\"20\">" +
                        "</div>" +
//"\t\t<div class=\"form-group\">\n" +
//"Your Mobile number:"
//+"<input type=\"text\" name=\"mobile_no\" size=\"20\">" +
//"</div>" +
                        "\t\t<div class=\"form-group\">\n" +
                        "___E-mail___:"
                        +"<input type=\"text\" name=\"email\" size=\"20\">" +
                        "</div>" +
                        "\t\t<div class=\"form-group\">\n" +
                        "<input type=\"submit\" value=\"Зарегистрироваться\">" +
                        "</div>\n"
/*
+"<table border=\"10\"><tr><td valign=\"top\">"
+"Введите имя: </td> <td valign=\"top\">"
+"<input type=\"text\" name=\"firstname\" size=\"20\">"
+"</td></tr><tr><td valign=\"top\">"
+"Введите фамилию: </td> <td valign=\"top\">"
+"<input type=\"text\" name=\"lastname\" size=\"20\">"
+"</td></tr><tr><td valign=\"top\">"
+"Номер телефона: </td> <td valign=\"top\">"
+"<input type=\"text\" name=\"phone\" size=\"10\">"
+ "</td></tr><tr><td valign=\"top\">"
+"Ваша почта: </td> <td valign=\"top\">"
+"<input type=\"text\" name=\"email\" size=\"20\">"
+"</td></tr><tr><td valign=\"top\">"
+"<input type=\"submit\" value=\"Зарегестрироваться\"></td></tr>"
+"</table></form>"
*/
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

}
