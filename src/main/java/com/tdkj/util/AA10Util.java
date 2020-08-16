package com.tdkj.util;

import com.tdkj.dto.message;
import com.tdkj.model.*;
import com.tdkj.service.Param.Aa10Service;
import com.tdkj.service.Param.CentralizedSettlementsService;
import com.tdkj.service.Ztree.ZtreeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AA10Util {
    @Autowired
    private Aa10Service aa10Service;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private CentralizedSettlementsService centralizedSettlementsService;
    @Autowired
    private ZtreeService ztreeService;

    private com.tdkj.dto.message message= new message();

    public  message queryAa10(String aaa100, String value, String name,Object OBJ){
        if(!"".equals(aaa100) && aaa100 != null){
            Aa10 aa10= aa10Service.queryAa10ByAaa100Type(aaa100,value.trim());
            if(aa10 !=null){

                message.setValue(aa10.getAaa102());
                message.setAae100("1");
            }else{
                message.setMessage(name+"类型错误==");
                message.setValue(value);
                message.setAae100("0");
            }
            return message;
        }else{
            return null;
        }
    }

    public message getName(String name){
        String aab301;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        aab301 = fpUser.getAab301();
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else   if(poorXzqh!=null && !"".equals(poorXzqh)) {
            String type = poorXzqh.getType();
            if (type.equals("1")) {
                aab301 = aab301.substring(0, 2);
            } else if (type.equals("2")) {
                aab301 = aab301.substring(0, 4);
            } else if (type.equals("3")) {
                aab301 = aab301.substring(0, 6);
            } else if (type.equals("4")) {
                aab301 = aab301.substring(0, 6);
            } else if (type.equals("5")) {
                aab301 = aab301.substring(0, 6);
            }
        }
        List<CentralizedSettlements> list= centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301);
        for (CentralizedSettlements cs:list) {
             String cts002=cs.getCts002();
             if(cts002.equals(name.trim())){
               message.setAae100("1");
               message.setValue(cs.getCts001());
             }else{
                 message.setValue(name);
                 message.setAae100("0");
                 message.setMessage("安置点名称选择有误==");
             }
        }

        return message;
    }
}
