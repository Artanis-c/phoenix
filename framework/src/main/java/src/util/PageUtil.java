package src.util;

import com.github.pagehelper.IPage;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import src.Response.ResponseModel;

/*
@author: tom.cui
@date 2020/4/16
@description: 分页查询工具
*/
public class PageUtil<T> {

    /**
    *@author: tom.cui
    *@date: 2020/4/16
    *@description: 根据自己想要的方式分页
    */
    public ResponseModel<PageInfo<T>> doPage(IPage page, ISelect select) {
        PageInfo<T> data = PageHelper.startPage(page.getPageNum(), page.getPageSize())
                .doSelectPageInfo(select);
        return new ResponseModel<PageInfo<T>>().ok(data);
    }
}
