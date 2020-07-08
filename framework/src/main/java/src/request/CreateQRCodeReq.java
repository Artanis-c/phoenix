package src.request;

/*
 *@description: 生成二维码
 *@author: tom.cui
 *@date: 2020/3/23 13:53
 */
public class CreateQRCodeReq {
    //二维码大小
    private int codeSize;
    //二维码内容
    private String content;
    //是否要在二维码中间插入Logo
    private boolean insertLogo;
    //logo地址
    private String logoPath;
    //logo宽度
    private int logoWidth;
    //logo高度
    private int logoHight;

    public int getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(int codeSize) {
        this.codeSize = codeSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isInsertLogo() {
        return insertLogo;
    }

    public void setInsertLogo(boolean insertLogo) {
        this.insertLogo = insertLogo;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public int getLogoHight() {
        return logoHight;
    }

    public void setLogoHight(int logoHight) {
        this.logoHight = logoHight;
    }

}
