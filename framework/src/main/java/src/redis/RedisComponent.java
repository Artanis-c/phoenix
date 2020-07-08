package src.redis;

import src.excepetion.ComponentException;

import java.util.List;

/*
 *@description: redis组件
 *@author: tom.cui
 *@date: 2020/3/23 14:27
 */
public interface RedisComponent {

    //region 分布式锁相关
    /**
     * @description: 获取分布式锁
     * @author: tom.cui
     * @date: 2020/2/21 17:56
     * @params: [key, value, time, unit]
     * @return: boolean
     */
    boolean getLock(String key,  long time) throws ComponentException;

    /**
     *@description: 释放锁
     *@author: tom.cui
     *@date: 2020/2/21 19:25
     *@params: [key]
     *@return: boolean
     */
    boolean disposeLock(String key) throws ComponentException;
    //endregion


    //region 添加缓存相关

    /**
     *@description: 设置永久缓存
     *@author: tom.cui
     *@date: 2020/2/21 19:21
     *@params: [key, value]
     *@return: void
     */
    boolean setString(String key,String value) throws ComponentException;

    /**
     * @description: 缓存字符串并指定过期时间
     * @author: tom.cui
     * @date: 2020/2/21 19:12
     * @params: [key, value, time, unit]
     * @return: void
     */
    boolean setString(String key, String value, long time) throws ComponentException;

    /**
     * @description: 添加分布式缓存
     * @author: tom.cui
     * @date: 2020/2/21 17:57
     * @params: [key, value, time, unit]
     * @return: boolean
     */
    <T> boolean addCache(String key, T value, long time) throws ComponentException;


    /**
     * @description: 如果不存在则新增
     * @author: tom.cui
     * @date: 2020/2/21 19:17
     * @params: [key, value, time, unit]
     * @return: boolean
     */
    boolean setNx(String key, String value) throws ComponentException;

    //endregion


    //region 获取缓存相关
    /**
     * @description: 获取缓存字符串
     * @author: tom.cui
     * @date: 2020/2/21 19:13
     * @params: [key]
     * @return: java.lang.String
     */
    String getString(String key) throws ComponentException;

    /**
     * @description: 获取对应的对象缓存
     * @author: tom.cui
     * @date: 2020/2/21 19:06
     * @params: [key]
     * @return: T
     */
    <T> T getCache(String key, Class<T> tClass) throws ComponentException;

    /**
     * @description: 获取缓存数组对象
     * @author: tom.cui
     * @date: 2020/2/21 19:18
     * @params: [key, tClass]
     * @return: java.util.List<T>
     */
    <T> List<T> getArrayCache(String key, Class<T> tClass) throws ComponentException;

    /**
     * @description: 判断key是否存在
     * @author: tom.cui
     * @date: 2020/2/21 19:15
     * @params: [key]
     * @return: boolean
     */
    boolean checkKeyExist(String key) throws ComponentException;

    //endregion
}
