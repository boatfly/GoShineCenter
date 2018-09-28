package com.goshine.gscenter.common.isc.util;

import com.sgcc.isc.core.orm.complex.FunctionNode;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;

public class FunctionNodeUtil {
    private static final Logger log=Logger.getLogger(FunctionNodeUtil.class);
    /**
     * 获取菜单的URL
     * @param node
     * @return
     */
    public static String getFuncUrlByNode(FunctionNode node){
        String funcUrl=null;
        String menuUrl=null;
        String[] funcUrlArray=null;

        Iterator<Map.Entry<String,Object>> iter = node.getCurrentNode().getResExt().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> e = iter.next();
            log.info("key=" + e.getKey() + ";value=" + e.getValue());
            if(e.getKey().indexOf("FUNC_URL")>=0){
                funcUrl=e.getKey();
                funcUrlArray=funcUrl.split(";");
                if(funcUrlArray.length==2){
                    if(funcUrlArray[1]!=null && !funcUrlArray[1].equals("")){
                        menuUrl=funcUrlArray[1];
                    }
                }
                break;
            }
        }

        return "" + menuUrl;
    }
}
