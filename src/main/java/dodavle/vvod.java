package dodavle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

@WebServlet("/vvod")
public class vvod extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head>"
                    +"<title <table align=\"center\" style=\"margin-top: 10%\" border=\"1\">>Добавление данных</title><body>"
                    + "<link href='style32.css' type='text/css' rel='stylesheet'/>"
                    + "</head>");
            out.println("<br><h1 align=\"center\">Добавление данных</h1>"
                    + "<table border=\"1\" align=\"center\">"
                    + "<tr><form action=vvod><td>"
                    + ""
                    + "<input type=submit name=clei value=\"Добавить клиента\">"
                    + "</td>"
                    + "<td>"
                    + "<input type=submit name=tov value=\"Добавить товары\">"
                    + "</td><td>"
                    + "<input type=submit name=osn value=\"Вернуться в основное меню\">"
                    + "</form>"
                    + "</td></tr>");

            try{
                String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
                String username = "root";
                String password = "root";

                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection con = DriverManager.getConnection(url, username, password)) {
                    Statement statement = con.createStatement();

                        if (request.getParameter("clei") != null) {
                            out.println("<html><head>"
                                    + "<title>Ввод данных</title></head><body>"

                                    //make sure method="post" so that the servlet service method
                                    //calls doPost in the response to this form submit
                                    +
                                    "<form method=\"post\" action =\"" + request.getContextPath() +
                                    "/vvod\" >"
                                    + "<table align=\"center\">"
                                    + "<br><br><h2 align=\"center\">Добавление клиента</h2>"
                                    + "<tr><td valign=\"top\">"//border="10"
                                    + "Введите имя: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"firstname\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Введите фамилию: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"lastname\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Номер телефона: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"phone\" size=\"10\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Ваша почта: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"email\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Ваш пароль: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"password\" size=\"20\">"
                                    + "</td></tr></table><table align=\"center\"><tr><td valign=\"top\">"
                                    + "<input type=submit name=vvodClient value=Ввести></td></tr>"
                                    + "</table></form>"
                                    + "</body></html>");
                        }
                        else if (request.getParameter("vvodClient") != null) {
                            String sql2 = "INSERT INTO clients (name, lastName, email, phone, password) Values (?, ?, ?, ?, ?)";
                            try(PreparedStatement preparedStatement = con.prepareStatement(sql2)){

                                //preparedStatement.setInt(1, 4);
                                preparedStatement.setString(1, request.getParameter("firstname"));
                                preparedStatement.setString(2, request.getParameter("lastname"));
                                preparedStatement.setString(3, request.getParameter("email"));
                                preparedStatement.setInt(4, Integer.parseInt(request.getParameter("phone")));
                                preparedStatement.setString(5, request.getParameter("password"));
                                preparedStatement.executeUpdate();
                                out.println("Успешно добавился клиент в БД!");
                            }
                            catch (Exception ex){
                                out.println("Не вышло");
                                out.println(ex);
                            }
                        }
                        else if (request.getParameter("tov") != null) {
                            out.println("<html><head>"
                                    + "<title>Ввод данных</title></head><body>"

                                    //make sure method="post" so that the servlet service method
                                    //calls doPost in the response to this form submit
                                    +
                                    "<form method=\"post\" action =\"" + request.getContextPath() +
                                    "/vvod\" >"
                                    + "<table align=\"center\">"
                                    + "<br><br><h2 align=\"center\">Добавление товара: </h2>"
                                    + "<tr><td valign=\"top\">"//border="10"
                                    + "Введите наименование: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"tovarName\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Введите категорию товара: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"category\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Введите тип товара: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"type\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Введите количество: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"kolvo\" size=\"20\">"
                                    + "</td></tr><tr><td valign=\"top\">"
                                    + "Введите цену: </td>  <td valign=\"top\">"
                                    + "<input type=\"text\" name=\"price\" size=\"20\">"
                                    + "</td></tr></table><table align=\"center\"><tr><td>"// valign=\"top\">"
                                    + "<input type=submit name=vvodTovar value=Ввести></td></tr>"
                                    + "</table></form>"
                                    + "</body></html>");
                            //out.println("tut");
                            //out.println(request.getParameter("tov"));
                            //out.println(request.getParameter("vvvod"));
                        }
                        else if (request.getParameter("vvodTovar") != null) {
                            String sql1 = "INSERT INTO tovar (tovarName, category, type, kolvo, price) Values (?, ?, ?, ?, ?)";
                            //out.println("tut");
                            try (PreparedStatement preparedStatement = con.prepareStatement(sql1)) {

                                //preparedStatement.setInt(1, 4);
                                preparedStatement.setString(1, request.getParameter("tovarName"));
                                //out.println("ввел");
                                preparedStatement.setString(2, request.getParameter("category"));
                                //out.println("ввел");
                                preparedStatement.setString(3, request.getParameter("type"));
                                //out.println("ввел");
                                preparedStatement.setInt(4, Integer.parseInt(request.getParameter("kolvo")));//.getIntHeader("kolvo"));
                                //out.println("ввел");
                                preparedStatement.setDouble(5, Double.parseDouble(request.getParameter("price")));
                                preparedStatement.executeUpdate();
                                out.println("Успешно добавился товар в БД!");
                            }
                            catch (Exception ex){
                                out.println("Не вышло");
                                out.println(ex);
                            }
                        }
                        else if (request.getParameter("osn") != null) {
                            response.sendRedirect("osnova");
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
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
