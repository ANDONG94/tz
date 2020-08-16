package com.tdkj.service.DataStatistics;

import com.tdkj.model.PoorHouseholdTranslation;

import java.util.List;

public interface StatisticsService {
    //贫困户
    List<PoorHouseholdTranslation> query(PoorHouseholdTranslation poorHouseholdTranslation);
    String queryAllByAab301(PoorHouseholdTranslation poorHouseholdTranslation);

    //易地搬迁
    List<PoorHouseholdTranslation> RelocationQuery(PoorHouseholdTranslation poorHouseholdTranslation);

    //就业
    List<PoorHouseholdTranslation> EploymentQuery(PoorHouseholdTranslation poorHouseholdTranslation);

    //苏陕协作
    List<PoorHouseholdTranslation> CooperationQuery(PoorHouseholdTranslation poorHouseholdTranslation);

    //综合信息
    List<PoorHouseholdTranslation> ComprehensiveQuery(PoorHouseholdTranslation poorHouseholdTranslation);
}
