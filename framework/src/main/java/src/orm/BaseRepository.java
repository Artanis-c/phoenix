package src.orm;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import src.Response.ResponseModel;
import src.orm.model.PageModel;
import src.orm.model.PageRequest;

import java.util.List;

public interface BaseRepository<T> {

    /**
     * @description: 插入单个实体
     * @author: tom.cui
     * @date: 2020/3/23-10:29
     */
    ResponseModel<T> insert(T entity);

    /**
     * @description: 批量插入SQL方式 经过性能测试6000条数据插入需要116秒
     * @author: tom.cui
     * @date: 2020/3/23-10:30
     */
    ResponseModel<Integer> insertBatch(List<T> entities) throws IllegalAccessException, NoSuchMethodException;

    /**
     * @author: tom.cui
     * @date: 2020/4/16
     * @description: 批量插入Mybatis循环方式 经过性能测试6000条数据插入需要160秒
     */
    ResponseModel<Integer> insertBathPlus(List<T> entities);

    /**
     * @description: 更新单条
     * @author: tom.cui
     * @date: 2020/3/23-10:30
     */
    ResponseModel<T> update(T entity);

    /**
     * @description: 根据主键进行删除
     * @author: tom.cui
     * @date: 2020/3/23-10:31
     */
    ResponseModel<Boolean> delete(int id);

    /**
     * @description: 查询单个实体根据主键
     * @author: tom.cui
     * @date: 2020/3/23-10:52
     */
    ResponseModel<T>getModel(int id);


    /**
     * @description: 单纯分页
     * @author: tom.cui
     * @date: 2020/3/23-10:50
     */
     ResponseModel<PageInfo<T>> getPageList(PageRequest req, ISelect select);
}
