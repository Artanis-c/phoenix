package src.util;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/*
 *@description: 字符串工具类
 *@author: tom.cui
 *@date: 2020/3/23 14:14
 */
public class StringUtil {

    public static boolean isNullOrWhiteSpace(String str){
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    public static String md5(String encodeString) {
        if (StringUtils.isEmpty(encodeString)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(encodeString.getBytes()).toUpperCase();
    }
}
