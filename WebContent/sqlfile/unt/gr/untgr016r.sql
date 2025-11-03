-- 程式：untgr016r 土地使用關係統計表
-- 注意：執行完untgr016r.sql後再執行untgr016r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:權屬ownership(1:市有,2:國有)
-- 傳入值：&4:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&5:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&6:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 執行指令：@路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5
-- 執行指令範例：@D:\eclipse\workspace\kfcp\sqlfile\unt\gr\untgr016r.sql test 383030000D 1 99 99 99


-- 執行指令的記錄檔
--spool D:\temp\kfcp\UNTGR_untgr016r--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete UNTGR_untgr016r
 where editID='&1'
;
commit;


-- 新增「使用情形」資料
-- 檔案說明：土地主檔UNTLA_Land、土地使用關係統計表UNTGR_untgr016r
prompt ◎新增「使用情形」資料
insert into UNTGR_untgr016r(editID,ownership,propertyKind,fundType,useState,amount,holdArea,bookValue)
select '&1','&3',a.propertyKind,a.fundType,a.useState,count(*),sum(a.holdArea),sum(a.bookValue)
  from UNTLA_Land a
 where a.enterOrg='&2'
   and a.ownership='&3'
   and a.enterDate<=lpad((to_char(sysdate,'YYYY')-1911),3,'0')||to_char(sysdate,'MMDD')
   and (
          decode('&4','99','99',a.propertyKind)=decode('&4','00','01','&4') or
          decode('&4','99','99',a.propertyKind)=decode('&4','00','02','&4') or
          decode('&4','99','99',a.propertyKind)=decode('&4','00','03','&4') 
       )
   and decode('&5','99','99',a.fundType)='&5'
   and decode('&6','99','99',a.valuable)='&6'
   and a.verify='Y'
   and a.dataState='1'
 group by a.propertyKind,a.fundType,a.useState
;


-- 計算每一組「財產性質+基金財產」之合計
prompt ◎計算每一組「財產性質+基金財產」之合計
insert into UNTGR_untgr016r(editID,ownership,propertyKind,fundType,useState,amount,holdArea,bookValue)
select '&1','&3',propertyKind,fundType,'ZZ',sum(nvl(amount,0)),sum(nvl(holdArea,0)),sum(nvl(bookValue,0))
  from UNTGR_untgr016r
 where editID='&1'
 group by propertyKind,fundType
;


-- 取「財產性質名稱、基金財產名稱、項目名稱」
-- 檔案說明：UNTGR_untgr016r、SYSCA_Code
prompt ◎取「財產性質名稱、基金財產名稱、項目名稱」
update UNTGR_untgr016r a set 
       propertyKindName = decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','異常'),
       fundTypeName     = (select b.codeName from SYSCA_Code b where b.codeKindID='FUD' and b.codeID=a.fundType ),
       useStateName     = decode(a.useState,'ZZ','合計',(select b.codeName from SYSCA_Code b where b.codeKindID='UST' and b.codeID=a.useState ))
 where a.editID='&1'
;

commit;
--spool off
exit;
