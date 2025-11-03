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
-- 注意：測試完後，請將有spool字言的mark，有exit的將mark拿掉
-- 功能：將報表所需要的資料存至「&9」檔案
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:異動日期－起enterDateS
-- 傳入值：&4:異動日期－訖enterDateE
-- 傳入值：&5:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&6:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&7:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&8:權屬ownership(1：市有,2：國有,3：縣有,4：KOC,5：鄉鎮市有)
-- 傳入值：&9:報表檔(UNTGR_untgr010r :市有財產目錄（普通公務機關用）)
-- 傳入值：&9:報表檔(UNTGR_untgr011r :國有財產目錄（普通公務機關用）)
-- 傳入值：&9:報表檔(UNTGR_untgr012r :市有財產目錄（營業基金及非營業循環基金用）)
-- 傳入值：&9:報表檔(UNTGR_untgr013r :國有財產目錄（營業基金及非營業循環基金用）)
-- 傳入值：&9:報表檔(UNTGR_untgr014r :財產盤存報告表（依保管單位換頁）)
-- 傳入值：&9:報表檔(UNTGR_untgr014r1:財產盤存報告表（依財產類別換頁）)
-- 傳入值：&9:報表檔(UNTGR_untgr010r1:非消耗品目錄 )
-- 傳入值：&9:報表檔(UNTGR_untgr011r1:物品目錄（普通公務機關用）)
-- 傳入值：&9:報表檔(UNTGR_untgr014r2:非消耗品盤存報告表（依保管單位換頁）)
-- 傳入值：&9:報表檔(UNTGR_untgr014r3:非消耗品盤存報告表（依財產類別換頁）)
-- 傳入值：&10:財產類別propertyType(1:土地,2:土地改良物,3:建物,4:機械,5:交通,6:什項,7:有價證券,8:權利,傳入99表示未選擇)
-- 傳入值：&11:查詢種類propertyKind1(1:財產,2:非消耗品)
-- 傳入值：&12:上層機關彙總表isorganmanager(Y:是,N:否)
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1 傳入值2 傳入值3 傳入值4 傳入值5 傳入值6 傳入值7 傳入值8 傳入值9 傳入值10 傳入值11 傳入值12
-- 執行指令範例(財產    :untgr010r  程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 1 UNTGR_untgr010r  99 1 N
-- 執行指令範例(財產    :untgr010r  程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 1 UNTGR_untgr010r  99 1 Y
-- 執行指令範例(財產    :untgr011r  程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 2 UNTGR_untgr011r  99 1 N
-- 執行指令範例(財產    :untgr011r  程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 2 UNTGR_untgr011r  99 1 Y
-- 執行指令範例(財產    :untgr012r  程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 1 UNTGR_untgr012r  99 1 N
-- 執行指令範例(財產    :untgr012r  程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 1 UNTGR_untgr012r  99 1 Y
-- 執行指令範例(財產    :untgr013r  程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 2 UNTGR_untgr013r  99 1 N
-- 執行指令範例(財產    :untgr013r  程式-上層機關彙總表)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 2 UNTGR_untgr013r  99 1 Y
-- 執行指令範例(財產    :untgr014r  程式-非珍貴        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 3 UNTGR_untgr014r  99 1 N
-- 執行指令範例(財產    :untgr014r  程式-珍貴          )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 Y 3 UNTGR_untgr014r  99 1 N
-- 執行指令範例(財產    :untgr014r1 程式-非珍貴        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 3 UNTGR_untgr014r1 99 1 N
-- 執行指令範例(財產    :untgr014r1 程式-珍貴          )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 Y 3 UNTGR_untgr014r1 99 1 N
-- 執行指令範例(非消耗品:untgr010r1 程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 1 UNTGR_untgr010r1 99 2 N
-- 執行指令範例(物品    :untgr011r1 程式               )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 3 UNTGR_untgr011r1 99 2 N
-- 執行指令範例(非消耗品:untgr014r2 程式-非珍貴        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 3 UNTGR_untgr014r2 99 2 N
-- 執行指令範例(非消耗品:untgr014r3 程式-非珍貴        )：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr010r.sql test 000000002Z 0980101 0981231 99 99 N 3 UNTGR_untgr014r3 99 2 N

-- 執行指令的記錄檔
--spool D:\temp\kfcp\&9--&1

-- 先刪除該使用者先前產製的資料
prompt ◎先刪除該使用者先前產製的資料
delete &9
 where editID='&1'
;
commit;

-- 新增「財產類別」資料
prompt ◎新增「財產類別」資料
insert into &9(editID,ownership,propertyKind,propertyKindName,fundType,fundTypeName,keepUnit,unitName,propertyType,
               propertyNo,propertyName,propertyUnit,oldAmount,oldArea,addAmount,addArea,reduceAmount,reduceArea,
               nowAmount,nowArea,nowBookValue,nowAppraiseValue,nowaccumDepr,nowBookValue1,enterOrg)
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,'1',
          decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo) propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTLA_Land
    where '&11'='1'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&9','UNTGR_untgr013r',decode(fundType,null,0,1),1)=1
      and decode('&10','99','99','1')='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,'2',
          decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo) propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTRF_Attachment
    where '&11'='1'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&9','UNTGR_untgr013r',decode(fundType,null,0,1),1)=1
      and decode('&10','99','99','2')='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,'3',
          decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo) propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTBU_Building
    where '&11'='1'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&9','UNTGR_untgr013r',decode(fundType,null,0,1),1)=1
      and decode('&10','99','99','3')='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,decode(substr(propertyNo,1,1),'3','4','4','5','6'),
          decode(substr(propertyNo,1,3),
                 '503',decode('&8','1',substr(propertyNo,1,5),'503'),
                 decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo)
                )  propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTMP_Movable a
    where '&11'='1'
      and '&9'!='UNTGR_untgr014r'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&9','UNTGR_untgr013r',decode(fundType,null,0,1),1)=1
      and decode('&10','99','99',decode(substr(propertyNo,1,1),'3','4','4','5','6'))='&10'
)
union
(
   select distinct '&1','&8',a.propertyKind,null,a.fundType,null,b.originalKeepUnit,null,decode(substr(a.propertyNo,1,1),'3','4','4','5','6'),
          decode(substr(a.propertyNo,1,3),
                 '503',decode('&8','1',substr(a.propertyNo,1,5),'503'),
                 decode('&9','UNTGR_untgr011r',substr(a.propertyNo,1,7),'UNTGR_untgr013r',substr(a.propertyNo,1,7),a.propertyNo)
                )  propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,a.enterOrg
     from UNTMP_Movable a,UNTMP_MovableDetail b
    where '&11'='1'
      and '&9'='UNTGR_untgr014r'
      and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and a.ownership='&8'
      and decode('&7','99','99',a.valuable)='&7'
      and a.verify='Y'
      and (
             decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',a.fundType)='&6'
      and decode('&10','99','99',decode(substr(a.propertyNo,1,1),'3','4','4','5','5','6','0'))='&10'
      and a.enterOrg   = b.enterOrg
      and a.ownership  = b.ownership
      and a.propertyNo = b.propertyNo
      and a.lotNo      = b.lotNo
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,newKeepUnit,null,decode(substr(propertyNo,1,1),'3','4','4','5','6'),
          decode(substr(propertyNo,1,3),
                 '503',decode('&8','1',substr(propertyNo,1,5),'503'),
                 decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo)
                )  propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTMP_MoveDetail
    where '&11'='1'
      and '&9'='UNTGR_untgr014r'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&10','99','99',decode(substr(propertyNo,1,1),'3','4','4','5','5','6','0'))='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,keepUnit,null,decode(substr(propertyNo,1,1),'3','4','4','5','6'),
          decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo) propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTMP_AdjustDetail
    where '&11'='1'
      and '&9'='UNTGR_untgr014r'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&10','99','99',decode(substr(propertyNo,1,1),'3','4','4','5','5','6','0'))='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,'7',
          decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo) propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTVP_AddProof
    where '&11'='1'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&9','UNTGR_untgr013r',decode(fundType,null,0,1),1)=1
      and decode('&10','99','99','9')='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,'8',
          decode('&9','UNTGR_untgr011r',substr(propertyNo,1,7),'UNTGR_untgr013r',substr(propertyNo,1,7),propertyNo) propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTRT_AddProof
    where '&11'='1'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
      and decode('&9','UNTGR_untgr013r',decode(fundType,null,0,1),1)=1
      and decode('&10','99','99','8')='&10'
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,'#',null,'A',
          propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTNE_Nonexp a
    where '&11'='2'
      and '&9'!='UNTGR_untgr014r2'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
)
union
(
   select distinct '&1','&8',a.propertyKind,null,a.fundType,null,b.originalKeepUnit,null,'A',
          a.propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,a.enterOrg
     from UNTNE_Nonexp a,UNTNE_NonexpDetail b
    where '&11'='2'
      and '&9'='UNTGR_untgr014r2'
      and ( a.enterOrg='&2' or ( '&12'='Y' and a.enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and a.ownership='&8'
      and decode('&7','99','99',a.valuable)='&7'
      and a.verify='Y'
      and (
             decode('&5','99','99',a.propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',a.propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',a.propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',a.fundType)='&6'
      and a.enterOrg   = b.enterOrg
      and a.ownership  = b.ownership
      and a.propertyNo = b.propertyNo
      and a.lotNo      = b.lotNo
)
union
(
   select distinct '&1','&8',propertyKind,null,fundType,null,newKeepUnit,null,'A',
          propertyNo,
          null,null,0,0,0,0,0,0,0,0,0,0,0,0,enterOrg
     from UNTNE_MoveDetail
    where '&11'='2'
      and '&9'='UNTGR_untgr014r2'
      and ( enterOrg='&2' or ( '&12'='Y' and enterOrg in (select organID from SYSCA_Organ where organSuperior='&2') ) )
      and ownership='&8'
      and decode('&7','99','99',valuable)='&7'
      and verify='Y'
      and (
             decode('&5','99','99',propertyKind)=decode('&5','00','01','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','02','&5') or
             decode('&5','99','99',propertyKind)=decode('&5','00','03','&5') 
          )
      and decode('&6','99','99',fundType)='&6'
)
;


-- 財產類別：土地，本期結存
-- 檔案說明：土地主檔UNTLA_Land、&9
prompt ◎財產類別：土地，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTLA_Land b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select count(*)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowArea      = nvl(a.nowArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTLA_Land b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTLA_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.holdArea)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTLA_Land b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTLA_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='1'
;


-- 財產類別：土地，本期增加(新增)
-- 檔案說明：土地主檔UNTLA_Land、&9
prompt ◎財產類別：土地，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTLA_Land b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTLA_Land b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='1'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地，本期增加(增值)
-- 檔案說明：土地增減值單明細檔UNTLA_AdjustDetail、&9
prompt ◎財產類別：土地，本期增加(增值)
update &9 a set 
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTLA_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.adjustArea>=0
                            and b.adjustHoldArea>=0
                            and b.adjustBookUnit>=0
                            and b.adjustBookValue>=0
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='1'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地，本期減少(減少)
-- 檔案說明：土地減損單明細檔UNTLA_ReduceDetail、&9
prompt ◎財產類別：土地，本期減少(減少)
update &9 a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                      (
                         select sum(b.holdArea)
                           from UNTLA_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='1'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地，本期減少(減值)
-- 檔案說明：土地增減值單明細檔UNTLA_AdjustDetail、&9
prompt ◎財產類別：土地，本期減少(減值)
update &9 a set 
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                      (
                         select sum(-b.adjustHoldArea)
                           from UNTLA_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.adjustArea<=0
                            and b.adjustHoldArea<=0
                            and b.adjustBookUnit<=0
                            and b.adjustBookValue<=0
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='1'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地改良物，本期結存
-- 檔案說明：土地改良物主檔UNTRF_Attachment、&9
prompt ◎財產類別：土地改良物，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTRF_Attachment b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select count(*)
                           from UNTRF_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowArea      = nvl(a.nowArea,0)+nvl(
                      (
                         select sum(b.originalMeasure)
                           from UNTRF_Attachment b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustMeasure,-b.adjustMeasure))
                           from UNTRF_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.measure)
                           from UNTRF_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTRF_Attachment b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                           from UNTRF_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTRF_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowaccumDepr = nvl(a.nowaccumDepr,0)+nvl(
                      (
                         select sum(b.deprAmount)
                           from UNTRF_Attachment b
                          where '&9' in ('UNTGR_untgr012r','UNTGR_untgr013r')
                            and b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                            and b.deprMethod in ('02','03','04')
                            and ( b.dataState='1' or ( b.dataState='2' and b.reduceDate>'&4' ) )
                            and substr('&4',1,5)>b.apportionEndYM
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.accumDepr)+
                                sum( MONTHS_BETWEEN( to_date(substr('&4',1,5)||'01'+19110000,'YYYYMMDD') , to_date(b.accumDeprYM||'01'+19110000,'YYYYMMDD') ) * nvl(b.monthDepr,0) )
                           from UNTRF_Attachment b
                          where '&9' in ('UNTGR_untgr012r','UNTGR_untgr013r')
                            and b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                            and b.deprMethod in ('02','03','04')
                            and ( b.dataState='1' or ( b.dataState='2' and b.reduceDate>'&4' ) )
                            and substr('&4',1,5)<=b.apportionEndYM
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='2'
;


-- 財產類別：土地改良物，本期增加(新增)
-- 檔案說明：土地改良物主檔UNTRF_Attachment、&9
prompt ◎財產類別：土地改良物，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTRF_Attachment b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.originalMeasure)
                           from UNTRF_Attachment b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='2'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地改良物，本期增加(增值)
-- 檔案說明：土地改良物增減值單明細檔UNTRF_AdjustDetail、&9
prompt ◎財產類別：土地改良物，本期增加(增值)
update &9 a set 
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.adjustMeasure)
                           from UNTRF_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.adjustType='1'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='2'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地改良物，本期減少(減少)
-- 檔案說明：土地改良物減損單明細檔UNTRF_ReduceDetail、&9
prompt ◎財產類別：土地改良物，本期減少(減少)
update &9 a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTRF_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                      (
                         select sum(b.measure)
                           from UNTRF_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='2'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：土地改良物，本期減少(減值)
-- 檔案說明：土地改良物增減值單明細檔UNTRF_AdjustDetail、&9
prompt ◎財產類別：土地改良物，本期減少(減值)
update &9 a set 
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                      (
                         select sum(b.adjustMeasure)
                           from UNTRF_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.adjustType='2'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='2'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：房屋建築及設備，本期結存
-- 檔案說明：建物主檔UNTBU_Building、&9
prompt ◎財產類別：房屋建築及設備，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTBU_Building b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select count(*)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowArea      = nvl(a.nowArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTBU_Building b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustHoldArea,-b.adjustHoldArea))
                           from UNTBU_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.holdArea)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTBU_Building b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                           from UNTBU_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowaccumDepr = nvl(a.nowaccumDepr,0)+nvl(
                      (
                         select sum(nvl(b.deprAmount,0))
                           from UNTBU_Building b
                          where '&9' in ('UNTGR_untgr012r','UNTGR_untgr013r')
                            and b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                            and b.deprMethod in ('02','03','04')
                            and ( b.dataState='1' or ( b.dataState='2' and b.reduceDate>'&4' ) )
                            and substr('&4',1,5)>b.apportionEndYM
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(nvl(b.accumDepr,0))+
                                sum( MONTHS_BETWEEN( to_date(substr('&4',1,5)||'01'+19110000,'YYYYMMDD') , to_date(b.accumDeprYM||'01'+19110000,'YYYYMMDD') ) * nvl(b.monthDepr,0) )
                           from UNTBU_Building b
                          where '&9' in ('UNTGR_untgr012r','UNTGR_untgr013r')
                            and b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                            and b.deprMethod in ('02','03','04')
                            and ( b.dataState='1' or ( b.dataState='2' and b.reduceDate>'&4' ) )
                            and substr('&4',1,5)<=b.apportionEndYM
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='3'
;


-- 財產類別：房屋建築及設備，本期增加(新增)
-- 檔案說明：建物主檔UNTBU_Building、&9
prompt ◎財產類別：房屋建築及設備，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTBU_Building b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.originalHoldArea)
                           from UNTBU_Building b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='3'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：房屋建築及設備，本期增加(增值)
-- 檔案說明：建物增減值單明細檔UNTBU_AdjustDetail、&9
prompt ◎財產類別：房屋建築及設備，本期增加(增值)
update &9 a set 
       addArea      = nvl(a.addArea,0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTBU_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.adjustType='1'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='3'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：房屋建築及設備，本期減少(減少)
-- 檔案說明：建物減損單明細檔UNTBU_ReduceDetail、&9
prompt ◎財產類別：房屋建築及設備，本期減少(減少)
update &9 a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                      (
                         select sum(b.holdArea)
                           from UNTBU_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='3'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：房屋建築及設備，本期減少(減值)
-- 檔案說明：建物增減值單明細檔UNTBU_AdjustDetail、&9
prompt ◎財產類別：房屋建築及設備，本期減少(減值)
update &9 a set 
       reduceArea      = nvl(a.reduceArea,0)+nvl(
                      (
                         select sum(b.adjustHoldArea)
                           from UNTBU_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.adjustType='2'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='3'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：動產，本期結存
-- 檔案說明：動產主檔－批號資料UNTMP_Movable、&9
prompt ◎財產類別：動產，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',c.originalKeepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                            and b.enterOrg   = c.enterOrg
                            and b.ownership  = c.ownership
                            and b.propertyNo = c.propertyNo
                            and b.lotNo      = c.lotNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.adjustBookAmount)
                           from UNTMP_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',b.keepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.bookAmount)
                           from UNTMP_MoveDetail b
                          where decode('&9','UNTGR_untgr014r',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.newKeepUnit=a.keepUnit
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookAmount)
                           from UNTMP_MoveDetail b
                          where decode('&9','UNTGR_untgr014r',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.oldKeepUnit=a.keepUnit
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(c.originalBV)
                           from UNTMP_Movable b,UNTMP_MovableDetail c
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',c.originalKeepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                            and b.enterOrg   = c.enterOrg
                            and b.ownership  = c.ownership
                            and b.propertyNo = c.propertyNo
                            and b.lotNo      = c.lotNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                           from UNTMP_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',b.keepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTMP_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',b.keepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.bookValue)
                           from UNTMP_MoveDetail b
                          where decode('&9','UNTGR_untgr014r',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.newKeepUnit=a.keepUnit
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTMP_MoveDetail b
                          where decode('&9','UNTGR_untgr014r',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.oldKeepUnit=a.keepUnit
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowaccumDepr = nvl(a.nowaccumDepr,0)+nvl(
                      (
                         select sum(c.deprAmount)
                           from UNTMP_Movable b,UNTMP_MovableDetail c
                          where '&9' in ('UNTGR_untgr012r','UNTGR_untgr013r')
                            and b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',c.originalKeepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                            and b.deprMethod in ('02','03','04')
--                            and c.dataState='1'
                            and ( c.dataState='1' or ( c.dataState='2' and c.reduceDate>'&4' ) )
                            and substr('&4',1,5)>b.apportionEndYM
                            and b.enterOrg   = c.enterOrg
                            and b.ownership  = c.ownership
                            and b.propertyNo = c.propertyNo
                            and b.lotNo      = c.lotNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(c.accumDepr)+
                                sum( MONTHS_BETWEEN( to_date(substr('&4',1,5)||'01'+19110000,'YYYYMMDD') , to_date(c.accumDeprYM||'01'+19110000,'YYYYMMDD') ) * nvl(monthDepr,0) )
                           from UNTMP_Movable b,UNTMP_MovableDetail c
                          where '&9' in ('UNTGR_untgr012r','UNTGR_untgr013r')
                            and b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r',c.originalKeepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                            and b.deprMethod in ('02','03','04')
                            and ( c.dataState='1' or ( c.dataState='2' and c.reduceDate>'&4' ) )
                            and substr('&4',1,5)<=b.apportionEndYM
                            and b.enterOrg   = c.enterOrg
                            and b.ownership  = c.ownership
                            and b.propertyNo = c.propertyNo
                            and b.lotNo      = c.lotNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType in ('4','5','6')
;


-- 財產類別：動產，本期增加(新增)
-- 檔案說明：動產主檔－批號資料UNTMP_Movable、&9
prompt ◎財產類別：動產，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select sum(b.originalAmount)
                           from UNTMP_Movable b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType in ('4','5','6')
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：動產，本期減少(減少)
-- 檔案說明：動產減損單明細檔UNTMP_ReduceDetail、&9
prompt ◎財產類別：動產，本期減少(減少)
update &9 a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                      (
                         select sum(b.adjustBookAmount)
                           from UNTMP_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode( substr(b.propertyNo,1,3),
                                        '503',decode('&8','1',substr(b.propertyNo,1,5),'503'),
                                        decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)
                                      ) = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType in ('4','5','6')
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
;


-- 財產類別：有價證券，本期結存
-- 檔案說明：有價證券增加單UNTVP_AddProof、&9
prompt ◎財產類別：有價證券，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select sum(b.originalAmount)
                           from UNTVP_AddProof b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.adjustBookAmount)
                           from UNTVP_AdjustProof b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTVP_AddProof b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTVP_AdjustProof b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='7'
   and decode('&7','Y',0,1)=1
;


-- 財產類別：有價證券，本期增加(新增)
-- 檔案說明：有價證券增加單UNTVP_AddProof、&9
prompt ◎財產類別：有價證券，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select sum(b.originalAmount)
                           from UNTVP_AddProof b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='7'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
   and decode('&7','Y',0,1)=1
;


-- 財產類別：有價證券，本期增加(增值)
-- 檔案說明：有價證券增減值單UNTVP_AdjustProof、&9
prompt ◎財產類別：有價證券，本期增加(增值)
update &9 a set 
       addAmount      = nvl(a.addAmount,0)+nvl(
                      (
                         select sum(b.adjustBookAmount)
                           from UNTVP_AdjustProof b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.adjustBookAmount>0
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='7'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
   and decode('&7','Y',0,1)=1
;


-- 財產類別：有價證券，本期減少(減值)
-- 檔案說明：有價證券增減值單UNTVP_AdjustProof、&9
prompt ◎財產類別：有價證券，本期減少(減值)
update &9 a set 
       reduceAmount      = nvl(a.reduceAmount,0)+nvl(
                      (
                         select sum(-b.adjustBookAmount)
                           from UNTVP_AdjustProof b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.adjustBookAmount<0
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='7'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
   and decode('&7','Y',0,1)=1
;


-- 財產類別：權利，本期結存
-- 檔案說明：權利增加單UNTRT_AddProof、&9
prompt ◎財產類別：權利，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTRT_AddProof b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select count(*)
                           from UNTRT_ReduceProof b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.newBookValue=0
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(b.originalBV)
                           from UNTRT_AddProof b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                           from UNTRT_AdjustProof b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.reduceBookValue)
                           from UNTRT_ReduceProof b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='8'
   and decode('&7','Y',0,1)=1
;


-- 財產類別：權利，本期增加(新增)
-- 檔案說明：權利增加單UNTRT_AddProof、&9
prompt ◎財產類別：權利，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTRT_AddProof b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='8'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
   and decode('&7','Y',0,1)=1
;


-- 財產類別：權利，本期減少(減少)
-- 檔案說明：權利減損單UNTRT_ReduceProof、&9
prompt ◎財產類別：權利，本期減少(減少)
update &9 a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                      (
                         select count(*)
                           from UNTRT_ReduceProof b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and b.verify='Y'
                            and b.newBookValue=0
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr011r',substr(b.propertyNo,1,7),'UNTGR_untgr013r',substr(b.propertyNo,1,7),b.propertyNo)=a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='1'
   and a.editID='&1'
   and propertyType='8'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,1)=1
   and decode('&7','Y',0,1)=1
;


-- 財產類別：非消耗品，本期結存
-- 檔案說明：非消耗品主檔－批號資料UNTNE_Nonexp、&9
prompt ◎財產類別：非消耗品，本期結存
update &9 a set 
       nowAmount    = nvl(a.nowAmount,0)+nvl(
                      (
                         select sum(c.originalAmount)
                           from UNTNE_Nonexp b,UNTNE_NonexpDetail c
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r2',c.originalKeepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and b.propertyNo = a.propertyNo
                            and b.enterOrg   = c.enterOrg
                            and b.ownership  = c.ownership
                            and b.propertyNo = c.propertyNo
                            and b.lotNo      = c.lotNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.adjustBookAmount)
                           from UNTNE_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r2',b.keepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.bookAmount)
                           from UNTNE_MoveDetail b
                          where decode('&9','UNTGR_untgr014r2',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.newKeepUnit=a.keepUnit
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookAmount)
                           from UNTNE_MoveDetail b
                          where decode('&9','UNTGR_untgr014r2',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.oldKeepUnit=a.keepUnit
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0),
       nowBookValue = nvl(a.nowBookValue,0)+nvl(
                      (
                         select sum(c.originalBV)
                           from UNTNE_Nonexp b,UNTNE_NonexpDetail c
                          where b.enterOrg=a.enterOrg
                            and b.enterDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r2',c.originalKeepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and b.propertyNo = a.propertyNo
                            and b.enterOrg   = c.enterOrg
                            and b.ownership  = c.ownership
                            and b.propertyNo = c.propertyNo
                            and b.lotNo      = c.lotNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(decode(b.adjustType,'1',b.adjustBookValue,-b.adjustBookValue))
                           from UNTNE_AdjustDetail b
                          where b.enterOrg=a.enterOrg
                            and b.adjustDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r2',b.keepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.adjustBookValue)
                           from UNTNE_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and decode('&9','UNTGR_untgr014r2',b.keepUnit,1)=decode(a.keepUnit,'#','1',a.keepUnit)
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)+nvl(
                      (
                         select sum(b.bookValue)
                           from UNTNE_MoveDetail b
                          where decode('&9','UNTGR_untgr014r2',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.newKeepUnit=a.keepUnit
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)-nvl(
                      (
                         select sum(b.bookValue)
                           from UNTNE_MoveDetail b
                          where decode('&9','UNTGR_untgr014r2',1,0)=1
                            and b.enterOrg=a.enterOrg
                            and b.newMoveDate<='&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.oldKeepUnit=a.keepUnit
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='2'
   and a.editID='&1'
;


-- 財產類別：非消耗品，本期增加(新增)
-- 檔案說明：非消耗品主檔－批號資料UNTNE_Nonexp、&9
prompt ◎財產類別：非消耗品，本期增加(新增)
update &9 a set 
       addAmount    = nvl(a.addAmount,0)+nvl(
                      (
                         select sum(b.originalAmount)
                           from UNTNE_Nonexp b
                          where b.enterOrg=a.enterOrg
                            and b.enterDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='2'
   and a.editID='&1'
   and decode('&9','UNTGR_untgr014r2',0,'UNTGR_untgr014r3',0,1)=1
;


-- 財產類別：非消耗品，本期減少(減少)
-- 檔案說明：非消耗品減損單明細檔UNTNE_ReduceDetail、&9
prompt ◎財產類別：非消耗品，本期減少(減少)
update &9 a set 
       reduceAmount    = nvl(a.reduceAmount,0)+nvl(
                      (
                         select sum(b.adjustBookAmount)
                           from UNTNE_ReduceDetail b
                          where b.enterOrg=a.enterOrg
                            and b.reduceDate between '&3' and '&4'
                            and b.ownership='&8'
                            and decode('&7','99','99',b.valuable)='&7'
                            and b.verify='Y'
                            and b.propertyKind=a.propertyKind
                            and nvl(b.fundType,1)=nvl(a.fundType,1)
                            and b.propertyNo = a.propertyNo
                          group by b.propertyKind,b.fundType
                      ),0)
 where '&11'='2'
   and a.editID='&1'
   and decode('&9','UNTGR_untgr014r2',0,'UNTGR_untgr014r3',0,1)=1
;


-- 計算「財產類別」之「上期結存」
-- 檔案說明：&9
prompt ◎計算「財產類別」之「上期結存」
update &9 a set 
       oldAmount    = nowAmount    - addAmount    + reduceAmount,
       oldArea      = nowArea      - addArea      + reduceArea
 where a.editID='&1'
   and decode('&9','UNTGR_untgr014r',0,'UNTGR_untgr014r1',0,'UNTGR_untgr014r2',0,'UNTGR_untgr014r3',0,1)=1
;


-- 計算「財產類別」之「本期結存－帳面價值」
-- 檔案說明：&9
prompt ◎計算「財產類別」之「本期結存－帳面價值」
update &9 a set 
       nowBookValue1 = nowBookValue - nowaccumDepr
 where a.editID='&1'
   and decode('&9','UNTGR_untgr012r',1,'UNTGR_untgr013r',1,0)=1
;


-- 取「財產名稱、單位」
-- 檔案說明：&9、SYSPK_PropertyCode
prompt ◎取「財產名稱、單位」
update &9 a set 
       (propertyName,propertyUnit) = (  select b.propertyName,
                                               decode(a.propertyType,'1','公頃','3','平方公尺',b.propertyUnit)
                                          from SYSPK_PropertyCode b
                                         where b.enterOrg in ('000000000A',a.enterOrg)
                                           and b.propertyNo=a.propertyNo
                                     )
 where '&11'='1'
   and a.editID='&1'
;


-- 取「非消耗品名稱、單位」
-- 檔案說明：&9、SYSPK_PropertyCode2
prompt ◎取「非消耗品名稱、單位」
update &9 a set 
       (propertyName,propertyUnit) = (  select b.propertyName,b.propertyUnit
                                          from SYSPK_PropertyCode2 b
                                         where b.enterOrg=a.enterOrg
                                           and b.propertyNo=a.propertyNo
                                     )
 where '&11'='2'
   and a.editID='&1'
;


-- 上層機關彙總表：新增彙總資料
-- 檔案說明：&9
prompt ◎上層機關彙總表：新增彙總資料
insert into &9(editID,ownership,propertyKind,fundType,keepUnit,propertyType,propertyNo,propertyName,propertyUnit,
               oldAmount,oldArea,addAmount,addArea,
               reduceAmount,reduceArea,nowAmount,nowArea,
               nowBookValue,nowAppraiseValue,nowaccumDepr,nowBookValue1,
               enterOrg,isorganmanager)
select '&1','&8',propertyKind,fundType,keepUnit,propertyType,propertyNo,propertyName,propertyUnit,
       sum(nvl(oldAmount,0)),sum(nvl(oldArea,0)),sum(nvl(addAmount,0)),sum(nvl(addArea,0)),
       sum(nvl(reduceAmount,0)),sum(nvl(reduceArea,0)),sum(nvl(nowAmount,0)),sum(nvl(nowArea,0)),
       sum(nvl(nowBookValue,0)),sum(nvl(nowAppraiseValue,0)),sum(nvl(nowaccumDepr,0)),sum(nvl(nowBookValue1,0)),
       '&2','Y'
  from &9
 where '&12'='Y'
   and editID='&1'
   and '&9' in ('UNTGR_untgr010r','UNTGR_untgr011r','UNTGR_untgr012r','UNTGR_untgr013r','UNTGR_untgr010r1','UNTGR_untgr011r1')
 group by propertyKind,fundType,keepUnit,propertyType,propertyNo,propertyName,propertyUnit
;


-- 上層機關彙總表：刪除非彙總資料
-- 檔案說明：&9
prompt ◎上層機關彙總表：刪除非彙總資料
delete &9 a
 where '&12'='Y'
   and a.editID='&1'
   and '&9' in ('UNTGR_untgr010r','UNTGR_untgr011r','UNTGR_untgr012r','UNTGR_untgr013r','UNTGR_untgr010r1','UNTGR_untgr011r1')
   and isorganmanager is null
;


-- 計算每一組「財產性質+基金財產＋保管單位」之各類別小計
prompt ◎計算每一組「財產性質+基金財產＋保管單位」之各類別小計
insert into &9(editID,ownership,propertyKind,fundType,keepUnit,propertyType,propertyNo,
               propertyName,propertyUnit,
               oldAmount,oldArea,
               addAmount,addArea,
               reduceAmount,reduceArea,
               nowAmount,nowArea,
               nowBookValue,nowAppraiseValue,nowaccumDepr,nowBookValue1)
select '&1','&8',propertyKind,fundType,keepUnit,propertyType,'A999990',
       decode(propertyType,'1','土地小計','2','土地改良物小計','3','房屋建築及設備小計','4','機械及設備小計','5','交通及運輸設備小計','6','什項設備小計','7','有價證券小計','8','權利小計','A','非消耗品小計','異常小計'),
       decode(propertyType,'1','公頃','2','平方公尺','3','平方公尺',null),
       sum(nvl(oldAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',oldArea,0),oldArea),0)),
       sum(nvl(addAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',addArea,0),addArea),0)),
       sum(nvl(reduceAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',reduceArea,0),reduceArea),0)),
       sum(nvl(nowAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',nowArea,0),nowArea),0)),
       sum(nvl(nowBookValue,0)),sum(nvl(nowAppraiseValue,0)),sum(nvl(nowaccumDepr,0)),sum(nvl(nowBookValue1,0))
  from &9
 where editID='&1'
 group by propertyKind,fundType,keepUnit,propertyType
;


-- 計算每一組「財產性質+基金財產＋保管單位」之合計
prompt ◎計算每一組「財產性質+基金財產＋保管單位」之合計
insert into &9(editID,ownership,propertyKind,fundType,keepUnit,propertyType,propertyNo,propertyName,
               oldAmount,oldArea,
               addAmount,addArea,
               reduceAmount,reduceArea,
               nowAmount,nowArea,
               nowBookValue,nowAppraiseValue,nowaccumDepr,nowBookValue1)
select '&1','&8',propertyKind,fundType,keepUnit,'Z','A999999','合計',
       sum(nvl(oldAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',oldArea,0),oldArea),0)),
       sum(nvl(addAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',addArea,0),addArea),0)),
       sum(nvl(reduceAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',reduceArea,0),reduceArea),0)),
       sum(nvl(nowAmount,0)),
       sum(nvl(decode(propertyType,'2',decode(propertyUnit,'平方公尺',nowArea,0),nowArea),0)),
       sum(nvl(nowBookValue,0)),sum(nvl(nowAppraiseValue,0)),sum(nvl(nowaccumDepr,0)),sum(nvl(nowBookValue1,0))
  from &9
 where '&11'='1'
   and editID='&1'
   and propertyNo!='A999990'
 group by propertyKind,fundType,keepUnit
;


-- 刪除「原有量值、本期暫加、本期減少、現有量值」皆為0之資料
-- 檔案說明：&9
prompt ◎刪除「原有量值、本期暫加、本期減少、現有量值」皆為0之資料
delete &9 a
where a.editID='&1'
  and oldAmount        = 0
  and oldArea          = 0
  and addAmount        = 0
  and addArea          = 0
  and reduceAmount     = 0
  and reduceArea       = 0
  and nowAmount        = 0
  and nowArea          = 0
  and nowBookValue     = 0
  and nowAppraiseValue = 0
  and nowaccumDepr     = 0
  and nowBookValue1    = 0
;


-- 取「入帳機關名稱」
-- 檔案說明：UNTGR_untgr006r、SYSCA_Organ
prompt ◎取「入帳機關名稱」
update &9 a set
       (a.enterOrgName1,a.enterOrgName2)=(select b.organAName,b.organSName from SYSCA_Organ b where b.organID=a.enterOrg) 
 where a.editID='&1'
;


-- 取「財產性質名稱、基金財產名稱、保管單位名稱」
-- 檔案說明：&9、SYSCA_Code、UNTMP_KeepUnit
prompt ◎取「財產性質名稱、基金財產名稱、保管單位名稱」
update &9 a set 
       propertyKindName = decode(propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','異常'),
       fundTypeName     = (select b.codeName from SYSCA_Code b where b.codeKindID='FUD' and b.codeID=a.fundType ),
       unitName         = (select b.unitName from UNTMP_KeepUnit b where b.enterOrg='&2' and b.unitNo=a.keepUnit )
 where a.editID='&1'
;

commit;
--spool off
exit;
