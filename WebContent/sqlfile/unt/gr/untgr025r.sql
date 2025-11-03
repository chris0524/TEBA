-- 程式：untgr025r 毀損處理清冊
-- 注意：執行完untgr025r.sql後再執行untgr025r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「UNTGR_untgr025r」檔案
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:毀損報局屆滿日－起damageExpireS
-- 傳入值：&4:毀損報局屆滿日－訖damageExpireE
-- 傳入值：&5:權屬ownership(1:市有,2:國有)
-- 傳入值：&6:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&7:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&8:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&9:財產類別propertyType(1:建物,2:土地改良物,3:動產,4:非消耗品,傳入99表示未選擇)
-- 執行指令：@路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5 傳入值6 傳入值7 傳入值8 傳入值9
-- 執行指令範例：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr025r.sql test 383030000D 0950101 1001231 1 99 99 99 99


-- 執行指令的記錄檔
--spool D:\temp\kfcp\UNTGR_untgr025r--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete UNTGR_untgr025r
 where editID='&1'
;
commit;


-- 新增「建物」資料
-- 檔案說明：UNTGR_untgr025r、建物主檔UNTBU_Building
prompt ◎新增「建物」資料
insert into UNTGR_untgr025r(editID,ownership,propertyKind,fundType,valuable,propertyType,sourceDate,
                            propertyNo,serialNo,specification,propertyUnit,bookAmount,bookUnit,bookValue,
                            damageDate,damageExpire,buyDate,limitYear,damageMark)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,'1',a.sourceDate,
       a.propertyNo,a.serialNo,
       (select b.signdesc from sysca_sign b where b.signno=substr(a.signNo,1,7)) || substr(a.signNo,8,5) || '-' || substr(a.signNo,13,3) || '建號',
       null,a.holdArea,null,a.bookValue,
       a.damageDate,a.damageExpire,a.buildDate,a.otherLimitYear,a.damageMark
  from UNTBU_Building a
 where a.enterOrg='&2'
   and a.ownership='&5'
   and a.damageExpire between '&3' and '&4'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and a.dataState='1'
   and decode('&9','99','99','1')='&9'
;


-- 新增「土地改良物主檔」資料
-- 檔案說明：UNTGR_untgr025r、土地改良物主檔UNTRF_Attachment
prompt ◎新增「土地改良物主檔」資料
insert into UNTGR_untgr025r(editID,ownership,propertyKind,fundType,valuable,propertyType,sourceDate,
                            propertyNo,serialNo,specification,propertyUnit,bookAmount,bookUnit,bookValue,
                            damageDate,damageExpire,buyDate,limitYear,damageMark)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,'2',a.sourceDate,
       a.propertyNo,a.serialNo,
       (select c.signDesc||substr(b.signNo,8,4)||'-'||substr(b.signNo,12,4)||'地號'
          from UNTRF_Base b,SYSCA_Sign c
         where b.enterOrg   = a.enterOrg
           and b.ownership  = a.ownership
           and b.propertyNo = a.propertyNo
           and b.serialNo   = a.serialNo
           and substr(b.signNo,1,7)=c.signNo
           and rownum=1
       ) ||
       (select decode(count(*),0,null,1,null,'等'||count(*)||'筆')
          from UNTRF_Base b
         where b.enterOrg   = a.enterOrg                  
           and b.ownership  = a.ownership                 
           and b.propertyNo = a.propertyNo                
           and b.serialNo   = a.serialNo                  
       ),
       null,a.measure,null,a.bookValue,
       a.damageDate,a.damageExpire,a.buildDate,a.otherLimitYear,a.damageMark
  from UNTRF_Attachment a
 where a.enterOrg='&2'
   and a.ownership='&5'
   and a.damageExpire between '&3' and '&4'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and a.dataState='1'
   and decode('&9','99','99','2')='&9'
;


-- 新增「動產」資料
-- 檔案說明：UNTGR_untgr025r、動產主檔－批號資料UNTMP_Movable、動產主檔－批號明細UNTMP_MovableDetail
prompt ◎新增「動產」資料
insert into UNTGR_untgr025r(editID,ownership,propertyKind,fundType,valuable,propertyType,sourceDate,
                            propertyNo,serialNo,specification,propertyUnit,bookAmount,bookUnit,bookValue,
                            damageDate,damageExpire,buyDate,limitYear,damageMark)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,'3',a.sourceDate,
       a.propertyNo,c.serialNo,
       a.specification||'/'||a.nameplate,
       a.otherPropertyUnit,c.bookAmount,decode(a.originalUnit,null,null,c.bookValue),c.bookValue,
       c.damageDate,c.damageExpire,a.buyDate,a.otherLimitYear,c.damageMark
  from UNTMP_Movable a,UNTMP_MovableDetail c
 where a.enterOrg='&2'
   and a.ownership='&5'
   and c.damageExpire between '&3' and '&4'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and c.dataState='1'
   and decode('&9','99','99','3')='&9'
   and a.enterOrg   = c.enterOrg
   and a.ownership  = c.ownership
   and a.propertyNo = c.propertyNo
   and a.lotNo      = c.lotNo
;


-- 新增「非消耗品」資料
-- 檔案說明：UNTGR_untgr025r、非消耗品主檔－批號資料UNTNE_Nonexp、非消耗品主檔－批號明細UNTNE_NonexpDetail
prompt ◎新增「非消耗品」資料
insert into UNTGR_untgr025r(editID,ownership,propertyKind,fundType,valuable,propertyType,sourceDate,
                            propertyNo,serialNo,specification,propertyUnit,bookAmount,bookUnit,bookValue,
                            damageDate,damageExpire,buyDate,limitYear,damageMark)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,'4',a.sourceDate,
       a.propertyNo,c.serialNo,
       a.specification||'/'||a.nameplate,
       null,c.bookAmount,decode(a.originalUnit,null,null,c.bookValue),c.bookValue,
       c.damageDate,c.damageExpire,a.buyDate,a.otherLimitYear,c.damageMark
  from UNTNE_Nonexp a,UNTNE_NonexpDetail c
 where a.enterOrg='&2'
   and a.ownership='&5'
   and c.damageExpire between '&3' and '&4'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and c.dataState='1'
   and decode('&9','99','99','4')='&9'
   and a.enterOrg   = c.enterOrg
   and a.ownership  = c.ownership
   and a.propertyNo = c.propertyNo
   and a.lotNo      = c.lotNo
;


-- 更新「已使用年數」資料
-- 檔案說明：UNTGR_untgr025r
prompt ◎更新「已使用年數」資料
update UNTGR_untgr025r a set 
       useYear = substr(a.damageDate,1,3) - substr(a.buyDate,1,3) + decode(sign(substr(a.damageDate,4,4)-substr(a.buyDate,4,4)),-1,-1,0) || '年' ||
                 MOD( MONTHS_BETWEEN( to_date(substr(a.damageDate,1,5)||'01'+19110000,'YYYYMMDD') , to_date(substr(a.buyDate,1,5)||'01'+19110000,'YYYYMMDD') ) ,12 ) || '個月'
 where a.editID='&1'
;


-- 取「建物、土地改良物、動產」之「財產名稱、單位、使用年限」
-- 檔案說明：UNTGR_untgr025r、SYSPK_PropertyCode
prompt ◎取「建物、土地改良物、動產」之「財產名稱、單位、使用年限」
update UNTGR_untgr025r a set 
       (propertyName,propertyUnit,limitYear) = 
          (  select nvl(b.propertyName,a.propertyName),
                    decode(a.propertyType,'1','平方公尺',nvl(b.propertyUnit,a.propertyUnit)),
                    nvl(b.limitYear,a.limitYear)
               from SYSPK_PropertyCode b
              where b.enterOrg in ('000000000A','&2')
                and b.propertyNo=a.propertyNo
          )
 where a.editID='&1'
   and a.propertyType!='4'
   and propertyNo!='A999999'
;


-- 取「非消耗品」之「財產名稱、單位、使用年限」
-- 檔案說明：UNTGR_untgr025r、SYSPK_PropertyCode
prompt ◎取「非消耗品」之「財產名稱、單位、使用年限」
update UNTGR_untgr025r a set 
       (propertyName,propertyUnit,limitYear) = 
          (  select nvl(b.propertyName,a.propertyName),
                    nvl(b.propertyUnit,a.propertyUnit),
                    nvl(b.limitYear,a.limitYear)
               from SYSPK_PropertyCode2 b
              where b.enterOrg='&2'
                and b.propertyNo=a.propertyNo
          )
 where a.editID='&1'
   and a.propertyType='4'
   and propertyNo!='A999999'
;

-- 依財產性質換頁：計算每一組「珍貴財產+財產性質+基金財產+財產類別」之合計
prompt ◎依財產性質換頁：計算每一組「珍貴財產+財產性質+基金財產+財產類別」之合計
insert into UNTGR_untgr025r(editID,ownership,valuable,propertyKind,fundType,propertyType,sourceDate,propertyNo,
                            propertyUnit,
                            bookAmount,
                            bookValue)
select '&1','&5',valuable,propertyKind,fundType,propertyType,'合計','A999999',
       decode(propertyType,'1','平方公尺','2','平方公尺',null),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',bookAmount,0),bookAmount),0)),
       sum(nvl(bookValue,0))
  from UNTGR_untgr025r
 where editID='&1'
 group by valuable,propertyKind,fundType,propertyType
;


-- 取「財產性質名稱、基金財產名稱、珍貴財產名稱、財產類別名稱、財產編號組合」
-- 檔案說明：UNTGR_untgr025r、SYSCA_Code、UNTMP_KeepUnit
prompt ◎取「財產性質名稱、基金財產名稱、珍貴財產名稱、財產類別名稱、財產編號組合」
update UNTGR_untgr025r a set 
       propertyKindName = decode(propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用',null),
       fundTypeName     = (select '　'||b.codeName from SYSCA_Code b where b.codeKindID='FUD' and b.codeID=a.fundType ),
       valuableName     = decode(a.valuable,'Y','珍貴財產　',null),
       propertyTypeName = decode(a.propertyType,'1','房屋建築及設備','2','土地改良物','3','動產','4','非消耗品',null),
       propertyNo1      = decode(a.propertyNo,'A999999',null,
                                 decode(substr(a.propertyNo,1,3),'503',a.propertyNo,
                                                                 '504',a.propertyNo,
                                                                 '505',a.propertyNo,
                                                                       decode(a.propertyType,'4',a.propertyNo,substr(a.propertyNo,1,7)||'-'||substr(a.propertyNo,8))
                                       ) ||'-'|| a.serialNo
                                )
 where a.editID='&1'
;

commit;
--spool off
exit;