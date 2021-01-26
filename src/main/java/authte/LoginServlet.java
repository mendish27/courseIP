package authte;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    //http
    private static final long serialVersionUID = 1L;
    private String userID = "admin";
    private String password = "password";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // get request parameters for userID and password
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        if(userID.equals(user) && password.equals(pwd)){
            HttpSession session = request.getSession();
            session.setAttribute("user", "Pankaj");
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(30*60);
            response.addCookie(userName);
            response.sendRedirect("LoginSuccess.jsp");
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
        }

    }
     //cookies
        /*private static final long serialVersionUID = 1L;
        private final String userID = "Pankaj";
        private final String password = "journaldev";

        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {

            // get request parameters for userID and password
            String user = request.getParameter("user");
            String pwd = request.getParameter("pwd");

            if(userID.equals(user) && password.equals(pwd)){
                Cookie loginCookie = new Cookie("user",user);
                //setting cookie to expiry in 30 mins
                loginCookie.setMaxAge(30*60);
                response.addCookie(loginCookie);
                response.sendRedirect("LoginSuccess.jsp");
            }else{
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out= response.getWriter();
                out.println("<font color=red>Either user name or password is wrong.</font>");
                rd.include(request, response);
            }

        }*/
}
