package forAdm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@WebServlet("/comFrom")
public class comFrom extends HttpServlet {
    final int tableBorderWidth = 3;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>HomePage</title>"
                + "<link href='foundation.css' type='text/css' rel='stylesheet'/>"
                + "<link href='style3.css' type='text/css' rel='stylesheet'/>" +
                "</head>");
        out.println("<form action=osnova>"
                + "<input type=submit name=od value=\"Вернуться в главное меню\">"
                +  "</form>");
        try {
            String url = "jdbc:mysql://localhost/velo?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            //PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                out.println("<h2>Отзывы пользователей: </h2>");
                String sqlQuery = "SELECT idClie,text,idVelika, idPark FROM commets";
                PreparedStatement preparedStatement2 = con.prepareStatement(sqlQuery);
                ResultSet resotz = preparedStatement2.executeQuery();
                out.println("<table border=" + tableBorderWidth + " bgcolor=white>");
                out.println("<tr>");
                out.println("<td>");
                out.println("id клиента");
                out.println("</td>");
                out.println("<td>");
                out.println("id велосипеда");
                out.println("</td>");
                out.println("<td>");
                out.println("id парковки");
                out.println("</td>");
                out.println("<td>");
                out.println("Текст комментария");
                out.println("</td>");
                out.println("</tr>");
                while (resotz.next()) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(resotz.getInt("idClie"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getInt("idVelika"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getInt("idPark"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getString("text"));
                    //int i = resotz.getInt("kolZvezd");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                resotz.close();
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
    }
}
