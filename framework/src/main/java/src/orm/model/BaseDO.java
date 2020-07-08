package src.orm.model;

/*
@author: tom.cui
@date 2020/4/16
@description: 实体基类
*/
public class BaseDO extends  SimpleBaseDO{

    private int sortOrder;

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
