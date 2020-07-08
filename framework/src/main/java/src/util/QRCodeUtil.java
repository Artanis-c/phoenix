package src.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import src.request.CreateQRCodeReq;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/*
 *@description: 二维码工具类
 *@author: tom.cui
 *@date: 2020/3/23 13:51
 */
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";

    /**
    *@description: 生成二维码
    *@author: tom.cui
    *@date: 2020/3/23-13:57
    */
    public static BufferedImage  createQRCode(CreateQRCodeReq request) throws Exception {
        HashMap<EncodeHintType, Object> hashmap = new HashMap<EncodeHintType, Object>();
        hashmap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hashmap.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hashmap.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(request.getContent(),
                BarcodeFormat.QR_CODE, request.getCodeSize(), request.getCodeSize(), hashmap);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        //是否在二维码中间插入logo
        if (request.isInsertLogo()) {
            insertImage(image, request);
        }
        return image;
    }

    private static void insertImage(BufferedImage source, CreateQRCodeReq request) throws Exception {

        File file = new File(request.getLogoPath());
        if (!file.exists()) {
            System.err.println("" + request.getLogoPath() + "  该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(request.getLogoPath()));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (true) { // 压缩LOGO
            if (width > request.getLogoWidth()) {
                width = request.getLogoWidth();
            }
            if (height > request.getLogoHight()) {
                height = request.getLogoHight();
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (request.getCodeSize() - width) / 2;
        int y = (request.getCodeSize() - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
}
