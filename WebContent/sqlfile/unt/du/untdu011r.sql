(select distinct 
       a.enterOrg,a.ownership,a.propertyNo,a.serialNo,
       a.originalKeepUnit,a.originalKeeper,
       a.keepUnit,a.keeper,
       a.originalUseUnit,a.originalUser,
       a.useUnit,a.userNo,
       c.organAName as enterOrgName,
       (select z.codeName from sysca_code z where 1=1 and z.codeKindID='OWA' and a.ownership=z.codeID) as ownershipName,
       isnull(b.propertyName1,'') as propertyName1, 
       isnull(b.buyDate,'') as buyDate, 
       isnull(b.nameplate,'')||'/'||isnull(b.specification,'') as nameplateSpecification, 
       '('||a.originalKeepUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.originalKeepUnit=z.unitNo),'') as originalKeepUnitName,
       '('||a.originalKeeper||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.originalKeepUnit=z.unitNo and a.originalKeeper=z.keeperNo),'') as originalKeeperName,
       '('||a.keepUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.keepUnit=z.unitNo),'') as keepUnitName,
       '('||a.keeper||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.keepUnit=z.unitNo and a.keeper=z.keeperNo),'') as keeperName,
       '('||a.originalUseUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.originalUseUnit=z.unitNo),'') as originalUseUnitName,
       '('||a.originalUser||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.originalUseUnit=z.unitNo and a.originalUser=z.keeperNo),'') as originalUserName,
       '('||a.useUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.useUnit=z.unitNo),'') as useUnitName,
       '('||a.userNo||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.useUnit=z.unitNo and a.userNo=z.keeperNo),'') as userNoName,
       isnull(a.place,'') as place,
       case a.dataState when '1' then '現存' else '減損' end as dataStateName
  from UNTMP_MovableDetail a, UNTMP_Movable b, SYSCA_Organ c
 where (
        (a.originalKeepUnit,a.originalKeeper) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.originalKeepUnit
                                                          and b.keeperNo=a.originalKeeper
                                                          and a.enterOrg=b.enterOrg
                                                     )
        or
        (a.keepUnit,a.keeper) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.keepUnit
                                                          and b.keeperNo=a.keeper
                                                          and a.enterOrg=b.enterOrg
                                                     )
        or
        (a.originalUseUnit,a.originalUser) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.originalUseUnit
                                                          and b.keeperNo=a.originalUser
                                                          and a.enterOrg=b.enterOrg
                                                     )
        or
        (a.useUnit,a.userNo) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.useUnit
                                                          and b.keeperNo=a.userNo
                                                          and a.enterOrg=b.enterOrg
                                                     ) 
       )
   and a.enterOrg = c.organID  
   and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.lotNo = b.lotNo
   and a.enterOrg = '&1'
)union(
select distinct 
       a.enterOrg,a.ownership,a.propertyNo,a.serialNo,
       a.originalKeepUnit,a.originalKeeper,
       a.keepUnit,a.keeper,
       a.originalUseUnit,a.originalUser,
       a.useUnit,a.userNo,
       c.organAName as enterOrgName,
       (select z.codeName from sysca_code z where 1=1 and z.codeKindID='OWA' and a.ownership=z.codeID) as ownershipName,
       isnull(b.propertyName1,'') as propertyName1, 
       isnull(b.buyDate,'') as buyDate, 
	   isnull(b.nameplate,'')||'/'||isnull(b.specification,'') as nameplateSpecification,
	   '('||a.originalKeepUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.originalKeepUnit=z.unitNo),'') as originalKeepUnitName,
       '('||a.originalKeeper||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.originalKeepUnit=z.unitNo and a.originalKeeper=z.keeperNo),'') as originalKeeperName,
       '('||a.keepUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.keepUnit=z.unitNo),'') as keepUnitName,
       '('||a.keeper||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.keepUnit=z.unitNo and a.keeper=z.keeperNo),'') as keeperName,
       '('||a.originalUseUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.originalUseUnit=z.unitNo),'') as originalUseUnitName,
       '('||a.originalUser||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.originalUseUnit=z.unitNo and a.originalUser=z.keeperNo),'') as originalUserName,
       '('||a.useUnit||')'||isnull((select z.unitName from UNTMP_KeepUnit z where a.enterOrg=z.enterOrg and a.useUnit=z.unitNo),'') as useUnitName,
       '('||a.userNo||')'||isnull((select z.keeperName from UNTMP_Keeper z where a.enterOrg=z.enterOrg and a.useUnit=z.unitNo and a.userNo=z.keeperNo),'') as userNoName,
       isnull(a.place,'') as place,
       case a.dataState when '1' then '現存' else '減損' end as dataStateName
  from UNTNE_NonexpDetail a, UNTNE_Nonexp b, SYSCA_Organ c
 where (
        (a.originalKeepUnit,a.originalKeeper) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.originalKeepUnit
                                                          and b.keeperNo=a.originalKeeper
                                                          and a.enterOrg=b.enterOrg
                                                     )
        or
        (a.keepUnit,a.keeper) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.keepUnit
                                                          and b.keeperNo=a.keeper
                                                          and a.enterOrg=b.enterOrg
                                                     )
        or
        (a.originalUseUnit,a.originalUser) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.originalUseUnit
                                                          and b.keeperNo=a.originalUser
                                                          and a.enterOrg=b.enterOrg
                                                     )
        or
        (a.useUnit,a.userNo) not in (
                                                       select b.unitNo,b.keeperNo
                                                         from UNTMP_Keeper b
                                                        where b.unitNo=a.useUnit
                                                          and b.keeperNo=a.userNo
                                                          and a.enterOrg=b.enterOrg
                                                     ) 
       )
   and a.enterOrg = c.organID  
   and a.enterOrg = b.enterOrg and a.ownership = b.ownership and a.propertyNo = b.propertyNo and a.lotNo = b.lotNo
   and a.enterOrg = '&1'
)
order by originalKeepUnit,originalKeeper,keepUnit,keeper,originalUseUnit,originalUser,useUnit,userNo,
         enterOrg,ownership,propertyNo,serialNo                                                                                                       
                                                     