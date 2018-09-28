package com.goshine.gscenter.common.isc.util;

public class CommFunc {
    /**
     * 判断是否访问页面
     * @param url
     * @return
     */
    public static boolean isPage(String url){
        boolean result=false;

        if(url!=null && !url.equals("")){
            int pos=url.indexOf("/page/");
            if(pos>=0){
                result=true;
            }
        }

        return result;
    }

    /**
     * 获取页面的URL
     * @param url
     * @return
     */
    public static String getPageUrl(String url){
        String pageUrl=null;

        if(CommFunc.isPage(url)){
            int pos=url.indexOf("/page/");
            pageUrl=url.substring(pos + 5);
        }

        return pageUrl;
    }
}
