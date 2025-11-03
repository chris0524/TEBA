-- 程式：untgr005r 財產分類量值統計季報表
-- 程式：untgr008r 市有財產增減結存表
-- 程式：untgr009r 國有財產增減結存表
-- 程式：untgr017r 財產異動統計表
-- 程式：untgr019r 財產目錄總表
-- 程式：untgr021r 珍貴動產、不動產增減結存表
-- 程式：untgr022r 珍貴動產、不動產目錄總表
-- 程式：pubgr026r 珍貴動產、不動產目錄總表
-- 程式：pubgr037r 國有財產目錄總表
-- 注意：執行完untgr005r.sql後再執行untgr005r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「&9」檔案
-- 傳入值：&1:異動人員editid
-- 傳入值：&2:入帳機關enterorg(傳入99表示全部機關(不含測試機關3839999))
-- 傳入值：&3:異動日期－起enterdateS
-- 傳入值：&4:異動日期－訖enterdateE
-- 傳入值：&5:財產性質propertykind(傳入99表示未選擇)
-- 傳入值：&6:基金財產fundtype(傳入99表示未選擇)
-- 傳入值：&7:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&8:權屬ownership(1：市有,2：國有,3：縣有,4：KOC,5：鄉鎮市有)
-- 傳入值：&9:報表檔(UNTGR_UNTGR005R:財產分類量值統計季報表)
-- 傳入值：&9:報表檔(UNTGR_UNTGR008R:市有財產增減結存表)
-- 傳入值：&9:報表檔(UNTGR_UNTGR009R:國有財產增減結存表)
-- 傳入值：&9:報表檔(UNTGR_UNTGR017R:財產異動統計表)
-- 傳入值：&9:報表檔(UNTGR_UNTGR019R:財產目錄總表)
-- 傳入值：&9:報表檔(UNTGR_UNTGR021R:珍貴動產、不動產增減結存表)
-- 傳入值：&9:報表檔(UNTGR_UNTGR022R:珍貴動產、不動產目錄總表)
-- 傳入值：&9:報表檔(PUBGR_PUBGR026R:珍貴動產、不動產目錄總表)
-- 傳入值：&9:報表檔(PUBGR_PUBGR037R:國有財產目錄總表)
-- 傳入值：&10:上層機關彙總表isorganmanager(Y:是,N:否)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5 傳入值6 傳入值7 傳入值8 傳入值9 傳入值10
-- 執行指令範例(untgr005r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980701 0980930 99 99  N 3 UNTGR_UNTGR005R N
-- 執行指令範例(untgr005r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980701 0980930 99 99  N 3 UNTGR_UNTGR005R Y
-- 執行指令範例(untgr008r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0951001 0951231 99 99  N 1 UNTGR_UNTGR008R N
-- 執行指令範例(untgr008r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0951001 0951231 99 99  N 1 UNTGR_UNTGR008R Y
-- 執行指令範例(untgr009r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980701 0980731 99 99  N 2 UNTGR_UNTGR009R N
-- 執行指令範例(untgr009r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980701 0980731 99 99  N 2 UNTGR_UNTGR009R Y
-- 執行指令範例(untgr017r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0951001 0951231 99 99  N 1 UNTGR_UNTGR017R N
-- 執行指令範例(untgr017r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0951001 0951231 99 99  N 1 UNTGR_UNTGR017R Y
-- 執行指令範例(untgr019r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980729 0980729 99 99 99 3 UNTGR_UNTGR019R N
-- 執行指令範例(untgr019r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980729 0980729 99 99 99 3 UNTGR_UNTGR019R Y
-- 執行指令範例(untgr021r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980701 0980731 99 99  Y 3 UNTGR_UNTGR021R N
-- 執行指令範例(untgr021r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0980701 0980731 99 99  Y 3 UNTGR_UNTGR021R Y
-- 執行指令範例(untgr022r程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0981231 0981231 99 99  Y 3 UNTGR_UNTGR022R N
-- 執行指令範例(untgr022r程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 000000002Z 0981231 0981231 99 99  Y 3 UNTGR_UNTGR022R Y
-- 執行指令範例(pubgr026r程式-全部機關      )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 99         0981231 0981231 99 99  Y 2 PUBGR_PUBGR026R N
-- 執行指令範例(pubgr037r程式-全部機關      )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r.sql test 99         0981231 0981231 99 99  N 2 PUBGR_PUBGR037R N


--訊息 207，層級 16，狀態 1，行 5691 Invalid column name 'newBookValue'.
-- 訊息 207，層級 16，狀態 1，行 5734 Invalid column name 'reducebookvalue'.

-- 執行指令的記錄檔
--spool D:\temp\kfcp\&9--&1

-- 先刪除該使用者先前產製的資料
--prompt ◎先刪除該使用者先前產製的資料
delete &9
 where editid='&1'
;
--commit;

-- 新增「財產類別」資料
--prompt ◎新增「財產類別」資料
insert into &9
select '&1',b.propertykind,b.fundtype,a.propertytype,
       case when a.propertytype=010 then 01
            when a.propertytype=020 then 02
            when a.propertytype=030 then 03
            when a.propertytype=040 then 03
            when a.propertytype=050 then 03
            when a.propertytype=055 then 03
            when a.propertytype=060 then 03
            when a.propertytype=070 then 04
            when a.propertytype=080 then 04
            when a.propertytype=090 then 04
            when a.propertytype=100 then 04
            when a.propertytype=110 then 05
            when a.propertytype=120 then 05
            when a.propertytype=130 then 05
            when a.propertytype=140 then 05
            when a.propertytype=150 then 05
            when a.propertytype=160 then 05
            when a.propertytype=165 then 05
            when a.propertytype=170 then 05
            when a.propertytype=180 then 06
            when a.propertytype=190 then 06
            when a.propertytype=200 then 06
            when a.propertytype=205 then 06
            when a.propertytype=210 then 06
            when a.propertytype=220 then 07
            when a.propertytype=230 then 08
            when a.propertytype=240 then 09
            when a.propertytype=250 then 10
            else 00 end,
       0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  from UNTGR_PROPERTYTYPE a,
       (
         (
            select distinct propertykind,fundtype
              from UNTLA_LAND
             where ( enterorg=case when '&2'='99' then enterorg else '&2' end  or ( '&10'='Y' and enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
               and ownership='&8'
               and case when '&7'='99' then '99' else valuable end ='&7'
               and verify='Y'
               and (
                      case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '01' else '&5' end or
                      case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '02' else '&5' end or
                      case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '03' else '&5' end 
                   )
               and case  when '&6'='99' then '99' else fundtype end='&6'
         )
         union
         (
           select distinct propertykind,fundtype
             from UNTBU_BUILDING
            where ( enterorg=case when '&2'='99' then enterorg else '&2' end  or ( '&10'='Y' and enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
              and ownership='&8'
              and case when '&7'='99' then '99' else valuable end ='&7'
              and verify='Y'
              and (
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '01' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '02' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '03' else '&5' end 
                  )
              and case  when '&6'='99' then '99' else fundtype end='&6'
         )
         union
         (
           select distinct propertykind,fundtype
             from UNTRF_ATTACHMENT
            where ( enterorg=case when '&2'='99' then enterorg else '&2' end  or ( '&10'='Y' and enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
              and ownership='&8'
              and case when '&7'='99' then '99' else valuable end ='&7'
              and verify='Y'
              and (
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '01' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '02' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '03' else '&5' end 
                  )
              and case  when '&6'='99' then '99' else fundtype end='&6'
         )
         union
         (
           select distinct propertykind,fundtype
             from UNTMP_MOVABLE
            where ( enterorg=case when '&2'='99' then enterorg else '&2' end  or ( '&10'='Y' and enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
              and ownership='&8'
              and case when '&7'='99' then '99' else valuable end ='&7'
              and verify='Y'
              and (
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '01' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '02' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '03' else '&5' end 
                  )
              and case  when '&6'='99' then '99' else fundtype end='&6'
         )
         union
         (
           select distinct propertykind,fundtype
             from UNTVP_ADDPROOF
            where ( enterorg=case when '&2'='99' then enterorg else '&2' end  or ( '&10'='Y' and enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
              and ownership='&8'
              and verify='Y'
              and (
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '01' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '02' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '03' else '&5' end 
                  )
              and case  when '&6'='99' then '99' else fundtype end='&6'
         )
         union
         (
           select distinct propertykind,fundtype
             from UNTRT_ADDPROOF
            where ( enterorg=case when '&2'='99' then enterorg else '&2' end  or ( '&10'='Y' and enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
              and ownership='&8'
              and verify='Y'
              and (
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '01' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '02' else '&5' end or
                     case when '&5'='99' then '99' else propertykind end=case when '&5'='00' then '03' else '&5' end 
                  )
              and case  when '&6'='99' then '99' else fundtype end='&6'
         )
       ) b
 where programno='&9'
;


-- 財產類別：10.土地，本期結存
-- 檔案說明：土地主檔UNTLA_LAND、&9
--prompt ◎財產類別：10.土地，本期結存
update  a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTLA_LAND b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTLA_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowarea      = isnull(a.nowarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTLA_LAND b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTLA_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.holdarea)
                           from UNTLA_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTLA_LAND b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTLA_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
			 select sum(b.bookvalue)
			   from UNTLA_REDUCEDETAIL b
			  where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
				and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
				and b.reducedate<='&4'
				and b.ownership='&8'
				and case when '&7'='99' then '99' else b.valuable end ='&7'
				and b.verify='Y'
				and b.propertykind=a.propertykind
				and isnull(b.fundtype,1)=isnull(a.fundtype,1)
			  group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=10
;


-- 財產類別：10.土地，本期增加(新增)
-- 檔案說明：土地主檔UNTLA_LAND、&9
--prompt ◎財產類別：10.土地，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select count(*)
                           from UNTLA_LAND b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTLA_LAND b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTLA_LAND b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=10
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：10.土地，本期增加(增值)
-- 檔案說明：土地增減值單明細檔UNTLA_ADJUSTDETAIL、&9
--prompt ◎財產類別：10.土地，本期增加(增值)
update a set 
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTLA_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
--                            and b.adjustArea>=0
                            and b.adjustholdarea>=0
--                            and b.adjustBookUnit>=0
                            and b.addbookvalue>=0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTLA_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
--                            and b.adjustArea>=0
                            and b.adjustholdarea>=0
--                            and b.adjustBookUnit>=0
                            and b.addbookvalue>=0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=10
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：10.土地，本期減少(減少)
-- 檔案說明：土地減損單明細檔UNTLA_REDUCEDETAIL、&9
--prompt ◎財產類別：10.土地，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select count(*)
                           from UNTLA_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.holdarea)
                           from UNTLA_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTLA_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
from &9 a                      
 where a.editid='&1'
   and a.propertytype=10
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：10.土地，本期減少(減值)
-- 檔案說明：土地增減值單明細檔UNTLA_ADJUSTDETAIL、&9
--prompt ◎財產類別：10.土地，本期減少(減值)
update a set 
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(-b.adjustholdarea)
                           from UNTLA_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.adjustholdarea<=0
                            and b.reducebookvalue<=0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(-b.reducebookvalue)
                           from UNTLA_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
--                            and b.adjustArea<=0
                            and b.adjustholdarea<=0
--                            and b.adjustBookUnit<=0
                            and b.reducebookvalue<=0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a                      
 where a.editid='&1'
   and a.propertytype=10
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：20.土地改良物，本期結存
-- 檔案說明：土地改良物主檔UNTRF_ATTACHMENT、&9
--prompt ◎財產類別：20.土地改良物，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTRF_ATTACHMENT b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTRF_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowarea      = isnull(a.nowarea,0)+isnull(
                      (
                         select sum(b.originalmeasure)
                           from UNTRF_ATTACHMENT b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addmeasure)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducemeasure)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.measure)
                           from UNTRF_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTRF_ATTACHMENT b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTRF_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=20
;


-- 財產類別：20.土地改良物，本期增加(新增)
-- 檔案說明：土地改良物主檔UNTRF_ATTACHMENT、&9
--prompt ◎財產類別：20.土地改良物，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select count(*)
                           from UNTRF_ATTACHMENT b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.originalmeasure)
                           from UNTRF_ATTACHMENT b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTRF_ATTACHMENT b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=20
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;

-- 財產類別：20.土地改良物，本期增加(增值)
-- 檔案說明：土地改良物增減值單明細檔UNTRF_ADJUSTDETAIL、&9
--prompt ◎財產類別：20.土地改良物，本期增加(增值)
update a set 
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.addmeasure)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from  &9  a
 where a.editid='&1'
   and a.propertytype=20
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：20.土地改良物，本期減少(減少)
-- 檔案說明：土地改良物減損單明細檔UNTRF_REDUCEDETAIL、&9
--prompt ◎財產類別：20.土地改良物，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select count(*)
                           from UNTRF_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.measure)
                           from UNTRF_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTRF_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from  &9 a
 where a.editid='&1'
   and a.propertytype=20
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：20.土地改良物，本期減少(減值)
-- 檔案說明：土地改良物增減值單明細檔UNTRF_ADJUSTDETAIL、&9
--prompt ◎財產類別：20.土地改良物，本期減少(減值)
update a set 
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.addmeasure)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and '平方公尺'=(select propertyunit from SYSPK_PROPERTYCODE c where c.enterorg='000000000A' and c.propertyno=b.propertyno)
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTRF_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=20
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：30.房屋建築及設備－辦公房屋，本期結存
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：30.房屋建築及設備－辦公房屋，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowarea      = isnull(a.nowarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a                      
 where a.editid='&1'
   and a.propertytype=30
;


-- 財產類別：30.房屋建築及設備－辦公房屋，本期增加(新增)
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：30.房屋建築及設備－辦公房屋，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=30
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：30.房屋建築及設備－辦公房屋，本期增加(增值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：30.房屋建築及設備－辦公房屋，本期增加(增值)
update a set 
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'                            
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a                      
 where a.editid='&1'
   and a.propertytype=30
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：30.房屋建築及設備－辦公房屋，本期減少(減少)
-- 檔案說明：建物減損單明細檔UNTBU_REDUCEDETAIL、&9
--prompt ◎財產類別：30.房屋建築及設備－辦公房屋，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=30
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：30.房屋建築及設備－辦公房屋，本期減少(減值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：30.房屋建築及設備－辦公房屋，本期減少(減值)
update a set 
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) between '20102' and '20105'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a                     
 where a.editid='&1'
   and a.propertytype=30
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;



--UNTGR_UNTGR005R
-- 財產類別：40.房屋建築及設備－宿舍，本期結存
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：40.房屋建築及設備－宿舍，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowarea      = isnull(a.nowarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=40
;


-- 財產類別：40.房屋建築及設備－宿舍，本期增加(新增)
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：40.房屋建築及設備－宿舍，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=40
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：40.房屋建築及設備－宿舍，本期增加(增值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：40.房屋建築及設備－宿舍，本期增加(增值)
update a set 
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=40
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：40.房屋建築及設備－宿舍，本期減少(減少)
-- 檔案說明：建物減損單明細檔UNTBU_REDUCEDETAIL、&9
--prompt ◎財產類別：40.房屋建築及設備－宿舍，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=40
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：40.房屋建築及設備－宿舍，本期減少(減值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：40.房屋建築及設備－宿舍，本期減少(減值)
update a set 
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=40
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;



-- 財產類別：50.房屋建築及設備－其他，本期結存
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：50.房屋建築及設備－其他，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowarea      = isnull(a.nowarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=50
;


-- 財產類別：50.房屋建築及設備－其他，本期增加(新增)
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：50.房屋建築及設備－其他，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=50
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：50.房屋建築及設備－其他，本期增加(增值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：50.房屋建築及設備－其他，本期增加(增值)
update a set 
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=50
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：50.房屋建築及設備－其他，本期減少(減少)
-- 檔案說明：建物減損單明細檔UNTBU_REDUCEDETAIL、&9
--prompt ◎財產類別：50.房屋建築及設備－其他，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=50
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：50.房屋建築及設備－其他，本期減少(減值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：50.房屋建築及設備－其他，本期減少(減值)
update a set 
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5) not between '20102' and '20106'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a
 where a.editid='&1'
   and a.propertytype=50
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：55.房屋建築及設備，本期結存
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：55.房屋建築及設備，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowarea      = isnull(a.nowarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
 from &9 a  
 where a.editid='&1'
   and a.propertytype=55
;


-- 財產類別：55.房屋建築及設備，本期增加(新增)
-- 檔案說明：建物主檔UNTBU_BUILDING、&9
--prompt ◎財產類別：55.房屋建築及設備，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.originalholdarea)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTBU_BUILDING b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=55
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：55.房屋建築及設備，本期增加(增值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：55.房屋建築及設備，本期增加(增值)
update a set 
       addarea      = isnull(a.addarea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=55
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：55.房屋建築及設備，本期減少(減少)
-- 檔案說明：建物減損單明細檔UNTBU_REDUCEDETAIL、&9
--prompt ◎財產類別：55.房屋建築及設備，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select count(*)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.holdarea)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.bookvalue)
                           from UNTBU_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=55
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：55.房屋建築及設備，本期減少(減值)
-- 檔案說明：建物增減值單明細檔UNTBU_ADJUSTDETAIL、&9
--prompt ◎財產類別：55.房屋建築及設備，本期減少(減值)
update a set 
       reducearea      = isnull(a.reducearea,0)+isnull(
                      (
                         select sum(b.adjustholdarea)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTBU_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=55
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：60.房屋建築及設備－小計
-- 檔案說明：&9
--prompt ◎財產類別：60.房屋建築及設備－小計
update a set 
        addamount=b.addamount,
        addarea=b.addarea,
        addbookvalue=b.addbookvalue,
        reduceamount=b.reduceamount,
        reducearea=b.reducearea,
        reducebookvalue=b.reducebookvalue,
        nowamount=b.nowamount,
        nowarea=b.nowarea,
        nowbookvalue=b.nowbookvalue
       from &9 a  inner join
          (select sum(addamount) as addamount,sum(addarea) as addarea,sum(addbookvalue) as addbookvalue,
                 sum(reduceamount) as reduceamount,sum(reducearea) as reducearea,sum(reducebookvalue)  as reducebookvalue,
                 sum(nowamount) as nowamount,sum(nowarea) as nowarea,sum(nowbookvalue) as nowbookvalue,
                 editid,propertykind,fundtype
            from &9 
            group by editid,propertykind,fundtype
            ) b
           on b.editid='&1'
             and propertytype between 30 and 50
             and b.propertykind=a.propertykind
             and isnull(b.fundtype,1)=isnull(a.fundtype,1)   
  where a.editid='&1'
   and a.propertytype=60
;


-- 財產類別：70.機械及設備－電腦設備，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：70.機械及設備－電腦設備，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=70
;


-- 財產類別：70.機械及設備－電腦設備，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：70.機械及設備－電腦設備，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=70
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：70.機械及設備－電腦設備，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：70.機械及設備－電腦設備，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=70
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：70.機械及設備－電腦設備，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：70.機械及設備－電腦設備，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=70
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;



-- 財產類別：70.機械及設備－電腦設備，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：70.機械及設備－電腦設備，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=70
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：80.機械及設備－其他，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：80.機械及設備－其他，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=80
;


-- 財產類別：80.機械及設備－其他，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：80.機械及設備－其他，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=80
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：80.機械及設備－其他，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：80.機械及設備－其他，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=80
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：80.機械及設備－其他，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：80.機械及設備－其他，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=80
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：80.機械及設備－其他，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：80.機械及設備－其他，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and substring(b.propertyno,1,3)!='314'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=80
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：90.機械及設備，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：90.機械及設備，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=90
;


-- 財產類別：90.機械及設備，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：90.機械及設備，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=90
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：90.機械及設備，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：90.機械及設備，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=90
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：90.機械及設備，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：90.機械及設備，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=90
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：90.機械及設備，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：90.機械及設備，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='3'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=90
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：100.機械及設備－小計
-- 檔案說明：&9
--prompt ◎財產類別：100.機械及設備－小計
update a set 
        addamount=b.addamount,
        addarea=b.addarea,
        addbookvalue=b.addbookvalue,
        reduceamount=b.reduceamount,
        reducearea=b.reducearea,
        reducebookvalue=b.reducebookvalue,
        nowamount=b.nowamount,
        nowarea=b.nowarea,
        nowbookvalue=b.nowbookvalue
       from &9 a  inner join
          (select sum(addamount) as addamount,sum(addarea) as addarea,sum(addbookvalue) as addbookvalue,
                 sum(reduceamount) as reduceamount,sum(reducearea) as reducearea,sum(reducebookvalue)  as reducebookvalue,
                 sum(nowamount) as nowamount,sum(nowarea) as nowarea,sum(nowbookvalue) as nowbookvalue,
                 editid,propertykind,fundtype
            from &9 
            group by editid,propertykind,fundtype
            ) b
           on b.editid='&1'
             and propertytype between 70 and 90
             and b.propertykind=a.propertykind
             and isnull(b.fundtype,1)=isnull(a.fundtype,1)   
  where a.editid='&1'
   and a.propertytype=100
;


-- 財產類別：110.交通及運輸設備－船，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：110.交通及運輸設備－船，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=110
;


-- 財產類別：110.交通及運輸設備－船，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：110.交通及運輸設備－船，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=110
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：110.交通及運輸設備－船，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：110.交通及運輸設備－船，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=110
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：110.交通及運輸設備－船，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：110.交通及運輸設備－船，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=110
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：110.交通及運輸設備－船，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：110.交通及運輸設備－船，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40201'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=110
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;

-- 財產類別：120.交通及運輸設備－飛機，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：120.交通及運輸設備－飛機，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=120
;


-- 財產類別：120.交通及運輸設備－飛機，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：120.交通及運輸設備－飛機，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=120
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：120.交通及運輸設備－飛機，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：120.交通及運輸設備－飛機，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=120
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：120.交通及運輸設備－飛機，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：120.交通及運輸設備－飛機，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=120
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：120.交通及運輸設備－飛機，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：120.交通及運輸設備－飛機，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,5)='40302'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=120
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：130.交通及運輸設備－汽車，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：130.交通及運輸設備－汽車，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=130
;


-- 財產類別：130.交通及運輸設備－汽車，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：130.交通及運輸設備－汽車，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=130
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：130.交通及運輸設備－汽車，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：130.交通及運輸設備－汽車，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=130
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：130.交通及運輸設備－汽車，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：130.交通及運輸設備－汽車，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=130
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：130.交通及運輸設備－汽車，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：130.交通及運輸設備－汽車，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704')
                            and b.propertyno not in ('401070207','401070208','401070209')
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=130
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：140.交通及運輸設備－機車，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：140.交通及運輸設備－機車，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=140
;


-- 財產類別：140.交通及運輸設備－機車，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：140.交通及運輸設備－機車，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=140
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：140.交通及運輸設備－機車，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：140.交通及運輸設備－機車，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=140
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：140.交通及運輸設備－機車，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：140.交通及運輸設備－機車，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=140
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：140.交通及運輸設備－機車，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：140.交通及運輸設備－機車，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and ( substring(b.propertyno,1,5)='40104' or substring(b.propertyno,1,7)='4010705' )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=140
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：150.交通及運輸設備－汽（機）車－本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：150.交通及運輸設備－汽（機）車－本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=150
;


-- 財產類別：150.交通及運輸設備－汽（機）車－本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：150.交通及運輸設備－汽（機）車－本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=150
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：150.交通及運輸設備－汽（機）車－本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：150.交通及運輸設備－汽（機）車－本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=150
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：150.交通及運輸設備－汽（機）車－本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：150.交通及運輸設備－汽（機）車－本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=150
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：150.交通及運輸設備－汽（機）車－本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：150.交通及運輸設備－汽（機）車－本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and (substring(b.propertyno,1,5)='40104' or ( substring(b.propertyno,1,7) in ('4010701','4010702','4010703','4010704','4010705') and b.propertyno not in ('401070207','401070208','401070209') ) )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=150
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：160.交通及運輸設備－其他，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：160.交通及運輸設備－其他，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=160
;


-- 財產類別：160.交通及運輸設備－其他，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：160.交通及運輸設備－其他，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=160
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：160.交通及運輸設備－其他，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：160.交通及運輸設備－其他，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=160
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：160.交通及運輸設備－其他，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：160.交通及運輸設備－其他，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=160
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：160.交通及運輸設備－其他，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：160.交通及運輸設備－其他，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and substring(b.propertyno,1,5) not in ('40201','40302','40104')
                            and ( substring(b.propertyno,1,7) not in ('4010701','4010702','4010703','4010704','4010705') or b.propertyno in ('401070207','401070208','401070209') )
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=160
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：165.交通及運輸設備，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：165.交通及運輸設備，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=165
;


-- 財產類別：165.交通及運輸設備，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：165.交通及運輸設備，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=165
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：165.交通及運輸設備，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：165.交通及運輸設備，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=165
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：165.交通及運輸設備，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：165.交通及運輸設備，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=165
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：165.交通及運輸設備，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：165.交通及運輸設備，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='4'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=165
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：170.交通及運輸設備－小計
-- 檔案說明：&9
--prompt ◎財產類別：170.交通及運輸設備－小計
update a set 
        addamount=b.addamount,
        addarea=b.addarea,
        addbookvalue=b.addbookvalue,
        reduceamount=b.reduceamount,
        reducearea=b.reducearea,
        reducebookvalue=b.reducebookvalue,
        nowamount=b.nowamount,
        nowarea=b.nowarea,
        nowbookvalue=b.nowbookvalue
       from &9 a  inner join
          (select sum(addamount) as addamount,sum(addarea) as addarea,sum(addbookvalue) as addbookvalue,
                 sum(reduceamount) as reduceamount,sum(reducearea) as reducearea,sum(reducebookvalue)  as reducebookvalue,
                 sum(nowamount) as nowamount,sum(nowarea) as nowarea,sum(nowbookvalue) as nowbookvalue,
                 editid,propertykind,fundtype
            from &9 
            group by editid,propertykind,fundtype
            ) b
           on b.editid='&1'
             and propertytype between 110 and 160
             and b.propertykind=a.propertykind
             and isnull(b.fundtype,1)=isnull(a.fundtype,1)   
  where a.editid='&1'
   and a.propertytype=170
;

-- 財產類別：180.什項設備－圖書，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：180.什項設備－圖書，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=180
;


-- 財產類別：180.什項設備－圖書，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：180.什項設備－圖書，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=180
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：180.什項設備－圖書，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：180.什項設備－圖書，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=180
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：180.什項設備－圖書，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：180.什項設備－圖書，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=180
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：180.什項設備－圖書，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：180.什項設備－圖書，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='503'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=180
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：190.什項設備－博物－本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：190.什項設備－博物－本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=190
;


-- 財產類別：190.什項設備－博物－本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：190.什項設備－博物－本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=190
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：190.什項設備－博物－本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：190.什項設備－博物－本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=190
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：190.什項設備－博物－本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：190.什項設備－博物－本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=190
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：190.什項設備－博物－本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：190.什項設備－博物－本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,3)='504'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=190
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：200.什項設備－其他，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：200.什項設備－其他，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=200
;


-- 財產類別：200.什項設備－其他，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：200.什項設備－其他，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=200
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：200.什項設備－其他，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：200.什項設備－其他，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=200
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：200.什項設備－其他，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：200.什項設備－其他，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=200
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：200.什項設備－其他，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：200.什項設備－其他，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and substring(b.propertyno,1,3)!='503'
                            and substring(b.propertyno,1,3)!=case when '&9'='UNTGR_UNTGR021R' then '504' when '&9'='UNTGR_UNTGR022R' then '504' when '&9'='PUBGR_PUBGR026R' then '504' else '1' end 
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=200
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：205.什項設備，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：205.什項設備，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=205
;


-- 財產類別：205.什項設備，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_MOVABLE、&9
--prompt ◎財產類別：205.什項設備，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTMP_MOVABLE b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=205
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：205.什項設備，本期增加(增值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：205.什項設備，本期增加(增值)
update a set 
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=205
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：205.什項設備，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_REDUCEDETAIL、&9
--prompt ◎財產類別：205.什項設備，本期減少(減少)
update a set 
       reduceamount    = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.adjustbookamount)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.adjustbookvalue)
                           from UNTMP_REDUCEDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=205
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：205.什項設備，本期減少(減值)
-- 檔案說明：動產增減值單明細檔UNTMP_ADJUSTDETAIL、&9
--prompt ◎財產類別：205.什項設備，本期減少(減值)
update a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTMP_ADJUSTDETAIL b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and case when '&7'='99' then '99' else b.valuable end ='&7'
                            and b.verify='Y'
                            and substring(b.propertyno,1,1)='5'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=205
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
;


-- 財產類別：210.什項設備－小計
-- 檔案說明：&9
--prompt ◎財產類別：210.什項設備－小計
update a set 
        addamount=b.addamount,
        addarea=b.addarea,
        addbookvalue=b.addbookvalue,
        reduceamount=b.reduceamount,
        reducearea=b.reducearea,
        reducebookvalue=b.reducebookvalue,
        nowamount=b.nowamount,
        nowarea=b.nowarea,
        nowbookvalue=b.nowbookvalue
       from &9 a  inner join
          (select sum(addamount) as addamount,sum(addarea) as addarea,sum(addbookvalue) as addbookvalue,
                 sum(reduceamount) as reduceamount,sum(reducearea) as reducearea,sum(reducebookvalue)  as reducebookvalue,
                 sum(nowamount) as nowamount,sum(nowarea) as nowarea,sum(nowbookvalue) as nowbookvalue,
                 editid,propertykind,fundtype
            from &9 
            group by editid,propertykind,fundtype
            ) b
           on b.editid='&1'
             and propertytype between 180 and 200
             and b.propertykind=a.propertykind
             and isnull(b.fundtype,1)=isnull(a.fundtype,1)   
  where a.editid='&1'
   and a.propertytype=210
;


-- 財產類別：220.有價證券，本期結存
-- 檔案說明：有價證券增加單UNTVP_ADDPROOF、&9
--prompt ◎財產類別：220.有價證券，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTVP_ADDPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookamount)
                           from UNTVP_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTVP_ADDPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTVP_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=220
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：220.有價證券，本期增加(新增)
-- 檔案說明：有價證券增加單UNTVP_ADDPROOF、&9
--prompt ◎財產類別：220.有價證券，本期增加(新增)
update a set 
       addamount    = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.originalamount)
                           from UNTVP_ADDPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTVP_ADDPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=220
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：220.有價證券，本期增加(增值)
-- 檔案說明：有價證券增減值單UNTVP_ADJUSTPROOF、&9
--prompt ◎財產類別：220.有價證券，本期增加(增值)
update a set 
       addamount      = isnull(a.addamount,0)+isnull(
                      (
                         select sum(b.addbookamount)
                           from UNTVP_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.adjustbookamount>0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTVP_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.addbookvalue>0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=220
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：220.有價證券，本期減少(減值)
-- 檔案說明：有價證券增減值單UNTVP_ADJUSTPROOF、&9
--prompt ◎財產類別：220.有價證券，本期減少(減值)
update a set 
       reduceamount      = isnull(a.reduceamount,0)+isnull(
                      (
                         select sum(b.reducebookamount)
                           from UNTVP_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.addbookamount<0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTVP_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.addbookvalue<0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=220
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：230.權利，本期結存
-- 檔案說明：權利增加單UNTRT_ADDPROOF、&9
--prompt ◎財產類別：230.權利，本期結存
update a set 
       nowamount    = isnull(a.nowamount,0)+isnull(
                      (
                         select count(*)
                           from UNTRT_ADDPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select count(*)
                           from UNTRT_REDUCEPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.bookvalue=0
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0),
       nowbookvalue = isnull(a.nowbookvalue,0)+isnull(
                      (
                         select sum(b.originalbv)
                           from UNTRT_ADDPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)+isnull(
                      (
                         select sum(b.addbookvalue)
                           from UNTRT_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTRT_ADJUSTPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)-isnull(
                      (
                         select sum(b.reducebookvalue)
                           from UNTRT_REDUCEPROOF b
                          where ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                          group by b.propertykind,b.fundtype
                      ),0)
  from &9 a  where a.editid='&1'
   and a.propertytype=230
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：230.權利，本期增加(新增)
-- 檔案說明：權利增加單UNTRT_ADDPROOF、&9
--prompt ◎財產類別：230.權利，本期增加(新增)
update  a set 
       addamount = isnull(a.addamount,0)+isnull(b.addamount,0),
       addbookvalue = isnull(a.addbookvalue,0)+isnull(b.addbookvalue,0)
from  &9 a INNER JOIN 
                         (select count(*) as addamount,sum(originalbv) as addbookvalue,
                           enterorg,enterdate,ownership,verify,propertykind,fundtype
                           from UNTRT_ADDPROOF 
                           GROUP BY enterorg,enterdate,ownership,verify,propertykind,fundtype
                           ) b
                          ON ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.enterdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)                            
                      
  where a.editid='&1'
   and a.propertytype=230
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;



-- 財產類別：230.權利，本期增加(增值)
-- 檔案說明：權利增減值單UNTRT_ADJUSTPROOF、&9
--prompt ◎財產類別：230.權利，本期增加(增值)
update  a set 
       addbookvalue= isnull(a.addbookvalue,0)+isnull(b.addbookvalue,0)
from  &9 a INNER JOIN 
                         (select sum(addbookvalue) as addbookvalue,
                           enterorg,adjustdate,ownership,verify,propertykind,fundtype
                           from UNTRT_ADJUSTPROOF 
                           GROUP BY enterorg,adjustdate,ownership,verify,propertykind,fundtype
                           ) b
                          ON ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                      
  where a.editid='&1'
   and a.propertytype=230
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：230.權利，本期減少(減少)
-- 檔案說明：權利減損單UNTRT_REDUCEPROOF、&9
--prompt ◎財產類別：230.權利，本期減少(減少)
update  a set 
       reduceamount = isnull(a.reduceamount,0)+isnull(b.reduceamount,0),
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(b.reducebookvalue,0)
from  &9 a INNER JOIN 
                         (select count(*) as reduceamount,sum(bookvalue) as reducebookvalue,
                           enterorg,reducedate,ownership,verify,propertykind,fundtype
                           from UNTRT_REDUCEPROOF 
                           GROUP BY enterorg,reducedate,ownership,verify,propertykind,fundtype
                           ) b
                          ON ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.reducedate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                      
  where a.editid='&1'
   and a.propertytype=230
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：230.權利，本期減少(減值)
-- 檔案說明：權利增減值單UNTRT_ADJUSTPROOF、&9
--prompt ◎財產類別：230.權利，本期減少(減值)
update  a set 
       reducebookvalue = isnull(a.reducebookvalue,0)+isnull(b.reducebookvalue,0)
from  &9 a INNER JOIN 
                         (select sum(reducebookvalue) as reducebookvalue,
                           enterorg,adjustdate,ownership,verify,propertykind,fundtype
                           from UNTRT_ADJUSTPROOF 
                           GROUP BY enterorg,adjustdate,ownership,verify,propertykind,fundtype
                           ) b
                          ON ( b.enterorg=case when '&2'='99' then b.enterorg else '&2' end  or ( '&10'='Y' and b.enterorg in (select organid from SYSCA_ORGAN where organsuperior='&2') ) )
                            and case when '&2'='99' then substring(b.enterorg,1,7) else '1' end!='3839999'
                            and b.adjustdate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertykind=a.propertykind
                            and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                      
  where a.editid='&1'
   and a.propertytype=230
   and case when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end=1
   and case when '&7'='Y' then 0 else 1 end=1
;


-- 財產類別：240.其他，目前沒有

-- 財產類別：250.合計
-- 檔案說明：&9
--prompt ◎財產類別：250.合計
update  a set 
        addamount=b.addamount,
        addarea=b.addarea,
        addbookvalue=b.addbookvalue,        
        reduceamount=b.reduceamount,
        reducearea=b.reducearea,
        reducebookvalue=b.reducebookvalue,
        nowamount=b.nowamount,
        nowarea=b.nowarea,
        nowbookvalue=b.nowbookvalue
from &9  a   INNER JOIN         
          ( select sum(addamount) as addamount,
			     sum(addarea) as addarea,
			     sum(addbookvalue) as addbookvalue,
                 sum(reduceamount) as reduceamount,
                 sum(reducearea) as reducearea,
                 sum(reducebookvalue) as reducebookvalue,
                 sum(nowamount) as nowamount,
                 sum(nowarea) as nowarea,
                 sum(nowbookvalue) as nowbookvalue,
                 editid,propertytype,propertykind,fundtype
            from &9 group by editid,propertytype,propertykind,fundtype ) b
           on b.editid='&1'
             and b.propertytype not in (60,100,170,210)
             and b.propertykind=a.propertykind
             and isnull(b.fundtype,1)=isnull(a.fundtype,1)
                
  where a.editid='&1'
   and a.propertytype=250
;


-- 計算「財產類別」之「上期結存」
-- 檔案說明：&9
--prompt ◎計算「財產類別」之「上期結存」
update  a set 
       oldamount    = nowamount    - addamount    + reduceamount,
       oldarea      = nowarea      - addarea      + reducearea,
       oldbookvalue = nowbookvalue - addbookvalue + reducebookvalue
from &9 a
 where a.editid='&1'
   and case when '&9'='UNTGR_UNTGR017R' then 0 when '&9'='UNTGR_UNTGR019R' then 0 when '&9'='UNTGR_UNTGR022R' then 0 when '&9'='PUBGR_PUBGR026R' then 0 when '&9'='PUBGR_PUBGR037R' then 0 else 1 end =1
;


-- 計算「價值類別小計」
-- 檔案說明：&9
--prompt ◎計算「價值類別小計」
update a
set 
       oldbvsubtotal=b.oldbookvalue,
       addbvsubtotal=b.addbvsubtotal,
       redecebvsubtotal=b.redecebvsubtotal,
       nowbvsubtotal=b.nowbvsubtotal
from  &9  a  INNER JOIN         
          ( select sum(oldbookvalue) as oldbookvalue,
				   sum(addbookvalue) as addbvsubtotal,
				   sum(reducebookvalue) as redecebvsubtotal,
				   sum(nowbookvalue) as nowbvsubtotal,
				   editid,propertykind,fundtype,propertymark
            from  &9  group by editid,propertykind,fundtype,propertymark ) b
           on b.editid='&1'
             and b.propertykind=a.propertykind
             and isnull(b.fundtype,1)=isnull(a.fundtype,1)
             and b.propertymark=a.propertymark

 where a.editid='&1'
   and case when '&9'='UNTGR_UNTGR005R' then 0 when '&9'='UNTGR_UNTGR008R' then 0 when '&9'='UNTGR_UNTGR017R' then 0 else 1 end=1
;


---- 將「公務用基金」資料彙總成一頁，pubgr026r 珍貴動產、不動產目錄總表
----prompt ◎將「公務用基金」資料彙總成一頁，pubgr026r 珍貴動產、不動產目錄總表
--insert into &9(editid,propertykind,fundtype,propertytype,propertymark,
--               oldamount,oldarea,oldbookvalue,oldbvsubtotal,
--               addamount,addarea,addbookvalue,addbvsubtotal,
--               reduceamount,reducearea,reducebookvalue,redecebvsubtotal,
--               nowamount,nowarea,nowbookvalue,nowbvsubtotal)
--select '&1','05',null,propertytype,propertymark,
--       sum(isnull(oldamount   ,0)),sum(isnull(oldarea   ,0)),sum(isnull(oldbookvalue   ,0)),sum(isnull(oldbvsubtotal   ,0)),
--       sum(isnull(addamount   ,0)),sum(isnull(addarea   ,0)),sum(isnull(addbookvalue   ,0)),sum(isnull(addbvsubtotal   ,0)),
--       sum(isnull(reduceamount,0)),sum(isnull(reducearea,0)),sum(isnull(reducebookvalue,0)),sum(isnull(redecebvsubtotal,0)),
--       sum(isnull(nowamount   ,0)),sum(isnull(nowarea   ,0)),sum(isnull(nowbookvalue   ,0)),sum(isnull(nowbvsubtotal   ,0))
--  from &9
-- where editid='&1'
--   and '&9'='PUBGR_PUBGR026R'
--   and propertykind='01'
--   and fundtype is not null
-- group by propertytype,propertymark
--;
--
--delete &9
-- where editid='&1'
--   and '&9'='PUBGR_PUBGR026R'
--   and propertykind='01'
--   and fundtype is not null
--;


-- 若該群組之「合計」資料皆為0，則刪除該群組全部資料
-- 檔案說明：&9
--prompt ◎若該群組之「合計」資料皆為0，則刪除該群組全部資料
delete &9 
where &9.editid='&1'
  and (&9.propertykind+isnull(&9.fundtype,1)) in (
         select b.propertykind+isnull(b.fundtype,1)
           from &9 b
          where b.editid='&1'
            and b.propertytype=250
            and b.oldamount        = 0
            and b.oldarea          = 0
            and b.oldbookvalue     = 0
            and b.oldbvsubtotal    = 0
            and b.addamount        = 0
            and b.addarea          = 0
            and b.addbookvalue     = 0
            and b.addbvsubtotal    = 0
            and b.reduceamount     = 0
            and b.reducearea       = 0
            and b.reducebookvalue  = 0
            and b.redecebvsubtotal = 0
            and b.nowamount        = 0
            and b.nowarea          = 0
            and b.nowbookvalue     = 0
            and b.nowbvsubtotal    = 0
      )
;
--commit;
--spool off
--exit;
