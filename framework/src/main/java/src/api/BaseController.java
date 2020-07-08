package src.api;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.Response.ResponseModel;
import src.orm.BaseRepository;
import src.orm.model.PageRequest;

import java.util.List;

/*
@author: tom.cui
@date 2020/4/16
@description: 基础控制器
*/
public class BaseController<R extends BaseRepository<T>, T> {

    @Autowired
    protected R repository;

    /**
     * @description: 插入单个实体
     * @author: tom.cui
     * @date: 2020/3/23-10:29
     */
    @ApiOperation("创建")
    @PostMapping
    ResponseModel<T> insert(@RequestBody T  entity) {
        return repository.insert(entity);
    }


    /**
     * @description: 更新单条
     * @author: tom.cui
     * @date: 2020/3/23-10:30
     */
    @ApiOperation("修改")
    @PutMapping
    ResponseModel<T> update(@RequestBody T entity) {
        return repository.update(entity);
    }

    /**
     * @description: 根据主键进行删除
     * @author: tom.cui
     * @date: 2020/3/23-10:31
     */
    @ApiOperation("删除")
    @DeleteMapping
    ResponseModel<Boolean> delete(@PathVariable int id) {
        return repository.delete(id);
    }

    /**
     * @description: 查询单个实体根据主键
     * @author: tom.cui
     * @date: 2020/3/23-10:52
     */
    @ApiOperation("获取")
    @GetMapping
    ResponseModel<T> getModel(@PathVariable int id) {
        return repository.getModel(id);
    }

}
