delete UNTMP_Store

INSERT INTO UNTMP_Store (
 enterOrg
,storeNo
,storeName
,tel1
,seller
,fax
,notes
,editID
,editDate
)
SELECT 
 DECODE((STORE_SORG||STORE_SUNIT),
        '376470000A11', '000000011Z', 
        STORE_SORG,
 ) as enterOrg
,STORE_NO     as storeNo  
,STORE_NAME   as storeName
,STORE_TEL    as tel1     
,STORE_OWNER  as seller   
,STORE_FAX    as fax      
,STORE_NOTE   as notes    
,STORE_SNAME  as editID   
,STORE_SDATE  as editDate 
FROM PT_STORE A

