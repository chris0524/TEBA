-- 程式：untgr010r  市有財產目錄（普通公務機關用）(高雄市)
-- 程式：untgr011r  國有財產目錄（普通公務機關用）
-- 程式：untgr012r  市有財產目錄（營業基金及非營業循環基金用）(高雄市)
-- 程式：untgr013r  國有財產目錄（營業基金及非營業循環基金用）
-- 程式：untgr014r  財產盤存報告表（依保管單位換頁）
-- 程式：untgr014r1 財產盤存報告表（依財產類別換頁）
-- 程式：untgr010r1 非消耗品目錄(高雄市)
-- 程式：untgr011r1 物品目錄（普通公務機關用）
-- 程式：untgr014r2 非消耗品盤存報告表（依保管單位換頁）
-- 程式：untgr014r3 非消耗品盤存報告表（依財產類別換頁）
-- 注意：執行完untgr010r.sql後再執行untgr010r_1.sql
-- 功能：由「&2」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:報表檔(UNTGR_untgr010r :市有財產目錄（普通公務機關用）)
-- 傳入值：&2:報表檔(UNTGR_untgr011r :國有財產目錄（普通公務機關用）)
-- 傳入值：&2:報表檔(UNTGR_untgr012r :市有財產目錄（營業基金及非營業循環基金用）)
-- 傳入值：&2:報表檔(UNTGR_untgr013r :國有財產目錄（營業基金及非營業循環基金用）)
-- 傳入值：&2:報表檔(UNTGR_untgr014r :財產盤存報告表（依保管單位換頁）)
-- 傳入值：&2:報表檔(UNTGR_untgr014r1:財產盤存報告表（依財產類別換頁）)
-- 傳入值：&2:報表檔(UNTGR_untgr010r1:非消耗品目錄 )
-- 傳入值：&2:報表檔(UNTGR_untgr011r1:物品目錄（普通公務機關用）)
-- 傳入值：&2:報表檔(UNTGR_untgr014r2:非消耗品盤存報告表（依保管單位換頁）)
-- 傳入值：&2:報表檔(UNTGR_untgr014r3:非消耗品盤存報告表（依財產類別換頁）)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2
-- 執行指令範例(財產    :untgr010r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr010r
-- 執行指令範例(財產    :untgr011r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr011r
-- 執行指令範例(財產    :untgr012r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr012r
-- 執行指令範例(財產    :untgr013r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr013r
-- 執行指令範例(財產    :untgr014r  程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr014r
-- 執行指令範例(財產    :untgr014r1 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr014r1
-- 執行指令範例(非消耗品:untgr010r1 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr010r1
-- 執行指令範例(物品    :untgr011r1 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr011r1
-- 執行指令範例(非消耗品:untgr014r2 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr014r2
-- 執行指令範例(非消耗品:untgr014r3 程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r_1.sql test UNTGR_untgr014r3


select decode('&2','UNTGR_untgr014r',a.propertyKind||a.fundType||a.keepUnit,
                   'UNTGR_untgr014r1',a.propertyKind||a.fundType||a.propertyType,
                   'UNTGR_untgr014r2',a.propertyKind||a.fundType||a.keepUnit,
                   'UNTGR_untgr014r3',a.propertyKind||a.fundType||a.propertyType,
                   a.propertyKind||a.fundType
             ) propertyKindFundTypeNo,
       decode('&2','UNTGR_untgr014r' ,'財產性質分類：' || a.propertyKindName || '　' || a.fundTypeName || ' nextLine ' || 
                                      '保管單位：' || a.unitName,
                   'UNTGR_untgr014r1','財產性質分類：' || a.propertyKindName || '　' || a.fundTypeName || ' nextLine ' || 
                                      '財產類別：' || decode(a.propertyType,'1','土地','2','土地改良物','3','房屋建築及設備','4','機械及設備','5','交通及運輸設備','6','什項設備','7','有價證券','8','權利','A','物品','異常'),
                   'UNTGR_untgr014r2','物品性質分類：' || a.propertyKindName || '　' || a.fundTypeName || ' nextLine ' || 
                                      '保管單位：' || a.unitName,
                   'UNTGR_untgr014r3','物品性質分類：' || a.propertyKindName || '　' || a.fundTypeName || ' nextLine ' || 
                                      '物品類別：物品' ,
                   'UNTGR_untgr010r1','物品性質分類：' || a.propertyKindName || '　' || a.fundTypeName,
                   'UNTGR_untgr011r1','物品性質分類：' || a.propertyKindName || '　' || a.fundTypeName,
                   '財產性質分類：' || a.propertyKindName || '　' || a.fundTypeName
             ) propertyKindFundTypeName,
       a.propertyNo,
       decode('&2','UNTGR_untgr010r1',decode(a.propertyNo,'A999990',decode(a.propertyType,'1','1','2','111','3','2','4','3','5','4','6','5','7','9','8','8','A','6',null),
                                                          'A999999',null,
                                                          a.propertyNo),
                   'UNTGR_untgr011r1',decode(a.propertyNo,'A999990',decode(a.propertyType,'1','1','2','111','3','2','4','3','5','4','6','5','7','9','8','8','A','6',null),
                                                          'A999999',null,
                                                          a.propertyNo),
                   'UNTGR_untgr014r2',decode(a.propertyNo,'A999990',decode(a.propertyType,'1','1','2','111','3','2','4','3','5','4','6','5','7','9','8','8','A','6',null),
                                                          'A999999',null,
                                                          a.propertyNo),                                                          
                   'UNTGR_untgr014r3',decode(a.propertyNo,'A999990',decode(a.propertyType,'1','1','2','111','3','2','4','3','5','4','6','5','7','9','8','8','A','6',null),
                                                          'A999999',null,
                                                          a.propertyNo),          
                                      decode(a.propertyNo,'A999990',decode(a.propertyType,'1','1','2','1','3','2','4','3','5','4','6','5','7','9','8','8','A','6',null),
                                                          'A999999',null,
                                                          substr(a.propertyNo,1,1))
              ) propertyNo1,
       decode(a.propertyNo,'A999990',decode(a.propertyType,'2','11',null),
                           'A999999',null,
                           substr(a.propertyNo,2,2)||'　') propertyNo2,
       decode(a.propertyNo,'A999990',null,
                           'A999999',null,
                           substr(a.propertyNo,4,2)||'　') propertyNo3,
       decode(a.propertyNo,'A999990',null,
                           'A999999',null,
                           substr(a.propertyNo,6,2)||'　') propertyNo4,
       decode(a.propertyNo,'A999990',null,
                           'A999999',null,
                           substr(a.propertyNo,8)||'　') propertyNo5,
       a.propertyName,a.propertyUnit,
       ltrim(rtrim(to_char(a.oldAmount,'999G999G999G999G990'))) oldAmount,
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.oldArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.oldArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.oldArea,'999G999G999G999G990D99')))) oldArea,
       ltrim(rtrim(to_char(a.oldAmount,'999G999G999G999G990'))) || 
       decode('&2','UNTGR_untgr014r','','UNTGR_untgr014r1','','UNTGR_untgr014r2','','UNTGR_untgr014r3','',
       decode(a.propertyType,'4',null,'5',null,'6',null,'7',null,'8',null,'A',null,' nextLine ' ||
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.oldArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.oldArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.oldArea,'999G999G999G999G990D99')))))) oldAmountOldArea,
       ltrim(rtrim(to_char(a.addAmount,'999G999G999G999G990'))) addAmount,
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.addArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.addArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.addArea,'999G999G999G999G990D99')))) addArea,

       ltrim(rtrim(to_char(a.addAmount,'999G999G999G999G990'))) || 
       decode('&2','UNTGR_untgr014r','','UNTGR_untgr014r1','','UNTGR_untgr014r2','','UNTGR_untgr014r3','',
       decode(a.propertyType,'4',null,'5',null,'6',null,'7',null,'8',null,'A',null,' nextLine ' ||
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.addArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.addArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.addArea,'999G999G999G999G990D99')))))) addAmountAddArea,
       ltrim(rtrim(to_char(a.reduceAmount,'999G999G999G999G990'))) reduceAmount,
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.reduceArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.reduceArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.reduceArea,'999G999G999G999G990D99')))) reduceArea,
       ltrim(rtrim(to_char(a.reduceAmount,'999G999G999G999G990'))) || 
       decode('&2','UNTGR_untgr014r','','UNTGR_untgr014r1','','UNTGR_untgr014r2','','UNTGR_untgr014r3','',
       decode(a.propertyType,'4',null,'5',null,'6',null,'7',null,'8',null,'A',null,' nextLine ' ||
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.reduceArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.reduceArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.reduceArea,'999G999G999G999G990D99')))))) reduceAmountReduceArea,
       ltrim(rtrim(to_char(a.nowAmount,'999G999G999G999G990'))) nowAmount,
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.nowArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.nowArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.nowArea,'999G999G999G999G990D99')))) nowArea,
       ltrim(rtrim(to_char(a.nowAmount,'999G999G999G999G990'))) || 
       decode('&2','UNTGR_untgr014r','','UNTGR_untgr014r1','','UNTGR_untgr014r2','','UNTGR_untgr014r3','',
       decode(a.propertyType,'4',null,'5',null,'6',null,'7',null,'8',null,'A',null,' nextLine ' ||
       ltrim(rtrim(decode(a.propertyType,'1',decode(a.ownership,'1',to_char(a.nowArea/10000,'999G999G999G999G990D999999'),
                                                              to_char(round(a.nowArea/10000,6),'999G999G999G999G990D999999')
                                                ),
                                         to_char(a.nowArea,'999G999G999G999G990D99')))))) nowAmountNowArea,
       ltrim(rtrim(to_char(a.nowBookValue,'999G999G999G999G990'))) nowBookValue,
       ltrim(rtrim(to_char(a.nowAppraiseValue,'999G999G999G999G990'))) nowAppraiseValue,
       ltrim(rtrim(to_char(a.nowaccumDepr,'999G999G999G999G990'))) nowaccumDepr,
       ltrim(rtrim(to_char(a.nowBookValue1,'999G999G999G999G990'))) nowBookValue1
  from &2 a
 where a.editID='&1'
   and decode('&2','UNTGR_untgr014r1',a.propertyType,1) != decode('&2','UNTGR_untgr014r1','Z',0)
 order by propertyKindFundTypeNo,propertyType,propertyNo
;

