package forUser;

import prosmotr.vspomogat.Client;
import prosmotr.vspomogat.Otzivski;
import prosmotr.vspomogat.ParkPlace;


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
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/Otziv")
public class Otziv extends HttpServlet {
    protected String name;
    final int tableBorderWidth = 3;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //out.println("<body background=\"vel1.jpg\"; width=\"100%; height=100%\">");
            out.println("<html><head>"
                    + "<title>Отзывы</title>"
                    + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                    + "</head><body>"
                    + "<h2 align=\"center\">Отзыв</h2>"

                    //make sure method="post" so that the servlet service method
                    //calls doPost in the response to this form submit
                    +
                    "<form method=\"post\" action =\"" + request.getContextPath() +
                    "/Otziv\" >"
                    + "<table align=\"center\">"
                    + "<tr><td valign=\"top\">"// border="1"
                    + "<p><b>Введите ваш отзыв:</b></p>"
                    + "<p><textarea rows=\"10\" cols=\"65\" name=\"text\"></textarea></p>"
                    //+ "Введите текст отзыва: </td>"//  <td valign=\"top\">"
                    //+ "<input type=\"text\" name=\"text\" size=\"20\">"
                    + "</td>"
                    + "</tr></table><table align=\"center\">"
                    + "<tr><td valign=\"top\">"
                    + "Введите количество звезд от 0 до 5: </td>  <td valign=\"top\">"
                    + "<input type=\"text\" name=\"kolZvezd\" size=\"20\">"
                    + "</td></tr></table><table align=\"center\"><tr><td valign=\"top\">"
                    + "<input type=\"submit\" name=davay value=\"Добавить\"></td></tr>"
                    + "</table></form>"
                    + "</body></html>");

            try {
                String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
                String username = "root";
                String password = "root";
                //PrintWriter out = response.getWriter();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                //out.println("tut1");
                try (Connection con = DriverManager.getConnection(url, username, password)) {
                    //String sqlQuery = "SELECT id,street,numbike FROM parkingpalces";

                    HttpSession session = request.getSession();
                    //out.println("Здесь2");
                    //out.println(session.getAttribute("idclients"));
                    //out.println("sldad");
                    //out.println(session.getAttribute("name"));
                    //HttpSession session = request.getSession(false);
                    int idClie = (int) session.getAttribute("idclients");
                    //out.println("tut2");
                    try {
                        String sql1 = "SELECT * FROM clients WHERE idclients=?";
                        PreparedStatement preparedStatement1 = con.prepareStatement(sql1);
                        preparedStatement1.setInt(1, idClie);
                        ResultSet resultSet1 = preparedStatement1.executeQuery();
                        //out.println("tut3");
                        if (resultSet1.next()){
                            name = resultSet1.getString(2);
                        }
                        out.println(name);
                    } catch (SQLException throwables) {
                        out.println(throwables);
                    }

                    //out.println("tut4");
                    if (request.getParameter("davay") != null) {
                        //out.println("Pltcm2");
                        //out.println("tut5");
                        String sql = "INSERT INTO otzivi (clientOt, text, zvezd, date) Values (?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                            //preparedStatement.setInt(1, idClie);
                            //out.println("tut6");
                            //out.println(Date.valueOf(LocalDate.now()));
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, request.getParameter("text"));
                            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("kolZvezd")));
                            //out.println(Date.valueOf(LocalDate.now()));
                            preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
                            preparedStatement.executeUpdate();
                            out.println("<div align=\"center\">Успешно!</div>");
                            out.println("<form action=Otziv><div align=\"center\">"
                                            //+ "Отзывы о работе проката" + "</td><td>"
                                            + "<input type=submit name=optio value=\"Обновить\">"
                                            +  "</div></form>");/*
                                    "<form action=main><div align=\"center\">"
                                    + "Вернуться к отзывам-> "
                                    + "<input type=submit name=od value=OK>"
                                    + "</div></form>");*/
                        }
                    }
                    out.println("<h2 align=\"center\">Отзывы пользователей: </h2>");

                    try {
                        String sqlQuery = "SELECT text,zvezd, clientOt, date FROM otzivi";
                        PreparedStatement preparedStatement2 = con.prepareStatement(sqlQuery);
                        ResultSet resotz = preparedStatement2.executeQuery();
                        out.println("<table border=\"1\"   align=\"center\">");
                        while (resotz.next()) {
                            out.println("<tr>");
                            out.println("<td>");
                            out.println(resotz.getString("clientOt"));
                            out.println("</td>");
                            out.println("<td>");
                            out.println(resotz.getString("text"));
                            out.println("</td>");
                            out.println("<td>");
                            int i = resotz.getInt("zvezd");
                            while (i > 0){
                                out.print("&#11088;");
                                i--;
                            }
                            out.println("</td>");
                            out.println("<td>");
                            out.println(resotz.getDate("date"));
                            out.println("</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                        resotz.close();
                    } catch (SQLException throwables) {
                        out.println(throwables);
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
