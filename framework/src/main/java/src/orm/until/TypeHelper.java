package src.orm.until;


import src.cache.MemoryCache;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 *@description:
 *@author: tom.cui
 *@date: 2020/3/23 11:26
 */
public class TypeHelper<T> {

    /**
     * @description: 获取泛型类型
     * @author: tom.cui
     * @date: 2020/3/23-11:28
     */
    public Class<T> getClass(Class clas) {
        Type[] types = null;
        Object cache = MemoryCache.getCache(clas.getName());
        if (cache == null) {
            Type parentType = clas.getGenericSuperclass();
            ParameterizedType type = (ParameterizedType) parentType;
            types = type.getActualTypeArguments();
            MemoryCache.addCache(clas.getName(), types);
        } else {
            types = (Type[]) cache;
        }
        return (Class<T>) types[1];

    }
}
