package edu.csu.dlna.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxi on 2015/11/29.
 */
public class ResMap {
    private static final Map<String, String> sMap = Collections.synchronizedMap(new HashMap<String, String>());

    public static void put(String id, String path) {
        sMap.put(id, path);
    }

    public static String get(String id) {
        return sMap.get(id);
    }
}
