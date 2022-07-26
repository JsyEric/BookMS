package com.jsy.action;

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
import java.util.Random;
@WebServlet(urlPatterns = "/code.let",loadOnStartup = 1)
public class ValCodeServlet extends HttpServlet {
    Random random = new Random();
    public String getRandomString(){
        String strList = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index =random.nextInt(strList.length());
            char letter = strList.charAt(index);
            stringBuilder.append(letter);
        }
        return stringBuilder.toString();
    }

    private Color getBackColor(){
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red,green,blue);
    }

    private Color getFrontColor(Color backColor){
        int red = 255 - backColor.getRed();
        int green = 255 - backColor.getGreen();
        int blue = 255 - backColor.getBlue();
        return new Color(red,green,blue);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg");

        BufferedImage bufferedImage = new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        Color bgColor = getBackColor();
        g.setColor(bgColor);
        g.fillRect(0,0,80,30);
        g.setColor(getFrontColor(bgColor));
        g.setFont(new Font("黑体",Font.BOLD,26));
        String randomStr = getRandomString();
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("code", randomStr);
        g.drawString(randomStr,10,28);

        for (int i = 0; i < 30; i++) {
            g.setColor(Color.white);
            int x = random.nextInt(80);
            int y = random.nextInt(30);
            g.fillRect(x,y,1,1);
        }

        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.setUseCache(false);
        ImageIO.write(bufferedImage,"jpeg", outputStream);

    }
}
