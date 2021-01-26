package forUser;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import static forUser.graphic.vectorToColumnsBarPlot;

@WebServlet("/secGraphic")
public class secGraphic extends HttpServlet {

    protected static BufferedImage getImage(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage imgBuff3 = new BufferedImage(500, 500,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = imgBuff3.createGraphics();
        g2.setColor(Color.cyan);
        g2.setFont(new Font("Serif", Font.BOLD, 32));
        g2.drawString("Количество аренды", 100, 30);
        g2.setStroke(new BasicStroke(5));
        /*g2.drawLine(100, 105, 400, 105);
        g2.drawLine(50, 150, 300, 150);
        g2.drawLine(50, 150, 50, 50);
        g2.drawOval(100, 100, 50, 50);*/

        // вывод графика
        Vector<Integer> imgVector = new Vector<Integer>();

        try{
            String url = "jdbc:mysql://localhost/velo?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            //PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                //Statement statement = con.createStatement();
                //PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE idClient = ?");
                //ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE idClient = ?");
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE idClie = ?")) {
                    HttpSession session = request.getSession(false);
                    int idClie = (int) session.getAttribute("userId");
                    ps.setInt(1, idClie);
                    ResultSet resultSet = ps.executeQuery();
                    /*while(resultSet.next()){
                        int hours = resultSet.getInt(5);
                        imgVector.add(hours);
                    }*/
                    int order = 0;
                    while(resultSet.next()){
                        order += 1;
                    }
                    int x = 20;
                    while (order != 0){
                        g2.setColor(Color.MAGENTA);
                        g2.drawOval(x,250,50,50);
                        x += 60;
                        order--;
                    }
                }

            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        g2.dispose();
        return imgBuff3;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg ");
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(getImage(request, response), "jpeg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
