-- 程式：untgr015r 財產/非消耗品檢查單
-- 程式：untmp029r 動產明細清冊
-- 程式：untne028r 非消耗品明細清冊
-- 注意：執行完untgr015r.sql後再執行untgr015r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「&10」檔案
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:權屬ownership(1：市有,2：國有,3：縣有,4：KOC,5：鄉鎮市有)
-- 傳入值：&4:異動日期－訖(截止日期、結存日期)enterDateE
-- 傳入值：&5:保管單位keepUnit(傳入99表示未選擇)
-- 傳入值：&6:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&7:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&8:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&9:換頁方式(1:依財產性質換頁)
-- 傳入值：&9:換頁方式(2:依保管單位－保管人換頁)
-- 傳入值：&9:換頁方式(3:依財產類別換頁)
-- 傳入值：&10:報表檔(UNTGR_untgr015r  :財產檢查單      )
-- 傳入值：&10:報表檔(UNTGR_untgr015r1 :非消耗品檢查單  )
-- 傳入值：&10:報表檔(UNTMP_untmp029r  :動產明細清冊    )
-- 傳入值：&10:報表檔(UNTNE_untne028r  :非消耗品明細清冊)
-- 傳入值：&11:查詢種類propertyKind1(1:財產,2:非消耗品)
-- 傳入值：&12:資料類別dataType(1:增加數,2:減少數,3:結存數)
-- 傳入值：&13:異動日期－起enterDateS
-- 執行指令：@路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5 傳入值6 傳入值7 傳入值8 傳入值9 傳入值10 傳入值11 傳入值12 傳入值13
-- 執行指令範例(財產檢查單      --依財產性質換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTGR_untgr015r  1 3 0980731
-- 執行指令範例(財產檢查單      --依保管單位換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 2 UNTGR_untgr015r  1 3 0980731
-- 執行指令範例(財產檢查單      --依財產類別換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 3 UNTGR_untgr015r  1 3 0980731
-- 執行指令範例(非消耗品檢查單  --依財產性質換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTGR_untgr015r1 2 3 0980731
-- 執行指令範例(非消耗品檢查單  --依保管單位換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 2 UNTGR_untgr015r1 2 3 0980731
-- 執行指令範例(非消耗品檢查單  --依財產類別換頁)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 3 UNTGR_untgr015r1 2 3 0980731
-- 執行指令範例(動產明細清冊    --增加數        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTMP_untmp029r  1 1 0980731
-- 執行指令範例(動產明細清冊    --減少數        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTMP_untmp029r  1 2 0980731
-- 執行指令範例(動產明細清冊    --結存數        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTMP_untmp029r  1 3 0980731
-- 執行指令範例(非消耗品明細清冊--增加數        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTNE_untne028r  2 1 0980731
-- 執行指令範例(非消耗品明細清冊--減少數        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTNE_untne028r  2 2 0980731
-- 執行指令範例(非消耗品明細清冊--結存數        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr015r.sql test 000000002Z 3 0980731 99 99 99 99 1 UNTNE_untne028r  2 3 0980731


-- 執行指令的記錄檔
--spool D:\temp\kfcp\&10--&12--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete &10
 where editID='&1'
;
commit;


-- 新增「動產增加」資料
-- 檔案說明：&10、動產主檔－批號資料UNTMP_Movable、動產主檔－批號明細UNTMP_MovableDetail
prompt ◎新增「動產增加」資料
insert into &10(editID,ownership,propertyKind,fundType,valuable,
                keepUnit,
                keeper,
                propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
                specification,propertyUnit,
                amount,
                buyDate,limitYear,
                place,
                material,
                bookValue,
                sourceDate,
                useUnit,
                userNo,
                oldPropertyNo,
                useYearEnd)
select '&1','&3',a.propertyKind,a.fundType,a.valuable,
       decode('&10','UNTGR_untgr015r',c.keepUnit,'UNTGR_untgr015r1',c.keepUnit,c.originalKeepUnit),
       decode('&10','UNTGR_untgr015r',c.keeper,'UNTGR_untgr015r1',c.keeper,c.originalKeeper),             
       substr(a.propertyNo,1,1),a.propertyNo,c.serialNo,c.oldSerialNo,a.propertyName1,
       a.specification||'/'||a.nameplate,a.otherPropertyUnit,
       decode('&10','UNTGR_untgr015r',c.bookAmount,'UNTGR_untgr015r1',c.bookAmount,c.originalAmount),
       a.buyDate,a.otherLimitYear,
       decode('&10','UNTGR_untgr015r',c.place,'UNTGR_untgr015r1',c.place,c.originalPlace),             
       a.otherMaterial,
       decode('&10','UNTGR_untgr015r',c.bookValue,'UNTGR_untgr015r1',c.bookValue,c.originalBV),
       a.sourceDate,
       decode('&10','UNTGR_untgr015r',c.useUnit,'UNTGR_untgr015r1',c.useUnit,c.originalUseUnit),
       decode('&10','UNTGR_untgr015r',c.userNo,'UNTGR_untgr015r1',c.userNo,c.originalUser),
       c.oldPropertyNo,
       decode('&10','UNTGR_untgr015r',(select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual),
                    'UNTGR_untgr015r1',(select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual),
                    decode('&12','1',least( (select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual) , nvl(c.reduceDate,'9999999') ),
                                 least( '&4' , nvl(c.reduceDate,'9999999') )
                          )
             )
  from UNTMP_Movable a,UNTMP_MovableDetail c
 where '&11'='1'
   and '&12'<>'2'
   and a.enterOrg='&2'
   and a.ownership='&3'
   and decode('&12','1',a.enterDate,'2')>=decode('&12','1','&13','1')
   and a.enterDate<='&4'
   and decode('&5','99','99',
                   decode('&10','UNTGR_untgr015r',c.keepUnit,
                                'UNTGR_untgr015r1',c.keepUnit,
                                decode('&12','1',c.originalKeepUnit,
                                             nvl(( select substr(max(lpad(nvl(b.editDate,'#'),7,'#')||lpad(nvl(b.editTime,'#'),6,'#')||lpad(nvl(b.newMoveDate,'#'),7,'#')||b.newKeepUnit),21) 
                                                     from UNTMP_MoveDetail b 
                                                    where b.enterOrg   = c.enterOrg
                                                      and b.ownership  = c.ownership
                                                      and b.propertyNo = c.propertyNo
                                                      and b.serialNo   = c.serialNo
                                                      and b.newMoveDate<='&4'
                                                      and b.verify='Y'
                                                 ),c.originalKeepUnit)
                                      )
                         )        
             )='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and ( '&12'='1'       or
         c.dataState='1' or
         ( ('&10' in ('UNTMP_untmp029r','UNTNE_untne028r') ) and c.dataState='2' and c.reduceDate>'&4' ) 
       )
   and a.enterOrg   = c.enterOrg
   and a.ownership  = c.ownership
   and a.propertyNo = c.propertyNo
   and a.lotNo      = c.lotNo
;


-- 更新動產「保管單位、保管人、使用單位、使用人、存置地點」資料
-- 檔案說明：&10、動產移動單明細檔UNTMP_MoveDetail
prompt ◎更新動產「保管單位、保管人、使用單位、使用人、存置地點」資料
update &10 a set
       (a.keepUnit,a.keeper,a.useUnit,a.userNo,a.place)
       = (
            select b.newKeepUnit,b.newKeeper,b.newUseUnit,b.newUserNo,b.newPlace
              from UNTMP_MoveDetail b
             where b.enterOrg   = '&2'
               and b.ownership  = '&3'
               and b.propertyNo = a.propertyNo
               and b.serialNo   = a.serialNo
               and b.newMoveDate<='&4'
               and b.verify='Y'
               and b.caseNo = (
                                 select substr(max(lpad(nvl(d.editDate,'#'),7,'#')||lpad(nvl(d.editTime,'#'),6,'#')||lpad(nvl(d.newMoveDate,'#'),7,'#')||d.caseNo),21)
                                   from UNTMP_MoveDetail d 
                                  where d.enterOrg   = '&2'
                                    and d.ownership  = '&3'
                                    and d.propertyNo = b.propertyNo
                                    and d.serialNo   = b.serialNo
                                    and d.newMoveDate<='&4'
                                    and d.verify='Y'
                              )
         ) 
 where '&11'='1'
   and '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and '&12'='3'
   and a.editID='&1'
   and (a.propertyNo,a.serialNo) in (
                                       select c.propertyNo,c.serialNo
                                         from UNTMP_MoveDetail c
                                        where c.enterOrg  = '&2'
                                          and c.ownership = '&3'
                                          and c.newMoveDate<='&4'
                                          and c.verify='Y'
                                    )
;


-- 更新動產「價值」資料
-- 檔案說明：&10、動產增減值單明細檔UNTMP_AdjustDetail
prompt ◎更新動產「價值」資料
update &10 a set 
       a.bookValue = a.bookValue + nvl(( select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                                           from UNTMP_AdjustDetail b
                                          where b.enterOrg   = '&2'
                                            and b.ownership  = a.ownership
                                            and b.propertyNo = a.propertyNo
                                            and b.serialNo   = a.serialNo
                                            and b.adjustDate<='&4'
                                            and b.verify='Y'
                                       ),0)
 where '&11'='1'
   and '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and '&12'='3'
   and a.editID='&1'
;

-- 更新動產「數量、價值」資料
-- 檔案說明：&10、動產減損單明細檔UNTMP_ReduceDetail
prompt ◎更新動產「數量、價值」資料
update &10 a set 
       a.amount    = a.amount    - nvl(( select sum(b.adjustBookAmount)
                                           from UNTMP_ReduceDetail b
                                          where b.enterOrg   = '&2'
                                            and b.ownership  = a.ownership
                                            and b.propertyNo = a.propertyNo
                                            and b.serialNo   = a.serialNo
                                            and b.reduceDate<='&4'
                                            and b.verify='Y'
                                       ),0),
       a.bookValue = a.bookValue - nvl(( select sum(b.adjustBookValue)
                                           from UNTMP_ReduceDetail b
                                          where b.enterOrg   = '&2'
                                            and b.ownership  = a.ownership
                                            and b.propertyNo = a.propertyNo
                                            and b.serialNo   = a.serialNo
                                            and b.reduceDate<='&4'
                                            and b.verify='Y'
                                       ),0)
 where '&11'='1'
   and '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and '&12'='3'
   and a.editID='&1'
;


-- 新增「動產增減值」資料
-- 檔案說明：&10、動產增減值單明細檔UNTMP_AdjustDetail
prompt ◎新增「動產增減值」資料
insert into &10(editID,ownership,propertyKind,fundType,valuable,keepUnit,keeper,
                propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
                specification,
                propertyUnit,amount,
                buyDate,
                limitYear,place,material,bookValue,sourceDate,useUnit,userNo,oldPropertyNo,
                useYearEnd)
select '&1','&3',a.propertyKind,a.fundType,a.valuable,a.keepUnit,a.keeper,
       substr(a.propertyNo,1,1),a.propertyNo,a.serialNo,a.oldSerialNo,a.propertyName1,
       (  select b.specification||'/'||b.nameplate 
            from UNTMP_Movable b 
           where b.enterOrg   = a.enterOrg
             and b.ownership  = a.ownership
             and b.propertyNo = a.propertyNo
             and b.lotNo      = a.lotNo
       ),
       a.otherPropertyUnit,0,
       (  select b.buyDate 
            from UNTMP_Movable b 
           where b.enterOrg   = a.enterOrg
             and b.ownership  = a.ownership
             and b.propertyNo = a.propertyNo
             and b.lotNo      = a.lotNo
       ),
       a.otherLimitYear,a.place,a.otherMaterial,a.adjustBookValue,a.sourceDate,a.useUnit,a.userNo,a.oldPropertyNo,
       least( (select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual) , 
              nvl(  
                    (
                       select max(e.reduceDate)
                         from UNTMP_ReduceDetail e
                        where e.verify='Y'
                          and e.newBookAmount=0
                          and e.newBookValue=0
                          and e.enterOrg   = a.enterOrg
                          and e.ownership  = a.ownership
                          and e.propertyNo = a.propertyNo
                          and e.serialNo   = a.serialNo
                    )
                    ,
                    '9999999'
                 )
            )
  from UNTMP_AdjustDetail a
 where '&11'='1'
   and '&12' in ('1','2')
   and a.enterOrg='&2'
   and a.ownership='&3'
   and a.adjustDate>='&13'
   and a.adjustDate<='&4'
   and decode('&5','99','99',a.keepUnit)='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and a.adjustType='&12'
;


-- 新增「動產減損」資料
-- 檔案說明：&10、動產減損單明細檔UNTMP_ReduceDetail
prompt ◎新增「動產減損」資料
insert into &10(editID,ownership,propertyKind,fundType,valuable,keepUnit,keeper,
                propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,specification,
                propertyUnit,amount,buyDate,
                limitYear,place,material,bookValue,sourceDate,useUnit,userNo,oldPropertyNo,
                useYearEnd)
select '&1','&3',a.propertyKind,a.fundType,a.valuable,a.keepUnit,a.keeper,
       substr(a.propertyNo,1,1),a.propertyNo,a.serialNo,a.oldSerialNo,a.propertyName1,a.specification||'/'||a.nameplate,
       a.otherPropertyUnit,a.adjustBookAmount,a.buyDate,
       a.otherLimitYear,a.place,a.otherMaterial,a.adjustBookValue,a.sourceDate,a.useUnit,a.userNo,a.oldPropertyNo,
       a.reduceDate
  from UNTMP_ReduceDetail a
 where '&11'='1'
   and '&12'='2'
   and a.enterOrg='&2'
   and a.ownership='&3'
   and a.reduceDate>='&13'
   and a.reduceDate<='&4'
   and decode('&5','99','99',a.keepUnit)='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
;


-- 新增「非消耗品增加」資料
-- 檔案說明：&10、非消耗品主檔－批號資料UNTNE_Nonexp、非消耗品主檔－批號明細UNTNE_NonexpDetail
prompt ◎新增「非消耗品增加」資料
insert into &10(editID,ownership,propertyKind,fundType,valuable,
                keepUnit,
                keeper,
                propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
                specification,propertyUnit,
                amount,
                buyDate,limitYear,
                place,
                material,
                bookValue,
                sourceDate,
                useUnit,
                userNo,
                oldPropertyNo,
                useYearEnd)
select '&1','&3',a.propertyKind,a.fundType,a.valuable,
       decode('&10','UNTGR_untgr015r',c.keepUnit,'UNTGR_untgr015r1',c.keepUnit,c.originalKeepUnit),
       decode('&10','UNTGR_untgr015r',c.keeper,'UNTGR_untgr015r1',c.keeper,c.originalKeeper),             
       substr(a.propertyNo,1,1),a.propertyNo,c.serialNo,c.oldSerialNo,a.propertyName1,
       a.specification||'/'||a.nameplate,a.otherPropertyUnit,
       decode('&10','UNTGR_untgr015r',c.bookAmount,'UNTGR_untgr015r1',c.bookAmount,c.originalAmount),
       a.buyDate,a.otherLimitYear,
       decode('&10','UNTGR_untgr015r',c.place,'UNTGR_untgr015r1',c.place,c.originalPlace),             
       a.otherMaterial,
       decode('&10','UNTGR_untgr015r',c.bookValue,'UNTGR_untgr015r1',c.bookValue,c.originalBV),
       a.sourceDate,
       decode('&10','UNTGR_untgr015r',c.useUnit,'UNTGR_untgr015r1',c.useUnit,c.originalUseUnit),
       decode('&10','UNTGR_untgr015r',c.userNo,'UNTGR_untgr015r1',c.userNo,c.originalUser),
       c.oldPropertyNo,
       decode('&10','UNTGR_untgr015r',(select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual),
                    'UNTGR_untgr015r1',(select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual),
                    decode('&12','1',least( (select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual) , nvl(c.reduceDate,'9999999') ),
                                 least( '&4' , nvl(c.reduceDate,'9999999') )
                          )
             )
  from UNTNE_Nonexp a,UNTNE_NonexpDetail c
 where '&11'='2'
   and '&12'<>'2'
   and a.enterOrg='&2'
   and a.ownership='&3'
   and decode('&12','1',a.enterDate,'2')>=decode('&12','1','&13','1')
   and a.enterDate<='&4'
   and decode('&5','99','99',
                   decode('&10','UNTGR_untgr015r',c.keepUnit,
                                'UNTGR_untgr015r1',c.keepUnit,
                                decode('&12','1',c.originalKeepUnit,
                                             nvl(( select substr(max(lpad(nvl(b.editDate,'#'),7,'#')||lpad(nvl(b.editTime,'#'),6,'#')||lpad(nvl(b.newMoveDate,'#'),7,'#')||b.newKeepUnit),21) 
                                                     from UNTNE_MoveDetail b 
                                                    where b.enterOrg   = c.enterOrg
                                                      and b.ownership  = c.ownership
                                                      and b.propertyNo = c.propertyNo
                                                      and b.serialNo   = c.serialNo
                                                      and b.newMoveDate<='&4'
                                                      and b.verify='Y'
                                                 ),c.originalKeepUnit)
                                      )
                         )        
             )='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and ( '&12'='1'       or
         c.dataState='1' or
         ( ('&10' in ('UNTMP_untmp029r','UNTNE_untne028r') ) and c.dataState='2' and c.reduceDate>'&4' ) 
       )
   and a.enterOrg   = c.enterOrg
   and a.ownership  = c.ownership
   and a.propertyNo = c.propertyNo
   and a.lotNo      = c.lotNo
;


-- 更新非消耗品「保管單位、保管人、使用單位、使用人、存置地點」資料
-- 檔案說明：&10、非消耗品移動單明細檔UNTNE_MoveDetail
prompt ◎更新非消耗品「保管單位、保管人、使用單位、使用人、存置地點」資料
update &10 a set
       (a.keepUnit,a.keeper,a.useUnit,a.userNo,a.place)
       = (
            select b.newKeepUnit,b.newKeeper,b.newUseUnit,b.newUserNo,b.newPlace
              from UNTNE_MoveDetail b
             where b.enterOrg   = '&2'
               and b.ownership  = '&3'
               and b.propertyNo = a.propertyNo
               and b.serialNo   = a.serialNo
               and b.newMoveDate<='&4'
               and b.verify='Y'
               and b.caseNo = (
                                 select substr(max(lpad(nvl(d.editDate,'#'),7,'#')||lpad(nvl(d.editTime,'#'),6,'#')||lpad(nvl(d.newMoveDate,'#'),7,'#')||d.caseNo),21)
                                   from UNTNE_MoveDetail d 
                                  where d.enterOrg   = '&2'
                                    and d.ownership  = '&3'
                                    and d.propertyNo = b.propertyNo
                                    and d.serialNo   = b.serialNo
                                    and d.newMoveDate<='&4'
                                    and d.verify='Y'
                              )
         ) 
 where '&11'='2'
   and '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and '&12'='3'
   and a.editID='&1'
   and (a.propertyNo,a.serialNo) in (
                                       select c.propertyNo,c.serialNo
                                         from UNTNE_MoveDetail c
                                        where c.enterOrg  = '&2'
                                          and c.ownership = '&3'
                                          and c.newMoveDate<='&4'
                                          and c.verify='Y'
                                    )
;


-- 更新非消耗品「價值」資料
-- 檔案說明：&10、非消耗品增減值單明細檔UNTNE_AdjustDetail
prompt ◎更新非消耗品「價值」資料
update &10 a set 
       a.bookValue = a.bookValue + nvl(( select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                                           from UNTNE_AdjustDetail b
                                          where b.enterOrg   = '&2'
                                            and b.ownership  = a.ownership
                                            and b.propertyNo = a.propertyNo
                                            and b.serialNo   = a.serialNo
                                            and b.adjustDate<='&4'
                                            and b.verify='Y'
                                       ),0)
 where '&11'='2'
   and '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and '&12'='3'
   and a.editID='&1'
;


-- 更新非消耗品「數量、價值」資料
-- 檔案說明：&10、非消耗品減損單明細檔UNTNE_ReduceDetail
prompt ◎更新非消耗品「數量、價值」資料
update &10 a set 
       a.amount    = a.amount    - nvl(( select sum(b.adjustBookAmount)
                                           from UNTNE_ReduceDetail b
                                          where b.enterOrg   = '&2'
                                            and b.ownership  = a.ownership
                                            and b.propertyNo = a.propertyNo
                                            and b.serialNo   = a.serialNo
                                            and b.reduceDate<='&4'
                                            and b.verify='Y'
                                       ),0),
       a.bookValue = a.bookValue - nvl(( select sum(b.adjustBookValue)
                                           from UNTNE_ReduceDetail b
                                          where b.enterOrg   = '&2'
                                            and b.ownership  = a.ownership
                                            and b.propertyNo = a.propertyNo
                                            and b.serialNo   = a.serialNo
                                            and b.reduceDate<='&4'
                                            and b.verify='Y'
                                       ),0)
 where '&11'='2'
   and '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and '&12'='3'
   and a.editID='&1'
;


-- 新增「非消耗品增減值」資料
-- 檔案說明：&10、非消耗品增減值單明細檔UNTNE_AdjustDetail
prompt ◎新增「非消耗品增減值」資料
insert into &10(editID,ownership,propertyKind,fundType,valuable,keepUnit,keeper,
                propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,
                specification,
                propertyUnit,amount,
                buyDate,
                limitYear,place,material,bookValue,sourceDate,useUnit,userNo,oldPropertyNo,
                useYearEnd)
select '&1','&3',a.propertyKind,a.fundType,a.valuable,a.keepUnit,a.keeper,
       substr(a.propertyNo,1,1),a.propertyNo,a.serialNo,a.oldSerialNo,a.propertyName1,
       (  select b.specification||'/'||b.nameplate 
            from UNTNE_Nonexp b 
           where b.enterOrg   = a.enterOrg
             and b.ownership  = a.ownership
             and b.propertyNo = a.propertyNo
             and b.lotNo      = a.lotNo
       ),
       a.otherPropertyUnit,0,
       (  select b.buyDate 
            from UNTNE_Nonexp b 
           where b.enterOrg   = a.enterOrg
             and b.ownership  = a.ownership
             and b.propertyNo = a.propertyNo
             and b.lotNo      = a.lotNo
       ),
       a.otherLimitYear,a.place,a.otherMaterial,a.adjustBookValue,a.sourceDate,a.useUnit,a.userNo,a.oldPropertyNo,
       least( (select lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD') from dual) , 
              nvl(  
                    (
                       select max(e.reduceDate)
                         from UNTNE_ReduceDetail e
                        where e.verify='Y'
                          and e.newBookAmount=0
                          and e.newBookValue=0
                          and e.enterOrg   = a.enterOrg
                          and e.ownership  = a.ownership
                          and e.propertyNo = a.propertyNo
                          and e.serialNo   = a.serialNo
                    )
                    ,
                    '9999999'
                 )
            )
  from UNTNE_AdjustDetail a
 where '&11'='2'
   and '&12' in ('1','2')
   and a.enterOrg='&2'
   and a.ownership='&3'
   and a.adjustDate>='&13'
   and a.adjustDate<='&4'
   and decode('&5','99','99',a.keepUnit)='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
   and a.adjustType='&12'
;


-- 新增「非消耗品減損」資料
-- 檔案說明：&10、非消耗品減損單明細檔UNTNE_ReduceDetail
prompt ◎新增「非消耗品減損」資料
insert into &10(editID,ownership,propertyKind,fundType,valuable,keepUnit,keeper,
                propertyType,propertyNo,serialNo,oldSerialNo,propertyName1,specification,
                propertyUnit,amount,buyDate,
                limitYear,place,material,bookValue,sourceDate,useUnit,userNo,oldPropertyNo,
                useYearEnd)
select '&1','&3',a.propertyKind,a.fundType,a.valuable,a.keepUnit,a.keeper,
       substr(a.propertyNo,1,1),a.propertyNo,a.serialNo,a.oldSerialNo,a.propertyName1,a.specification||'/'||a.nameplate,
       a.otherPropertyUnit,a.adjustBookAmount,a.buyDate,
       a.otherLimitYear,a.place,a.otherMaterial,a.adjustBookValue,a.sourceDate,a.useUnit,a.userNo,a.oldPropertyNo,
       a.reduceDate
  from UNTNE_ReduceDetail a
 where '&11'='2'
   and '&12'='2'
   and a.enterOrg='&2'
   and a.ownership='&3'
   and a.reduceDate>='&13'
   and a.reduceDate<='&4'
   and decode('&5','99','99',a.keepUnit)='&5'
   and (
          decode('&6','99','99',propertyKind)=decode('&6','00','01','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','02','&6') or
          decode('&6','99','99',propertyKind)=decode('&6','00','03','&6') 
       )
   and decode('&7','99','99',fundType)='&7'
   and decode('&8','99','99',valuable)='&8'
   and a.verify='Y'
;


-- 刪除「數量及價值」為0之資料
-- 檔案說明：&10
prompt ◎刪除「數量及價值」為0之資料
delete &10
where editID='&1'
  and amount=0
  and bookValue=0
;


-- 更新「已使用年數」資料
-- 檔案說明：&10
prompt ◎更新「已使用年數」資料
update &10 a set 
       useYear = trunc(MONTHS_BETWEEN( to_date(substr(a.useYearEnd,1,5)||'01'+19110000,'YYYYMMDD') , to_date(substr(a.buyDate,1,5)||'01'+19110000,'YYYYMMDD') )/12) || '年' ||
                 MOD( MONTHS_BETWEEN( to_date(substr(a.useYearEnd,1,5)||'01'+19110000,'YYYYMMDD') , to_date(substr(a.buyDate,1,5)||'01'+19110000,'YYYYMMDD') ) ,12 ) || '個月'
 where a.editID='&1'
;


-- 動產明細清冊、非消耗品明細清冊：計算每一組「珍貴財產+財產性質+基金財產」之合計
prompt ◎動產明細清冊、非消耗品明細清冊：計算每一組「珍貴財產+財產性質+基金財產」之合計
insert into &10(editID,ownership,valuable,propertyKind,fundType,propertyNo,propertyName,amount,bookValue,amount1)
select '&1','&3',valuable,propertyKind,fundType,'A999990','合計',sum(nvl(amount,0)),sum(nvl(bookValue,0)),count(*)
  from &10
 where '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and editID='&1'
 group by valuable,propertyKind,fundType
;


-- 動產明細清冊、非消耗品明細清冊：計算總計
prompt ◎動產明細清冊、非消耗品明細清冊：計算總計
insert into &10(editID,ownership,propertyNo,propertyName,amount,bookValue,amount1)
select '&1',ownership,'A999999','合計',sum(nvl(amount,0)),sum(nvl(bookValue,0)),count(*)
  from &10
 where '&10' in ('UNTMP_untmp029r','UNTNE_untne028r')
   and editID='&1'
   and propertyNo!='A999990'
  group by ownership
;


---- 依財產性質換頁：計算每一組「珍貴財產+財產性質+基金財產」之合計
--prompt ◎依財產性質換頁：計算每一組「珍貴財產+財產性質+基金財產」之合計
--insert into &10(editID,ownership,valuable,propertyKind,fundType,propertyNo,propertyName,amount)
--select '&1','&3',valuable,propertyKind,fundType,'A999999','合計',sum(nvl(amount,0))
--  from &10
-- where '&9'='1'
--   and editID='&1'
-- group by valuable,propertyKind,fundType
--;
--
--
---- 依保管單位換頁：計算每一組「珍貴財產+保管單位+保管人」之合計
--prompt ◎依保管單位換頁：計算每一組「珍貴財產+保管單位+保管人」之合計
--insert into &10(editID,ownership,valuable,keepUnit,keeper,propertyNo,propertyName,amount)
--select '&1','&3',valuable,keepUnit,keeper,'A999999','合計',sum(nvl(amount,0))
--  from &10
-- where '&9'='2'
--   and editID='&1'
-- group by valuable,keepUnit,keeper
--;
--
--
---- 依財產類別換頁：計算每一組「珍貴財產+財產類別」之合計
--prompt ◎依財產類別換頁：計算每一組「珍貴財產+財產類別」之合計
--insert into &10(editID,ownership,valuable,propertyType,propertyNo,propertyName,amount)
--select '&1','&3',valuable,propertyType,'A999999','合計',sum(nvl(amount,0))
--  from &10
-- where '&9'='3'
--   and editID='&1'
-- group by valuable,propertyType
--;


-- 取「財產名稱、單位、使用年限」
-- 檔案說明：&10、SYSPK_PropertyCode
prompt ◎取「財產名稱、單位、使用年限」
update &10 a set 
       (propertyName,propertyUnit,limitYear,material) = 
          (  select nvl(b.propertyName,a.propertyName),
                    nvl(b.propertyUnit,a.propertyUnit),
                    nvl(b.limitYear,a.limitYear),
                    nvl(b.material,a.material)
               from SYSPK_PropertyCode b
              where b.enterOrg in ('000000000A','&2')
                and b.propertyNo=a.propertyNo
          )
 where '&11'='1'
   and a.editID='&1'
   and propertyNo not in ('A999990','A999999')
;


-- 取「非消耗品名稱、單位、使用年限」
-- 檔案說明：&10、SYSPK_PropertyCode2
prompt ◎取「非消耗品名稱、單位、使用年限」
update &10 a set 
       (propertyName,propertyUnit,limitYear,material) = 
          (  select nvl(b.propertyName,a.propertyName),
                    nvl(b.propertyUnit,a.propertyUnit),
                    nvl(b.limitYear,a.limitYear),
                    nvl(b.material,a.material)
               from SYSPK_PropertyCode2 b
              where b.enterOrg='&2'
                and b.propertyNo=a.propertyNo
          )
 where '&11'='2'
   and a.editID='&1'
   and propertyNo not in ('A999990','A999999')
;


-- 取「財產性質名稱、基金財產名稱、珍貴財產名稱、保管單位名稱、保管人名稱、財產類別名稱、財產編號組合、使用單位名稱、使用人名稱」
-- 檔案說明：&10、SYSCA_Code、UNTMP_KeepUnit
prompt ◎取「財產性質名稱、基金財產名稱、珍貴財產名稱、保管單位名稱、財產類別名稱、財產編號組合、使用單位名稱、使用人名稱」
update &10 a set 
       propertyKindName = decode(propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用',null),
       fundTypeName     = (select '　'||b.codeName from SYSCA_Code b where b.codeKindID='FUD' and b.codeID=a.fundType ),
       valuableName     = decode(a.valuable,'Y','珍貴財產　',null),
       unitName         = (select b.unitName from UNTMP_KeepUnit b where b.enterOrg='&2' and b.unitNo=a.keepUnit ),
       keeperName       = (select b.keeperName from UNTMP_Keeper b where b.enterOrg='&2' and b.unitNo=a.keepUnit and b.keeperNo=a.keeper ),
       propertyTypeName = decode(a.propertyType,'3','機械及設備','4','交通及運輸設備','5','什項設備','6','非消耗品',null),
       propertyNo1      = decode(a.propertyNo,'A999990',null,'A999999',null,
                                 decode(substr(a.propertyNo,1,3),'503',a.propertyNo,
                                                                 '504',a.propertyNo,
                                                                 '505',a.propertyNo,
                                                                       decode(substr(a.propertyNo,1,1),'6',a.propertyNo,
                                                                                                           substr(a.propertyNo,1,7)||'-'||substr(a.propertyNo,8)
                                                                             )
                                       ) ||'-'|| a.serialNo
                                ),
       useUnitName      = (select b.unitName from UNTMP_KeepUnit b where b.enterOrg='&2' and b.unitNo=a.useUnit ),
       userNoName       = (select b.keeperName from UNTMP_Keeper b where b.enterOrg='&2' and b.unitNo=a.useUnit and b.keeperNo=a.userNo )
 where a.editID='&1'
;

commit;
--spool off
exit;
