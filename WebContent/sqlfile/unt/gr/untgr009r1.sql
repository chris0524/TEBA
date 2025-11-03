-- 程式：untgr009r1 國有財產增減結存表(地方政府使用)
-- 注意：執行順序 untgr005r.sql --> untgr009r1.sql --> untgr009r1_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「UNTGR_UNTGR009R1」檔案
-- 傳入值：&1:異動人員editid
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1
-- 執行指令範例(untgr009r1程式              )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr009r1.sql test

-- 執行指令的記錄檔
--spool D:\temp\kfcp\untgr009r1--&1

-- 先刪除該使用者先前產製的資料
----prompt ◎先刪除該使用者先前產製的資料
delete UNTGR_UNTGR009R1
 where editid='&1'
;
--commit;

-- 新增「財產類別」資料
--prompt ◎新增「財產類別」資料
insert into UNTGR_UNTGR009R1
select '&1',a.propertytype,
       case when a.propertytype=011 then '01'  
            when a.propertytype=012 then '02'  
            when a.propertytype=021 then '01'  
            when a.propertytype=022 then '02'  
            when a.propertytype=031 then '01'  
            when a.propertytype=041 then '01'  
            when a.propertytype=051 then '01'  
            when a.propertytype=052 then '02'  
            when a.propertytype=250 then '99'  
                             else a.propertytype end
              ,
       a.propertymark,
       0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
  from UNTGR_PROPERTYTYPE a
 where programno='UNTGR_UNTGR009R1'
;

-- 由檔案 UNTGR_UNTGR009R 取符合的資料進行更新
--prompt ◎由檔案 UNTGR_UNTGR009R 取符合的資料進行更新
update  a 
set 
          oldamount      =b.oldamount      ,
          oldarea        =b.oldarea        ,
          oldbookvalue   =b.oldbookvalue   ,
          addamount      =b.addamount      ,
          addarea        =b.addarea        ,
          addbookvalue   =b.addbookvalue   ,
          reduceamount   =b.reduceamount   ,
          reducearea     =b.reducearea     ,
          reducebookvalue=b.reducebookvalue,
          nowamount      =b.nowamount      ,
          nowarea        =b.nowarea        ,
          nowbookvalue   =b.nowbookvalue     
FROM UNTGR_UNTGR009R1 a INNER JOIN
          ( select isnull(sum(oldamount      ),0) as oldamount,
                 isnull(sum(oldarea        ),0) as oldarea,
                 isnull(sum(oldbookvalue   ),0) as oldbookvalue,
                 isnull(sum(addamount      ),0) as addamount,
                 isnull(sum(addarea        ),0) as addarea,
                 isnull(sum(addbookvalue   ),0) as addbookvalue,
                 isnull(sum(reduceamount   ),0) as reduceamount,
                 isnull(sum(reducearea     ),0) as reducearea,
                 isnull(sum(reducebookvalue),0) as reducebookvalue,
                 isnull(sum(nowamount      ),0) as nowamount,
                 isnull(sum(nowarea        ),0) as nowarea,
                 isnull(sum(nowbookvalue   ),0) as nowbookvalue,
                 editid,propertytype,propertykind
            from UNTGR_UNTGR009R 
            group by editid,propertytype,propertykind ) b
           on b.editid=a.editid
             and b.propertytype=case when a.propertytype=011 then 010 
                                     when a.propertytype=012 then 010
                                     when a.propertytype=021 then 020
                                     when a.propertytype=022 then 020
                                     when a.propertytype=031 then 030
                                     when a.propertytype=041 then 040
                                     when a.propertytype=051 then 050
                                     when a.propertytype=052 then 050
                                     when a.propertytype=250 then 250
                                     else 000 end
                                      
             and b.propertykind=a.propertykind        
 where a.editid='&1'
   and a.propertytype!=250
;

-- 財產類別：250.合計
-- 檔案說明：UNTGR_UNTGR009R1
--prompt ◎財產類別：250.合計
update  a 
set 
          oldamount       =b.oldamount      ,
          oldarea         =b.oldarea        ,
          oldbookvalue    =b.oldbookvalue   ,
          oldbvsubtotal   =b.oldbvsubtotal  ,
          addamount       =b.addamount      ,
          addarea         =b.addarea        ,
          addbookvalue    =b.addbookvalue   ,
          addbvsubtotal   =b.addbvsubtotal  ,
          reduceamount    =b.reduceamount   ,
          reducearea      =b.reducearea     ,
          reducebookvalue =b.reducebookvalue,
          redecebvsubtotal=b.redecebvsubtotal,
          nowamount       =b.nowamount      ,
          nowarea         =b.nowarea        ,
          nowbookvalue    =b.nowbookvalue   ,
          nowbvsubtotal   =b.nowbvsubtotal     
FROM UNTGR_UNTGR009R1 a INNER JOIN
        ( select isnull(sum(oldamount      ),0) as oldamount,
                 isnull(sum(oldarea        ),0) as oldarea,
                 isnull(sum(oldbookvalue   ),0) as oldbookvalue,
                 isnull(sum(oldbookvalue   ),0) as oldbvsubtotal,
                 isnull(sum(addamount      ),0) as addamount,
                 isnull(sum(addarea        ),0) as addarea,
                 isnull(sum(addbookvalue   ),0) as addbookvalue,
                 isnull(sum(addbookvalue   ),0) as addbvsubtotal,
                 isnull(sum(reduceamount   ),0) as reduceamount,
                 isnull(sum(reducearea     ),0) as reducearea,
                 isnull(sum(reducebookvalue),0) as reducebookvalue,
                 isnull(sum(reducebookvalue),0) as redecebvsubtotal,
                 isnull(sum(nowamount      ),0) as nowamount,
                 isnull(sum(nowarea        ),0) as nowarea,
                 isnull(sum(nowbookvalue   ),0) as nowbookvalue,
                 isnull(sum(nowbookvalue   ),0) as nowbvsubtotal,
                 editid,propertytype,propertykind
            from UNTGR_UNTGR009R 
            group by editid,propertytype,propertykind ) b
           on b.editid=a.editid
             and b.propertytype!=250                                            
 where a.editid='&1'
   and a.propertytype=250
;

-- 計算「價值類別小計」
-- 檔案說明：UNTGR_UNTGR009R1
--prompt ◎計算「價值類別小計」
update  a 
set 
       oldbvsubtotal=b.oldbvsubtotal,
       addbvsubtotal=b.addbvsubtotal,
       redecebvsubtotal=b.redecebvsubtotal,
       nowbvsubtotal=b.nowbvsubtotal
From UNTGR_UNTGR009R1 a INNER JOIN       
          (select sum(oldbookvalue) as oldbvsubtotal,
                  sum(addbookvalue) as addbvsubtotal,
                  sum(reducebookvalue) as redecebvsubtotal,
                  sum(nowbookvalue) as nowbvsubtotal,
                  editid,propertykind,propertymark
            from UNTGR_UNTGR009R1 group by editid,propertykind,propertymark ) b
           on b.editid=a.editid
             and b.propertykind=a.propertykind
             and b.propertymark=a.propertymark
 where a.editid='&1'
   and a.propertytype!=250
;

--commit;
--spool off
--exit;
