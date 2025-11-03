-- 程式：untgr016r 土地使用關係統計表
-- 注意：執行完untgr016r.sql後再執行untgr016r_1.sql
-- 功能：由「UNTGR_untgr016r」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1
-- 執行指令範例：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr016r_1.sql test


select a.propertyKind||a.fundType propertyKindFundTypeNo,
       '財產性質分類：' || a.propertyKindName || '　' || a.fundTypeName propertyKindFundTypeName,
       a.useState,a.useStateName item,
       ltrim(rtrim(to_char(a.amount,'999G999G999G999G990'))) amount,
       ltrim(rtrim(to_char(a.holdArea/10000,'999G999G999G999G990D999999'))) superficial,
       ltrim(rtrim(to_char(a.bookValue,'999G999G999G999G990'))) value
  from UNTGR_untgr016r a
 where a.editID='&1'
 order by propertyKindFundTypeNo,a.useState
;

