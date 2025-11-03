-- 程式：untgr006r  市有財產增減表(高雄市)
-- 程式：untgr006r1 非消耗品增減表(高雄市)
-- 程式：untgr007r  國有財產增減表
-- 程式：untgr007r1 物品增減表(彰化縣)
-- 程式：untgr020r「珍貴動產、不動產增減表」
-- 注意：執行完untgr006r.sql後再執行untgr006r_1.sql
-- 功能：由「UNTGR_untgr006r」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:程式代碼(untgr006r  市有財產增減表(高雄市)    )
-- 傳入值：&2:程式代碼(untgr006r1 非消耗品增減表(高雄市)    )
-- 傳入值：&2:程式代碼(untgr007r  國有財產增減表            )
-- 傳入值：&2:程式代碼(untgr007r1 物品增減表(彰化縣)        )
-- 傳入值：&2:程式代碼(untgr020r  「珍貴動產、不動產增減表」)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2
-- 執行指令範例(untgr006r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr006r_1.sql test untgr006r
-- 執行指令範例(untgr006r1 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr006r_1.sql test untgr006r1
-- 執行指令範例(untgr007r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr006r_1.sql test untgr007r
-- 執行指令範例(untgr007r1 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr006r_1.sql test untgr007r1
-- 執行指令範例(untgr020r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr006r_1.sql test untgr020r


select a.propertyKind||a.fundType propertyKindFundTypeNo,
       decode('&2','untgr006r1','物品性質分類：','untgr007r1','物品性質分類：','財產性質分類：') || decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','異常')||'　'||
       ( select b.codename from sysca_code b where b.codekindid='FUD' and b.codeid=a.fundType ) propertyKindFundTypeName,
       a.propertyNo,
       decode(a.serialNo,'A999990',decode(a.propertyType,'1','土地','2','土地改良物','3','房屋建築及設備','4','機械及設備','5','交通及運設備','6','什項設備','7','有價證券','8','權利','9','非消耗品','異常'),
                         'A999999',null,
                         decode(substr(a.propertyNo,1,3),'503',a.propertyNo,
                                                         '504',a.propertyNo,
                                                         '505',a.propertyNo,
                                                         decode(substr(a.propertyNo,1,1),'6',a.propertyNo,
                                                                                         '8',a.propertyNo,
                                                                                         '9',a.propertyNo,
                                                                                             substr(a.propertyNo,1,7)||'-'||substr(a.propertyNo,8)
                                                               )
                               )||'-'
             ) propertyNo1,
       a.serialNo,
       decode(a.propertyNo,'A999990',null,'A999999',null,a.serialNo) serialNo1,
       decode(a.propertyNo,'A999990','小計','A999999','合計',a.propertyName) propertyName,
       a.adjustType,
       a.enterDate,
       a.specification,
       decode(a.buyDate,null,null,substr(a.buyDate,1,3)||'/'||substr(a.buyDate,4,2)||'/'||substr(a.buyDate,6,2)) buyDate,
       decode(a.sourceDate,null,null,substr(a.sourceDate,1,3)||'/'||substr(a.sourceDate,4,2)||'/'||substr(a.sourceDate,6,2)) sourceDate,
       a.limitYear,
       a.propertyUnit,
       ltrim(rtrim(to_char(a.bookUnit,'999G999G999G999G999'))) bookUnit,
       ltrim(rtrim(to_char(a.add_amount,'999G999G999G999G999'))) || 
       decode(a.propertyType,'4',null,'5',null,'6',null,'7',null,'8',null,'9',null,' nextLine ' ||
       ltrim(rtrim(to_char(a.add_area,'999G999G999G999G999D99')))) add_amount,
       ltrim(rtrim(to_char(a.add_bookValue,'999G999G999G999G999'))) add_bookValue,
       ltrim(rtrim(to_char(a.reduce_amount,'999G999G999G999G999'))) ||
       decode(a.propertyType,'4',null,'5',null,'6',null,'7',null,'8',null,'9',null,' nextLine ' ||
       ltrim(rtrim(to_char(a.reduce_area,'999G999G999G999G999D99')))) reduce_amount,
       ltrim(rtrim(to_char(a.reduce_bookValue,'999G999G999G999G999'))) reduce_bookValue,
       a.reduce_cause,
       ltrim(rtrim(to_char(a.scrapValue2,'999G999G999G999G999'))) scrapValue2,
       a.approveOrg
  from UNTGR_untgr006r a
 where a.programNo='&2'
   and a.editID='&1'
 order by propertyKind,fundType,propertyType,propertyNo,serialNo,adjustType,enterDate
;

