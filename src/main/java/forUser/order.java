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
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/order")
public class order extends HttpServlet {
    private interface HTMLGenerator{
        class Form{

            private static String makeForm(HttpServletRequest request) {
                return "<html><head>"
                        +"<title>Аренда</title>"
                        + "<link href='style3.css' type='text/css' rel='stylesheet'/>"
                        + "</head><body>"

                        +"<h2>Аренда велосипеда: </h2>"

                        //make sure method="post" so that the servlet service method
                        //calls doPost in the response to this form submit
                        +
                        "<form method=\"post\" action =\"" + request.getContextPath() +
                        "/order\" >"
                        +"<table border=\"10\"><tr><td valign=\"top\">"
                        +"Введите код велосипеда: </td>  <td valign=\"top\">"
                        +"<input type=\"text\" name=\"idVelika\" size=\"20\">"
                        +"</td></tr><tr><td valign=\"top\">"
                        +"Введите id локации: </td>  <td valign=\"top\">"
                        +"<input type=\"text\" name=\"idLoc\" size=\"20\">"
                        +"</td></tr><tr><td valign=\"top\">"
                        +"Введите время использования: </td>  <td valign=\"top\">"
                        +"<input type=\"text\" name=\"timeForUse\" size=\"10\">"
                        + "</td></tr><tr><td valign=\"top\">"
                        +"Минимальная цена проката 100 рублей за 1 час. </td>  <td valign=\"top\">"
                        +"</td></tr><tr><td valign=\"top\">"
                        +"<input type=\"submit\" value=\"Арендовать\"></td></tr>"
                        +"</table></form>"
                        +"</body></html>";
            }
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            String url = "jdbc:mysql://localhost/velo?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                //out.println("Подкл");

                String sql = "INSERT INTO orders (idClie, idVelika, idLoc, timeForUse, price) Values ( ?, ?, ?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    HttpSession session = request.getSession(false);
                    int idClie = (int) session.getAttribute("userId");
                    int time = Integer.parseInt(request.getParameter("timeForUse"));
                    out.println(time);
                    int price = time * 100;
                    out.println(price);
                    //preparedStatement.setInt(1, 5);
                    preparedStatement.setInt(1, idClie);
                    preparedStatement.setInt(2, Integer.parseInt(request.getParameter("idVelika")));
                    preparedStatement.setInt(3, Integer.parseInt(request.getParameter("idLoc")));
                    preparedStatement.setInt(4, Integer.parseInt(request.getParameter("timeForUse")));
                    preparedStatement.setInt(5, price);
                    preparedStatement.executeUpdate();
                    out.println("Успешно!");

                    out.println("<form action=Oplata>"
                            + "Оплатить аренду -> "
                            + "<input type=submit name=ma value=OK>"
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

        out.println(order.HTMLGenerator.Form.makeForm(request));
    }
}
