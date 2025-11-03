-- 程式：sysst012r 財政局非公用土地清理情形表
-- 程式：sysst013r 財政局非公用房屋建築清理情形表
-- 程式：sysst014r 財政局非公用房地租金及使用補償金徵收情形表
-- 注意：執行完sysst012r.sql後再執行sysst012r_1.sql
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「&4」檔案
-- 固定值：權屬為「1:市有」
-- 固定值：財產性質為「04:非公用」
-- 固定值：珍貴財產為「N:非珍貴」
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:異動日期－起enterDateS
-- 傳入值：&3:異動日期－訖enterDateE
-- 傳入值：&4:程式代碼(sysst012r 財政局非公用土地清理情形表)
-- 傳入值：&4:程式代碼(sysst013r 財政局非公用房屋建築清理情形表)
-- 傳入值：&4:程式代碼(sysst014r 財政局非公用房地租金及使用補償金徵收情形表)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5
-- 執行指令範例(sysst012r程式)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\sys\st\sysst012r.sql test 0950101 0951231 sysst012r
-- 執行指令範例(sysst013r程式)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\sys\st\sysst012r.sql test 0950101 0951231 sysst013r
-- 執行指令範例(sysst014r程式)：sqlplus kfcp/kfcp@kfcp_local @D:\eclipse\workspace\kfcp\sqlfile\sys\st\sysst012r.sql test 0950101 0951231 sysst014r


-- 執行指令的記錄檔
--spool D:\temp\kfcp\&4--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete SYSST_sysst012r
 where programNo='&4'
   and editID='&1'
;
commit;


-- 新增「財產類別－土地」資料
prompt ◎新增「財產類別－土地」資料
insert into SYSST_sysst012r select '&4','&1','1','01','小計'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst012r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','1','02','出租'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst012r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','1','03','被占用' ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst012r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','1','04','閒置'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst012r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','1','05','其他'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst012r','sysst014r');


-- 新增「財產類別－建物」資料
prompt ◎新增「財產類別－建物」資料
insert into SYSST_sysst012r select '&4','&1','2','01','小計'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst013r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','2','02','出租'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst013r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','2','03','被占用' ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst013r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','2','04','閒置'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst013r','sysst014r');
insert into SYSST_sysst012r select '&4','&1','2','05','其他'   ,0,0,0,0,0,0,0,0,0,0,0,0 from dual where '&4' in ('sysst013r','sysst014r');




-- 財產類別：土地－上期結存
-- 檔案說明：土地主檔UNTLA_Land、土地增減值單明細檔UNTLA_AdjustDetail、土地減損單明細檔UNTLA_ReduceDetail、SYSST_sysst012r
prompt ◎財產類別：土地－上期結存
update SYSST_sysst012r a set 
       oldAmount    = nvl(a.oldAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTLA_Land b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.enterDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)-nvl(
                      (
                         select count(*)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.reduceDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0),
       oldArea      = nvl(a.oldArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTLA_Land b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.enterDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTLA_AdjustDetail b,UNTLA_Land c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(c.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.adjustDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0)-nvl(
                      (
                         select sum(b.holdArea)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.reduceDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0),
       oldBookValue = nvl(a.oldBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTLA_Land b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.enterDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTLA_AdjustDetail b,UNTLA_Land c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(c.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.adjustDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.reduceDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)
 where '&4' in ('sysst012r','sysst014r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.propertyType='1'
;


-- 財產類別：土地－本期減少
-- 檔案說明：土地增減值單明細檔UNTLA_AdjustDetail、土地減損單明細檔UNTLA_ReduceDetail、SYSST_sysst012r
prompt ◎財產類別：土地－本期減少
update SYSST_sysst012r a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                         (
                            select count(*)
                              from UNTLA_ReduceDetail b
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.grass,'Y','05',
                                                  decode(b.taxCredit,'Y','05',
                                                                     decode(b.useState,'01','02','03','03','50','04','05')
                                                        )
                                         )
                                   =a.useState
                               and b.reduceDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                         ),0),
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                         (
                            select sum(-b.adjustHoldArea)
                              from UNTLA_AdjustDetail b,UNTLA_Land c
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.grass,'Y','05',
                                                  decode(b.taxCredit,'Y','05',
                                                                     decode(c.useState,'01','02','03','03','50','04','05')
                                                        )
                                         )
                                   =a.useState
                               and b.adjustDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                               and b.adjustArea<=0
                               and b.adjustHoldArea<=0
                               and b.adjustBookUnit<=0
                               and b.adjustBookValue<=0
                               and b.enterOrg=c.enterOrg
                               and b.ownership=c.ownership
                               and b.propertyNo=c.propertyNo
                               and b.serialNo=c.serialNo
                         ),0)+nvl(
                         (
                            select sum(b.holdArea)
                              from UNTLA_ReduceDetail b
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.grass,'Y','05',
                                                  decode(b.taxCredit,'Y','05',
                                                                     decode(b.useState,'01','02','03','03','50','04','05')
                                                        )
                                         )
                                   =a.useState
                               and b.reduceDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                         ),0),
       reduceBookValue = nvl(a.reduceBookValue,0)+nvl(
                         (
                            select sum(-b.adjustBookValue)
                              from UNTLA_AdjustDetail b,UNTLA_Land c
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.grass,'Y','05',
                                                  decode(b.taxCredit,'Y','05',
                                                                     decode(c.useState,'01','02','03','03','50','04','05')
                                                        )
                                         )
                                   =a.useState
                               and b.adjustDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                               and b.adjustArea<=0
                               and b.adjustHoldArea<=0
                               and b.adjustBookUnit<=0
                               and b.adjustBookValue<=0
                               and b.enterOrg=c.enterOrg
                               and b.ownership=c.ownership
                               and b.propertyNo=c.propertyNo
                               and b.serialNo=c.serialNo
                         ),0)+nvl(
                         (
                            select sum(b.bookValue)
                              from UNTLA_ReduceDetail b
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.grass,'Y','05',
                                                  decode(b.taxCredit,'Y','05',
                                                                     decode(b.useState,'01','02','03','03','50','04','05')
                                                        )
                                         )
                                   =a.useState
                               and b.reduceDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                         ),0)
 where '&4' in ('sysst012r','sysst014r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.propertyType='1'
;


-- 財產類別：土地－本期增加
-- 檔案說明：土地主檔UNTLA_Land、土地增減值單明細檔UNTLA_AdjustDetail、SYSST_sysst012r
prompt ◎財產類別：土地－本期增加
update SYSST_sysst012r a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTLA_Land b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.enterDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0),
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTLA_Land b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.enterDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTLA_AdjustDetail b,UNTLA_Land c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(c.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.adjustDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.adjustArea>=0
                            and b.adjustHoldArea>=0
                            and b.adjustBookUnit>=0
                            and b.adjustBookValue>=0
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0),
       addBookValue = nvl(a.addBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTLA_Land b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(b.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.enterDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTLA_AdjustDetail b,UNTLA_Land c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.grass,'Y','05',
                                               decode(b.taxCredit,'Y','05',
                                                                  decode(c.useState,'01','02','03','03','50','04','05')
                                                     )
                                      )
                                =a.useState
                            and b.adjustDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.adjustArea>=0
                            and b.adjustHoldArea>=0
                            and b.adjustBookUnit>=0
                            and b.adjustBookValue>=0
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0)
 where '&4' in ('sysst012r','sysst014r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.propertyType='1'
;


-- 財產類別：建物－上期結存
-- 檔案說明：建物主檔UNTBU_Building、建物增減值單明細檔UNTBU_AdjustDetail、建物減損單明細檔UNTBU_ReduceDetail、SYSST_sysst012r
prompt ◎財產類別：建物－上期結存
update SYSST_sysst012r a set 
       oldAmount    = nvl(a.oldAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTBU_Building b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.enterDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)-nvl(
                      (
                         select count(*)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.reduceDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0),
       oldArea      = nvl(a.oldArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTBU_Building b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.enterDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustHoldArea,-b.adjustHoldArea))
                           from UNTBU_AdjustDetail b,UNTBU_Building c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(c.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.adjustDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0)-nvl(
                      (
                         select sum(b.holdArea)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.reduceDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0),
       oldBookValue = nvl(a.oldBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTBU_Building b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.enterDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                           from UNTBU_AdjustDetail b,UNTBU_Building c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(c.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.adjustDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.reduceDate<'&2'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)
 where '&4' in ('sysst013r','sysst014r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.propertyType='2'
;


-- 財產類別：建物－本期減少
-- 檔案說明：建物增減值單明細檔UNTBU_AdjustDetail、建物減損單明細檔UNTBU_ReduceDetail、SYSST_sysst012r
prompt ◎財產類別：建物－本期減少
update SYSST_sysst012r a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                         (
                            select count(*)
                              from UNTBU_ReduceDetail b
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.taxCredit,'Y','05',
                                                      decode(b.useState,'01','02','03','03','50','04','05')
                                         )
                                   =a.useState
                               and b.reduceDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                         ),0),
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                         (
                            select sum(b.adjustHoldArea)
                              from UNTBU_AdjustDetail b,UNTBU_Building c
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.taxCredit,'Y','05',
                                                      decode(c.useState,'01','02','03','03','50','04','05')
                                         )
                                   =a.useState
                               and b.adjustDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                               and b.adjustType='2'
                               and b.enterOrg=c.enterOrg
                               and b.ownership=c.ownership
                               and b.propertyNo=c.propertyNo
                               and b.serialNo=c.serialNo
                         ),0)+nvl(
                         (
                            select sum(b.holdArea)
                              from UNTBU_ReduceDetail b
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.taxCredit,'Y','05',
                                                      decode(b.useState,'01','02','03','03','50','04','05')
                                         )
                                   =a.useState
                               and b.reduceDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                         ),0),
       reduceBookValue = nvl(a.reduceBookValue,0)+nvl(
                         (
                            select sum(b.adjustBookValue)
                              from UNTBU_AdjustDetail b,UNTBU_Building c
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.taxCredit,'Y','05',
                                                      decode(c.useState,'01','02','03','03','50','04','05')
                                         )
                                   =a.useState
                               and b.adjustDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                               and b.adjustType='2'
                               and b.enterOrg=c.enterOrg
                               and b.ownership=c.ownership
                               and b.propertyNo=c.propertyNo
                               and b.serialNo=c.serialNo
                         ),0)+nvl(
                         (
                            select sum(b.bookValue)
                              from UNTBU_ReduceDetail b
                             where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                               and decode(b.taxCredit,'Y','05',
                                                      decode(b.useState,'01','02','03','03','50','04','05')
                                         )
                                   =a.useState
                               and b.reduceDate between '&2' and '&3'
                               and b.ownership='1'
                               and b.valuable='N'
                               and b.verify='Y'
                               and b.propertyKind='04'
                         ),0)
 where '&4' in ('sysst013r','sysst014r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.propertyType='2'
;


-- 財產類別：建物－本期增加
-- 檔案說明：建物主檔UNTBU_Building、建物增減值單明細檔UNTBU_AdjustDetail、SYSST_sysst012r
prompt ◎財產類別：建物－本期增加
update SYSST_sysst012r a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTBU_Building b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.enterDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0),
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTBU_Building b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.enterDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTBU_AdjustDetail b,UNTBU_Building c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(c.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.adjustDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.adjustType='1'
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0),
       addBookValue = nvl(a.addBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTBU_Building b
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(b.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.enterDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                      ),0)+nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTBU_AdjustDetail b,UNTBU_Building c
                          where b.enterOrg=(select propertyChiefNO from SYSCA_Argu where countyYN='Y')
                            and decode(b.taxCredit,'Y','05',
                                                   decode(c.useState,'01','02','03','03','50','04','05')
                                      )
                                =a.useState
                            and b.adjustDate between '&2' and '&3'
                            and b.ownership='1'
                            and b.valuable='N'
                            and b.verify='Y'
                            and b.propertyKind='04'
                            and b.adjustType='1'
                            and b.enterOrg=c.enterOrg
                            and b.ownership=c.ownership
                            and b.propertyNo=c.propertyNo
                            and b.serialNo=c.serialNo
                      ),0)
 where '&4' in ('sysst013r','sysst014r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.propertyType='2'
;

-- 財產類別：小計
prompt ◎財產類別：小計
update SYSST_sysst012r a set 
       (oldAmount,oldArea,oldBookValue,
        reduceAmount,reduceArea,reduceBookValue,
        addAmount,addArea,addBookValue) =
       (
          select sum(b.oldAmount),sum(b.oldArea),sum(b.oldBookValue),
                 sum(b.reduceAmount),sum(b.reduceArea),sum(b.reduceBookValue),
                 sum(b.addAmount),sum(b.addArea),sum(b.addBookValue)
            from SYSST_sysst012r b
           where '&4' in ('sysst012r','sysst013r')
             and b.programNo='&4'
             and b.editID='&1'
             and b.useState between '02' and '05'
       )
 where '&4' in ('sysst012r','sysst013r')
   and a.programNo='&4'
   and a.editID='&1'
   and a.useState='01'
;


-- 本期結存
prompt ◎本期結存
update SYSST_sysst012r a set 
       nowAmount    = oldAmount    + addAmount    - reduceAmount,
       nowArea      = oldArea      + addArea      - reduceArea,
       nowBookValue = oldBookValue + addBookValue - reduceBookValue
 where a.programNo='&4'
   and a.editID='&1'
;

commit;
--spool off
exit;
