-- 程式：untgr025r 毀損處理清冊
-- 注意：執行完untgr025r.sql後再執行untgr025r_1.sql
-- 功能：由「UNTGR_untgr025r」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 執行指令：@路徑及檔名 傳入值1
-- 執行指令範例：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr025r_.sql test


select a.valuable||a.propertyKind||rpad(a.fundType,10,'#')||a.propertyType changePage,
       ('財產性質：'||a.valuableName||a.propertyKindName||a.fundTypeName) propertyKindfundTypeName,
       ('財產類別：'||a.propertyTypeName) propertyTypeName,
       decode(a.propertyNo,'A999999',a.sourceDate,
       decode(a.sourceDate,'','',substr(a.sourceDate,1,3)||'/'||substr(a.sourceDate,4,2)||'/'||substr(a.sourceDate,6,2))) sourceDate,
       a.propertyNo1,a.propertyName,
       decode(a.specification,'/',null,a.specification) specification,
       a.propertyUnit,
       decode(a.propertyType,'1',ltrim(rtrim(to_char(a.bookAmount,'999G999G999G999G990D99'))),
                             '2',ltrim(rtrim(to_char(a.bookAmount,'999G999G999G999G990D99'))),
                                 ltrim(rtrim(to_char(a.bookAmount,'999G999G999G999G990')))
             ) bookAmount,
       ltrim(rtrim(to_char(a.bookUnit,'999G999G999G999G990'))) bookUnit,
       ltrim(rtrim(to_char(a.bookValue,'999G999G999G999G990'))) bookValue,
       decode(a.propertyNo,'A999999',null,substr(a.damageDate,1,3)||'/'||substr(a.damageDate,4,2)||'/'||substr(a.damageDate,6,2)) damageDate,
       decode(a.propertyNo,'A999999',null,substr(a.damageExpire,1,3)||'/'||substr(a.damageExpire,4,2)||'/'||substr(a.damageExpire,6,2)) damageExpire,
       a.limitYear,a.useYear,
       decode(a.damageMark,'1','待處理',null) damageMark,
       decode(a.ownership,'1','市有','2','國有') ownershipName
  from UNTGR_untgr025r a
 where a.editID='&1'
 order by changePage,propertyNo,serialNo  
;

