-- 程式：untgr005r 財產分類量值統計季報表
-- 程式：untgr008r 市有財產增減結存表
-- 程式：untgr009r 國有財產增減結存表
-- 程式：untgr017r 財產異動統計表
-- 程式：untgr019r 財產目錄總表
-- 程式：untgr021r 珍貴動產、不動產增減結存表
-- 程式：untgr022r 珍貴動產、不動產目錄總表
-- 程式：pubgr026r 珍貴動產、不動產目錄總表
-- 程式：pubgr037r 國有財產目錄總表
-- 注意：執行完untgr005r.sql後再執行untgr005r_1.sql
-- 功能：由「&2」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:報表檔(UNTGR_untgr005r:財產分類量值統計季報表)
-- 傳入值：&2:報表檔(UNTGR_untgr008r:市有財產增減結存表)
-- 傳入值：&2:報表檔(UNTGR_untgr009r:國有財產增減結存表)
-- 傳入值：&2:報表檔(UNTGR_untgr017r:財產異動統計表)
-- 傳入值：&2:報表檔(UNTGR_untgr019r:財產目錄總表)
-- 傳入值：&2:報表檔(UNTGR_untgr021r:珍貴動產、不動產增減結存表)
-- 傳入值：&2:報表檔(UNTGR_untgr022r:珍貴動產、不動產目錄總表)
-- 傳入值：&2:報表檔(PUBGR_pubgr026r:珍貴動產、不動產目錄總表)
-- 傳入值：&2:報表檔(PUBGR_pubgr037r:國有財產目錄總表)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2
-- 執行指令範例(untgr005r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr005r
-- 執行指令範例(untgr008r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr008r
-- 執行指令範例(untgr009r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr009r
-- 執行指令範例(untgr017r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr017r
-- 執行指令範例(untgr019r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr019r
-- 執行指令範例(untgr021r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr021r
-- 執行指令範例(untgr022r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test UNTGR_untgr022r
-- 執行指令範例(pubgr026r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test PUBGR_pubgr026r
-- 執行指令範例(pubgr037r程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr005r_1.sql test PUBGR_pubgr037r


select a.propertyKind,
       a.fundType,
       a.propertyKind||a.fundType propertyKindFundTypeNo,
       decode('&2','PUBGR_pubgr037r','管理機關(基金)名稱：','財產性質分類：') || 
       decode('&2','PUBGR_pubgr037r',(select governmentName from SYSCA_Argu where countyYN='Y') || '　','') || 
       decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','異常') || '　' ||
       ( select b.codename from sysca_code b where b.codekindid='FUD' and b.codeid=a.fundType ) propertyKindFundTypeName,
       a.propertyType,
       ( select c.propertyTypeName 
           from UNTGR_propertyType c 
          where c.programNo='&2' 
            and c.propertyType=a.propertyType
       ) propertyTypeName,
       a.propertyMark,
       ltrim(rtrim(to_char(a.oldAmount,'999G999G999G999G990'))) oldAmount,
       ltrim(rtrim(decode(propertyType,10,decode('&2','UNTGR_untgr005r',to_char(a.oldArea/10000,'999G999G999G999G990D999999'),
                                                      'UNTGR_untgr008r',to_char(a.oldArea/10000,'999G999G999G999G990D999999'),
                                                      to_char(round(a.oldArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.oldArea,'999G999G999G999G990D99')))) oldArea,
       ltrim(rtrim(to_char(decode('&2','UNTGR_untgr005r',a.oldBookValue,
                                       'UNTGR_untgr008r',a.oldBookValue,
                                       'UNTGR_untgr017r',a.oldBookValue,
                                       'UNTGR_untgr022r',a.oldBookValue,
                                       'PUBGR_pubgr026r',a.oldBookValue,
                                                         a.oldBVSubTotal),'999G999G999G999G990'))) oldBookValue,
       ltrim(rtrim(to_char(a.addAmount,'999G999G999G999G990'))) addAmount,
       ltrim(rtrim(decode(propertyType,10,decode('&2','UNTGR_untgr005r',to_char(a.addArea/10000,'999G999G999G999G990D999999'),
                                                      'UNTGR_untgr008r',to_char(a.addArea/10000,'999G999G999G999G990D999999'),
                                                      to_char(round(a.addArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.addArea,'999G999G999G999G990D99')))) addArea,
       ltrim(rtrim(to_char(decode('&2','UNTGR_untgr005r',a.addBookValue,
                                       'UNTGR_untgr008r',a.addBookValue,
                                       'UNTGR_untgr017r',a.addBookValue,
                                       'UNTGR_untgr022r',a.addBookValue,
                                       'PUBGR_pubgr026r',a.addBookValue,
                                                         a.addBVSubTotal),'999G999G999G999G990'))) addBookValue,
       ltrim(rtrim(to_char(a.reduceAmount,'999G999G999G999G990'))) reduceAmount,
       ltrim(rtrim(decode(propertyType,10,decode('&2','UNTGR_untgr005r',to_char(a.reduceArea/10000,'999G999G999G999G990D999999'),
                                                      'UNTGR_untgr008r',to_char(a.reduceArea/10000,'999G999G999G999G990D999999'),
                                                      to_char(round(a.reduceArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.reduceArea,'999G999G999G999G990D99')))) reduceArea,
       ltrim(rtrim(to_char(decode('&2','UNTGR_untgr005r',a.reduceBookValue,
                                       'UNTGR_untgr008r',a.reduceBookValue,
                                       'UNTGR_untgr017r',a.reduceBookValue,
                                       'UNTGR_untgr022r',a.reduceBookValue,
                                       'PUBGR_pubgr026r',a.reduceBookValue,
                                                         a.redeceBVSubTotal),'999G999G999G999G990'))) reduceBookValue,
       ltrim(rtrim(to_char(a.nowAmount,'999G999G999G999G990'))) nowAmount,
       ltrim(rtrim(decode(propertyType,10,decode('&2','UNTGR_untgr005r',to_char(a.nowArea/10000,'999G999G999G999G990D999999'),
                                                      'UNTGR_untgr008r',to_char(a.nowArea/10000,'999G999G999G999G990D999999'),
                                                      to_char(round(a.nowArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.nowArea,'999G999G999G999G990D99')))) nowArea,
       ltrim(rtrim(to_char(decode('&2','UNTGR_untgr005r',a.nowBookValue,
                                       'UNTGR_untgr008r',a.nowBookValue,
                                       'UNTGR_untgr017r',a.nowBookValue,
                                       'UNTGR_untgr022r',a.nowBVSubTotal,
                                       'PUBGR_pubgr026r',a.nowBookValue,
                                                         a.nowBVSubTotal),'999G999G999G999G990'))) nowBookValue
  from &2 a
 where a.editID='&1'
 order by propertyKind,fundType,propertyType
;

