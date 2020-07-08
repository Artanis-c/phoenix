package src.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache {

    private   static Map<String,Object> cacheMap= new ConcurrentHashMap<String,Object>();

    private   static Map<String,String> stringCacheMap= new ConcurrentHashMap<String,String>();

    public static  void addCache(String key,Object value){
        cacheMap.put(key,value);
    }

    public static  Object getCache(String key){
        return  cacheMap.get(key);
    }

    public static  void removeCache(String key){
         cacheMap.remove(key);
    }


    public static  void addCacheString(String key,String value){
        stringCacheMap.put(key,value);
    }

    public static  String getCacheSting(String key){
        return  stringCacheMap.get(key);
    }

    public static  void removeCacheString(String key){
        stringCacheMap.remove(key);
    }
}
