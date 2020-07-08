package src.orm.model;

import java.util.List;

/*
 *@description: 自定义分页信息
 *@author: tom.cui
 *@date: 2020/3/23 10:45
 */
public class PageModel<T> {

    /**
    *@description: 数据
    *@author: tom.cui
    *@date: 2020/3/23-13:22
    */
    private List<T> data;

    /**
     * @description: 页码
     * @author: tom.cui
     * @date: 2020/3/23-10:46
     */
    private int pageNum;

    /**
     * @description: 数量
     * @author: tom.cui
     * @date: 2020/3/23-10:46
     */
    private int pageSize;

    /**
     * @description: 总数
     * @author: tom.cui
     * @date: 2020/3/23-10:46
     */
    private int totalCount;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
