-- 程式：untgr015r 財產/非消耗品檢查單
-- 程式：untmp029r 動產明細清冊
-- 程式：untne028r 非消耗品明細清冊
-- 注意：執行完untgr015r.sql後再執行untgr015r_1.sql
-- 功能：由「UNTGR_untgr015r」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:換頁方式(1:依財產性質換頁)
-- 傳入值：&2:換頁方式(2:依保管單位－保管人換頁)
-- 傳入值：&2:換頁方式(3:依財產類別換頁)
-- 傳入值：&3:報表檔(UNTGR_untgr015r  :財產檢查單      )
-- 傳入值：&3:報表檔(UNTGR_untgr015r1 :非消耗品檢查單  )
-- 傳入值：&3:報表檔(UNTMP_untmp029r  :動產明細清冊    )
-- 傳入值：&3:報表檔(UNTNE_untne028r  :非消耗品明細清冊)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3
-- 執行指令範例(財產檢查單    --依財產性質換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 1 UNTGR_untgr015r
-- 執行指令範例(財產檢查單    --依保管單位換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 2 UNTGR_untgr015r
-- 執行指令範例(財產檢查單    --依財產類別換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 3 UNTGR_untgr015r
-- 執行指令範例(非消耗品檢查單--依財產性質換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 1 UNTGR_untgr015r1
-- 執行指令範例(非消耗品檢查單--依保管單位換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 2 UNTGR_untgr015r1
-- 執行指令範例(非消耗品檢查單--依財產類別換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 3 UNTGR_untgr015r1
-- 執行指令範例(動產明細清冊                  )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 1 UNTMP_untmp029r
-- 執行指令範例(非消耗品明細清冊              )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r_1.sql test 1 UNTNE_untne028r


select decode('&2','1',a.valuable||a.propertyKind||a.fundType,
                   '2',a.valuable||rpad(a.keepUnit,10,'#')||rpad(a.keeper,10,'#'),
                       a.valuable||a.propertyType
             ) propertyKindFundTypeNo,
       decode('&2','1',decode('&3','UNTGR_untgr015r' ,'財產性質分類：',
                                   'UNTGR_untgr015r1','物品性質分類：',
                                   'UNTMP_untmp029r' ,'財產區分：',
                                   'UNTNE_untne028r' ,'物品區分：',
                                   '異常'
                             )||a.valuableName||a.propertyKindName||a.fundTypeName,
                   '2','保管單位：'                                                    ||a.valuableName||a.unitName||'　'||a.keeperName,
                       decode('&3','UNTGR_untgr015r','財產類別：'    ,'物品類別：'    )||a.valuableName||a.propertyTypeName
             ) propertyKindFundTypeName,       
       a.propertyNo,a.serialNo,
       a.propertyNo1||decode(a.oldSerialNo,null,null,' nextLine '||'原'||a.oldSerialNo) propertyNo1,
       decode(a.propertyNo,'A999990',ltrim(rtrim(to_char(a.amount1,'999G999G999G999G990')))||'筆',
                           'A999999',ltrim(rtrim(to_char(a.amount1,'999G999G999G999G990')))||'筆',
                           a.propertyName) propertyName,
       a.propertyName1,decode(a.specification,'/',null,a.specification) specification,a.propertyUnit,
       ltrim(rtrim(to_char(a.amount,'999G999G999G999G990'))) amount,
       decode(a.buyDate,null,null,substr(a.buyDate,1,3)||'/'||substr(a.buyDate,4,2)||'/'||substr(a.buyDate,6,2)) buyDate,
       a.limitYear,a.useYear,a.place,
       decode(a.propertyNo,'A999990','合計','A999999','總計',a.propertyNo1||decode(a.oldSerialNo,null,null,' nextLine '||'(原'||a.oldSerialNo||')')) propertyNo2,
       a.material,
       ltrim(rtrim(to_char(a.bookValue,'999G999G999G999G990'))) bookValue,
       decode(a.sourceDate,null,null,substr(a.sourceDate,1,3)||'/'||substr(a.sourceDate,4,2)||'/'||substr(a.sourceDate,6,2)) sourceDate,
       decode(a.propertyNo,'A999990',null,'A999999',null,a.unitName||' nextLine '||a.keeperName) keepUnit,
       decode(a.propertyNo,'A999990',null,'A999999',null,a.useUnitName||' nextLine '||a.userNoName) useUnit,
       a.useYearEnd,a.buyDate buyDate1,
       a.propertyKind||a.fundType as propertyKindFundType,
       (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName
  from &3 a
 where a.editID='&1'
 order by propertyKindFundTypeNo,propertyNo,serialNo  
;

