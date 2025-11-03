-- 程式：sysst016r 資訊設備現況調查歷年資料查詢
-- 注意：執行完sysst016r.sql後再執行sysst016r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「SYSST_sysst016r」檔案
-- 「權屬ownership」固定為「1:市有」、「審核verify」固定為「Y:是」、「財產編號propertyNo」固定為'314'開頭者
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg(傳入99表示全部機關(不含測試機關3839999))
-- 傳入值：&3:年度reportYear
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3
-- 執行指令範例(單一機關)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\sys\st\sysst016r.sql test 383030000D 097
-- 執行指令範例(全部機關)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\sys\st\sysst016r.sql test 99 097


-- 執行指令的記錄檔
--spool D:\temp\kfcp\sysst016r--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete SYSST_sysst016r
 where editID='&1'
;
commit;

-- 新增「財產編號」資料
prompt ◎新增「財產編號」資料
insert into SYSST_sysst016r(editID,propertyNo,propertyName,limitYear,propertyUnit,
                            subTotal,amount1,amount2,amount3,amount4,amount5,amount6)
select '&1',a.propertyNo,a.propertyName,a.limitYear,a.propertyUnit,
       0,0,0,0,0,0,0
  from SYSPK_PropertyCode a
 where a.enterOrg='000000000A'
   and a.propertyNo like '314%'
;


-- 動產
prompt ◎動產
update SYSST_sysst016r a set 
       amount1    = nvl(a.amount1,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c,SYSCA_Organ d 
                          where b.ownership='1'
                            and b.verify='Y'
                            and b.buyDate>=lpad(('&3' -1),3,'0')||'0101'
                            and b.buyDate<=lpad(('&3' -1),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.propertyNo=a.propertyNo
                            and b.enterOrg=c.enterOrg 
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.lotNo=c.lotNo
                            and b.enterOrg=d.organID
                          group by a.propertyNo
                      ),0),
       amount2    = nvl(a.amount2,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c,SYSCA_Organ d 
                          where b.ownership='1'
                            and b.verify='Y'
                            and b.buyDate>=lpad(('&3' -2),3,'0')||'0101'
                            and b.buyDate<=lpad(('&3' -2),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.propertyNo=a.propertyNo
                            and b.enterOrg=c.enterOrg 
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.lotNo=c.lotNo
                            and b.enterOrg=d.organID
                          group by a.propertyNo
                      ),0),
       amount3    = nvl(a.amount3,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c,SYSCA_Organ d 
                          where b.ownership='1'
                            and b.verify='Y'
                            and b.buyDate>=lpad(('&3' -3),3,'0')||'0101'
                            and b.buyDate<=lpad(('&3' -3),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.propertyNo=a.propertyNo
                            and b.enterOrg=c.enterOrg 
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.lotNo=c.lotNo
                            and b.enterOrg=d.organID
                          group by a.propertyNo
                      ),0),
       amount4    = nvl(a.amount4,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c,SYSCA_Organ d 
                          where b.ownership='1'
                            and b.verify='Y'
                            and b.buyDate>=lpad(('&3' -4),3,'0')||'0101'
                            and b.buyDate<=lpad(('&3' -4),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.propertyNo=a.propertyNo
                            and b.enterOrg=c.enterOrg 
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.lotNo=c.lotNo
                            and b.enterOrg=d.organID
                          group by a.propertyNo
                      ),0),
       amount5    = nvl(a.amount5,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c,SYSCA_Organ d 
                          where b.ownership='1'
                            and b.verify='Y'
                            and b.buyDate>=lpad(('&3' -5),3,'0')||'0101'
                            and b.buyDate<=lpad(('&3' -5),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.propertyNo=a.propertyNo
                            and b.enterOrg=c.enterOrg 
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.lotNo=c.lotNo
                            and b.enterOrg=d.organID
                          group by a.propertyNo
                      ),0),
       amount6    = nvl(a.amount6,0)+nvl(
                      (
                         select sum(c.bookAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c,SYSCA_Organ d 
                          where b.ownership='1'
                            and b.verify='Y'
                            and c.dataState='1'
                            and b.buyDate<=lpad(('&3' -1),3,'0')||'1231'
                            and b.permitReduceDate<=lpad(('&3' -1),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.propertyNo=a.propertyNo
                            and b.enterOrg=c.enterOrg 
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.lotNo=c.lotNo
                            and b.enterOrg=d.organID
                          group by a.propertyNo
                      ),0)
 where a.editID='&1'
;


-- 電腦軟體
prompt ◎電腦軟體
insert into SYSST_sysst016r(editID,propertyNo,propertyName,limitYear,propertyUnit,
                            subTotal,amount1,amount2,amount3,amount4,amount5,amount6)
select distinct '&1','9999999',b.softName,null,'套',
       0,0,0,0,0,0,0
  from UNTSO_PCSoftware b
 where b.enterOrg=decode('&2','99',b.enterOrg,'&2')
;

update SYSST_sysst016r a set 
       amount1    = nvl(a.amount1,0)+nvl(
                      (
                         select sum(b.softAmount)
                           from UNTSO_PCSoftware b,SYSCA_Organ d 
                          where b.verify='Y'
                            and b.startDate>=lpad(('&3' -1),3,'0')||'0101'
                            and b.startDate<=lpad(('&3' -1),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.softName=a.propertyName
                            and b.enterOrg=d.organID
                          group by b.softName
                      ),0),
       amount2    = nvl(a.amount2,0)+nvl(
                      (
                         select sum(b.softAmount)
                           from UNTSO_PCSoftware b,SYSCA_Organ d 
                          where b.verify='Y'
                            and b.startDate>=lpad(('&3' -2),3,'0')||'0101'
                            and b.startDate<=lpad(('&3' -2),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.softName=a.propertyName
                            and b.enterOrg=d.organID
                          group by b.softName
                      ),0),
       amount3    = nvl(a.amount3,0)+nvl(
                      (
                         select sum(b.softAmount)
                           from UNTSO_PCSoftware b,SYSCA_Organ d 
                          where b.verify='Y'
                            and b.startDate>=lpad(('&3' -3),3,'0')||'0101'
                            and b.startDate<=lpad(('&3' -3),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.softName=a.propertyName
                            and b.enterOrg=d.organID
                          group by b.softName
                      ),0),
       amount4    = nvl(a.amount4,0)+nvl(
                      (
                         select sum(b.softAmount)
                           from UNTSO_PCSoftware b,SYSCA_Organ d 
                          where b.verify='Y'
                            and b.startDate>=lpad(('&3' -4),3,'0')||'0101'
                            and b.startDate<=lpad(('&3' -4),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.softName=a.propertyName
                            and b.enterOrg=d.organID
                          group by b.softName
                      ),0),
       amount5    = nvl(a.amount5,0)+nvl(
                      (
                         select sum(b.softAmount)
                           from UNTSO_PCSoftware b,SYSCA_Organ d 
                          where b.verify='Y'
                            and b.startDate>=lpad(('&3' -5),3,'0')||'0101'
                            and b.startDate<=lpad(('&3' -5),3,'0')||'1231'
                            and b.enterOrg=decode('&2','99',b.enterOrg,'&2')
                            and d.IsManager=decode('&2','99','Y',d.IsManager) 
                            and substr(b.enterOrg,1,7)!=decode('&2','99','3839999','1')
                            and b.softName=a.propertyName
                            and b.enterOrg=d.organID
                          group by b.softName
                      ),0)
 where a.editID='&1'
   and a.propertyNo='9999999'
;


-- 小計
prompt ◎小計
update SYSST_sysst016r set
       subTotal=amount1+amount2+amount3+amount4+amount5+amount6
 where editID='&1'
;       

-- 若數量皆為0，則刪除該筆資料
prompt ◎若數量皆為0，則刪除該筆資料
delete SYSST_sysst016r
where editID='&1'
　and subTotal = 0
  and amount1  = 0
  and amount2  = 0
  and amount3  = 0
  and amount4  = 0
  and amount5  = 0
  and amount6  = 0
;

commit;
--spool off
exit;
