package src.orm.until;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
 *@description: SQL帮助类
 *@author: tom.cui
 *@date: 2020/3/23 10:57
 */
@Component
public class SqlHelper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${mybatis-plus.configuration.map-underscore-to-camel-case}")
    private boolean mapUnderscoreToCamelCase;

    @Autowired
    SqlHelper sqlHelper;


    public static boolean checkBool(Integer req) {
        return req != null && req > 0;
    }


    /**
    *@author: tom.cui
    *@date: 2020/4/16
    *@description: 字段名称转换
    */
    public  String parseFiledString(String filedName) {
        if (mapUnderscoreToCamelCase) {
            StringBuilder result = new StringBuilder();
            if (filedName != null && filedName.length() > 0) {
                // 将第一个字符处理成小写
                result.append(filedName.substring(0, 1).toLowerCase());
                // 循环处理其余字符
                for (int i = 1; i < filedName.length(); i++) {
                    String s = filedName.substring(i, i + 1);
                    // 在大写字母前添加下划线
                    if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                        result.append("_");
                    }
                    // 其他字符直接转成小写
                    result.append(s.toLowerCase());
                }
            }
            return result.toString();
        } else {
            return filedName;
        }
    }

    /**
     * @description: 构建sql字段
     * @author: tom.cui
     * @date: 2020/3/23-11:50
     */
    public  String buildFieldsSql(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        int size = fields.length - 1;
        for (int i = 0; i <= size; i++) {

            Field field = fields[i];
            //如果是自增主键，跳过字段
            if (field.isAnnotationPresent(TableId.class)) {
                IdType idType = ((TableId) field.getAnnotation(TableId.class)).type();
                if (idType == IdType.AUTO) {
                    continue;
                }
            }

            if (i < size) {
                if (field.isAnnotationPresent(TableField.class)) {
                    if (!field.getAnnotation(TableField.class).exist()){
                        continue;
                    }
                    sb.append("`");
                    sb.append(field.getAnnotation(TableField.class).value());
                    sb.append("`");
                    sb.append(",");
                } else {
                    sb.append("`");
                    sb.append(sqlHelper.parseFiledString(field.getName()));
                    sb.append("`");
                    sb.append(",");
                }
            } else {
                if (field.isAnnotationPresent(TableField.class)) {
                    sb.append("`");
                    sb.append(field.getAnnotation(TableField.class).value());
                    sb.append("`");
                } else {
                    sb.append("`");
                    sb.append(sqlHelper.parseFiledString(field.getName()));
                    sb.append("`");
                }
            }
        }
        sb.append(" ) ");
        return sb.toString();
    }

    /**
     * @description: 构造返回值字段
     * @author: tom.cui
     * @date: 2020/3/23-12:37
     */
    public  String buldValueSql(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append(" VALUES( ");
        int size = fields.length - 1;
        for (int i = 0; i <= size; i++) {
            Field field = fields[i];
            //如果是自增主键，跳过字段
            if (field.isAnnotationPresent(TableId.class)) {
                IdType idType = ((TableId) field.getAnnotation(TableId.class)).type();
                if (idType == IdType.AUTO) {
                    continue;
                }
            }
            if (field.isAnnotationPresent(TableField.class)){
                if (!field.getAnnotation(TableField.class).exist()){
                    continue;
                }
            }
            if (i < size) {

                sb.append("?");
                sb.append(",");
            } else {
                sb.append("?");
            }
        }
        sb.append(" ) ");
        return sb.toString();
    }

    public  Object[] buildValueObjects(Field[] fields, Object data) throws IllegalAccessException {
        List<Object> res = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            res.add(field.get(data));
        }
        return res.toArray();
    }

    /**
     * @description: 构造JDBC参数
     * @author: tom.cui
     * @date: 2020/3/23-12:38
     */
    public  List<Object[]> buildSqlParams(Field[] fields, List<Object> data) throws IllegalAccessException {
        List<Object[]> objects = new ArrayList<Object[]>();
        for (Object datum : data) {
            List<Object> objectList = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                //如果是自增主键，跳过字段
                if (field.isAnnotationPresent(TableId.class)) {
                    IdType idType = ((TableId) field.getAnnotation(TableId.class)).type();
                    if (idType == IdType.AUTO) {
                        continue;
                    }
                }
                if (field.isAnnotationPresent(TableField.class)) {
                    TableField tableField = field.getAnnotation(TableField.class);
                    if (!tableField.exist()) {
                        continue;
                    }
                    Object filedValue = field.get(datum);
                    objectList.add(filedValue);
                } else {

                    Object filedValue = field.get(datum);
                    objectList.add(filedValue);

                }
            }
            objects.add(objectList.toArray());
        }
        for (Object[] object : objects) {
            System.out.println(Arrays.toString(object));
        }
        return objects;
    }

    /**
     * @description: 查询列表
     * @author: tom.cui
     * @date: 2020/3/23-13:42
     */
    public <T> List<T>  queryObjectList(String sql, List<Object> params, Class type) {
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(type);
        System.out.println(sql);
        List<T> data = jdbcTemplate.query(sql, rowMapper, params);
        return data;
    }

    /**
     * @description: 查询单个
     * @author: tom.cui
     * @date: 2020/3/23-13:43
     */
    public <T> T queryObject(String sql, List<Object> params, Class type) {
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(type);
        System.out.println(sql);
        T data = jdbcTemplate.queryForObject(sql, rowMapper, params);
        return data;
    }
}
