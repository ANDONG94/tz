package com.tdkj.dao.importExcle;

import com.tdkj.model.TechnicalSchoolsTmp;
import com.tdkj.model.TrainingSituationTmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingSituationTmpMapper {
    int deleteByPrimaryKey(String tsn001);

    int insert(TrainingSituationTmp record);

    int insertSelective(TrainingSituationTmp record);

    int addList(@Param("list") List<TrainingSituationTmp> list);

    int updateCardList(@Param("updatelist") List<TrainingSituationTmp> list);

    TrainingSituationTmp selectByPrimaryKey(String tsn001);

    int updateByPrimaryKeySelective(TrainingSituationTmp record);

    int updateByPrimaryKey(TrainingSituationTmp record);
    /**
     * 查询正确信息
     * @param batch
     */
    @Select("select tsn010,aac003, f_getcodename('AAA007',tsn003) tsn003, f_getcodename('AAA021',tsn004) tsn004,\n" +
            "                        tsn005,tsn006, tsn007, tsn008, tsn011,f_getcodename('EDC441',tsn012) tsn012,\n" +
            "                        tsn016, tsn009  from TRAINING_SITUATION_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='1' ")
    List<TrainingSituationTmp> getTrainingSituationCorrect(String batch) ;

    /**
     * 查询错误信息
     * @param batch
     */
    @Select("select message,tsn010,aac003,aac003, f_getcodename('AAA007',tsn003) tsn003, f_getcodename('AAA021',tsn004) tsn004,\n" +
            "                        tsn005,tsn006, tsn007, tsn008, tsn011,f_getcodename('EDC441',tsn012) tsn012,\n" +
            "                        tsn016, tsn009  from TRAINING_SITUATION_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='2' ")
    List<TrainingSituationTmp> getTrainingSituationError(String batch) ;
    /**
     * 保存正确信息
     * @param batch
     */
    @Insert("INSERT INTO TRAINING_SITUATION SELECT * FROM  TRAINING_SITUATION_TMP S  WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='1'")
    int SaveTrainingSituation(String batch) ;

    @Update(" call Import_TSN_Check(#{0},#{1})")
    int stored_procedure(String batch,String type_1);
}