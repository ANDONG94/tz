package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.PoorLaborForceMapper;
import com.tdkj.model.PoorLaborForce;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-28.
 */
@Service
public class PoorLaborForceServiceImpl implements PoorLaborForceService {

    @Autowired
    private PoorLaborForceMapper poorLaborForceMapper;

    @Override
    public int deleteByPrimaryKey(String plf001) {
        return poorLaborForceMapper.deleteByPrimaryKey(plf001);
    }

    @Override
    public int insert(PoorLaborForce record) {
        return 0;
    }

    @Override
    public int insertSelective(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.insertSelective(poorLaborForce);
    }

    @Override
    public PoorLaborForce selectByPrimaryKey(String plf001) {
        return poorLaborForceMapper.selectByPrimaryKey(plf001);
    }

    @Override
    public int updateByPrimaryKeySelective(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.updateByPrimaryKeySelective(poorLaborForce);
    }

    @Override
    public int updateByPrimaryKey(PoorLaborForce poorLaborForce) {
        return 0;
    }

    /**
     * 根据贫困户id 查询劳动力信息
     * @return
     */
    @Override
    public List<PoorLaborForce> queryPoorLaborForceByPoorWork(String plf002){
       return poorLaborForceMapper.queryPoorLaborForceByPoorWork(plf002);
    }

    /**
     * 根据贫困户id 查询劳动力信息
     * @return
     */
    @Override
    public List<PoorLaborForce> queryExcel(String aab301){
        return poorLaborForceMapper.queryExcel(aab301);
    }


    /**
     * 根据身份证号码查询，该用户是否已经登记
     * @param plf005
     * @return
     */
    @Override
    public String queryWorkerByIdCard(String plf005) {
        return poorLaborForceMapper.queryWorkerByIdCard(plf005);
    }

    @Override
    public PoorLaborForce queryName(String plf005) {
        return poorLaborForceMapper.queryName(plf005);
    }


    /**
     * 根据贫困户id 删除劳动力信息
     * @param plf002
     * @return
     */
    @Override
    public int deleteByPlf002(String plf002,String realname) {
        return poorLaborForceMapper.deleteByPlf002(plf002,realname);
    }


    /**
     * 根据行政区划查询劳动力台账信息
     * @return
     */
    @Override
    public List<PoorLaborForce> queryPoorLaborForceAccountByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryPoorLaborForceAccountByAab301(poorLaborForce,startRecord,pageSize);
    }



    /**
     *根据行政区划查询台账总共多条
     * @return
     */
    @Override
    public String queryAllPoorLaborForceAccoutByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllPoorLaborForceAccoutByAab301(poorLaborForce);
    }

    /**
     * 根据行政区划查询所有贫困人口信息
     */
    @Override
    public List<PoorLaborForce> queryPoorPersonAccountByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryPoorPersonAccountByAab301(poorLaborForce,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询所有贫困人口总数信息
     */
    @Override
    public String queryAllPoorPersonAccountByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllPoorPersonAccountByAab301(poorLaborForce);
    }

    @Override
    public String queryAllPoorLaborForceFamily(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllPoorLaborForceFamily(poorLaborForce);
    }

    @Override
    public PoorLaborForce selectByPrimaryidCard(String idcard) {
        return poorLaborForceMapper.selectByPrimaryidCard(idcard);
    }

    /**
     * 根据贫困户id 查询劳动力信息  用于批量删除
     * @return
     */
    public List<PoorLaborForce> queryPoorLaborForceByPoorWorkIds(String plf0002s[]){
        return poorLaborForceMapper.queryPoorLaborForceByPoorWorkIds(plf0002s);
    }

    /**
     * 根据贫困户id 判断该贫困户下有多少个劳动力属于正常劳动力
     * phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    @Override
    public String queryNormalWorkerByPlf002(String plf002) {
        return poorLaborForceMapper.queryNormalWorkerByPlf002(plf002);
    }

    /**
     * 根据行政区划查询劳动力台账信息导出
     * @return
     */
    @Override
    public List<PoorLaborForce> queryPoorLaborForceImportByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryPoorLaborForceAccountByAab301(poorLaborForce,startRecord,pageSize);
    }

    @Override
    public List<PoorLaborForce> queryPoorLaborForceFamily(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryPoorLaborForceFamily(poorLaborForce);
    }

    /**
     *   根据贫困户id 判断该贫困户下有  有就业创业意愿人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    @Override
    public String havejiuyechuangyecount(String plf002) {
        return poorLaborForceMapper.havejiuyechuangyecount(plf002);
    }

    /**
     *   根据贫困户id 判断该贫困户下有  已就业创业人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    @Override
    public String yijiuyechuangyecount(String plf002) {
        return poorLaborForceMapper.yijiuyechuangyecount(plf002);
    }

    /**
     *   根据贫困户id 判断该贫困户下有  已就业创业人名称
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    @Override
    public String yijiuyechuangyename(String plf002) {
        return poorLaborForceMapper.yijiuyechuangyename(plf002);
    }

    /**
     *   根据贫困户id 判断该贫困户下有  未就业创业人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    @Override
    public String weijiuyechuangyecount(String plf002) {
        return poorLaborForceMapper.weijiuyechuangyecount(plf002);
    }

    /**
     *   根据贫困户id 判断该贫困户下有  未就业创业人名称
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    @Override
    public String weijiuyechuangyename(String plf002) {
        return poorLaborForceMapper.weijiuyechuangyename(plf002);
    }

    /**
     * 根据行政区划查询待确认劳动力信息
     * @return
     */
    @Override
    public List<PoorLaborForce> queryPoorLaborForceToBeComfireByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryPoorLaborForceToBeComfireByAab301(poorLaborForce,startRecord,pageSize);
    }

    /**
     *根据行政区划查询根据行政区划查询待确认劳动力信息总共多条
     * @return
     */
    @Override
    public String queryAllPoorLaborForceToBeComfireByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllPoorLaborForceToBeComfireByAab301(poorLaborForce);
    }

    //***************************************待帮扶查询**********************************************************************************************

    @Override
    public List<PoorLaborForce> queryTobeHelpWorkerByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryTobeHelpWorkerByAab301(poorLaborForce,startRecord,pageSize);
    }

    @Override
    public String queryAllTobeHelpWorkerByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllTobeHelpWorkerByAab301(poorLaborForce);
    }


    @Override
    public String queryAllPoorWorkerAccountByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllPoorWorkerAccountByAab301(poorLaborForce);
    }



    //*************************************************************************************************************************

    @Override
    public List<PoorLaborForce> queryRepeatPoorLaborForceByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryRepeatPoorLaborForceByAab301(poorLaborForce,startRecord,pageSize);
    }

    @Override
    public String queryRepeatPoorLaborForceAccoutByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryRepeatPoorLaborForceAccoutByAab301(poorLaborForce);
    }



    //************************************以下是劳动力有转移就业状态,没有就业信息的  台账信息**********************************************************************

    /**
     * 根据行政区划查询劳动力转移就业状态,没有就业信息
     * @return
     */
    @Override
    public List<PoorLaborForce> queryErrorPoorLaborForceAccountByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize) {
        return poorLaborForceMapper.queryErrorPoorLaborForceAccountByAab301(poorLaborForce,startRecord,pageSize);
    }

    /**
     *根据行政区划查询台账总共多条  转移就业状态,没有就业信息
     * @return
     */
    @Override
    public String queryAllErrorPoorLaborForceAccoutByAab301(PoorLaborForce poorLaborForce) {
        return poorLaborForceMapper.queryAllErrorPoorLaborForceAccoutByAab301(poorLaborForce);
    }


    /*************************************************弱劳动力台账************************************************************************************************/

    /**
     * 根据行政区划查询弱劳动力台账信息
     * @return
     */
    public List<PoorLaborForce> queryRuoPoorLaborForceAccountByAab301(PoorLaborForce poorLaborForce, String startRecord, String pageSize){
        return poorLaborForceMapper.queryRuoPoorLaborForceAccountByAab301(poorLaborForce,startRecord,pageSize);
    }



    /**
     *根据行政区划查询弱劳动力台账总共多条
     * @return
     */
    public String queryAllRuoPoorLaborForceAccoutByAab301(PoorLaborForce poorLaborForce){
        return poorLaborForceMapper.queryAllRuoPoorLaborForceAccoutByAab301(poorLaborForce);
    }




}
