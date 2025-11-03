delete UNTMP_KeepUnit

INSERT INTO UNTMP_KeepUnit (
 enterOrg
,unitNo
,unitName
,notes
,editID
,editDate
)
SELECT 
 DECODE((KEEPER_SORG||KEEPER_SUNIT),
        '376470000A11', '000000011Z', 
        KEEPER_SORG,
 ) as enterOrg
,KEEPER_UNIT   as unitNo
,(select UNIT_NAME from PT_UNIT where UNIT_UNO=A.KEEPER_UNO and UNIT_NO=A.KEEPER_UNIT) as unitName  
,KEEPER_NOTE   as notes   
,KEEPER_SNAME  as editID  
,KEEPER_SDATE  as editDate
FROM PT_KEEPER A