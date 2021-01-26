package forUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@WebServlet("/Comme")
public class Comme extends HttpServlet {
    final int tableBorderWidth = 3;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>"
                    + "<title>Коментарий</title>"
                    + "<link href='style3.css' type='text/css' rel='stylesheet'/>"
                    + "</head><body>"
                    + "<h2>Оставте коментарий о состоянии велосипеда: </h2>"

                    //make sure method="post" so that the servlet service method
                    //calls doPost in the response to this form submit
                    +
                    "<form method=\"post\" action =\"" + request.getContextPath() +
                    "/Comme\" >"
                    + "<table border=\"10\"><tr><td valign=\"top\">"
                    + "Введите id велосипеда: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"idVelika\" size=\"20\">"
                    + "</td></tr><tr><td valign=\"top\">"
                    + "Введите id парковки, где стоит велосипед: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"idPark\" size=\"20\">"
                    + "</td></tr><tr><td valign=\"top\">"
                    + "Введите текст комментария: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"text\" size=\"20\">"
                    + "</td></tr><tr><td valign=\"top\">"
                    + "<input type=\"submit\" name=davay value=\"Добавить\"></td></tr>"
                    + "</table></form>"
                    + "</body></html>");

            try {
                String url = "jdbc:mysql://localhost/velo?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
                String username = "root";
                String password = "root";
                //PrintWriter out = response.getWriter();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection con = DriverManager.getConnection(url, username, password)) {
                    //String sqlQuery = "SELECT id,street,numbike FROM parkingpalces";
                    //out.println("Здесь2");


                    if (request.getParameter("davay") != null) {
                        //out.println("Pltcm2");
                        String sql = "INSERT INTO commets (idClie, idVelika, idPark, text) Values ( ?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                            HttpSession session = request.getSession(false);
                            int idClie = (int) session.getAttribute("userId");

                            preparedStatement.setInt(1, idClie);
                            preparedStatement.setInt(2, Integer.parseInt(request.getParameter("idVelika")));
                            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("idPark")));
                            preparedStatement.setString(4, request.getParameter("text"));
                            preparedStatement.executeUpdate();
                            out.println("Успешно!");
                            out.println("<form action=main>"
                                    + "Вернуться в главное меню-> "
                                    + "<input type=submit name=od value=OK>"
                                    + "</form>");
                        }
                    }

                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            out.println("</body>");
            out.println("</html>");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
