package src.request;

/*
 *@description: 生产图片验证码
 *@author: tom.cui
 *@date: 2020/3/23 13:59
 */
public class ValidateCodeReq {


    //宽 单位：像素
    private int width;
    //高 单位：像素
    private int height;
    //干扰线数量
    private int linSize;
    //字体，必须指定
    private String font;
    //字体大小 默认14像素
    private Integer fontSize;
    //图片上有几个字符
    private int length;
    //字符之间的横向间距 单位：像素 默认20
    private Integer fontDistance;


    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getFontDistance() {
        return fontDistance;
    }

    public void setFontDistance(Integer fontDistance) {
        this.fontDistance = fontDistance;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLinSize() {
        return linSize;
    }

    public void setLinSize(int linSize) {
        this.linSize = linSize;
    }

}
