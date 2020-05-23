package com.lzr.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    //将request中的key转换成整形
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static double getDouble(HttpServletRequest request,String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }
        public static long getLong(HttpServletRequest request,String key) {
            try {
                return Long.valueOf(request.getParameter(key));
            } catch (Exception e) {
                return -1L;
            }
        }

            public static Boolean getBoolean(HttpServletRequest request,String key){
                try {
                    return Boolean.valueOf(request.getParameter(key));
                }catch (Exception e){
                    return false;
                }
    }

    public static String getString(HttpServletRequest request,String key){
        try{
            String result = request.getParameter(key);
            if (result != null){
                //去掉左右空格
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }

}
