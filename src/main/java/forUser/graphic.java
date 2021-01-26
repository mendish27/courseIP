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
import java.io.PrintWriter;
import java.sql.*;
import java.util.Iterator;
import java.util.Vector;

@WebServlet("/graphic")
public class graphic extends HttpServlet {

    // внутренний статический класс для задания отступов графика
    static class OffSetImgBuffPlot {
        //static, иначе придется объявлять как (new BufferedImageCreating()).new OffSetImgBuffPlot(0.1);
        double offset;

        public OffSetImgBuffPlot(double offset) throws OffsetException {

            if (offset < 0.1 || offset > 0.5)
                throw new OffsetException();
            else
                this.offset = offset;
        }

        public double getOffset() {
            return offset;
        }

        // внутренний внутренний класс исключения
        public class OffsetException extends Exception {

            public OffsetException() {
                super("Неправильно задан отступ. Минимум: 0.1, максимум: 0.5.");
            }

        }

    }

    // вывод столбиковой диаграммы (по оси Y значения вектора <Integer>a)
    public static BufferedImage vectorToColumnsBarPlot(Vector<Integer> a,
                                                       int width, int height, double offset1percent) {

        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        int count = a.size();

        // максимальный элемент для дальнейшего определения высоты единицы
        // деления по Y
        int maxVectorElement = 0;
        for (Iterator iterator = a.iterator(); iterator.hasNext();) {

            Integer integer = (Integer) iterator.next();
            maxVectorElement = Math.max(maxVectorElement, integer);

        }

        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());// заливаем все белым

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.setFont(new Font("Serif", Font.BOLD, 14));

        int xAxisesYPosition = (int) (height * (1 - offset1percent));// уровень оси X по y
        int yAxisesXPosition = (int) (width * offset1percent);// уровень оси Y по x

        int intervalWidth = (int) (width * (1 - offset1percent) / count);// ширина интервала
        int columnsOnePointHeight = (int) (height * (1 - offset1percent) / maxVectorElement);// высота единицы деления по Y

        for (int i = 0; i < a.size(); i++) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(4));
            // набор координат
            int MarkX1 = yAxisesXPosition + intervalWidth * (i + 1);
            int MarkY1 = xAxisesYPosition;
            int MarkX2 = yAxisesXPosition + intervalWidth * (i + 1);
            int MarkY2 = xAxisesYPosition
                    + (int) (height * offset1percent * 0.5);

            int stringX = yAxisesXPosition + intervalWidth * i
                    + (int) (intervalWidth * 0.25);
            int stringY = xAxisesYPosition
                    + (int) (height * offset1percent * 0.75);

            int barX = yAxisesXPosition + intervalWidth * i;
            int barY = xAxisesYPosition - a.get(i) * columnsOnePointHeight;
            int barWidth = intervalWidth;
            int barHeight = a.get(i) * columnsOnePointHeight;
            // рисуем график
            g2.drawLine(MarkX1, MarkY1, MarkX2, MarkY2);// зарубки на оси X
            g2.setColor(Color.RED);
            g2.drawString(a.get(i).toString(), stringX, stringY);// обозначения чисел на X
            g2.setColor(Color.BLACK);
            g2.drawRect(barX, barY, barWidth, barHeight);// столбики

        }

        g2.dispose();
        return img;

    }

    protected static BufferedImage getImage(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage imgBuff3 = new BufferedImage(500, 500,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = imgBuff3.createGraphics();
        g2.setColor(Color.cyan);
        g2.setFont(new Font("Serif", Font.BOLD, 32));
        g2.drawString("График аренды по часам", 100, 30);
        g2.setStroke(new BasicStroke(5));
        /*g2.drawLine(100, 105, 400, 105);
        g2.drawLine(50, 150, 300, 150);
        g2.drawLine(50, 150, 50, 50);
        g2.drawOval(100, 100, 50, 50);*/

        // вывод графика
        Vector<Integer> imgVector = new Vector<Integer>();

        try{
            String url = "jdbc:mysql://localhost/mebel?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
            String username = "root";
            String password = "root";
            //PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Connection con = DriverManager.getConnection(url, username, password)) {
                //Statement statement = con.createStatement();
                //PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE idClient = ?");
                //ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE idClient = ?");

                try (PreparedStatement ps = con.prepareStatement("SELECT DISTINCT date FROM otzivi ORDER BY date")) {
                    //HttpSession session = request.getSession(false);
                    //int idClie = (int) session.getAttribute("userId");
                    //ps.setInt(1, idClie);
                    PreparedStatement ps2 = con.prepareStatement("SELECT date FROM otzivi ORDER BY date");
                    ResultSet resultSet = ps.executeQuery();
                    while(resultSet.next()){
                        resultSet.getDate("date");
                        ResultSet temp = ps2.executeQuery();
                        int hours = 0;
                        while (temp.next()) {
                            if (resultSet.getDate("date").equals(temp.getDate("date")))
                                hours += 1;
                            //resultSet.getInt(5);
                        }
                        imgVector.add(hours);
                    }
                }

            }
        }catch(Exception ex){
            System.out.println(ex);
        }

        OffSetImgBuffPlot offset;
        try {
            offset = new OffSetImgBuffPlot(0.1); // необходима подобная конструкция, т.к. внутренний класс
            g2.drawImage(// вставляем столбиковую диаграмму
                    vectorToColumnsBarPlot(imgVector, 200, 200,
                            offset.getOffset()), null, 100, 200);
        } catch (OffSetImgBuffPlot.OffsetException e) {
            e.printStackTrace();
        }
        g2.dispose();
        return imgBuff3;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*HttpSession session = request.getSession(false);
        int idClie = (int) session.getAttribute("userId");*/

        response.setContentType("image/jpeg ");
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(getImage(request, response), "jpeg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
