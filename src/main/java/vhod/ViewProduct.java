package vhod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ViewProduct")
public class ViewProduct extends HttpServlet {

    private interface Names
    {
        String url = "jdbc:mysql://localhost:3306/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
        String sqlQuery = "SELECT idTovar,tovarName, price, rating FROM tovar WHERE id<=?";
        String username = "root";
        String password = "root";
    }
    final int tableBorderWidth = 3;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        String name = "";
        int min = 0;
        int max = Integer.MAX_VALUE;
        int rating = 0;
        try (PrintWriter out = response.getWriter()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection con = DriverManager.getConnection(Names.url, Names.username, Names.password);
                     PreparedStatement preparedStatement = con.prepareStatement(Names.sqlQuery);
                ) {
                    preparedStatement.setInt(1, Names.sqlQuery.length());
                    ResultSet productsResultSet = preparedStatement.executeQuery();

                    out.println("<table border=3>");
                    while (productsResultSet.next()) {
                        if (Integer.parseInt(request.getParameter("numberProduct")) == Integer.parseInt(productsResultSet.getString("idTovar"))) {
                            out.println("<tr>");
                            out.println(request.getParameter("numberProduct"));
                            out.println("<td width=\"1000\" height=\"40\">");
                            out.println(productsResultSet.getString("tovarName"));
                            out.println("</td>");
                            out.println("<td width=\"300\">");
                            out.println(productsResultSet.getString("price"));
                            out.println("</td>");
                            out.println("<td width=\"200\">");
                            out.println(productsResultSet.getString("rating") + "/10");
                            out.println("</td>");
                            out.println("</tr>");
                        }
                    }
                    out.println("<table>");
                    productsResultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            //out.println("</body>");
            //out.println("</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
