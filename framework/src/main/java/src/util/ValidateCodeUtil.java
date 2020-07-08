package src.util;

import src.Response.ValidateCodeResponse;
import src.request.ValidateCodeReq;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
 *@description: 验证码工具类
 *@author: tom.cui
 *@date: 2020/3/23 13:58
 */
public class ValidateCodeUtil {


    private static String randomString = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * @description: 获取字体
     * @author: tom.cui
     * @date: 2020/3/22-18:29
     */
    private static Font getFont(String fontName, Integer fontSize) {
        if (StringUtil.isNullOrWhiteSpace(fontName)){
            fontName="Times New Roman";
        }
        if (fontSize==null){
            fontSize=14;
        }
        return new Font(fontName, Font.ROMAN_BASELINE, fontSize);
    }

    /**
     * @description: 获取字体颜色和背景颜色
     * @author: tom.cui
     * @date: 2020/3/22-18:28
     */
    private static Color getRandomColor(Integer fontColor, Integer backColor) {
        if (fontColor == null) {
            fontColor = 255;
        }
        if (backColor == null) {
            backColor = 255;
        }
        Random random = new Random();
        int r = fontColor + random.nextInt(backColor - fontColor - 16);
        int g = fontColor + random.nextInt(backColor - fontColor - 14);
        int b = fontColor + random.nextInt(backColor - fontColor - 12);
        return new Color(r, g, b);
    }

    /**
     * @description: 绘制干扰线
     * @author: tom.cui
     * @date: 2020/3/22-18:33
     */
    private static void drawLine(Graphics g, ValidateCodeReq req) {
        Random random = new Random();
        int x = random.nextInt(req.getWidth());
        int y = random.nextInt(req.getHeight());
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * @description: 获取随机字符串
     * @author: tom.cui
     * @date: 2020/3/22-18:34
     */
    private static String getRandomString(int num) {
        if (num > 36) {
            num = 36;
        }
        num = num > 0 ? num : randomString.length();
        Random random = new Random();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

    private static String drawString(Graphics g, String randomStr, int i, ValidateCodeReq req) {
        g.setFont(getFont(req.getFont(),req.getFontSize()));
        g.setColor(getRandomColor(108, 190));
        Random random = new Random();
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, req.getFontDistance() * i + 10, 25);
        return randomStr;
    }


    public static ValidateCodeResponse getImgCodeBaseCode( ValidateCodeReq req) {
        if (req.getLength()==0){
            req.setLength(4);
        }
        if (req.getFontDistance()==null){
            req.setFontDistance(20);
        }
        if (req.getWidth()==0){
             req.setWidth(100);
        }
        if (req.getHeight()==0){
            req.setHeight(40);
        }
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(req.getWidth(), req.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, req.getWidth(), req.getHeight());
        // 获取颜色
        g.setColor(getRandomColor(105, 189));
        // 获取字体
        g.setFont(getFont(req.getFont(),req.getFontSize()));
        // 绘制干扰线
        for (int i = 0; i < req.getLinSize(); i++) {
            drawLine(g,req);
        }
        // 绘制随机字符
        String randomCode = "";
        for (int i = 0; i < req.getLength(); i++) {
            randomCode = drawString(g, randomCode, i, req);
        }
        System.out.println("验证码是：" + randomCode);
        g.dispose();
        ValidateCodeResponse result = new ValidateCodeResponse();
        result.setCode(randomCode);
        result.setImage(image);
        return result;
    }

}
