package com.tdkj.ServiceImpl.DataStatistics;

import com.tdkj.dao.DataStatistics.PoorHouseholdTranslationMapper;
import com.tdkj.dao.Ztree.PoorXzqhMapper;
import com.tdkj.model.PoorHouseholdTranslation;
import com.tdkj.service.DataStatistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PoorHouseholdStatisticsServiceImpl implements StatisticsService {

    @Autowired
    private PoorHouseholdTranslationMapper poorHouseholdTranslationMapper;
    @Autowired
    private PoorXzqhMapper poorXzqhMapper;
    @Override
    //贫困户统计
    public List<PoorHouseholdTranslation> query(PoorHouseholdTranslation poorHouseholdTranslation) {
        String type=poorHouseholdTranslation.getType();
        String type2="";
        if(type.equals("2")){
            type2="4";
        }else if(type.equals("4")){
            type2="6";
        }else if(type.equals("6")){
            type2="9";
        }else if(type.equals("9")){
            type2="12";
        }
        return poorXzqhMapper.queryAllByAab301Statistics(poorHouseholdTranslation.getAab301(),
                poorHouseholdTranslation.getPhd012(),poorHouseholdTranslation.getPhd013(),poorHouseholdTranslation.getPhd014(),type,type2);
    }
    //条数统计
    @Override
    public String queryAllByAab301(PoorHouseholdTranslation poorHouseholdTranslation) {
        return poorHouseholdTranslationMapper.queryAllByAab301(poorHouseholdTranslation.getAab301());
    }

    //易地搬迁
    @Override
    public List<PoorHouseholdTranslation> RelocationQuery(PoorHouseholdTranslation poorHouseholdTranslation) {
        String type=poorHouseholdTranslation.getType();
        String type2="";
        if(type.equals("2")){
            type2="4";
        }else if(type.equals("4")){
            type2="6";
        }else if(type.equals("6")){
            type2="9";
        }else if(type.equals("9")){
            type2="12";
        }
        return poorXzqhMapper.RelocationqueryAllByAab301Statistics(poorHouseholdTranslation.getAab301(),poorHouseholdTranslation.getPhd012(),poorHouseholdTranslation.getPhd013(),poorHouseholdTranslation.getPhd014(),type,type2);
    }

    //就业统计
    @Override
    public List<PoorHouseholdTranslation> EploymentQuery(PoorHouseholdTranslation poorHouseholdTranslation) {
        String type=poorHouseholdTranslation.getType();
        String type2="";
        if(type.equals("2")){
            type2="4";
        }else if(type.equals("4")){
            type2="6";
        }else if(type.equals("6")){
            type2="9";
        }else if(type.equals("9")){
            type2="12";
        }
        return poorXzqhMapper.EploymentqueryAllByAab301Statistics(poorHouseholdTranslation.getAab301(),poorHouseholdTranslation.getPhd012(),poorHouseholdTranslation.getPhd013(),poorHouseholdTranslation.getPhd014(),type,type2);

    }
    //苏陕协作统计
    @Override
    public List<PoorHouseholdTranslation> CooperationQuery(PoorHouseholdTranslation poorHouseholdTranslation) {
        String type=poorHouseholdTranslation.getType();
        String type2="";
        if(type.equals("2")){
            type2="4";
        }else if(type.equals("4")){
            type2="6";
        }else if(type.equals("6")){
            type2="9";
        }else if(type.equals("9")){
            type2="12";
        }
        return poorXzqhMapper.CooperationqueryAllByAab301Statistics(poorHouseholdTranslation.getAab301(),poorHouseholdTranslation.getPhd012(),poorHouseholdTranslation.getPhd013(),poorHouseholdTranslation.getPhd014(),type,type2);

    }
    //综合统计
    @Override
    public List<PoorHouseholdTranslation> ComprehensiveQuery(PoorHouseholdTranslation poorHouseholdTranslation) {
        String type=poorHouseholdTranslation.getType();
        String type2="";
        if(type.equals("2")){
            type2="4";
        }else if(type.equals("4")){
            type2="6";
        }else if(type.equals("6")){
            type2="9";
        }else if(type.equals("9")){
            type2="12";
        }
        return poorXzqhMapper.ComprehensivequeryAllByAab301Statistics(poorHouseholdTranslation.getAab301(),poorHouseholdTranslation.getPhd012(),poorHouseholdTranslation.getPhd013(),poorHouseholdTranslation.getPhd014(),type,type2);

    }
}
