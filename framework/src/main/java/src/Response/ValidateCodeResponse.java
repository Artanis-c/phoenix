package src.Response;

import java.awt.image.BufferedImage;

/*
 *@description: 验证码
 *@author: tom.cui
 *@date: 2020/3/23 14:12
 */
public class ValidateCodeResponse {
    /**
     *@description: 图片流
     *@author: tom.cui
     *@date: 2020/3/22-18:42
     */
    private BufferedImage image;


    /**
     *@description: 验证码
     *@author: tom.cui
     *@date: 2020/3/22-18:42
     */
    private String code;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
