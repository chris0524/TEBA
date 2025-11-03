-- 程式：sysst016r 資訊設備現況調查歷年資料查詢
-- 注意：執行完sysst016r.sql後再執行sysst016r_1.sql
-- 傳入值：&1:異動人員editID
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1
-- 執行指令範例(單一機關)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\sys\st\sysst016r_1.sql test


select propertyName,limitYear,propertyUnit,
       ltrim(rtrim(to_char(subTotal,'999G999G999G999G990'))) subTotal,
       ltrim(rtrim(to_char(amount1 ,'999G999G999G999G990'))) amount1,
       ltrim(rtrim(to_char(amount2 ,'999G999G999G999G990'))) amount2,
       ltrim(rtrim(to_char(amount3 ,'999G999G999G999G990'))) amount3,
       ltrim(rtrim(to_char(amount4 ,'999G999G999G999G990'))) amount4,
       ltrim(rtrim(to_char(amount5 ,'999G999G999G999G990'))) amount5,
       ltrim(rtrim(to_char(amount6 ,'999G999G999G999G990'))) amount6
  from SYSST_sysst016r
 where editID='&1'
 order by propertyNo,propertyName
;


