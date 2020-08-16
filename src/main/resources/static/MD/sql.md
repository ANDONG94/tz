###### 省全部

~~~sql
select xzqhmc,
       
      (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys004 ='1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%')  贫困劳动力转移就业,
       (select sum(epp012)
          from ENTREPRENEURSHIP
         where AAE100 = '1'
           AND AAB301 LIKE substr(c.xzqhbm, 0, 2) || '%') 创业带动贫困劳动力,
       (select count(1)
          from Poor_Labor_Force p ,TRAINING_SITUATION t
         where p.AAE100 = '1' and p.plf001=tsn010 
           and (t.tsn003='1' or t.tsn003='2' or t.tsn003='3')
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 2) || '%') 贫困劳动力技能培训,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '3' or eys011 = '2')
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 苏陕劳务协作转移就业,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '6'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 城镇公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '5'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 公益专岗数,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '7'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 特设公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '7' or eys011 = '5' or eys011 = '6')
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 公益性岗位合计,
       (select count(1)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and ctf002 is not null) 社区工厂,
       (select sum(ctf008)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and ctf002 is not null) 社区工厂吸纳贫困劳动力,
       (select count(1)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and pab013 is not null) 扶贫基地,
       (select sum(pab008)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and pab013 is not null) 扶贫基地吸纳贫困劳动力,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 创业孵化基地合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 创业孵化基地扶持劳动力人数,
       (select count(1)
          from poor_households p, poor_labor_force l, TECHNICAL_SCHOOLS e
         where p.phd001 = l.plf002
           and l.plf001 = e.ths002
           and e.aae100 = '1'
           and ths006 is not null
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 在读技工院校,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 返乡创业示范园区合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 返乡创业示范园区劳动力人数,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 标准化创业中心合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 标准化创业中心扶持劳动力,
       (select count(1)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 信用乡村,
       (select sum(cvg006)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 信用乡村发放贷款户数,
       (select sum(cvg008)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 信用乡村发放贷款万元,
       (select count(1)
          from JOB_FAIR s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会场,
       (select sum(jfr010)
          from JOB_FAIR s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会贫困劳动力参加人数,
       (select sum(jfr014)
          from JOB_FAIR  s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会贫困劳动力就业人数,
      (select sum(jfr013 )
          from JOB_FAIR  s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%')  招聘会公共就业人数,
       (select count(1)
          from LABOR_BROKERING s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 劳务经纪人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '2'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 贫困村创业致富带头人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 返乡创业致富带头人,
       (select count(1)
          from ENTREPRENEURIAL_TEACHERS s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 创业培训师资,
       (select count(1)
          from PUBLIC_SERVICE_WORKSTATION s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 公益性劳务输出站
  from poor_xzqh c
 where xzqhbm = '610000000000'

~~~

###### 市全部

~~~sql
select xzqhmc,
       
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys004 ='1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%')  贫困劳动力转移就业,
       (select sum(epp012)
          from ENTREPRENEURSHIP
         where AAE100 = '1'
           AND AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 创业带动贫困劳动力,
       (select count(1)
          from Poor_Labor_Force p ,TRAINING_SITUATION t
         where p.AAE100 = '1' and p.plf001=tsn010 
           and (t.tsn003='1' or t.tsn003='2' or t.tsn003='3')
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 贫困劳动力技能培训,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '3' or eys011 = '2')
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 苏陕劳务协作转移就业,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '6'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 城镇公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '5'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益专岗数,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '7'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 特设公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '7' or eys011 = '5' or eys011 = '6')
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益性岗位合计,
       (select count(1)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and ctf002 is not null) 社区工厂,
       (select sum(ctf008)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and ctf002 is not null) 社区工厂吸纳贫困劳动力,
       (select count(1)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and pab013 is not null) 扶贫基地,
       (select sum(pab008)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and pab013 is not null) 扶贫基地吸纳贫困劳动力,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 创业孵化基地合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 创业孵化基地扶持劳动力人数,
       (select count(1)
          from poor_households p, poor_labor_force l, TECHNICAL_SCHOOLS e
         where p.phd001 = l.plf002
           and l.plf001 = e.ths002
           and e.aae100 = '1'
           and ths006 is not null
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 在读技工院校,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 返乡创业示范园区合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 返乡创业示范园区劳动力人数,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 标准化创业中心合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 标准化创业中心扶持劳动力,
       (select count(1)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 信用乡村,
       (select sum(cvg006)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 信用乡村发放贷款户数,
       (select sum(cvg008)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 信用乡村发放贷款万元,
       (select count(1)
          from JOB_FAIR s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会场,
       (select sum(jfr010)
          from JOB_FAIR s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会贫困劳动力参加人数,
       (select sum(jfr014)
          from JOB_FAIR  s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会贫困劳动力就业人数,
      (select sum(jfr013 )
          from JOB_FAIR  s
         where aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%')  招聘会公共就业人数,
       (select count(1)
          from LABOR_BROKERING s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 劳务经纪人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '2'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 贫困村创业致富带头人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 返乡创业致富带头人,
       (select count(1)
          from ENTREPRENEURIAL_TEACHERS s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 创业培训师资,
       (select count(1)
          from PUBLIC_SERVICE_WORKSTATION s
         where s.aae100 = '1'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益性劳务输出站
  from poor_xzqh c
 where fxzqhbm = '610000000000'

~~~

###### 省当年

~~~sql
select xzqhmc,
       
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys004 = '1'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 贫困劳动力转移就业,
       (select sum(epp012)
          from ENTREPRENEURSHIP
         where AAE100 = '1'
           and substr(epp006, 0, 4) = '2019'
           AND AAB301 LIKE substr(c.xzqhbm, 0, 2) || '%') 创业带动贫困劳动力,
       (select count(1)
          from Poor_Labor_Force p, TRAINING_SITUATION t
         where p.AAE100 = '1'
           and p.plf001 = tsn010
           and (t.tsn003 = '1' or t.tsn003 = '2' or t.tsn003 = '3')
           and substr(tsn007, 0, 4) = '2019'
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 2) || '%') 贫困劳动力技能培训,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '3' or eys011 = '2')
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 苏陕劳务协作转移就业,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '6'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 城镇公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '5'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 公益专岗数,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '7'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 特设公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '7' or eys011 = '5' or eys011 = '6')
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 公益性岗位合计,
       (select count(1)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and substr(ctf005, 0, 4) = '2019'
           and ctf002 is not null) 社区工厂,
       (select sum(ctf008)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and substr(ctf005, 0, 4) = '2019'
           and ctf002 is not null) 社区工厂吸纳贫困劳动力,
       (select count(1)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and substr(pab005, 0, 4) = '2019'
           and pab013 is not null) 扶贫基地,
       (select sum(pab008)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%'
           and substr(pab005, 0, 4) = '2019'
           and pab013 is not null) 扶贫基地吸纳贫困劳动力,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 创业孵化基地合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 创业孵化基地扶持劳动力人数,
       (select count(1)
          from poor_households p, poor_labor_force l, TECHNICAL_SCHOOLS e
         where p.phd001 = l.plf002
           and l.plf001 = e.ths002
           and e.aae100 = '1'
           and ths006 is not null
           and substr(ths005, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 2) || '%') 在读技工院校,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 返乡创业示范园区合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 返乡创业示范园区劳动力人数,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 标准化创业中心合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 标准化创业中心扶持劳动力,
       (select count(1)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and substr(cvg004, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 信用乡村,
       (select sum(cvg006)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and substr(cvg004, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 信用乡村发放贷款户数,
       (select sum(cvg008)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and substr(cvg004, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 信用乡村发放贷款万元,
       (select count(1)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会场,
       (select sum(jfr010)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会贫困劳动力参加人数,
       (select sum(jfr014)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会贫困劳动力就业人数,
       (select sum(jfr013)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 招聘会公共就业人数,
       (select count(1)
          from LABOR_BROKERING s
         where s.aae100 = '1'
           and substr(lbk008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 劳务经纪人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '2'
           and substr(thl012, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 贫困村创业致富带头人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '1'
           and substr(thl012, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 返乡创业致富带头人,
       (select count(1)
          from ENTREPRENEURIAL_TEACHERS s
         where s.aae100 = '1'
           and substr(ets007, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 创业培训师资,
       (select count(1)
          from PUBLIC_SERVICE_WORKSTATION s
         where s.aae100 = '1'
           and substr(aae036, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 2) || '%') 公益性劳务输出站
  from poor_xzqh c
 where xzqhbm = '610000000000'

~~~





###### 市当年

~~~sql

select xzqhmc,
       
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys004 = '1'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 贫困劳动力转移就业,
       (select sum(epp012)
          from ENTREPRENEURSHIP
         where AAE100 = '1'
           and substr(epp006, 0, 4) = '2019'
           AND AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 创业带动贫困劳动力,
       (select count(1)
          from Poor_Labor_Force p, TRAINING_SITUATION t
         where p.AAE100 = '1'
           and p.plf001 = tsn010
           and (t.tsn003 = '1' or t.tsn003 = '2' or t.tsn003 = '3')
           and substr(tsn007, 0, 4) = '2019'
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 贫困劳动力技能培训,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '3' or eys011 = '2')
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 苏陕劳务协作转移就业,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '6'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 城镇公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '5'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益专岗数,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '7'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 特设公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '7' or eys011 = '5' or eys011 = '6')
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益性岗位合计,
       (select count(1)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and substr(ctf005, 0, 4) = '2019'
           and ctf002 is not null) 社区工厂,
       (select sum(ctf008)
          from COMMUNITY_FACTORY p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and substr(ctf005, 0, 4) = '2019'
           and ctf002 is not null) 社区工厂吸纳贫困劳动力,
       (select count(1)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and substr(pab005, 0, 4) = '2019'
           and pab013 is not null) 扶贫基地,
       (select sum(pab008)
          from POVERTY_ALLEVIATION_BASE p
         where aae100 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%'
           and substr(pab005, 0, 4) = '2019'
           and pab013 is not null) 扶贫基地吸纳贫困劳动力,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 创业孵化基地合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '1'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 创业孵化基地扶持劳动力人数,
       (select count(1)
          from poor_households p, poor_labor_force l, TECHNICAL_SCHOOLS e
         where p.phd001 = l.plf002
           and l.plf001 = e.ths002
           and e.aae100 = '1'
           and ths006 is not null
           and substr(ths005, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 在读技工院校,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 返乡创业示范园区合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '2'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 返乡创业示范园区劳动力人数,
       (select count(1)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 标准化创业中心合计,
       (select sum(ibb010)
          from INCUBATOR_BASE s
         where s.aae100 = '1'
           and s.ibb006 is not null
           and s.ibb005 = '3'
           and substr(ibb008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 标准化创业中心扶持劳动力,
       (select count(1)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and substr(cvg004, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 信用乡村,
       (select sum(cvg006)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and substr(cvg004, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 信用乡村发放贷款户数,
       (select sum(cvg008)
          from CREDIT_VILLAGE s
         where s.aae100 = '1'
           and substr(cvg004, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 信用乡村发放贷款万元,
       (select count(1)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会场,
       (select sum(jfr010)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会贫困劳动力参加人数,
       (select sum(jfr014)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会贫困劳动力就业人数,
       (select sum(jfr013)
          from JOB_FAIR s
         where aae100 = '1'
           and substr(jfr006, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 招聘会公共就业人数,
       (select count(1)
          from LABOR_BROKERING s
         where s.aae100 = '1'
           and substr(lbk008, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 劳务经纪人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '2'
           and substr(thl012, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 贫困村创业致富带头人,
       (select count(1)
          from TAKE_THE_LEAD s
         where s.aae100 = '1'
           and thl002 = '1'
           and substr(thl012, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 返乡创业致富带头人,
       (select count(1)
          from ENTREPRENEURIAL_TEACHERS s
         where s.aae100 = '1'
           and substr(ets007, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 创业培训师资,
       (select count(1)
          from PUBLIC_SERVICE_WORKSTATION s
         where s.aae100 = '1'
           and substr(aae036, 0, 4) = '2019'
           and s.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益性劳务输出站
  from poor_xzqh c
 where fxzqhbm = '610000000000'

~~~

###### 表九

~~~sql
select xzqhmc,
       
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys004 = '1'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 累计转移就业,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys004 = '1'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 当年转移就业,
       (select count(1)
          from ENTREPRENEURSHIP
         where AAE100 = '1'
      
           AND AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 累计创业,
       (select count(1)
          from ENTREPRENEURSHIP
         where AAE100 = '1'
           and substr(epp006, 0, 4) = '2019'
           AND AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 当年创业,
       (select count(1)
          from Poor_Labor_Force p, TRAINING_SITUATION t
         where p.AAE100 = '1'
           and p.plf001 = tsn010
           and (t.tsn003 = '1' or t.tsn003 = '2' or t.tsn003 = '3')
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 累计技能培训,
       (select count(1)
          from Poor_Labor_Force p, TRAINING_SITUATION t
         where p.AAE100 = '1'
           and p.plf001 = tsn010
           and (t.tsn003 = '1' or t.tsn003 = '2' or t.tsn003 = '3')
           and substr(tsn007, 0, 4) = '2019'
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 当年技能培训,
       (select count(1)
          from Poor_Labor_Force p, TRAINING_SITUATION t
         where p.AAE100 = '1'
           and p.plf001 = tsn010
           and t.tsn003 = '4'
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 累计创业培训,
       (select count(1)
          from Poor_Labor_Force p, TRAINING_SITUATION t
         where p.AAE100 = '1'
           and p.plf001 = tsn010
           and t.tsn003 = '4'
           and substr(tsn007, 0, 4) = '2019'
           AND p.AAB301 LIKE substr(c.xzqhbm, 0, 4) || '%') 当年创业培训,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '5'
      
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 累计公益专岗数,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '5'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 当年公益专岗数,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '7'
              
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 累计特设公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '7'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 当年特设公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '6'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 累计城镇公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and eys011 = '6'
           and substr(eys009, 0, 4) = '2019'
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 当年城镇公岗,
       (select count(1)
          from poor_households p, poor_labor_force l, EMPLOYMENT_SITUATION e
         where p.phd001 = l.plf002
           and l.plf001 = e.eys002
           and e.aae100 = '1'
           and (eys011 = '7' or eys011 = '5' or eys011 = '6')
           and p.aab301 like substr(c.xzqhbm, 0, 4) || '%') 公益性岗位合计

  from poor_xzqh c
 where fxzqhbm = '610000000000'  order by sort

~~~

