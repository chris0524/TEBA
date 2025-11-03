-- 程式：untgr006r  市有財產增減表(高雄市)
-- 程式：untgr006r1 非消耗品增減表(高雄市)
-- 程式：untgr007r  國有財產增減表
-- 程式：untgr007r1 物品增減表(彰化縣)
-- 程式：untgr020r「珍貴動產、不動產增減表」
-- 注意：執行完untgr006r.sql後再執行untgr006r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「UNTGR_untgr006r」檔案
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:異動日期－起enterDateS
-- 傳入值：&4:異動日期－訖enterDateE
-- 傳入值：&5:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&6:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&7:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&8:權屬ownership(1：市有,2：國有,3：縣有,4：KOC,5：鄉鎮市有)
-- 傳入值：&9:程式代碼(untgr006r  市有財產增減表(高雄市)    )
-- 傳入值：&9:程式代碼(untgr006r1 非消耗品增減表(高雄市)    )
-- 傳入值：&9:程式代碼(untgr007r  國有財產增減表            )
-- 傳入值：&9:程式代碼(untgr007r1 物品增減表(彰化縣)        )
-- 傳入值：&9:程式代碼(untgr020r  「珍貴動產、不動產增減表」)
-- 傳入值：&10:查詢種類propertyKind1(1:財產,2:非消耗品)
-- 傳入值：&11:新草衙grass(N:否,Y:是,傳入99表示未選擇)
-- 傳入值：&12:上層機關彙總表isorganmanager(Y:是,N:否)
-- 傳入值：&13:基金財產/一般財產fundType(99:全部,1:基金財產,2:一般財產)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5 傳入值6 傳入值7 傳入值8 傳入值9 傳入值10 傳入值11 傳入值12
-- 執行指令範例(untgr006r  程式                        )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  N 3 untgr006r  1 99 N 99
-- 執行指令範例(untgr006r  程式-新草衙                 )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  N 3 untgr006r  1  Y N 99
-- 執行指令範例(untgr006r  程式-非新草衙               )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  N 3 untgr006r  1  N N 99
-- 執行指令範例(untgr006r  程式-上層機關彙總表         )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  N 3 untgr006r  1 99 Y 99
-- 執行指令範例(untgr006r1 程式                        )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  N 3 untgr006r1 2 99 N 99
-- 執行指令範例(untgr007r  程式                        )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0980701 0980731 99 99  N 2 untgr007r  1 99 N 99
-- 執行指令範例(untgr007r  程式-上層機關彙總表         )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0980701 0980731 99 99  N 2 untgr007r  1 99 Y 99
-- 執行指令範例(untgr007r1 程式                        )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0980701 0980731 99 99  N 3 untgr007r1 2 99 N 99
-- 執行指令範例(untgr007r1 程式-上層機關彙總表         )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0980701 0980731 99 99  N 3 untgr007r1 2 99 Y 99
-- 執行指令範例(untgr020r  程式                        )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  Y 3 untgr020r  1 99 N 99
-- 執行指令範例(untgr020r  程式-上層機關彙總表         )：sqlplus kfcp/kfcp@TBEK_KD @C:\eclipse-lomboz-3.3-win32\workspace\TBEK\sqlfile\unt\gr\untgr006r.sql test 000000002Z 0951201 0951231 99 99  Y 3 untgr020r  1 99 Y 99
-- 其他說明(高雄市)：「土地、建物、土改」無「購置日期」欄位==>帶「取得日期」
-- 其他說明(彰化縣：99.01.19財政處江志宏)：「建物、土改」無「購置日期」欄位==>帶「建築日期」
-- 其他說明：「有價證券」無「購置日期、取得日期」欄位==>帶「入帳日期 or 增減值日期 or 減損日期」


-- 執行指令的記錄檔
--spool D:\temp\kfcp\&9--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete UNTGR_untgr006r
 where programNo='&9'
   and editID='&1'
;
commit;

-- 新增資料
prompt ◎新增資料
insert into UNTGR_untgr006r(programNo,editID,propertyKind,fundType,propertyType,propertyNo,serialNo,propertyName,
                            adjustType,enterDate,specification,buyDate,sourceDate,limitYear,propertyUnit,bookUnit,add_amount,
                            add_bookValue,reduce_amount,reduce_bookValue,reduce_cause,scrapValue2,approveOrg,
                            add_area,reduce_area,enterOrg) 
-- 類　　別：土地(增加、增減值、減少)
-- 檔案名稱：土地主檔UNTLA_Land
-- 檔案名稱：土地增減值單UNTLA_AdjustProof、土地增減值單明細檔UNTLA_AdjustDetail
-- 檔案名稱：土地減損單UNTLA_ReduceProof、土地減損單明細檔UNTLA_ReduceDetail
(
select '&9','&1',a.propertyKind,a.fundType,'1' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       (select b.signDesc||substr(a.signNo,8,4)||'-'||substr(a.signNo,12,4)||'地號' from SYSCA_Sign b where b.signNo=substr(a.signNo,1,7)) specification,
       null buyDate,
       a.sourceDate,
       to_char(null) limitYear,
       '平方公尺' propertyUnit,
       a.originalUnit bookUnit,
       1  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       originalHoldArea add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTLA_Land a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and decode('&11','99','99',a.grass)='&11'
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,'1' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       decode(sign(a.adjustBookValue),-1,'2','1') adjustType,
       a.adjustDate enterDate,
       (select b.signDesc||substr(a.signNo,8,4)||'-'||substr(a.signNo,12,4)||'地號' from SYSCA_Sign b where b.signNo=substr(a.signNo,1,7)) specification,
       null buyDate,
       a.sourceDate,
       to_char(null) limitYear,
       '平方公尺' propertyUnit,
       to_number(decode(sign(a.adjustBookValue),-1,-a.adjustBookUnit,a.adjustBookUnit)) bookUnit,
       to_number(decode(sign(a.adjustBookValue),-1,null,0)) add_amount,
       to_number(decode(sign(a.adjustBookValue),-1,null,a.adjustBookValue)) add_bookValue,
       to_number(decode(sign(a.adjustBookValue),-1,0,null)) reduce_amount,
       to_number(decode(sign(a.adjustBookValue),-1,-a.adjustBookValue,null)) reduce_bookValue,
       decode(a.cause,'1','公告地價調整','2','資產重估調整','3',a.cause1,'4','面積調整','') reduce_cause,
       to_number(null) scrapValue2,
       decode(sign(a.adjustBookValue),-1,(select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc,'電腦單號：'||b.caseNo) approveOrg,
       to_number(decode(sign(a.adjustHoldArea),1,a.adjustHoldArea,null)) add_area,
       to_number(decode(sign(a.adjustHoldArea),-1,-a.adjustHoldArea,null)) reduce_area,
       a.enterOrg
  from UNTLA_AdjustDetail a,UNTLA_AdjustProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and decode('&11','99','99',a.grass)='&11'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
(
select '&9','&1',a.propertyKind,a.fundType,'1' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '2' adjustType,
       a.reduceDate enterDate,
       (select b.signDesc||substr(a.signNo,8,4)||'-'||substr(a.signNo,12,4)||'地號' from SYSCA_Sign b where b.signNo=substr(a.signNo,1,7)) specification,
       null buyDate,
       a.sourceDate,
       to_char(null) limitYear,
       '平方公尺' propertyUnit,
       a.bookUnit bookUnit,
       to_number(null)  add_amount,
       to_number(null) add_bookValue,
       1 reduce_amount,
       a.bookValue reduce_bookValue,
       decode(a.cause,'499',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAA' and e.codeid=a.cause)) ||
       (select h.organAName from SYSCA_Organ h where h.organID=a.newEnterOrg) reduce_cause,
       to_number(null) scrapValue2,
       (select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc approveOrg,
       to_number(null) add_area,
       holdArea reduce_area,
       a.enterOrg
  from UNTLA_ReduceDetail a,UNTLA_ReduceProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.reduceDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and decode('&11','99','99',a.grass)='&11'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
-- 類　　別：土地改良物(增加、增減值、減少)
-- 檔案名稱：土地改良物主檔UNTRF_Attachment
-- 檔案名稱：土地改良物增減值單UNTRF_AdjustProof、土地改良物增減值單明細檔UNTRF_AdjustDetail
-- 檔案名稱：土地改良物減損單UNTRF_ReduceProof、土地改良物減損單明細檔UNTRF_ReduceDetail
(
select '&9','&1',a.propertyKind,a.fundType,'2' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       a.propertyName1 specification,
       a.buildDate buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       1  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       originalMeasure add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTRF_Attachment a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,'2' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       a.adjustType,
       a.adjustDate enterDate,
       a.propertyName1 specification,
       (select c.buildDate from UNTRF_Attachment c where c.enterOrg=a.enterOrg and c.ownership=a.ownership and c.propertyNo=a.propertyNo and c.serialNo=a.serialNo) buyDate,
       a.sourceDate,
       to_char((select e.otherLimitYear from UNTRF_Attachment e where e.enterOrg=a.enterOrg and e.ownership=a.ownership and e.propertyNo=a.propertyNo and e.serialNo=a.serialNo)) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       to_number(decode(a.adjustType,'2',null,0)) add_amount,
       to_number(decode(a.adjustType,'2',null,a.adjustBookValue)) add_bookValue,
       to_number(decode(a.adjustType,'2',0,null)) reduce_amount,
       to_number(decode(a.adjustType,'2',a.adjustBookValue,null)) reduce_bookValue,
       decode(a.adjustType,'2',decode(a.cause,'1','資產重估調整','2','整建',a.cause1),null) reduce_cause,
       to_number(null) scrapValue2,
       decode(a.adjustType,'2',(select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc,'電腦單號：'||b.caseNo) approveOrg,
       to_number(decode(a.adjustType,'2',null,a.adjustMeasure)) add_area,
       to_number(decode(a.adjustType,'2',a.adjustMeasure,null)) reduce_area,
       a.enterOrg       
  from UNTRF_AdjustDetail a,UNTRF_AdjustProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
(
select '&9','&1',a.propertyKind,a.fundType,'2' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '2' adjustType,
       a.reduceDate enterDate,
       a.propertyName1 specification,
       (select c.buildDate from UNTRF_Attachment c where c.enterOrg=a.enterOrg and c.ownership=a.ownership and c.propertyNo=a.propertyNo and c.serialNo=a.serialNo)  buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       to_number(null)  add_amount,
       to_number(null) add_bookValue,
       1 reduce_amount,
       a.bookValue reduce_bookValue,
       decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAC' and e.codeid=a.cause)) ||
       (select h.organAName from SYSCA_Organ h where h.organID=a.newEnterOrg) reduce_cause,
       to_number(null) scrapValue2,
       (select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc approveOrg,
       to_number(null) add_area,
       measure reduce_area,
       a.enterOrg
  from UNTRF_ReduceDetail a,UNTRF_ReduceProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.reduceDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
-- 類　　別：建物(增加、增減值、減少)
-- 檔案名稱：建物主檔UNTBU_Building
-- 檔案名稱：建物增減值單UNTBU_AdjustProof、建物增減值單明細檔UNTBU_AdjustDetail
-- 檔案名稱：建物減損單UNTBU_ReduceProof、建物減損單明細檔UNTBU_ReduceDetail
(
select '&9','&1',a.propertyKind,a.fundType,'3' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       (select b.signDesc||substr(a.signNo,8,5)||'-'||substr(a.signNo,13,3)||'建號' from SYSCA_Sign b where b.signNo=substr(a.signNo,1,7)) specification,
       buildDate buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear) limitYear,
       '平方公尺' propertyUnit,
       to_number(null) bookUnit,
       1  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       originalHoldArea add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTBU_Building a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,'3' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       a.adjustType,
       a.adjustDate enterDate,
       (select b.signDesc||substr(a.signNo,8,5)||'-'||substr(a.signNo,13,3)||'建號' from SYSCA_Sign b where b.signNo=substr(a.signNo,1,7)) specification,
       (select c.buildDate from UNTBU_Building c where c.enterOrg=a.enterOrg and c.ownership=a.ownership and c.propertyNo=a.propertyNo and c.serialNo=a.serialNo) buyDate,
       a.sourceDate,
       to_char((select e.otherLimitYear from UNTBU_Building e where e.enterOrg=a.enterOrg and e.ownership=a.ownership and e.propertyNo=a.propertyNo and e.serialNo=a.serialNo)) limitYear,
       '平方公尺' propertyUnit,
       to_number(null) bookUnit,
       to_number(decode(a.adjustType,'2',null,0)) add_amount,
       to_number(decode(a.adjustType,'2',null,adjustBookValue)) add_bookValue,
       to_number(decode(a.adjustType,'2',0,null)) reduce_amount,
       to_number(decode(a.adjustType,'2',adjustBookValue,null)) reduce_bookValue,
       decode(a.adjustType,'2',decode(a.cause,'1','資產重估調整','2','整建',a.cause1),null) reduce_cause,
       to_number(null) scrapValue2,
       decode(a.adjustType,'2',(select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc,'電腦單號：'||b.caseNo) approveOrg,
       to_number(decode(a.adjustType,'2',null,adjustHoldArea)) add_area,
       to_number(decode(a.adjustType,'2',adjustHoldArea,null)) reduce_area,
       a.enterOrg
  from UNTBU_AdjustDetail a,UNTBU_AdjustProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
(
select '&9','&1',a.propertyKind,a.fundType,'3' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '2' adjustType,
       a.reduceDate enterDate,
       (select b.signDesc||substr(a.signNo,8,5)||'-'||substr(a.signNo,13,3)||'建號' from SYSCA_Sign b where b.signNo=substr(a.signNo,1,7)) specification,
       (select c.buildDate from UNTBU_Building c where c.enterOrg=a.enterOrg and c.ownership=a.ownership and c.propertyNo=a.propertyNo and c.serialNo=a.serialNo) buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear) limitYear,
       '平方公尺' propertyUnit,
       to_number(null) bookUnit,
       to_number(null)  add_amount,
       to_number(null) add_bookValue,
       1 reduce_amount,
       a.bookValue reduce_bookValue,
       decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAC' and e.codeid=a.cause)) ||
       (select h.organAName from SYSCA_Organ h where h.organID=a.newEnterOrg) reduce_cause,
       to_number(null) scrapValue2,
       (select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc approveOrg,
       to_number(null) add_area,
       holdArea reduce_area,
       a.enterOrg
  from UNTBU_ReduceDetail a,UNTBU_ReduceProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.reduceDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
-- 類　　別：動產(增加、增減值、減少)
-- 檔案名稱：動產主檔－批號資料UNTMP_Movable
-- 檔案名稱：動產增減值單UNTMP_AdjustProof、動產增減值單明細檔UNTMP_AdjustDetail
-- 檔案名稱：動產減損單UNTMP_ReduceProof、動產減損單明細檔UNTMP_ReduceDetail
(
select '&9','&1',a.propertyKind,a.fundType,decode(substr(a.propertyNo,1,1),'3','4','4','5','5','6',null) propertyType,
       a.propertyNo,
       a.serialNoS||'-'||a.serialNoE serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       a.specification||'/'||a.nameplate specification,
       a.buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear) limitYear,
       a.otherPropertyUnit,
       a.originalUnit bookUnit,
       a.originalAmount  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTMP_Movable a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,decode(substr(a.propertyNo,1,1),'3','4','4','5','5','6',null) propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       a.adjustType,
       a.adjustDate enterDate,
       (select e.specification||'/'||e.nameplate from UNTMP_Movable e where e.enterOrg=a.enterOrg and e.ownership=a.ownership and e.propertyNo=a.propertyNo and e.lotNo=a.lotNo) specification,
       (select g.buyDate from UNTMP_Movable g where g.enterOrg=a.enterOrg and g.ownership=a.ownership and g.propertyNo=a.propertyNo and g.lotNo=a.lotNo) buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear),
       a.otherPropertyUnit,
       a.adjustBookUnit bookUnit,
       to_number(decode(adjustType,'2',null,0)) add_amount,
       to_number(decode(adjustType,'2',null,a.adjustBookValue)) add_bookValue,
       to_number(decode(adjustType,'2',0,null)) reduce_amount,
       to_number(decode(adjustType,'2',a.adjustBookValue,null)) reduce_bookValue,
       decode(a.adjustType,'2',a.cause,null) reduce_cause,
       to_number(null) scrapValue2,
       decode(a.adjustType,'2',(select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc,'電腦單號：'||b.caseNo) approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTMP_AdjustDetail a,UNTMP_AdjustProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
(
select '&9','&1',a.propertyKind,a.fundType,decode(substr(a.propertyNo,1,1),'3','4','4','5','5','6',null) propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '2' adjustType,
       a.reduceDate enterDate,
       a.specification||'/'||a.nameplate specification,
       a.buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear),
       a.otherPropertyUnit,
       a.oldBookUnit bookUnit,
       to_number(null)  add_amount,
       to_number(null) add_bookValue,
       a.adjustBookAmount reduce_amount,
       a.adjustBookValue reduce_bookValue,
       decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAC' and e.codeid=a.cause)) ||
       (select h.organAName from SYSCA_Organ h where h.organID=a.newEnterOrg) reduce_cause,
       a.scrapValue2 scrapValue2,
       (select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTMP_ReduceDetail a,UNTMP_ReduceProof b
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.reduceDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
-- 類　　別：有價證券(增加、增減值)
-- 檔案名稱：有價證券增加單UNTVP_AddProof、有價證券增減值單UNTVP_AdjustProof
(
select '&9','&1',a.propertyKind,a.fundType,'7' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       null specification,
       a.enterDate buyDate,
       a.enterDate sourceDate,
       to_char(null) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       a.originalAmount  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTVP_AddProof a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','Y',0,1)=1
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,'7' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       decode(sign(a.adjustBookValue),-1,'2','1') adjustType,
       a.adjustDate enterDate,
       null specification,
       a.adjustDate buyDate,
       a.adjustDate sourceDate,
       to_char(null) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       to_number(decode(sign(a.adjustBookAmount),-1,null,a.adjustBookAmount)) add_amount,
       to_number(decode(sign(a.adjustBookValue),-1,null,a.adjustBookValue)) add_bookValue,
       to_number(decode(sign(a.adjustBookAmount),-1,-a.adjustBookAmount,null)) reduce_amount,
       to_number(decode(sign(a.adjustBookValue),-1,-a.adjustBookValue,null)) reduce_bookValue,
       decode(sign(a.adjustBookValue),-1,decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAD' and e.codeid=a.cause)),null) reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTVP_AdjustProof a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','Y',0,1)=1
)
union all
-- 類　　別：權利(增加、增減值、減少)
-- 檔案名稱：權利增加單UNTRT_AddProof、權利增減值單UNTRT_AdjustProof、權利減損單UNTRT_ReduceProof
(
select '&9','&1',a.propertyKind,a.fundType,'8' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       null specification,
       a.buyDate,
       a.sourceDate,
       to_char(null) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       1  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTRT_AddProof a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','Y',0,1)=1
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,'8' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       a.adjustType,
       a.adjustDate enterDate,
       null specification,
       a.buyDate,
       (select f.sourceDate from UNTRT_AddProof f where f.enterOrg=a.enterOrg and f.ownership=a.ownership and f.propertyNo=a.propertyNo and f.serialNo=a.serialNo) sourceDate,
       to_char(null) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       to_number(null) add_amount,
       to_number(decode(a.adjustType,'2',null,a.adjustBookValue)) add_bookValue,
       to_number(null) reduce_amount,
       to_number(decode(a.adjustType,'2',a.adjustBookValue,null)) reduce_bookValue,
       decode(a.adjustType,'2',decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAE' and e.codeid=a.cause)),null) reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg       
  from UNTRT_AdjustProof a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','Y',0,1)=1
)
union all
(
select '&9','&1',a.propertyKind,a.fundType,'8' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '2' adjustType,
       a.reduceDate enterDate,
       null specification,
       a.buyDate,
       (select f.sourceDate from UNTRT_AddProof f where f.enterOrg=a.enterOrg and f.ownership=a.ownership and f.propertyNo=a.propertyNo and f.serialNo=a.serialNo) sourceDate,
       to_char(null) limitYear,
       null propertyUnit,
       to_number(null) bookUnit,
       to_number(null)  add_amount,
       to_number(null) add_bookValue,
       decode(a.newBookValue,0,1,0) reduce_amount,
       a.reduceBookValue reduce_bookValue,
       decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAC' and e.codeid=a.cause)) reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTRT_ReduceProof a
 where '&10'='1'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.reduceDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','Y',0,1)=1
)
union all
-- 類　　別：非消耗品(增加、增減值、減少)
-- 檔案名稱：非消耗品主檔－批號資料UNTNE_Nonexp
-- 檔案名稱：非消耗品增減值單UNTNE_AdjustProof、非消耗品增減值單明細檔UNTNE_AdjustDetail
-- 檔案名稱：非消耗品減損單UNTNE_ReduceProof、非消耗品減損單明細檔UNTNE_ReduceDetail
(
select '&9','&1',a.propertyKind,a.fundType,'9' propertyType,
       a.propertyNo,
       a.serialNoS||'-'||a.serialNoE serialNo,
       null propertyName,
       '1' adjustType,
       a.enterDate,
       a.specification||'/'||a.nameplate specification,
       a.buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear) limitYear,
       a.otherPropertyUnit,
       a.originalUnit bookUnit,
       a.originalAmount  add_amount,
       a.originalBV add_bookValue,
       to_number(null) reduce_amount,
       to_number(null) reduce_bookValue,
       null reduce_cause,
       to_number(null) scrapValue2,
       '電腦單號：'||a.caseNo approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTNE_Nonexp a
 where '&10'='2'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.enterDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
)
union all
(   
select '&9','&1',a.propertyKind,a.fundType,'9' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       a.adjustType,
       a.adjustDate enterDate,
       (select e.specification||'/'||e.nameplate from UNTNE_Nonexp e where e.enterOrg=a.enterOrg and e.ownership=a.ownership and e.propertyNo=a.propertyNo and e.lotNo=a.lotNo) specification,
       (select g.buyDate from UNTNE_Nonexp g where g.enterOrg=a.enterOrg and g.ownership=a.ownership and g.propertyNo=a.propertyNo and g.lotNo=a.lotNo) buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear),
       a.otherPropertyUnit,
       a.adjustBookUnit bookUnit,
       to_number(decode(adjustType,'2',null,0)) add_amount,
       to_number(decode(adjustType,'2',null,a.adjustBookValue)) add_bookValue,
       to_number(decode(adjustType,'2',0,null)) reduce_amount,
       to_number(decode(adjustType,'2',a.adjustBookValue,null)) reduce_bookValue,
       decode(a.adjustType,'2',a.cause,null) reduce_cause,
       to_number(null) scrapValue2,
       decode(a.adjustType,'2',(select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc,'電腦單號：'||b.caseNo) approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTNE_AdjustDetail a,UNTNE_AdjustProof b
 where '&10'='2'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.adjustDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
union all
(
select '&9','&1',a.propertyKind,a.fundType,'9' propertyType,
       a.propertyNo,
       a.serialNo,
       null propertyName,
       '2' adjustType,
       a.reduceDate enterDate,
       a.specification||'/'||a.nameplate specification,
       a.buyDate,
       a.sourceDate,
       to_char(a.otherLimitYear),
       a.otherPropertyUnit,
       a.oldBookUnit bookUnit,
       to_number(null)  add_amount,
       to_number(null) add_bookValue,
       a.adjustBookAmount reduce_amount,
       a.adjustBookValue reduce_bookValue,
       decode(a.cause,'99',a.cause1,(select e.codename from sysca_code e where e.codekindid='CAC' and e.codeid=a.cause)) ||
       (select h.organAName from SYSCA_Organ h where h.organID=a.newEnterOrg) reduce_cause,
       to_number(null) scrapValue2,
       (select f.codename from sysca_code f where f.codekindid='APO' and f.codeid=b.approveOrg)||b.approveDate||b.approveDoc approveOrg,
       to_number(null) add_area,
       to_number(null) reduce_area,
       a.enterOrg
  from UNTNE_ReduceDetail a,UNTNE_ReduceProof b
 where '&10'='2'
   and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
   and a.ownership='&8'
   and a.reduceDate between '&3' and '&4'
   and a.verify='Y'
   and (
          decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
          decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
       )
   and decode('&6','99','99',a.fundType)='&6'
   and decode('&13','99','99',decode(a.fundType,null,'2','1'))='&13'
   and decode('&7','99','99',a.valuable)='&7'
   and a.enterOrg=b.enterOrg
   and a.ownership=b.ownership
   and a.caseNo=b.caseNo
)
;


-- 取「入帳機關名稱」
-- 檔案說明：UNTGR_untgr006r、SYSCA_Organ
prompt ◎取「入帳機關名稱」
update UNTGR_untgr006r a set
       (a.enterOrgName1,a.enterOrgName2)=(select b.organAName,b.organSName from SYSCA_Organ b where b.organID=a.enterOrg) 
 where programNo='&9'
   and editID='&1'
;


-- 取「財產名稱、單位、使用年限」
-- 檔案說明：UNTGR_untgr006r、SYSPK_PropertyCode
prompt ◎取「財產名稱、單位、使用年限」
update UNTGR_untgr006r a set 
       (propertyName,propertyUnit,limitYear) = 
          (  select nvl(b.propertyName,a.propertyName),
                    decode(a.propertyType,'1',a.propertyUnit,'3',a.propertyUnit,nvl(b.propertyUnit,a.propertyUnit)),
                    nvl(b.limitYear,a.limitYear)
               from SYSPK_PropertyCode b
              where b.enterOrg in ('000000000A',a.enterOrg)
                and b.propertyNo=a.propertyNo
          )
 where '&10'='1'
   and programNo='&9'
   and editID='&1'
;


-- 取「非消耗品名稱、單位、使用年限」
-- 檔案說明：UNTGR_untgr006r、SYSPK_PropertyCode2
prompt ◎取「非消耗品名稱、單位、使用年限」
update UNTGR_untgr006r a set 
       (propertyName,propertyUnit,limitYear) = 
          (  select nvl(b.propertyName,a.propertyName),
                    nvl(b.propertyUnit,a.propertyUnit),
                    nvl(b.limitYear,a.limitYear)
               from SYSPK_PropertyCode2 b
              where b.enterOrg=a.enterOrg
                and b.propertyNo=a.propertyNo
          )
 where '&10'='2'
   and programNo='&9'
   and editID='&1'
;


-- 計算每一組「財產性質+基金財產」之各類別小計
prompt ◎計算每一組「財產性質+基金財產」之各類別小計
insert into UNTGR_untgr006r(programNo,editID,propertyKind,fundType,propertyType,propertyNo,serialNo,
                            add_amount,add_bookValue,reduce_amount,reduce_bookValue,propertyUnit,add_area,reduce_area)
select '&9','&1',propertyKind,fundType,propertyType,'A999990','A999990',
       sum(nvl(add_amount,0)),sum(nvl(add_bookValue,0)),sum(nvl(reduce_amount,0)),sum(nvl(reduce_bookValue,0)),
       decode(propertyType,'1','平方公尺','2','平方公尺','3','平方公尺',null),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',add_area,0),add_area),0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',reduce_area,0),reduce_area),0))
  from UNTGR_untgr006r
 where '&10'='1'
   and programNo='&9'
   and editID='&1'
 group by propertyKind,fundType,propertyType
;


-- 計算每一組「財產性質+基金財產」之合計
prompt ◎計算每一組「財產性質+基金財產」之合計
insert into UNTGR_untgr006r(programNo,editID,propertyKind,fundType,propertyNo,serialNo,
                            add_amount,add_bookValue,reduce_amount,reduce_bookValue,propertyUnit,add_area,reduce_area)
select '&9','&1',propertyKind,fundType,'A999999','A999999',
       sum(nvl(add_amount,0)),sum(nvl(add_bookValue,0)),sum(nvl(reduce_amount,0)),sum(nvl(reduce_bookValue,0)),
       '平方公尺',
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',add_area,0),add_area),0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',reduce_area,0),reduce_area),0))
  from UNTGR_untgr006r
 where programNo='&9'
   and editID='&1'
   and serialNo!='A999990'
 group by propertyKind,fundType
;

commit;
--spool off
exit;
