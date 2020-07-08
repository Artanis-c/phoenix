package src.orm.Impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import src.orm.BaseService;
import src.orm.model.PageModel;
import src.orm.model.PageRequest;
import src.orm.until.SqlHelper;
import src.orm.until.TypeHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/*
 *@description: 基础服务类
 *@author: tom.cui
 *@date: 2020/3/23 10:36
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T>  implements BaseService<T> {

    @Autowired
    M baseMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SqlHelper sqlHelper;

    Logger logger = LoggerFactory.getLogger(getClass());

    TypeHelper<T> typeHelper=new TypeHelper<T>();


    public T insert(T entity) {
        int res = baseMapper.insert(entity);
        if (SqlHelper.checkBool(res)) {
            return entity;
        }
        return null;
    }

    public boolean insertBatch(List<T> entities) throws IllegalAccessException, NoSuchMethodException {
        //获取泛型
        Class type = typeHelper.getClass(getClass());
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        //获取数据库表名
        if (!type.isAnnotationPresent(TableName.class)) {
            logger.error("实体:" + type.getName() + "必须指定TableName注解");
            throw  new IllegalArgumentException("实体:" + type.getName() + "必须指定TableName注解!");
        }
        String tableName = ((TableName) type.getAnnotation(TableName.class)).value();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(tableName)
                .append(sqlHelper.buildFieldsSql(fields))
                .append(sqlHelper.buldValueSql(fields));
        System.out.println("执行SQL:" + sql.toString());
        List<Object> data = new ArrayList<Object>();
        for (T entity : entities) {
            data.add(entity);
        }
        //执行sql
        int[] res = jdbcTemplate.batchUpdate(sql.toString(), sqlHelper.buildSqlParams(fields, data));
        int batchRes=0;
        for (int re : res) {
            batchRes+=re;
        }
        return SqlHelper.checkBool(batchRes);
    }

    public   boolean insertBathPlus(List<T> entities){
        int res= 0;
        for (T entity : entities) {
            res+=baseMapper.insert(entity);
        }
        return  SqlHelper.checkBool(res);
    }

    public boolean update(T entity) {
        return SqlHelper.checkBool(baseMapper.updateById(entity));
    }

    public boolean delete(int id) {
        return SqlHelper.checkBool(baseMapper.deleteById(id));
    }

    public T getModel(int id) {
        return baseMapper.selectById(id);
    }


    public PageModel<T> getPageList(PageRequest req) throws NoSuchMethodException {

        //获取泛型
        Class type = typeHelper.getClass(getClass());
        //获取数据库表名
        if (!type.isAnnotationPresent(TableName.class)) {
            logger.info("实体:" + type.getName() + "必须指定TableName注解");
        }
        String tableName = ((TableName) type.getAnnotation(TableName.class)).value();
        //计算分页
        int pageStart = 0;
        if (req.getPageNum() == 1) {
            req.setPageNum(0);
        }
        if (req.getPageNum() > 1) {
            req.setPageNum(req.getPageNum() - 1);
        }
        //插叙数据
        pageStart = req.getPageNum() * req.getPageSize();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM ")
                .append(tableName)
                .append(" LIMIT ")
                .append(pageStart)
                .append(",")
                .append(req.getPageSize());
        List<T> data = jdbcTemplate.queryForList(sql.toString(), type);
        //查询数据条数
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(0) FROM " + tableName, Integer.class);
        //构造返回数据
        PageModel<T> result = new PageModel<T>();
        result.setData(data);
        result.setPageNum(req.getPageNum());
        result.setPageSize(req.getPageSize());
        result.setTotalCount(count);
        return null;
    }

}
