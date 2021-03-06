package com.qr.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  redis 工具类
 * @Author wd
 * @since 10:54 2020/9/25
 **/
public class RedisUtils {

    private RedisUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置参数
     *
     * @param stringRedisTemplate
     * @param k             键
     * @param v             值
     */
    public static void add(StringRedisTemplate stringRedisTemplate, String k, Object v) {
        stringRedisTemplate.opsForValue().set(k, JSON.toJSONString(v));
    }

    public static void add(StringRedisTemplate stringRedisTemplate, String k, String v) {
        stringRedisTemplate.opsForValue().set(k, v);
    }

    /**
     * 设置参数和过期时间
     *
     * @param stringRedisTemplate
     * @param k             键
     * @param v             值
     * @param timeout       过期时间
     * @param unit          时间单位
     */
    public static void add(StringRedisTemplate stringRedisTemplate, String k, String v, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(k, v);
        stringRedisTemplate.expire(k, timeout, unit);
    }

    /**
     * 获得参数
     *
     * @param stringRedisTemplate
     * @param k             键
     * @return
     */
    public static String get(StringRedisTemplate stringRedisTemplate, String k) {

        return stringRedisTemplate.opsForValue().get(k);
    }

    /**
     *  incr 自增
     * @Author wd
     * @Date 17:15 2019/8/21
     **/
    public static Long incr(StringRedisTemplate stringRedisTemplate, String k, long timeout, TimeUnit unit){
        Long increment = stringRedisTemplate.opsForValue().increment(k);
        stringRedisTemplate.opsForValue().set(k,String.valueOf(increment),timeout,unit);
        return increment;
    }

    /**
     * 设置map参数
     *
     * @param stringRedisTemplate
     * @param k             键
     * @param map           集合
     */
    public static void addMap(StringRedisTemplate stringRedisTemplate, String k, Map<String, Object> map) {
        for (Map.Entry<String,Object> entry : map.entrySet()){
            String mapk = entry.getKey();
            if("ip".equals(mapk)) {
                map.put(mapk, map.get(mapk));
            }else {
                map.put(mapk, JSON.toJSONString(map.get(mapk)));
            }
        }
        stringRedisTemplate.opsForHash().putAll(k, map);
    }

    /**
     * 设置map参数和过期时间
     *
     * @param stringRedisTemplate
     * @param k             键
     * @param map           集合
     * @param timeout       过期时间
     * @param unit          时间单位
     */
    public static void addMap(StringRedisTemplate stringRedisTemplate, String k, Map<String,Object> map, long timeout, TimeUnit unit) {

        for (Map.Entry<String,Object> entry : map.entrySet()){
            String mapk = entry.getKey();
            if("ip".equals(mapk)) {
                map.put(mapk, map.get(mapk));
            }else {
                map.put(mapk, JSON.toJSONString(map.get(mapk)));
            }
        }
        stringRedisTemplate.opsForHash().putAll(k, map);
        stringRedisTemplate.expire(k, timeout, unit);
    }

    /**
     * 获得map参数
     *
     * @param stringRedisTemplate
     * @param k             键
     * @param mapK          map参数的键
     * @return
     */
    public static Object getMap(StringRedisTemplate stringRedisTemplate, String k, String mapK) {

        return stringRedisTemplate.opsForHash().get(k, mapK);
    }

    /**
     * 获得maplist参数
     *
     * @param stringRedisTemplate
     * @param k             键
     * @return
     */
    public static List<Object> getMapList(StringRedisTemplate stringRedisTemplate, String k) {

        return stringRedisTemplate.opsForHash().values(k);
    }

    /**
     * 删除参数
     * @param stringRedisTemplate
     * @param key              键
     */
    public static void  delete (StringRedisTemplate stringRedisTemplate, String key){
        stringRedisTemplate.delete(key);
    }


}
