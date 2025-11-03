-- 程式：untgr028r1 國有公用財產分類統計總表
-- 程式：pubgr025r  國有公用財產分類統計總表
-- 注意：執行完untgr028r1.sql後再執行untgr028r1_1.sql
-- 功能：由「UNTGR_untgr028r1」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1
-- 執行指令範例(untgr028r1程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr028r1_1.sql test

select ltrim(rtrim(to_char( a.landAmount1       , '999G999G999G999G990'        ))) landAmount1       ,
       ltrim(rtrim(to_char( a.landArea1         , '999G999G999G999G990D999999' ))) landArea1         ,
       ltrim(rtrim(to_char( a.landBV1           , '999G999G999G999G990'        ))) landBV1           ,
       ltrim(rtrim(to_char( a.landAmount2       , '999G999G999G999G990'        ))) landAmount2       ,
       ltrim(rtrim(to_char( a.landArea2         , '999G999G999G999G990D999999' ))) landArea2         ,
       ltrim(rtrim(to_char( a.landBV2           , '999G999G999G999G990'        ))) landBV2           ,
       ltrim(rtrim(to_char( a.attachmentAmount1 , '999G999G999G999G990'        ))) attachmentAmount1 ,
       ltrim(rtrim(to_char( a.attachmentBV1     , '999G999G999G999G990'        ))) attachmentBV1     ,
       ltrim(rtrim(to_char( a.attachmentAmount2 , '999G999G999G999G990'        ))) attachmentAmount2 ,
       ltrim(rtrim(to_char( a.attachmentBV2     , '999G999G999G999G990'        ))) attachmentBV2     ,
       ltrim(rtrim(to_char( a.buildingAmount1   , '999G999G999G999G990'        ))) buildingAmount1   ,
       ltrim(rtrim(to_char( a.buildingArea1     , '999G999G999G999G990D99'     ))) buildingArea1     ,
       ltrim(rtrim(to_char( a.buildingAmount2   , '999G999G999G999G990'        ))) buildingAmount2   ,
       ltrim(rtrim(to_char( a.buildingArea2     , '999G999G999G999G990D99'     ))) buildingArea2     ,
       ltrim(rtrim(to_char( a.buildingAmount3   , '999G999G999G999G990'        ))) buildingAmount3   ,
       ltrim(rtrim(to_char( a.buildingAmount4   , '999G999G999G999G990'        ))) buildingAmount4   ,
       ltrim(rtrim(to_char( a.buildingBV1       , '999G999G999G999G990'        ))) buildingBV1       ,
       ltrim(rtrim(to_char( a.buildingBV2       , '999G999G999G999G990'        ))) buildingBV2       ,
       ltrim(rtrim(to_char( a.buildingBV3       , '999G999G999G999G990'        ))) buildingBV3       ,
       ltrim(rtrim(to_char( a.totalAmount1      , '999G999G999G999G990'        ))) totalAmount1      ,
       ltrim(rtrim(to_char( a.totalArea1        , '999G999G999G999G990D999999' ))) totalArea1        ,
       ltrim(rtrim(to_char( a.totalAmount2      , '999G999G999G999G990'        ))) totalAmount2      ,
       ltrim(rtrim(to_char( a.totalArea2        , '999G999G999G999G990D99'     ))) totalArea2        ,
       ltrim(rtrim(to_char( a.totalAmount3      , '999G999G999G999G990'        ))) totalAmount3      ,
       ltrim(rtrim(to_char( a.totalBV           , '999G999G999G999G990'        ))) totalBV
  from UNTGR_untgr028r1 a
 where a.editID='&1'
;

