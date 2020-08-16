package com.tdkj.controller.Ztree;

import com.tdkj.dao.Ztree.PoorXzqhMapper;
import com.tdkj.model.FpUser;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.Ztree.ZtreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 2019-04-26.
 */
@Controller
@RequestMapping("ztree")
public class ZtreeController {

    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private PoorXzqhMapper poorXzqhMapper;


    /**
     * 机构树数据
     *   menutype 表示查询那个模块，mapper.xml中对应查询那个表
     * @return
     */
    @RequestMapping("/treedata")
    @ResponseBody
    public List<PoorXzqh> getGroupTreeData(HttpServletRequest request, Model model,String menutype) throws Exception {
        String parentid=request.getParameter("groupid");
        String first = "";
        if("".equals(parentid) || parentid == null){
           // parentid="100";//这是陕西省的
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            if(fpUser != null){
               parentid = fpUser.getAab301();
               // parentid="100";
            }else{
                parentid="100";
            }
            first = "1";//说明树 还没有进行点击，第一次查询，只查询出自己，点击后，查询他下面的区域
        }else{
            first = "";
        }
        List<PoorXzqh> list = new ArrayList<PoorXzqh>();
        list = ztreeService.getAllGroupList(parentid,first,menutype);
        return list;
    }

    //获取aab301的截取
    public  String aab301Substr(String aab301){
        PoorXzqh poorXzqh =poorXzqhMapper.queryPoorXzqhSub(aab301);
        if(poorXzqh!=null && !"".equals(poorXzqh)){
            String type=poorXzqh.getType();
            if(type.equals("1")){
                aab301=aab301.substring(0, 2);
            }else if(type.equals("2")){
                aab301=aab301.substring(0, 4);
            }else if(type.equals("3")){
                aab301=aab301.substring(0, 6);
            }else if(type.equals("4")){
                aab301=aab301.substring(0, 9);
            }else if(type.equals("5")){
                aab301=aab301;
            }
        }
        return aab301;
    }

    /**
     * g根据aab301 查询 总户数，确认户数，总人数  确认人数
     * @param request    2019-10-21日该方法不用了，使用下面的方法进行实时查询
     * @throws Exception
     */
    @RequestMapping("/queryZtreeCount")
    @ResponseBody
    public PoorXzqh queryZtreeCount(HttpServletRequest request, Model model,String aab301) throws Exception {
        PoorXzqh poorXzqh = new PoorXzqh();
        poorXzqh = ztreeService.queryAab301Count(aab301);
        return poorXzqh;
    }





}
