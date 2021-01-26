package forUser.Change;

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

@WebServlet("/changePassword")
public class changePassword extends HttpServlet {
    private interface HTMLGenerator{
        class Form{
            private static String makeForm(HttpServletRequest request) {
                return "<html><head>"
                        +"<title>Options</title>"
                        + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                        + "</head><body>"
                        +"<h2  align=\"center\" style=\"margin-top: 10%\" border=\"1\">Смена пароля: </h2>"

                        //make sure method="post" so that the servlet service method
                        //calls doPost in the response to this form submit
                        +
                        "<form method=\"post\" action =\"" + request.getContextPath() +
                        "/changePassword\" >"
                        +"<table border=\"1\" align=\"center\" <tr><td valign=\"top\">"
                        +"Введите новый пароль: </td>  <td valign=\"top\">"
                        +"<input type=\"text\" name=\"lastPassw\" size=\"20\">"
                        +"</td></tr><tr><td valign=\"top\">"
                        +"Введите код с почты: </td>  <td valign=\"top\">"
                        +"<input type=\"text\" name=\"kod\" size=\"10\">"
                        + "</td></tr><tr><td valign=\"top\">"
                        +"<input type=\"submit\" value=\"Сохранить\"></td></tr>"
                        + "<tr><td valign=\"top\">"
                        + "<input type=\"submit\" name=\"home\" value=\"Отмена\"></td></tr>"
                        + "</table></form>"
                        + "</body></html>";
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("home") != null) {
            response.sendRedirect("optio");
        }
        response.setContentType("text/html;charset=UTF-8");
        try{
            String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                //out.println("Подкл");

                String sql = "UPDATE clients SET password = ? WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    HttpSession session = request.getSession(false);
                    int idClie = (int) session.getAttribute("idclients");
                    preparedStatement.setString(1, request.getParameter("password"));
                    preparedStatement.setInt(2, idClie);
                    preparedStatement.executeUpdate();
                    out.println("Успешно!");
                    out.println("<form action=main>"
                            + "Вернуться в меню настроек -> "
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("command: " + request.getParameter("command"));

        //set the MIME type of the response, "text/html"
        response.setContentType("text/html;charset=UTF-8");

        //use a PrintWriter send text data to the client who has requested the servlet
        PrintWriter out = response.getWriter();

        out.println(changePassword.HTMLGenerator.Form.makeForm(request));
    }
}
