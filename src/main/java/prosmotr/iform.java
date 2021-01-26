package prosmotr;

import prosmotr.vspomogat.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/iform")
public class iform extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>"
                    +"<title align=\"center\">Просмотр данных</title><body>"
                    //+  "<link href='app.css' type='text/css' rel='stylesheet'/>"
                    + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                    //+ "<style> body { background-image: url(../../images/vel.png);}"
                    //+"<h2>Регистрация в системе: </h2>"
                    + "</head>");
            out.println("<h1 align=\"center\">Просмотр данных</h1>"
                    + "<table align=\"center\" style=\"margin-top: 5%\">"
                    + "<tr><td>"
                    + "<form action=iform>"
                    +" "
                    + "</td><td>"
                    + "<input type=submit name=clie value=\"Показать всех клиентов\">"
                    + "</td><td>"
                    + "<input type=submit name=otzivi value=\"Показать отзывы\">"
                    + "</td></form><form action=GetProducts><td>"
                    + "<input type=submit name=tovar value=\"Поиск товаров\">"
                    + "</td></form><form action=iform><td>"
                    + "<input type=submit name=osn value=\"Вернуться в основное меню\">"
                    + "</form>"
                    + "</td></tr>");
            try{
                String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
                String username = "root";
                String password = "root";
                //PrintWriter out = response.getWriter();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection con = DriverManager.getConnection(url, username, password)){
                    //String sqlQuery = "SELECT id,street,numbike FROM parkingpalces";
                    Statement statement = con.createStatement();

                    //writer.println("Connection to ProductDB succesfull!");
                    //String s = request.getParameter("pp");

                    if (request.getParameter("clie") != null){
                        //out.println("Cliets!");
                        ArrayList<Client> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE role ='user'");
                        while(resultSet.next()){
                            int idclients = resultSet.getInt(1);
                            String name = resultSet.getString(2);
                            String lastName = resultSet.getString(3);
                            String email = resultSet.getString(4);
                            String phone = resultSet.getString(5);
                            Client product = new Client(idclients, name, lastName, email, phone);
                            products.add(product);
                        }
                        vivodClie(products, out);
                    }
                    else if (request.getParameter("otzivi") != null){
                        ArrayList<Otzivski> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM otzivi");
                        while(resultSet.next()){
                            int id = resultSet.getInt(1);
                            String clientOt = resultSet.getString(2);
                            String text = resultSet.getString(3);
                            int zvezd = resultSet.getInt(4);
                            Date data = resultSet.getDate(5);

                            Otzivski product = new Otzivski(id, clientOt, text,zvezd, data);
                            products.add(product);
                        }
                        vivodOtz(products, out);
                    }
                    else if (request.getParameter("tovar") != null){
                        ArrayList<OrderMy> products = new ArrayList();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM tovar");
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
                        vivodTovar(products, out);
                    }
                    else if (request.getParameter("osn") != null){
                        response.sendRedirect("/osnova");
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

    protected void vivodTovar(ArrayList<OrderMy> orders, PrintWriter out){

        out.println("<table align=\"center\" >");//style="margin-top: 10%"
        out.println("<tr>");
        out.println("<td>");
        out.println("id заказа");
        out.println("</td>");
        out.println( "<td>" + " id клиента" +  "</td>" + "<td>" + "id велика" +  "</td>" + "<td>" + "id локации" +   "</td>" + "<td>" + "время использования + </td>");
        out.println("</tr>");
        for(OrderMy pp : orders){
            out.println("<br>");
            out.println("<tr>");
            out.println("<td>");
            out.println(pp.id);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.idCli);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.idVel);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.idLoc);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.timeForUse);
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }
    protected void vivodOtz(ArrayList<Otzivski> otzivi, PrintWriter out){
        out.println("<table  border=1 align=\"center\" >");
        //out.println("<br>");
        out.println("<tr>");
        out.println("<td>" +"id" +  "</td>" + "<td>" + "  наименование"+  "</td>" + "<td>" + "адресс" +  "</td>");
        //out.println("</td>");
        out.println("</tr>");
        for(Otzivski pp : otzivi){
            out.println("<br>");
            out.println("<tr>");
            out.println("<td>");
            out.println(pp.id);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.clientOt);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.text);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.zvezd);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.data);
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }
    protected void vivodClie(ArrayList<Client> clients, PrintWriter out){
        out.println("<table  border=1 align=\"center\" >");
        out.println("<tr>");
        out.println("<td>" + "id" +  "</td>" + "<td>" + "имя" +  "</td>" + "<td>" + "  Фамилия" +  "</td>" + "<td>" + "   email" +  "</td>" + "<td>" + "   Phone" + "</td>");
        //out.println("</td>");
        out.println("</tr>");
        for(Client pp : clients){
            out.println("<br>");
            out.println("<tr>");
            out.println("<td>");
            out.println(pp.id );
            out.println("</td>");
            out.println("<td>");
            out.println(pp.name );
            out.println("</td>");
            out.println("<td>");
            out.println(pp.last);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.email);
            out.println("</td>");
            out.println("<td>");
            out.println(pp.pho);
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
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
