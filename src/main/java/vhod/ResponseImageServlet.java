package vhod;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/ResponseImageServlet")
public class ResponseImageServlet extends HttpServlet {
    private static class Imagination {
        private static BufferedImage drawGraphics() {
            BufferedImage imgBuff = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = imgBuff.createGraphics();
            g2.setFont(new Font("Serif", Font.ITALIC, 36));
            g2.setColor(Color.CYAN);
            g2.drawString("Велопрокат", 50, 50);
            g2.setColor(Color.MAGENTA);
            /*g2.setStroke(new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, new float[]{10.0f}, 0.0f));*/
            g2.drawLine(0, 180, 1000, 180);
            g2.drawOval(50,140,50,50);
            g2.drawOval(55,145,40,40);
            g2.drawOval(150,140,50,50);
            g2.drawOval(155,145,40,40);

            g2.setColor(Color.WHITE);
            g2.drawLine(55, 145, 80, 100);
            /*g2.setStroke(new BasicStroke());
            g2.setColor(Color.red);*/
            //g2.drawRect(70,30, 30,30);
            g2.setColor(Color.cyan);
            Polygon tr = new Polygon();
            tr.addPoint(100, 150);
            tr.addPoint(150, 100);
            tr.addPoint(200, 150);
            g2.drawPolygon(tr);
            g2.setPaint(new GradientPaint(250,100,Color.GREEN,350, 150,Color.YELLOW));
            /*g2.fill (new .Double(100, 120, 50, 20));*/
//            https://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html*/
            g2.dispose();
            return imgBuff;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");
        try(ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(Imagination.drawGraphics(), "jpeg", out);
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
