package com.tdkj.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by hedd on 2019/6/26.
 */
public class EHCacheUtils {
    /**
     * 设置缓存对象
     * @param cacheManager
     * @param key
     * @param object
     */
    public static void setCache(CacheManager cacheManager, String key, Object object){

        Cache cache = cacheManager.getCache("objectCache");
        Element element = new Element(key,object);
        cache.put(element);
    }

    /**
     * 从缓存中取出对象
     * @param cacheManager
     * @param key
     * @return
     */
    public static Object getCache(CacheManager cacheManager,String key){
        Object object = null;
        Cache cache = cacheManager.getCache("objectCache");
        if(cache.get(key)!=null && !cache.get(key).equals("")){
            object = cache.get(key).getObjectValue();
        }
        return object;

    }
}
