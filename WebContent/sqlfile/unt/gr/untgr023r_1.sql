-- 程式：untgr023r 財產折舊月報表
-- 程式：untgr024r 固定資產月折舊明細表
-- 注意：執行完untgr023r.sql後再執行untgr023r_1.sql
-- 功能：由「&2」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:報表檔(UNTGR_untgr023r 財產折舊月報表      )
-- 傳入值：&2:報表檔(UNTGR_untgr024r 固定資產月折舊明細表)
-- 執行指令：@路徑及檔名 傳入值1 傳入值2
-- 執行指令範例(untgr023r程式)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr023r_1.sql test UNTGR_untgr023r
-- 執行指令範例(untgr024r程式)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr023r_1.sql test UNTGR_untgr024r


select a.propertyKind||rpad(a.fundType,10,'#')||a.propertyType propertyKindFundTypeNo,
       ('財產性質：'||a.propertyKindName||a.fundTypeName) propertyKindfundTypeName,
       ('財產類別：'||a.propertyTypeName) propertyTypeName,
       decode(a.propertyNo,'A999999','合計',a.propertyNo1) propertyNo1,
       a.propertyName,
       decode(a.propertyNo,'A999999',null,substr(a.buyDate,1,3)||'/'||substr(a.buyDate,4,2)||'/'||substr(a.buyDate,6,2)) buyDate,
       ltrim(rtrim(to_char(a.bookValue,'999G999G999G999G990'))) bookValue,
       ltrim(rtrim(to_char(a.scrapValue,'999G999G999G999G990'))) scrapValue,
       ltrim(rtrim(to_char(a.deprAmount,'999G999G999G999G990'))) deprAmount,
       a.limitYear,
       ltrim(rtrim(to_char(a.monthDepr,'999G999G999G999G990'))) monthDepr,
       ltrim(rtrim(to_char(a.accumDepr,'999G999G999G999G990'))) accumDepr,
       ltrim(rtrim(to_char(a.nonAccumDepr,'999G999G999G999G990'))) nonAccumDepr,
       ltrim(rtrim(to_char(a.nonAccumDepr+a.monthDepr,'999G999G999G999G990'))) nonAccumDepr1,
       decode(a.propertyNo,'A999999',null,substr(a.useEndYM,1,3)||'/'||substr(a.useEndYM,4,2)) useEndYM,
       a.unitName,
       decode(a.ownership,'1','市有','2','國有') ownershipName
  from &2 a
 where a.editID='&1'
 order by propertyKindFundTypeNo,propertyNo,serialNo  
;
