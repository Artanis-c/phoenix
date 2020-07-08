package src.orm.Impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.omg.IOP.ENCODING_CDR_ENCAPS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import src.Response.ResponseModel;
import src.cache.MemoryCache;
import src.orm.BaseRepository;
import src.orm.BaseService;
import src.orm.model.BaseDO;
import src.orm.model.PageModel;
import src.orm.model.PageRequest;
import src.orm.until.SqlHelper;
import src.orm.until.TypeHelper;
import src.util.StringUtil;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
@author: tom.cui
@date 2020/4/16
@description:  仓储单元
*/
@Repository
public class BaseRepositoryImpl<M extends BaseMapper<T>, T extends BaseDO> implements BaseRepository<T> {
    @Autowired
    protected M baseMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected SqlHelper sqlHelper;

    @Autowired
    PageHelper pageHelper;

    Logger logger = LoggerFactory.getLogger(getClass());

    TypeHelper<T> typeHelper = new TypeHelper<T>();


    public ResponseModel<T> insert(T entity) {
        entity.setCreatedTime(LocalDateTime.now());
        int res = baseMapper.insert(entity);
        if (SqlHelper.checkBool(res)) {
            return new ResponseModel<T>().ok(entity);
        }
        return new ResponseModel<T>().fail(entity);
    }

    public ResponseModel<Integer> insertBatch(List<T> entities) throws IllegalAccessException, NoSuchMethodException {
        //获取泛型进行缓存
        Class type = typeHelper.getClass(getClass());
        Field[] fields = null;
        String className = getClass().getName();
        String filedCacheKey = className + "_filed";
        Object filedCache = MemoryCache.getCache(filedCacheKey);
        if (filedCache == null) {
            fields = type.getDeclaredFields();
            MemoryCache.addCache(filedCacheKey, fields);
        } else {
            fields = (Field[]) filedCache;
        }
        //获取数据库表名进行缓存
        if (!type.isAnnotationPresent(TableName.class)) {
            logger.error("实体:" + type.getName() + "必须指定TableName注解");
            throw new IllegalArgumentException("实体:" + type.getName() + "必须指定TableName注解!");
        }
        String tableNameKey = className + "_table_name";
        String tableName = MemoryCache.getCacheSting(tableNameKey);
        if (StringUtil.isNullOrWhiteSpace(tableName)) {
            tableName = ((TableName) type.getAnnotation(TableName.class)).value();
            MemoryCache.addCacheString(tableNameKey, tableName);
        }
        //拼接SQL
        String sqlKey = className + "_sql";
        String sql = MemoryCache.getCacheSting(sqlKey);
        if (StringUtil.isNullOrWhiteSpace(sql)) {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("INSERT INTO ")
                    .append(tableName)
                    .append(sqlHelper.buildFieldsSql(fields))
                    .append(sqlHelper.buldValueSql(fields));
            sql = sqlBuilder.toString();
        }
        System.out.println("执行SQL:" + sql.toString());
        List<Object> data = new ArrayList<Object>();
        for (T entity : entities) {
            entity.setCreatedTime(LocalDateTime.now());
            data.add(entity);
        }
        //执行sql
        int[] res = jdbcTemplate.batchUpdate(sql.toString(), sqlHelper.buildSqlParams(fields, data));
        int batchRes = 0;
        for (int re : res) {
            batchRes += re;
        }
        if (SqlHelper.checkBool(batchRes)) {
            return new ResponseModel<Integer>().ok(batchRes);
        }
        return new ResponseModel<Integer>().fail(batchRes);
    }

    public ResponseModel<Integer> insertBathPlus(List<T> entities) {
        int res = 0;
        for (T entity : entities) {
            entity.setCreatedTime(LocalDateTime.now());
            res += baseMapper.insert(entity);
        }
        if (SqlHelper.checkBool(res)) {
            return new ResponseModel<Integer>().ok(res);
        }
        return new ResponseModel<Integer>().fail(res);
    }

    public ResponseModel<T> update(T entity) {
        entity.setModifiedTime(LocalDateTime.now());
        if (SqlHelper.checkBool(baseMapper.updateById(entity))) {
            return new ResponseModel<T>().ok(entity);
        }
        return new ResponseModel<T>().fail(entity);
    }

    public ResponseModel<Boolean> delete(int id) {

        if (SqlHelper.checkBool(baseMapper.deleteById(id))) {
            return new ResponseModel<Boolean>().ok(true, "删除成功");
        }
        return new ResponseModel<Boolean>().fail(false, "删除失败");
    }

    public ResponseModel<T> getModel(int id) {
        T data = baseMapper.selectById(id);
        if (data != null) {
            return new ResponseModel<T>().ok(data);
        }
        return new ResponseModel<T>().fail("没有找到数据");
    }


    public ResponseModel<PageInfo<T>> getPageList(PageRequest req, ISelect select) {
        PageInfo<T> data = PageHelper.startPage(req.getPageNum(), req.getPageSize())
                .doSelectPageInfo(select);
        return new ResponseModel<PageInfo<T>>().ok(data);
    }

}
