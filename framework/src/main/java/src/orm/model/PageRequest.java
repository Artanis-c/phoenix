package src.orm.model;

import com.github.pagehelper.IPage;

/*
 *@description: 分页请求
 *@author: tom.cui
 *@date: 2020/3/23 10:48
 */
public class PageRequest implements IPage {

    private int pageNum;

    private int pageSize;

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Integer getPageNum() {
        return this.pageNum;
    }

    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }

    @Override
    public String getOrderBy() {
        return null;
    }
}
