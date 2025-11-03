-- 程式：untgr009r1 國有財產增減結存表(地方政府使用)
-- 注意：執行順序 untgr005r.sql --> untgr009r1.sql --> untgr009r1_1.sql
-- 功能：由「UNTGR_untgr009r1」檔案取資料列印
-- 傳入值：&1:異動人員editID
-- 執行指令：sqlplus username/password@Database @路徑及檔名 傳入值1
-- 執行指令範例(untgr009r1程式)：sqlplus kfcp/kfcp@TBEK_KD @D:\eclipse\workspace\TBEK\sqlfile\unt\gr\untgr009r1_1.sql test

select a.propertytype,
       ( select c.propertytypename 
           from UNTGR_PROPERTYTYPE c 
          where c.programno='UNTGR_UNTGR009R1' 
            and c.propertytype=a.propertytype
       ) propertyTypeName,
       a.propertymark,
       ltrim(rtrim(convert(varchar,a.oldamount))) oldAmount,
       ltrim(rtrim(case when a.propertymark=1 then convert(varchar,round(a.oldarea/10000,6)) else
                                            convert(varchar,a.oldarea)end )) oldArea,
       ltrim(rtrim(convert(varchar,a.oldbvsubtotal))) oldBookValue,
       ltrim(rtrim(convert(varchar,a.addamount))) addAmount,
       ltrim(rtrim(case when a.propertymark=1 then convert(varchar,round(a.addarea/10000,6)) else
                                            convert(varchar,a.addarea) end )) addArea,
       ltrim(rtrim(convert(varchar,a.addbvsubtotal))) addBookValue,
       ltrim(rtrim(convert(varchar,a.reduceamount))) reduceAmount,
       ltrim(rtrim(case when a.propertymark=1 then convert(varchar,round(a.reducearea/10000,6)) else
                                            convert(varchar,a.reducearea)end )) reduceArea,
       ltrim(rtrim(convert(varchar,a.redecebvsubtotal))) reduceBookValue,
       ltrim(rtrim(convert(varchar,a.nowamount))) nowAmount,
       ltrim(rtrim(case when a.propertymark=1 then convert(varchar,round(a.nowarea/10000,6)) else
                                            convert(varchar,a.nowarea) end )) nowArea,
       ltrim(rtrim(convert(varchar,a.nowbvsubtotal))) nowBookValue
  from UNTGR_UNTGR009R1 a
 where a.editid='&1'
 order by propertytype
;

