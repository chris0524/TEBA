-- 程式：untgr028r1 國有公用財產分類統計總表
-- 程式：pubgr025r  國有公用財產分類統計總表
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「UNTGR_untgr028r1」檔案
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg(傳入99表示全部機關(不含測試機關3839999))
-- 傳入值：&3:結存日期enterDate
-- 傳入值：&4:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&5:上層機關彙總表isorganmanager(Y:是,N:否)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3
-- 執行指令範例(untgr028r1程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr028r1.sql test 383130000C 0951231 N N
-- 執行指令範例(untgr028r1程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr028r1.sql test 383130000C 0951231 N Y
-- 執行指令範例(pubgr025r 程式-全部機關      )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr028r1.sql test 99         0951231 N N


-- 執行指令的記錄檔
--spool D:\temp\kfcp\UNTGR_untgr028r1--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete UNTGR_untgr028r1
 where editID='&1'
;
commit;

-- 新增資料
prompt ◎新增資料
insert into UNTGR_untgr028r1(editID,landAmount1,landArea1,landBV1,landAmount2,landArea2,landBV2,
                             attachmentAmount1,attachmentBV1,attachmentAmount2,attachmentBV2,
                             buildingAmount1,buildingArea1,buildingAmount2,buildingArea2,
                             buildingAmount3,buildingAmount4,buildingBV1,buildingBV2,buildingBV3,
                             totalAmount1,totalArea1,totalAmount2,totalArea2,totalAmount3,totalBV) 
values('&1',0,0,0,0,0,0,
       0,0,0,0,
       0,0,0,0,
       0,0,0,0,0,
       0,0,0,0,0,0
      )
;


-- 分類項目：土地－公務用(包含「公務用、事業用」)
prompt ◎分類項目：土地－公務用(包含「公務用、事業用」)
update UNTGR_untgr028r1 a set 
       landAmount1    = nvl(a.landAmount1,0)+nvl(
                        (
                           select count(*)
                             from UNTLA_Land b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.enterDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)-nvl(
                        (
                           select count(*)
                             from UNTLA_ReduceDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.reduceDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0),
       landArea1      = nvl(a.landArea1,0)+nvl(
                        (
                           select sum(b.originalHoldArea)/10000
                             from UNTLA_Land b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.enterDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)+nvl(
                        (
                           select sum(b.adjustHoldArea)/10000
                             from UNTLA_AdjustDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.adjustDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)-nvl(
                        (
                           select sum(b.holdArea)/10000
                             from UNTLA_ReduceDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.reduceDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0),
       landBV1        = nvl(a.landBV1,0)+nvl(
                        (
                           select sum(b.originalBV)
                             from UNTLA_Land b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.enterDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)+nvl(
                        (
                           select sum(b.adjustBookValue)
                             from UNTLA_AdjustDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.adjustDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)-nvl(
                        (
                           select sum(b.bookValue)
                             from UNTLA_ReduceDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.reduceDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind in ('01','03')
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)
 where a.editID='&1'
;


-- 分類項目：土地－公共用
prompt ◎分類項目：土地－公共用
update UNTGR_untgr028r1 a set 
       landAmount2    = nvl(a.landAmount2,0)+nvl(
                        (
                           select count(*)
                             from UNTLA_Land b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.enterDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)-nvl(
                        (
                           select count(*)
                             from UNTLA_ReduceDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.reduceDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0),
       landArea2      = nvl(a.landArea2,0)+nvl(
                        (
                           select sum(b.originalHoldArea)/10000
                             from UNTLA_Land b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.enterDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)+nvl(
                        (
                           select sum(b.adjustHoldArea)/10000
                             from UNTLA_AdjustDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.adjustDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)-nvl(
                        (
                           select sum(b.holdArea)/10000
                             from UNTLA_ReduceDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.reduceDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0),
       landBV2        = nvl(a.landBV2,0)+nvl(
                        (
                           select sum(b.originalBV)
                             from UNTLA_Land b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.enterDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)+nvl(
                        (
                           select sum(b.adjustBookValue)
                             from UNTLA_AdjustDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.adjustDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)-nvl(
                        (
                           select sum(b.bookValue)
                             from UNTLA_ReduceDetail b,SYSCA_Organ c
                            where b.enterOrg=c.organID
                              and c.IsManager='Y'
                              and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                              and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                              and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                              and b.reduceDate<='&3'
                              and b.ownership='2'
                              and b.verify='Y'
                              and b.propertyKind='02'
                              and decode('&4','99','99',b.valuable)='&4'
                        ),0)
 where a.editID='&1'
;


-- 分類項目：土地改良物－公務用(包含「公務用、事業用」)
prompt ◎分類項目：土地改良物－公務用(包含「公務用、事業用」)
update UNTGR_untgr028r1 a set 
       attachmentAmount1 = nvl(a.attachmentAmount1,0)+nvl(
                           (
                              select count(*)
                                from UNTRF_Attachment b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.enterDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind in ('01','03')
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)-nvl(
                           (
                              select count(*)
                                from UNTRF_ReduceDetail b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.reduceDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind in ('01','03')
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0),
       attachmentBV1     = nvl(a.attachmentBV1,0)+nvl(
                           (
                              select sum(b.originalBV)
                                from UNTRF_Attachment b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.enterDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind in ('01','03')
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)+nvl(
                           (
                              select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                                from UNTRF_AdjustDetail b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.adjustDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind in ('01','03')
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)-nvl(
                           (
                              select sum(b.bookValue)
                                from UNTRF_ReduceDetail b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.reduceDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind in ('01','03')
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)
 where a.editID='&1'
;


-- 分類項目：土地改良物－公共用
prompt ◎分類項目：土地改良物－公共用
update UNTGR_untgr028r1 a set 
       attachmentAmount2 = nvl(a.attachmentAmount2,0)+nvl(
                           (
                              select count(*)
                                from UNTRF_Attachment b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.enterDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind='02'
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)-nvl(
                           (
                              select count(*)
                                from UNTRF_ReduceDetail b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.reduceDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind='02'
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0),
       attachmentBV2     = nvl(a.attachmentBV2,0)+nvl(
                           (
                              select sum(b.originalBV)
                                from UNTRF_Attachment b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.enterDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind='02'
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)+nvl(
                           (
                              select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                                from UNTRF_AdjustDetail b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.adjustDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind='02'
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)-nvl(
                           (
                              select sum(b.bookValue)
                                from UNTRF_ReduceDetail b,SYSCA_Organ c
                               where b.enterOrg=c.organID
                                 and c.IsManager='Y'
                                 and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                                 and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                                 and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                                 and b.reduceDate<='&3'
                                 and b.ownership='2'
                                 and b.verify='Y'
                                 and b.propertyKind='02'
                                 and decode('&4','99','99',b.valuable)='&4'
                           ),0)
 where a.editID='&1'
;


-- 分類項目：房屋建築及設備－辦公室－棟數、面積，包含「公務用、公共用、事業用」
prompt ◎房屋建築及設備－辦公室－棟數、面積，包含「公務用、公共用、事業用」
update UNTGR_untgr028r1 a set 
       buildingAmount1 = nvl(a.buildingAmount1,0)+nvl(
                         (
                            select count(*)
                              from UNTBU_Building b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.enterDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) between '20102' and '20105'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)-nvl(
                         (
                            select count(*)
                              from UNTBU_ReduceDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.reduceDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) between '20102' and '20105'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0),
       buildingArea1   = nvl(a.buildingArea1,0)+nvl(
                         (
                            select sum(b.originalHoldArea)
                              from UNTBU_Building b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.enterDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) between '20102' and '20105'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)+nvl(
                         (
                            select sum(decode(b.adjustType,'1',b.adjustHoldArea,-b.adjustHoldArea))
                              from UNTBU_AdjustDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.adjustDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) between '20102' and '20105'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)-nvl(
                         (
                            select sum(b.holdArea)
                              from UNTBU_ReduceDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.reduceDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) between '20102' and '20105'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)
 where a.editID='&1'
;


-- 分類項目：房屋建築及設備－宿舍－棟數、面積，包含「公務用、公共用、事業用」
prompt ◎房屋建築及設備－宿舍－棟數、面積，包含「公務用、公共用、事業用」
update UNTGR_untgr028r1 a set 
       buildingAmount2 = nvl(a.buildingAmount2,0)+nvl(
                         (
                            select count(*)
                              from UNTBU_Building b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.enterDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5)='20106'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)-nvl(
                         (
                            select count(*)
                              from UNTBU_ReduceDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.reduceDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5)='20106'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0),
       buildingArea2   = nvl(a.buildingArea2,0)+nvl(
                         (
                            select sum(b.originalHoldArea)
                              from UNTBU_Building b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.enterDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5)='20106'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)+nvl(
                         (
                            select sum(decode(b.adjustType,'1',b.adjustHoldArea,-b.adjustHoldArea))
                              from UNTBU_AdjustDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.adjustDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5)='20106'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)-nvl(
                         (
                            select sum(b.holdArea)
                              from UNTBU_ReduceDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.reduceDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5)='20106'
                               and b.propertyKind in ('01','02','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)
 where a.editID='&1'
;


-- 分類項目：房屋建築及設備－其他－個數，「公務用」包含「公務用、事業用」
prompt ◎分類項目：房屋建築及設備－其他－個數，「公務用」包含「公務用、事業用」
update UNTGR_untgr028r1 a set 
       buildingAmount3 = nvl(a.buildingAmount3,0)+nvl(
                         (
                            select count(*)
                              from UNTBU_Building b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.enterDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) not between '20102' and '20106'
                               and b.propertyKind in ('01','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)-nvl(
                         (
                            select count(*)
                              from UNTBU_ReduceDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.reduceDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) not between '20102' and '20106'
                               and b.propertyKind in ('01','03')
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0),
       buildingAmount4 = nvl(a.buildingAmount4,0)+nvl(
                         (
                            select count(*)
                              from UNTBU_Building b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.enterDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) not between '20102' and '20106'
                               and b.propertyKind='02'
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)-nvl(
                         (
                            select count(*)
                              from UNTBU_ReduceDetail b,SYSCA_Organ c
                             where b.enterOrg=c.organID
                               and c.IsManager='Y'
                               and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                               and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                               and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                               and b.reduceDate<='&3'
                               and b.ownership='2'
                               and b.verify='Y'
                               and substr(b.propertyNo,1,5) not between '20102' and '20106'
                               and b.propertyKind='02'
                               and decode('&4','99','99',b.valuable)='&4'
                         ),0)
 where a.editID='&1'
;


-- 分類項目：房屋建築及設備－價值
prompt ◎分類項目：房屋建築及設備－價值
update UNTGR_untgr028r1 a set 
       buildingBV1 = nvl(a.buildingBV1,0)+nvl(
                     (
                        select sum(b.originalBV)
                          from UNTBU_Building b,SYSCA_Organ c
                         where b.enterOrg=c.organID
                           and c.IsManager='Y'
                           and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                           and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                           and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                           and b.enterDate<='&3'
                           and b.ownership='2'
                           and b.verify='Y'
                           and b.propertyKind in ('01','03')
                           and decode('&4','99','99',b.valuable)='&4'
                     ),0)+nvl(
                     (
                        select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                          from UNTBU_AdjustDetail b,SYSCA_Organ c
                         where b.enterOrg=c.organID
                           and c.IsManager='Y'
                           and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                           and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                           and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                           and b.adjustDate<='&3'
                           and b.ownership='2'
                           and b.verify='Y'
                           and b.propertyKind in ('01','03')
                           and decode('&4','99','99',b.valuable)='&4'
                     ),0)-nvl(
                     (
                        select sum(b.bookValue)
                          from UNTBU_ReduceDetail b,SYSCA_Organ c
                         where b.enterOrg=c.organID
                           and c.IsManager='Y'
                           and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                           and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                           and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                           and b.reduceDate<='&3'
                           and b.ownership='2'
                           and b.verify='Y'
                           and b.propertyKind in ('01','03')
                           and decode('&4','99','99',b.valuable)='&4'
                     ),0),
       buildingBV2 = nvl(a.buildingBV2,0)+nvl(
                     (
                        select sum(b.originalBV)
                          from UNTBU_Building b,SYSCA_Organ c
                         where b.enterOrg=c.organID
                           and c.IsManager='Y'
                           and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                           and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                           and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                           and b.enterDate<='&3'
                           and b.ownership='2'
                           and b.verify='Y'
                           and b.propertyKind='02'
                           and decode('&4','99','99',b.valuable)='&4'
                     ),0)+nvl(
                     (
                        select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                          from UNTBU_AdjustDetail b,SYSCA_Organ c
                         where b.enterOrg=c.organID
                           and c.IsManager='Y'
                           and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                           and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                           and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                           and b.adjustDate<='&3'
                           and b.ownership='2'
                           and b.verify='Y'
                           and b.propertyKind='02'
                           and decode('&4','99','99',b.valuable)='&4'
                     ),0)-nvl(
                     (
                        select sum(b.bookValue)
                          from UNTBU_ReduceDetail b,SYSCA_Organ c
                         where b.enterOrg=c.organID
                           and c.IsManager='Y'
                           and ( b.enterOrg=decode('&2','99',b.enterOrg,'&2') or ( '&5'='Y' and b.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
                           and decode('&2','99',substr(b.enterOrg,1,7),'1')!='3839999'
                           and decode('&2','99',substr(b.enterOrg,1,3),'1')!='KOC'
                           and b.reduceDate<='&3'
                           and b.ownership='2'
                           and b.verify='Y'
                           and b.propertyKind='02'
                           and decode('&4','99','99',b.valuable)='&4'
                     ),0)
 where a.editID='&1'
;


-- 分類項目：總計
prompt ◎分類項目：總計
update UNTGR_untgr028r1 a set 
       buildingBV3  = buildingBV1       + buildingBV2,
       totalAmount1 = landAmount1       + landAmount2,
       totalArea1   = landArea1         + landArea2,
       totalAmount2 = buildingAmount1   + buildingAmount2,
       totalArea2   = buildingArea1     + buildingArea2,
       totalAmount3 = attachmentAmount1 + attachmentAmount2 + buildingAmount3 + buildingAmount4,
       totalBV      = landBV1 + landBV2 + attachmentBV1 + attachmentBV2 + buildingBV1 + buildingBV2
 where a.editID='&1'
;

commit;
--spool off
exit;
