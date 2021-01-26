package forUser;

import prosmotr.vspomogat.Client;
import prosmotr.vspomogat.OrderMy;
import prosmotr.vspomogat.ParkPlace;
import prosmotr.vspomogat.Tovar;

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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/check")
public class check extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>"
                    +"<title>Просмотр данных</title><body>"
                    + "<link href='style3.css' type='text/css' rel='stylesheet'/>"
                    + "</head>");
            out.println("<h1>Просмотр данных</h1>"
                    + "<table border=\"10\">"
                    + "<tr><td>"
                    + "<form action=check>"
                    + "<input type=submit name=pp value=\"Вывести все парковочные точки\">"
                    + "</td><td>"
                    + "<input type=submit name=orders value=\"Вывести мою историю аренды\">"
                    + "</td><td>"
                    + "<input type=submit name=graph value=\"Вывести график каждый аренды по часам\">"
                    + "</td><td>"
                    + "</td><td>"
                    + "<input type=submit name=secgraph value=\"Вывести количество аренды на рисунке\">"
                    + "</td><td>"
                    + "<input type=submit name=osn value=\"Вернуться в основное меню\">"
                    + "</form>"
                    + "</td></tr>");

            try{
                String url = "jdbc:mysql://localhost/velo?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
                String username = "root";
                String password = "root";
                //PrintWriter out = response.getWriter();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection con = DriverManager.getConnection(url, username, password)){
                    //String sqlQuery = "SELECT id,street,numbike FROM parkingpalces";
                    Statement statement = con.createStatement();

                    //writer.println("Connection to ProductDB succesfull!");
                    //String s = request.getParameter("pp");

                    if (request.getParameter("pp") != null){
                        ArrayList<ParkPlace> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM parkingplaces");
                        while(resultSet.next()){
                            int id = resultSet.getInt(1);
                            String name = resultSet.getString(2);
                            int price = resultSet.getInt(3);
                            ParkPlace product = new ParkPlace(id, name, price);
                            products.add(product);
                        }
                        vivod(products, out);
                    }
                    else if (request.getParameter("clie") != null){
                        //out.println("Cliets!");
                        ArrayList<Client> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
                        while(resultSet.next()){
                            int id = resultSet.getInt(1);
                            String name = resultSet.getString(2);
                            String lastname = resultSet.getString(3);
                            String email = resultSet.getString(4);
                            String phone = resultSet.getString(5);
                            Client product = new Client(id, name, lastname, email, phone);
                            products.add(product);
                        }
                        vivodClie(products, out);
                    }
                    else if (request.getParameter("veliki") != null){
                        ArrayList<Tovar> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM veliki");
                        while(resultSet.next()){
                            int id = resultSet.getInt(1);
                            String name = resultSet.getString(2);
                            String category = resultSet.getString(3);
                            String type = resultSet.getString(4);
                            int kolvo = resultSet.getInt(5);
                            double price = resultSet.getDouble(6);
                            Tovar product = new Tovar(id, name, category,type, kolvo, price);
                            products.add(product);
                        }
                        vivodVel(products, out);
                    }
                    else if (request.getParameter("orders") != null){
                        ArrayList<OrderMy> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
                        while(resultSet.next()){
                            int id = resultSet.getInt(1);
                            int idCli = resultSet.getInt(2);
                            int idVel = resultSet.getInt(3);
                            int idLoc = resultSet.getInt(4);
                            int time = resultSet.getInt(5);
                            int price = resultSet.getInt(6);
                            OrderMy product = new OrderMy(id, idCli, idVel, idLoc, time, price);
                            products.add(product);
                        }
                        vivodOrders(products, out, request);
                    }
                    else if (request.getParameter("osn") != null){
                        response.sendRedirect("/main");
                    }
                    else if (request.getParameter("graph") != null){
                        response.sendRedirect("/graphic");
                    }
                    else if (request.getParameter("secgraph") != null){
                        response.sendRedirect("/secGraphic");
                    }
                }
            }
            catch(Exception ex){
                out.println(ex);
            }
            finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void vivodOrders(ArrayList<OrderMy> orders, PrintWriter out, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        int idClie = (int) session.getAttribute("userId");

        out.println("<table border=\"1\">");
        //out.println("<br>");
        out.println("<tr>");
        out.println("<td>" + "id заказа" +"</td>" + "<td>"+  "id велика" +"</td>"+ "<td>" +  "id локации" +"</td>" + "<td>"+  "время использования" +"</td>" + "<td>"+  "Цена аренды" + "</td>");
        //out.println("</td>");
        out.println("</tr>");
        for(OrderMy pp : orders){
            if(idClie == pp.idCli) {
                out.println("<br>");
                out.println("<tr>");
                out.println("<td>");
                out.println(pp.idVel);
                out.println("</td>");
                out.println("<td>");
                out.println(pp.idCli + " | ");
                out.println("</td>");
                out.println("<td>");
                out.println(pp.idLoc);
                out.println("</td>");
                out.println("<td>");
                out.println(pp.timeForUse + " | ");
                out.println("</td>");
                out.println("<td>");
                out.println(pp.price);
                out.println("</td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
    }
    protected void vivodVel(ArrayList<Tovar> tovars, PrintWriter out){
        out.println("<br>");
        out.println("<tr>");
        out.println("id | " + "  наименование | " + "  адресс  ");
        out.println("</td>");
        out.println("</tr>");
        for(Tovar pp : tovars){
            out.println("<br>");
            out.println("<tr>");
            out.println("<td>");
            out.println(pp.id + " | ");
            out.println("</td>");
            out.println("<td>");
            out.println(pp.name + " | ");
            out.println("</td>");
            out.println("<td>");
            out.println(pp.category+ " | ");
            out.println("</td>");
            out.println("</tr>");
        }
    }
    protected void vivodClie(ArrayList<Client> clients, PrintWriter out){
        out.println("<table>");
        out.println("<br>");
        out.println("<tr>");
        out.println("id | " + "  имя | " + "  Фамилия  | " + "   email   |  " + "   Phone  ");
        out.println("</td>");
        out.println("</tr>");
        for(Client pp : clients){
            out.println("<br>");
            out.println("<tr>");
            out.println("<td>");
            out.println(pp.id + " | ");
            out.println("</td>");
            out.println("<td>");
            out.println(pp.name + " | ");
            out.println("</td>");
            out.println("<td>");
            out.println(pp.last+ " | ");
            out.println("</td>");
            out.println("<td>");
            out.println(pp.email+ " | ");
            out.println("</td>");
            out.println("<td>");
            out.println(pp.pho+ " | ");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
        }
    }
    protected void vivod(ArrayList<ParkPlace> products, PrintWriter out){
        out.println("<table border=\"10\">");
        //out.println("<tr>");
        out.println("<tr><td>" + "id" +  "</td>" +  "<td>" + "  улица" +  "</td>" + "<td>" + "  количество велосипедов"+ "</td></tr>");
        //out.println("</td>");
        //out.println("</tr>");
        for(ParkPlace pp : products){
            out.println("<br>");
            out.println("<tr>");
            out.println("<td>");
            out.println(pp.id);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.street);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.numbike);
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
