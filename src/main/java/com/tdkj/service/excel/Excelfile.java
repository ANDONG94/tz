package com.tdkj.service.excel;

import com.tdkj.model.*;

import java.util.List;

public interface Excelfile {
    /**
     * 贫困户
     * @param poorLaborForces
     */
    void saveExcelList(List<PoorHouseholdsTmp> poorLaborForces);
    List<PoorHouseholdsTmp> getPoorHouseholdsTmpCorrect(String batch);
    List<PoorHouseholdsTmp> getPoorHouseholdsTmpError(String batch);
    int SavePoorHouseholds(String batch) throws Exception;

    /**
     * 贫困劳动力
     * @param poorLaborForceTmps
     */
    void saveExcelListPLF(List<PoorLaborForceTmp> poorLaborForceTmps);
    int PutPoorHouseholds(String batch);
    List<PoorLaborForceTmp> getPoorLaborForceTmpCorrect(String batch);
    List<PoorLaborForceTmp> getPoorLaborForceTmpError(String batch);
    int SavePoorLaborForce(String batch) throws Exception;




    /**
     * 就业信息
     * @param poorLaborForceTmps
     */
    void saveExcelListES(List<EmploymentSituationTmp> poorLaborForceTmps);
    List<EmploymentSituationTmp> getEmploymentSintuationCorrect(String batch);
    List<EmploymentSituationTmp> getEmploymentSintuationError(String batch);
    int SaveEmploymentSintuation(String batch);


    /**
     * 创业信息
     * @param poorLaborForceTmps
     */
    void saveExcelListEP(List<EntrepreneurshipTmp> poorLaborForceTmps);
    List<EntrepreneurshipTmp> getEntrepreneurshipCorrect(String batch);
    List<EntrepreneurshipTmp> getEntrepreneurshipError(String batch);
    int SaveEntrepreneurship(String batch);


    /**
     * 培訓信息
     * @param list
     */
    void saveExcelListTS(List<TrainingSituationTmp> list);
    List<TrainingSituationTmp> getTrainingSituationCorrect(String batch);
    List<TrainingSituationTmp> getTrainingSituationError(String batch);
    int SaveTrainingSituation(String batch);



    /**
     *技工院校信息
     * @param list
     */
    void saveExcelListTSS(List<TechnicalSchoolsTmp> list);
    List<TechnicalSchoolsTmp> getTechnicalSchoolsCorrect(String batch);
    List<TechnicalSchoolsTmp> getTechnicalSchoolsError(String batch);
    int SaveTechnicalSchools(String batch);


}
