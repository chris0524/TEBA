-- 程式：untgr023r 財產折舊月報表
-- 程式：untgr024r 固定資產月折舊明細表
-- 注意：執行完untgr023r.sql後再執行untgr023r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「&9」檔案
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:財產類別propertyType(1:房屋建築及設備,2:土地改良物,3:機械及設備,4:交通及運輸設備,5:什項設備,傳入99表示未選擇)
-- 傳入值：&4:統計年月reportYM
-- 傳入值：&5:權屬ownership(1:市有,2:國有)
-- 傳入值：&6:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&7:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&8:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&9:報表檔(UNTGR_untgr023r 財產折舊月報表      )
-- 傳入值：&9:報表檔(UNTGR_untgr024r 固定資產月折舊明細表)
-- 執行指令：@路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5 傳入值6 傳入值7 傳入值8 傳入值9
-- 執行指令範例(untgr023r程式               )：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr023r.sql test 383530000H 99 09607 1 99 99 99 UNTGR_untgr023r
-- 執行指令範例(untgr023r程式-交通及運輸設備)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr023r.sql test 383530000H  4 09607 1 99 99 99 UNTGR_untgr023r
-- 執行指令範例(untgr024r程式               )：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr023r.sql test 383530000H 99 09607 1 99 99 99 UNTGR_untgr024r

-- 執行指令的記錄檔
--spool D:\temp\kfcp\&9--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete &9
 where editID='&1'
;
commit;


-- 新增「建物」資料
-- 檔案說明：&9、建物主檔UNTBU_Building
prompt ◎新增「建物」資料
insert into &9(editID,ownership,propertyKind,fundType,valuable,propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
               specification,
               buyDate,bookValue,scrapValue,deprAmount,limitYear,
               monthDepr,
               accumDepr,
               useEndYM,keepUnit)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,'1',a.propertyNo,a.serialNo,a.oldSerialNo,a.propertyName1,
       (select b.signdesc from sysca_sign b where b.signno=substr(a.signNo,1,7)) || substr(a.signNo,8,5) || '-' || substr(a.signNo,13,3) || '建號',
       a.buildDate,a.bookValue,a.scrapValue,decode(a.deprMethod,'05',a.bookValue,a.deprAmount),a.otherLimitYear,
       decode(a.deprMethod,'05',null,decode(sign(a.apportionEndYM-'&4'),-1,0,
                                                                         0,a.bookValue-(a.accumDepr+((MONTHS_BETWEEN(to_date(a.apportionEndYM||'01'+19110000,'YYYYMMDD'),to_date(a.accumDeprYM||'01'+19110000,'YYYYMMDD'))-1)*a.monthDepr)),
                                                                           a.monthDepr
                                          )
             ),
       decode(a.deprMethod,'05',a.bookValue,decode(sign(a.apportionEndYM-'&4'),1,a.accumDepr+(MONTHS_BETWEEN(to_date('&4'||'01'+19110000,'YYYYMMDD'),to_date(a.accumDeprYM||'01'+19110000,'YYYYMMDD'))*a.monthDepr),
                                                                                 a.deprAmount
                                          )
             ),
       a.useEndYM,null
  from UNTBU_Building a
 where a.enterOrg='&2'
   and decode('&3','99','99','1')='&3'
   and a.enterDate<='&4'||'31'
   and a.ownership='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and ( (a.deprMethod in ('02','03','04') and a.dataState='1' and a.verify='Y') or
         (a.deprMethod in ('02','03','04') and substr(a.reduceDate,1,5)>='&4'  ) or
         (a.deprMethod='05' and substr(a.reduceDate,1,5)='&4'                )
       )
;


-- 新增「土地改良物主檔」資料
-- 檔案說明：&9、土地改良物主檔UNTRF_Attachment
prompt ◎新增「土地改良物主檔」資料
insert into &9(editID,ownership,propertyKind,fundType,valuable,propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
               specification,
               buyDate,bookValue,scrapValue,deprAmount,limitYear,
               monthDepr,
               accumDepr,
               useEndYM,keepUnit)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,'2',a.propertyNo,a.serialNo,a.oldSerialNo,a.propertyName1,
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
       a.buildDate,a.bookValue,a.scrapValue,decode(a.deprMethod,'05',a.bookValue,a.deprAmount),a.otherLimitYear,
       decode(a.deprMethod,'05',null,decode(sign(a.apportionEndYM-'&4'),-1,0,
                                                                         0,a.bookValue-(a.accumDepr+((MONTHS_BETWEEN(to_date(a.apportionEndYM||'01'+19110000,'YYYYMMDD'),to_date(a.accumDeprYM||'01'+19110000,'YYYYMMDD'))-1)*a.monthDepr)),
                                                                           a.monthDepr
                                          )
             ),
       decode(a.deprMethod,'05',a.bookValue,decode(sign(a.apportionEndYM-'&4'),1,a.accumDepr+(MONTHS_BETWEEN(to_date('&4'||'01'+19110000,'YYYYMMDD'),to_date(a.accumDeprYM||'01'+19110000,'YYYYMMDD'))*a.monthDepr),
                                                                                 a.deprAmount
                                          )
             ),
       a.useEndYM,null
  from UNTRF_Attachment a
 where a.enterOrg='&2'
   and decode('&3','99','99','2')='&3'
   and a.enterDate<='&4'||'31'
   and a.ownership='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and ( (a.deprMethod in ('02','03','04') and a.dataState='1' and a.verify='Y') or
         (a.deprMethod in ('02','03','04') and substr(a.reduceDate,1,5)>='&4'  ) or
         (a.deprMethod='05' and substr(a.reduceDate,1,5)='&4'                )
       )
;


-- 新增「動產」資料
-- 檔案說明：&9、動產主檔－批號資料UNTMP_Movable、動產主檔－批號明細UNTMP_MovableDetail
prompt ◎新增「動產」資料
insert into &9(editID,ownership,propertyKind,fundType,valuable,propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
               specification,
               buyDate,bookValue,scrapValue,deprAmount,limitYear,
               monthDepr,
               accumDepr,
               useEndYM,keepUnit)
select '&1','&5',a.propertyKind,a.fundType,a.valuable,substr(a.propertyNo,1,1),a.propertyNo,c.serialNo,c.oldSerialNo,a.propertyName1,
       a.specification||'/'||a.nameplate,
       a.buyDate,c.bookValue,c.scrapValue,decode(a.deprMethod,'05',c.bookValue,c.deprAmount),a.otherLimitYear,
       decode(a.deprMethod,'05',null,decode(sign(a.apportionEndYM-'&4'),-1,0,
                                                                         0,c.bookValue-(c.accumDepr+((MONTHS_BETWEEN(to_date(a.apportionEndYM||'01'+19110000,'YYYYMMDD'),to_date(c.accumDeprYM||'01'+19110000,'YYYYMMDD'))-1)*c.monthDepr)),
                                                                           c.monthDepr
                                          )
             ),
       decode(a.deprMethod,'05',c.bookValue,decode(sign(a.apportionEndYM-'&4'),1,c.accumDepr+(MONTHS_BETWEEN(to_date('&4'||'01'+19110000,'YYYYMMDD'),to_date(c.accumDeprYM||'01'+19110000,'YYYYMMDD'))*c.monthDepr),
                                                                                 c.deprAmount
                                          )
             ),
       a.useEndYM,c.keepUnit
  from UNTMP_Movable a,UNTMP_MovableDetail c
 where a.enterOrg='&2'
   and decode('&3','99','99',substr(a.propertyNo,1,1))='&3'
   and a.enterDate<='&4'||'31'
   and a.ownership='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and ( (a.deprMethod in ('02','03','04') and c.dataState='1' and c.verify='Y') or
         (a.deprMethod in ('02','03','04') and substr(c.reduceDate,1,5)>='&4'  ) or
         (a.deprMethod='05' and substr(c.reduceDate,1,5)='&4'                )
       )
   and a.enterOrg   = c.enterOrg
   and a.ownership  = c.ownership
   and a.propertyNo = c.propertyNo
   and a.lotNo      = c.lotNo
;


-- 取「建物、土地改良物、動產」之「財產名稱、使用年限」
-- 檔案說明：&9、SYSPK_PropertyCode
prompt ◎取「建物、土地改良物、動產」之「財產名稱、使用年限」
update &9 a set 
       (propertyName,limitYear) = 
          (  select nvl(b.propertyName,a.propertyName),
                    nvl(b.limitYear,a.limitYear)
               from SYSPK_PropertyCode b
              where b.enterOrg in ('000000000A','&2')
                and b.propertyNo=a.propertyNo
          )
 where a.editID='&1'
   and propertyNo!='A999999'
;


-- 依財產性質換頁：計算每一組「財產性質+基金財產+財產類別」之合計
prompt ◎依財產性質換頁：計算每一組「財產性質+基金財產+財產類別」之合計
insert into &9(editID,ownership,propertyKind,fundType,propertyType,propertyNo,
               bookValue,scrapValue,deprAmount,
               monthDepr,accumDepr,nonAccumDepr)
select '&1','&5',propertyKind,fundType,propertyType,'A999999',
       sum(nvl(bookValue,0)),sum(nvl(scrapValue,0)),sum(nvl(deprAmount,0)),
       sum(nvl(monthDepr,0)),sum(nvl(accumDepr,0)),sum(nvl(nonAccumDepr,0))
  from &9
 where editID='&1'
 group by propertyKind,fundType,propertyType
;


-- 取「財產性質名稱、基金財產名稱、珍貴財產名稱、保管單位名稱、財產類別名稱、財產編號組合」
-- 檔案說明：&9、SYSCA_Code、UNTMP_KeepUnit
prompt ◎取「財產性質名稱、基金財產名稱、珍貴財產名稱、保管單位名稱、財產類別名稱、財產編號組合」
update &9 a set 
       propertyKindName = decode(propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用',null),
       fundTypeName     = (select '　'||b.codeName from SYSCA_Code b where b.codeKindID='FUD' and b.codeID=a.fundType ),
       valuableName     = decode(a.valuable,'Y','珍貴財產','N','非珍貴財產',null),
       propertyTypeName = decode(a.propertyType,'1','房屋建築及設備','2','土地改良物','3','機械及設備','4','交通及運輸設備','5','什項設備',null),
       propertyNo1      = decode(a.propertyNo,'A999999',null,
                                 decode(substr(a.propertyNo,1,3),'503',a.propertyNo,
                                                                 '504',a.propertyNo,
                                                                 '505',a.propertyNo,
                                                                       substr(a.propertyNo,1,7)||'-'||substr(a.propertyNo,8)
                                       ) ||'-'|| a.serialNo
                                ),
       unitName         = (select b.unitName from UNTMP_KeepUnit b where b.enterOrg='&2' and b.unitNo=a.keepUnit ),
       nonAccumDepr     = a.deprAmount - a.accumDepr
 where a.editID='&1'
;

commit;
--spool off
exit;