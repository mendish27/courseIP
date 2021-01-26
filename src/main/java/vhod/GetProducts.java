package vhod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@WebServlet("/GetProducts")
public class GetProducts extends HttpServlet {

    private interface Names
    {
        String url = "jdbc:mysql://localhost:3306/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
        String sqlQuery = "SELECT idTovar,tovarName, price, rating FROM tovar";
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
            /*out.println(""
                    + "<form action=GetProducts method=GET>"
                    + "Товары"
                    /*+ " "
                    + name
                    + " "
                    + min
                    + " "
                    + max
                    + " "
                    + rating*/
                    /*+ "<select name=" + Names.selectProductsNumber + ">"
                    + "<option>5</option><option>10</option><option>20</option><option>30</option><option>50</option><option>1000</option>"
                    + "</select>"
                    + "<input type=submit name=submitNumber value=\"Вывести продукты\">"
                    + "</form>");*/
                //Class.forName("com.mysql.cj.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection con = DriverManager.getConnection(Names.url, Names.username, Names.password)) {
                    PreparedStatement preparedStatement = con.prepareStatement(Names.sqlQuery);
                    //preparedStatement.setInt(1, Names.sqlQuery.length());
                    ResultSet productsResultSet = preparedStatement.executeQuery();
                    out.println("<head>"
                            + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                            +"</head>");
                    out.println("<table border=3>");
                    out.println("<tr>");
                    out.println("<td width=\"1000\" height=\"40\">");
                    out.println("Название");
                    out.println("</td>");
                    out.println("<td width=\"300\">");
                    out.println("Цена");
                    out.println("</td>");
                    out.println("<td width=\"200\">");
                    out.println("Оценка 0-10");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<table>");

                    out.println("<form action=GetProducts method=GET>");
                    out.println("<table border=3>");
                    out.println("<tr>");
                    out.println("<td width=\"1000\" height=\"40\">");
                    out.println("Поиск");
                    out.println("<input type=\"text\" name=\"Name\" size=\"135\">");
                    out.println("<input type=submit name=submitName value=\"Ок\">");
                    out.println("</td>");
                    out.println("<td width=\"300\">");
                    out.println("От");
                    out.println("<input type=\"text\" name=\"PriceMin\" size=\"10\">");
                    out.println("До");
                    out.println("<input type=\"text\" name=\"PriceMax\" size=\"10\">");
                    out.println("<input type=submit name=submitPrice value=\"Ок\">");
                    out.println("</td>");
                    out.println("<td width=\"200\">");
                    out.println("От");
                    out.println("<input type=\"text\" name=\"Rating\" size=\"10\">");
                    out.println("<input type=submit name=submitRating value=\"Ок\">");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<table>");
                    out.println("</form>");

                    if ((request.getParameter("Name") != null) && (request.getParameter("Name") != "")){
                        name = request.getParameter("Name");
                    }
                    if ((request.getParameter("PriceMin") != null) && (request.getParameter("PriceMin") != "")){
                        min = Integer.parseInt(request.getParameter("PriceMin"));
                    }
                    if ((request.getParameter("PriceMax") != null) && (request.getParameter("PriceMax") != "")){
                        max = Integer.parseInt(request.getParameter("PriceMax"));
                    }
                    if ((request.getParameter("Rating") != null) && (request.getParameter("Rating") != "")) {
                        rating = Integer.parseInt(request.getParameter("Rating"));
                    }

                    out.println("<table border=3>");
                    while (productsResultSet.next()) {
                        /*out.println("<tr>");
                        //out.println(productsResultSet.getString("idTovar"));
                        out.println("<td width=\"1000\" height=\"40\">");
                        out.println(productsResultSet.getString("tovarName"));
                        out.println("</td>");
                        out.println("<td width=\"300\">");
                        out.println(productsResultSet.getString("price"));
                        out.println("</td>");
                        out.println("<td width=\"200\">");

                        out.println("HEEEEY");
                        out.println(productsResultSet.getString("rating") + "/10");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println(name);
                        out.println(min);
                        out.println(max);
                        out.println(rating);
                        out.println(productsResultSet.getString("tovarName"));
                        out.println(productsResultSet.getInt("price"));
                        out.println(productsResultSet.getString("rating"));
                        out.println("hey");
                        out.println(productsResultSet.getString("tovarName").contains(name));
                        if (Math.min(min, productsResultSet.getInt("price")) == min)
                            out.println("govno");
                        //out.println(Math.min(min, Integer.parseInt(productsResultSet.getString("price"))) == min);
                        //out.println(max >= Integer.parseInt(productsResultSet.getString("price")));
                        out.println(rating <= Integer.parseInt(productsResultSet.getString("rating")));*/
                        //if (name != "")
                        if ((name != "" ? (productsResultSet.getString("tovarName").contains(name)) : true) &&
                                (min <= productsResultSet.getInt("price")) &&
                                (max >= productsResultSet.getInt("price")) &&
                                (rating <= Integer.parseInt(productsResultSet.getString("rating")))) {
                            //out.println("HEEEEY");
                            out.println("<tr>");
                            //out.println(productsResultSet.getString("idTovar"));
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
                            /*out.println("<tr>");
                            //out.println(productsResultSet.getString("idTovar"));
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
                            /*
                            out.println("<form action=ViewProduct method=GET>");
                            out.println("<tr>");
                            out.println("<td width=\"1000\" height=\"40\">");
                            out.println("<input type=hidden name=numberProduct value=" + productsResultSet.getString("idTovar") + ">");
                            out.println("<input type=submit name=submitProduct value=\"→\">");
                            out.println(productsResultSet.getString("tovarName"));
                            //out.println(productsResultSet.getString("id"));
                            out.println("</td>");
                            out.println("<td width=\"300\">");
                            out.println(productsResultSet.getString("price"));
                            out.println("</td>");
                            out.println("<td width=\"200\">");
                            out.println(productsResultSet.getString("rating") + "/10");
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("</form>");*/
                        }
                    }
                    out.println("</table>");
                    productsResultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            out.println("</body>");
            out.println("</html>");
            } catch (NumberFormatException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
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
