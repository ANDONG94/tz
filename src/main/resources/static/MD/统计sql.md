# 统计sql

###### 贫困户

~~~sql
select XZQHMC,
       (select count(1)
        from POOR_HOUSEHOLDS p,
             RECURSION r
        where p.AAB301 = r.AAB301
          and AAE100 = '1'
          and PHD013 = '1'
          and r.PARENTID = XZQHBM) 贫困户数,
       (select count(1)
        from POOR_HOUSEHOLDS p,
             RECURSION r
        where p.AAB301 = r.AAB301
          and AAE100 = '1'
          and PHD008 > 0
          and PHD013 = '1'
          and r.PARENTID = XZQHBM) 有劳动力贫困户数,
       (select count(1)
        from POOR_HOUSEHOLDS p,
             RECURSION r
        where p.AAB301 = r.AAB301
          and AAE100 = '1'
          and PHD008 = 0
          and PHD013 = '1'
          and r.PARENTID = XZQHBM) 无劳动力劳动力贫困户数,
       nvl((SELECT sum(count(distinct plf001)) cun2
            FROM poor_labor_force a,
                 POOR_HOUSEHOLDS p,
                 RECURSION r
            where a.plf002 = p.phd001
              and (plf018 in ('01', '02', '05') or plf018 is null)
              and ispoor = '1'
              and plf010 = '3'
              and a.aab301 = r.aab301
              and a.aae100 = '1'
              and plf011 = '1'
              and plf025 = '0'
              and PHD013 = '1'
              and YIJIUYECHUANGYECOUNT=0
              and r.parentid = xzqhbm
            group by a.aab301),
           0)                      无有转移就业意愿,
       (select nvl(count(distinct phd001), 0)
        from (select phd001
              from (SELECT phd001,
                           eys009,
                           eys010,

                           row_number() over (partition by phd001 order by eys009 desc) as sn
                    FROM poor_households h,
                         poor_labor_force A,
                         EMPLOYMENT_SITUATION e,
                         RECURSION r
                    where h.aab301 = r.aab301
                      and A.plf002 = h.phd001
                      and a.plf001 = e.eys002
                      and r.parentid = xzqhbm
                      and a.aae100 = '1'
                      and e.aae100 = '1'
                      and h.aae100 = '1'
                      and PHD013 = '1'
                      and (plf018 in ('01', '02', '05') or plf018 is null)
                      and ispoor = '1'
                      and plf025 = '0'
                      and plf015 is null
                      and eys002 is not null
                      and success = '1')
              where sn = 1
              union
              select phd001
              from (SELECT phd001,
                           epp006,
                           epp007,

                           row_number() over (partition by phd001 order by epp006 desc) as sn
                    FROM poor_households h,
                         poor_labor_force A,
                         ENTREPRENEURSHIP e,
                         RECURSION r
                    where h.aab301 = r.aab301
                      and A.plf002 = h.phd001
                      and a.plf001 = e.epp002
                      and r.parentid = xzqhbm
                      and a.aae100 = '1'
                      and e.aae100 = '1'
                      and h.aae100 = '1'
                      and PHD013 = '1'
                      and plf015 is null
                      and (plf018 in ('01', '02', '05') or plf018 is null)
                      and ispoor = '1'
                      and plf025 = '0'
                      and epp002 is not null
                      and success = '1')
              where sn = 1))       至少一人就业户
from POOR_XZQH
where FXZQHBM = '610000000000'
   or XZQHBM = '610000000000'
order by SORT;
;
~~~



###### 劳动力

~~~sql
select XZQHMC,
       (select count(PLF001)
        from POOR_HOUSEHOLDS p,
             POOR_LABOR_FORCE l,
             RECURSION r
        where p.AAB301 = r.AAB301
          and p.PHD001 = l.PLF002
          and l.AAE100 = '1'
          and PLF008 >= 16
          and PLF008 < 60
          and r.PARENTID = XZQHBM) 人数,
       (select count(PLF001)
        from POOR_HOUSEHOLDS p,
             POOR_LABOR_FORCE l,
             RECURSION r
        where p.AAB301 = r.AAB301
          and p.PHD001 = l.PLF002
          and l.AAE100 = '1'
          and PLF015 is not null
          and PLF008 >= 16
          and PLF008 < 60
          and r.PARENTID = XZQHBM) 上学,
       (select count(PLF001)
        from POOR_HOUSEHOLDS p,
             POOR_LABOR_FORCE l,
             RECURSION r
        where p.AAB301 = r.AAB301
          and p.PHD001 = l.PLF002
          and l.AAE100 = '1'
          and plf018 in ('03', '04')
          and PLF008 >= 16
          and PLF008 < 60
          and r.PARENTID = XZQHBM) 无劳动能力,
       (select count(PLF001)
        from POOR_HOUSEHOLDS p,
             POOR_LABOR_FORCE l,
             RECURSION r
        where p.AAB301 = r.AAB301
          and p.PHD001 = l.PLF002
          and l.AAE100 = '1'
          and plf010 = '3'
          and PLF008 >= 16
          and PLF008 < 60
          and r.PARENTID = XZQHBM) 无意愿,
       (select count(PLF001)
        from POOR_HOUSEHOLDS p,
             POOR_LABOR_FORCE l,
             RECURSION r
        where p.AAB301 = r.AAB301
          and p.PHD001 = l.PLF002
          and l.AAE100 = '1'
          and plf015 is null
          and (plf018 in ('01', '02', '05') or plf018 is null)
          and ispoor = '1'
          and plf025 = '0'
          and PLF008 >= 16
          and PLF008 < 60
          and r.PARENTID = XZQHBM) 劳动力,
       (select count(distinct PLF001)
        from (SELECT PLF001,
                     row_number() over (partition by PLF001 order by eys009 desc) as sn
              FROM poor_households h,
                   poor_labor_force A,
                   EMPLOYMENT_SITUATION e,
                   RECURSION r
              where h.aab301 = r.aab301
                and A.plf002 = h.phd001
                and a.plf001 = e.eys002
                and r.parentid = xzqhbm
                and a.aae100 = '1'
                and e.aae100 = '1'
                and h.aae100 = '1'
                and (plf018 in ('01', '02', '05') or plf018 is null)
                and ispoor = '1'
                and plf025 = '0'
                and plf015 is null
                and eys002 is not null
                and success = '1')
        where sn = 1)              就业,
       (select count(distinct PLF001)
        from (SELECT PLF001,

                     row_number() over (partition by PLF001 order by epp006 desc) as sn
              FROM poor_households h,
                   poor_labor_force A,
                   ENTREPRENEURSHIP e,
                   RECURSION r
              where h.aab301 = r.aab301
                and A.plf002 = h.phd001
                and a.plf001 = e.epp002
                and r.parentid = xzqhbm
                and a.aae100 = '1'
                and e.aae100 = '1'
                and h.aae100 = '1'
                and plf015 is null
                and (plf018 in ('01', '02', '05') or plf018 is null)
                and ispoor = '1'
                and plf025 = '0'
                and epp002 is not null
                and success = '1')
        where sn = 1)              创业
from POOR_XZQH
where FXZQHBM = '610000000000'
   or XZQHBM = '610000000000'
order by SORT;
;
~~~

