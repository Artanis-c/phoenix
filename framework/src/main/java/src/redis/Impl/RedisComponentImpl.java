package src.redis.Impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import src.excepetion.ComponentException;
import src.redis.RedisComponent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/*
 *@description: redis组件
 *@author: tom.cui
 *@date: 2020/3/23 14:30
 */

public class RedisComponentImpl implements RedisComponent {

    @Autowired
    RedisCommands<String, String> redisCommands;

    private  static  final  String LOCK_PRIFX="lock:";

    public boolean getLock(String key, long time) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }

        boolean successGetLock = redisCommands.setnx(LOCK_PRIFX+key, "LOCK");
        if (Boolean.FALSE.equals(successGetLock)) {
            LocalDateTime beginTime = LocalDateTime.now();
            while (true) {
                successGetLock = redisCommands.setnx(LOCK_PRIFX+key, "LOCK");
                if (successGetLock) {
                    return true;
                }
                LocalDateTime currTime = LocalDateTime.now();
                Duration diffTime = Duration.between(beginTime, currTime);
                if (diffTime.getSeconds() > time) {
                    break;
                }
            }
        }
        return successGetLock;
    }

    public boolean disposeLock(String key) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }
        return redisCommands.del(key) > 0;
    }

    public boolean setString(String key, String value) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }

        String setKey = redisCommands.set(key, value);
        if (setKey == null || setKey.equals("")) {
            return false;
        }
        return true;
    }

    public boolean setString(String key, String value, long time) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }
        SetArgs setArgs = new SetArgs();
        setArgs.ex(time);
        String result = redisCommands.set(key, value, setArgs);
        if (result == null || result.equals("")) {
            return false;
        }
        return true;
    }

    public <T> boolean addCache(String key, T value, long time) throws ComponentException {
        try {
            if (key == null || key.equals("")) {
                throw new ComponentException("redis的Key不可以为空");
            }
            SetArgs setArgs = new SetArgs();
            setArgs.ex(time);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(value);
            String result = redisCommands.set(key, json, setArgs);
            if (result == null || result.equals("")) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            throw new ComponentException(ex.getMessage());
        }
    }

    public boolean setNx(String key, String value) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }
        boolean result = redisCommands.setnx(key, value);
        return result;
    }

    public String getString(String key) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }
        return redisCommands.get(key);
    }

    public <T> T getCache(String key, Class<T> tClass) throws ComponentException {
        try {
            boolean keyExits=  checkKeyExist(key);
            if (Boolean.FALSE.equals(keyExits)){
                return  null;
            }
            String strValue = redisCommands.get(key);
            ObjectMapper mapper = new ObjectMapper();
            T value = mapper.readValue(strValue, tClass);
            return value;
        } catch (Exception ex) {
            throw new ComponentException(ex.toString());
        }
    }

    public <T> List<T> getArrayCache(String key, Class<T> tClass) throws ComponentException {
        try {
            boolean keyExits=  checkKeyExist(key);
            if (Boolean.FALSE.equals(keyExits)){
                return  null;
            }
            String strValue = redisCommands.get(key);
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, tClass);
            List<T> value = mapper.readValue(strValue, javaType);
            return value;
        } catch (Exception ex) {
            throw new ComponentException(ex.getMessage());
        }
    }

    public boolean checkKeyExist(String key) throws ComponentException {
        if (key == null || key.equals("")) {
            throw new ComponentException("redis的Key不可以为空");
        }
        return redisCommands.exists(key) > 0;
    }
}
