package vhod;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.CallableStatement;

@WebServlet("/homepage")
public class homepage extends HttpServlet {
    final int tableBorderWidth = 10;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>HomePage</title>"
                    + "<link href='foundation.css' type='text/css' rel='stylesheet'/>"
                    + "<link href='style01.css' type='text/css' rel='stylesheet'/>"
                    +"</head>");
            out.println("<body>");
            out.println("<table> <tr><td>" + "<img src= \"2.jpg\">" + "</td>");
            out.println("<td><h1>Добро пожаловать в мебельный магазин GUTМебель</h1></td></tr></table>");
            /*out.println("<br><img src=\"ResponseImageServlet\">" +
                    "<h1>Добро пожаловать в информационную систему для велопроката</h1><br>" );*/
            /*out.println("<img src= \"/src/main/java/vhod/vel.jpg\">");
            out.println("<h1>Добро пожаловать в информационную систему для велопроката</h1><br>");*/
            out.println("</body></html>");
            /*PrintWriter out = response.getWriter();*/
            /*out.println("<html><head>" +"<title>FirstLab</title></head><body>" +
                    "<h2>Велопрокат</h2>"); border="5" bgcolor=red*/
            out.println("<table><tr><td width=\"20px\">"
                    + "<form action=regestration>" +
                    "<input type=submit name=reg value=Зарегестрироваться>"+ "</form>"
                    + "</td>");
            out.println("<td>"
                    + "<form action=vhod>"
                    + "<input type=submit name=vhod value=Войти>"
                    + "</td></tr>"
                    + "</table>"
                    + "</form>");
            String url = "jdbc:mysql://localhost:3306/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            //PrintWriter out = response.getWriter();
            //out.println("<table>");
            out.println(//"<table><tr><td width=\"20px\">"
                     "<form action=GetProducts>" +
                    "<p style=\"margin-left: 10px\"><input type=submit name=search value=\"Поиск товаров\"></p>"+ "</form>");
                    //+ "</td></tr></table>");
            out.println("<h2>Все товары: </h2>");
            out.println("<tr>");
            out.println("<td>");
            out.println("_Код__");
            out.println("</td>");
            out.println("<td>");
            out.println("Название товара________________________________");
            out.println("</td>");
            out.println("<td>");
            out.println("Категория______");
            out.println("</td>");
            out.println("<td>");
            out.println("______Тип____________");
            out.println("</td>");
            out.println("<td>");
            out.println("_______Кол-во___");
            out.println("</td>");
            out.println("<td>");
            out.println("Цена");
            out.println("</td>");
            out.println("</tr>");
            //out.println("</table>");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                String sqlQuery = "SELECT idTovar,tovarName, category, type, kolvo, price FROM tovar";
                PreparedStatement preparedStatement2 = con.prepareStatement(sqlQuery);
                ResultSet resotz = preparedStatement2.executeQuery();
                out.println("<table>");// border=" + tableBorderWidth + " bgcolor=red>");
                while (resotz.next()) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(resotz.getInt("idTovar"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getString("tovarName"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getString("category"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getString("type"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getInt("kolvo"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println(resotz.getDouble("price"));
                    out.println("</td>");
                    //out.println("<td>");
                    //Blob imageBlob = resotz.getBlob("image");
                    //byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                    //BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    //imageBlob.getBinaryStream()
                    //BufferedImage myPicture = ImageIO.;
                    //out.println(img);
                    ///response.setContentType("image/png");
                    //ImageIO.write(img, "jpg", response.getOutputStream());
                    //out.print(ImageIO.createImageOutputStream(img));
                    //out.println(imageBytes);
                    // out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                resotz.close();
            } catch (SQLException throwables) {
                out.println(throwables);
            }

            out.println("<h2>Отзывы пользователей: </h2>");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                String sqlQuery = "SELECT text,zvezd,clientOt, date FROM otzivi";
                PreparedStatement preparedStatement2 = con.prepareStatement(sqlQuery);
                ResultSet resotz = preparedStatement2.executeQuery();
                out.println("<table>");// border=" + tableBorderWidth + " bgcolor=red>");
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

            /*if (request.getParameter("s1") != null){
                response.sendRedirect("/regestration");
            } else if (request.getParameter("s2") != null) {
                response.sendRedirect("/vhod");
            }*/
        }catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


}
