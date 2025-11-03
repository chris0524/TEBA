delete UNTMP_Keeper

INSERT INTO UNTMP_Keeper (
 enterOrg
,unitNo
,keeperNo
,keeperName
,notes
,editID
,editDate
,incumbencyYN
)
SELECT 
 DECODE((KEEPER_SORG||KEEPER_SUNIT),
        '376470000A11', '000000011Z', 
        KEEPER_SORG,
 ) as enterOrg   
,KEEPER_UNIT   as unitNo      
,KEEPER_ID     as keeperNo    
,KEEPER_NAME   as keeperName  
,KEEPER_NOTE   as notes       
,KEEPER_SNAME  as editID      
,KEEPER_SDATE  as editDate    
,'Y'           as incumbencyYN
FROM PT_KEEPER A

