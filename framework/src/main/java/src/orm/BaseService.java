package src.orm;

import src.orm.model.PageModel;
import src.orm.model.PageRequest;

import java.util.List;
import java.util.Map;

/*
 *@description: 基础服务层
 *@author: tom.cui
 *@date: 2020/3/23 10:27
 */
public interface BaseService<T> {

    /**
    *@description: 插入单个实体
    *@author: tom.cui
    *@date: 2020/3/23-10:29
    */
    T insert(T entity);

    /**
    *@description: 批量插入SQL方式
    *@author: tom.cui
    *@date: 2020/3/23-10:30
    */
    boolean insertBatch(List<T> entities) throws IllegalAccessException, NoSuchMethodException;

    /**
    *@author: tom.cui
    *@date: 2020/4/16
    *@description: 批量插入Mybatis循环方式
    */
    boolean insertBathPlus(List<T> entities);

    /**
    *@description: 更新单条
    *@author: tom.cui
    *@date: 2020/3/23-10:30
    */
    boolean update(T entity);

    /**
    *@description: 根据主键进行删除
    *@author: tom.cui
    *@date: 2020/3/23-10:31
    */
    boolean delete(int id);

    /**
    *@description: 查询单个实体根据主键
    *@author: tom.cui
    *@date: 2020/3/23-10:52
    */
    T getModel(int id);


    /**
    *@description: 单纯分页
    *@author: tom.cui
    *@date: 2020/3/23-10:50
    */
    PageModel<T> getPageList(PageRequest req) throws NoSuchMethodException;
}
