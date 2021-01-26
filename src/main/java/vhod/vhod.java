package vhod;

import secure.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/vhod")
public class vhod extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String userID = "admin";
    private String password = "password";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");

        try {
            PrintWriter out = response.getWriter();
            out.println("<body background=\"/mebel.jpg\"; width=\"100%\"; height=\"100%\"; background-repeat=no-repeat>");
            out.println("<html><head>" +"<title>Login Page</title>"
                    + "<link href='style71.css' type='text/css' rel='stylesheet'/>"
                    +"</head><body>" +
                    "<h2 align=center >Вход в систему</h2>");
            out.println("<form action=vhod>"
                    +"<table style=\"margin-top: 17%\" align=\"center\" opacity=\"0,5\"><tr><td valign=\"top\">"//border="10" bgcolor=white
                    +"Введите логин (ваш email): </td>  <td valign=\"top\">"
                    +"<input type=\"phone\" name=\"login\" size=\"20\">"
                    +"</td></tr><tr><td valign=\"top\">"
                    +"Введите пароль: </td>  <td valign=\"top\">"
                    +"<input type=\"password\" name=\"password\" size=\"20\">"
                    +"</td></tr><tr align=\"center\"><td valign=\"top\">"
                    + "<input align=\"center\" style=\"margin-left: 70%\" type=submit name=s1 value=Ok>"
                    //+ "<input type=hidden name=h1 value=\"hiddenValue\">"
                    +"</td></tr>"
                    + "</table></form>"
                    +"</body></html>");


            try{
                String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
                String username = "root";
                String password = "root";
                //PrintWriter out = response.getWriter();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                try (Connection con = DriverManager.getConnection(url, username, password)) {
                    Statement statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
                    //out.println("first");
                    if (request.getParameter("s1") != null){
                        ArrayList<User> hp = new ArrayList<>();
                        //out.println("first");
                        while (resultSet.next()){
                            String email = resultSet.getString(4);
                            String pwd = resultSet.getString(6);
                            int id = resultSet.getInt(1);
                            String role = resultSet.getString(7);
                            User user = new User(id, email, pwd, role);
                            hp.add(user);
                        }

                        String user = request.getParameter("login");
                        String pwd = request.getParameter("password");
                        String roleUs = "user";
                        String roleAdm = "admin";

                        for (User users : hp){
                            if ((users.login.equals(user)) && (users.password.equals(pwd)) && (users.role.equals("user"))){
                                HttpSession session = request.getSession();
                                session.setAttribute("idclients", users.id);
                                //session.setAttribute("name", users..id);
                                //setting session to expiry in 30 mins
                                session.setMaxInactiveInterval(30*60);
                                Cookie userName = new Cookie("user", user);
                                userName.setMaxAge(30*60);
                                response.addCookie(userName);
                                out.println("first");
                                response.sendRedirect("main");
                            } else if ((users.login.equals(user)) && (users.password.equals(pwd)) && (users.role.equals(roleAdm))){
                                HttpSession session = request.getSession();
                                session.setAttribute("userId", users.id);
                                //setting session to expiry in 30 mins
                                session.setMaxInactiveInterval(30*60);
                                Cookie userName = new Cookie("user", user);
                                userName.setMaxAge(30*60);
                                response.addCookie(userName);
                                out.println("sec");
                                response.sendRedirect("osnova");
                            }
                        }
                        response.sendRedirect("/vhod");
                    }
                }
            }catch(Exception ex){
                System.out.println(ex);
            }
            /*out.println("<form action=main>"
                    + "Просмотреть данные -> "
                    + "<input type=submit name=od value=OK>");*/
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
