package util;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

public class SQLtransfer{

	//============================================================
		public boolean columnIsCaps = false;
		public boolean tableIsCaps = true;
	
		private List keywordList = new ArrayList();
		private List locationList = new ArrayList();
		
		public String _transSQLFormat(String sql){
			return doTransSQLFormat(sql);
		}
	
			//============================================================
			private String doTransSQLFormat(String sql){
				String result = "";
				
				//=======================================================
				//取出單引號中的字串，替換成replacestring
				sql = sql.replaceAll("''", "'space'");

				String[] s = sql.split("'");				
				List valuetemp = new ArrayList();
				
				String sqltemp = "";
				for(int i = 0; i < s.length; i++){
					if(i%2 == 1){
						valuetemp.add(s[i]);
						sqltemp += "replacestring";
					}else{
						sqltemp += s[i];
					}
				}
				
				sql = sqltemp;

				//=======================================================
				//整理SQL，去除不希望出現的部分
//				sql = sql.replaceAll("[\\t\\r\\n]", "");
				sql = sql.replaceAll(" ,", ",");
				sql = sql.replaceAll(",", ", ");
				sql = sql.replaceAll("\\(", " \\(");
				sql = sql.replaceAll("  ", " ");

				//=======================================================
				//以空格來分割SQL，藉以判斷做何處理
				//
				//比如select col01 from table where col02 = '123'
				//
				//若第３個值為from，第５個值為where
				//則第４個值應該為talbe名稱，需轉成大寫
				//=======================================================
				String[] split = sql.split(" ");

				checkKeyword(split);
				
				result += transKeyword(split);
				
				//=======================================================
				
				result = result.replaceAll("  ", " ");
				
				//=======================================================
				//將replacestring還原成原本的字串
				Iterator iter = valuetemp.iterator();
				while(iter.hasNext()){
					String temp = iter.next().toString();
					if (temp.contains("$")) {
						temp = temp.replaceAll(Matcher.quoteReplacement("$"), "〒");
					}
					if("space".equals(temp)){
						result = result.replaceFirst("replacestring", "''");
						result = result.replaceFirst("REPLACESTRING", "''");
					}else{					
						result = result.replaceFirst("replacestring", "'" + temp + "'").replaceAll("〒", Matcher.quoteReplacement("$"));
						result = result.replaceFirst("REPLACESTRING", "'" + temp + "'").replaceAll("〒", Matcher.quoteReplacement("$"));
					}
				}

				//=======================================================
								
				result = result.replaceAll("''", "null");

				//=======================================================
				
				return result;
			}
		
				//========================================================
				private void checkKeyword(String[] split){
					int len = split.length;
					for(int i=0;i<len;i++){
						String st = split[i];
						if(st.matches(".*[fF][rR][oO][mM].*")){							
							keywordList.add("from");
							locationList.add(i);
						}			
						if(st.matches(".*[wW][hH][eE][rR][eE].*")){
							keywordList.add("where");
							locationList.add(i);
						}						
						if(st.matches(".*[aA][nN][dD].*")){							
							keywordList.add("and");
							locationList.add(i);
						}		
						if(st.matches(".*[jJ][oO][iI][nN].*")){							
							keywordList.add("join");
							locationList.add(i);
						}
						if(st.matches(".*[iI][nN][tT][oO].*")){							
							keywordList.add("into");
							locationList.add(i);
						}
						if(st.matches(".*[uU][pP][dD][aA][tT][eE]")){
							keywordList.add("update");
							locationList.add(i);
						}
						if(st.matches(".*[dD][eE][lL][eE][tT][eE].*")){							
							keywordList.add("delete");
							locationList.add(i);
						}
					}
					
				}
			
				private String transKeyword(String[] split){
					StringBuilder result = new StringBuilder();
					
					Iterator keyIter = keywordList.iterator();
					Iterator locIter = locationList.iterator();
					
					boolean isTable = true;
					boolean isNotTable = false;
					boolean isTail = true;
					boolean checkIsCaps = false;
					int locStart = 0;
					while(keyIter.hasNext()){
						String str = keyIter.next().toString();
						int loc = (Integer)locIter.next();

						if(str.equals("from")){
							result.append(transCaps(split, locStart, loc + 1, isNotTable));
							locStart = loc + 1;
							checkIsCaps = true;
							
						}else if(str.equals("where")){
							result.append(transCaps(split, locStart, loc + 1, isTable));
							locStart = loc + 1;
							checkIsCaps = false;

						}else if(str.equals("join")){							
							result.append(transCaps(split, locStart, loc + 1, isTable));
							locStart = loc + 1;

						}else if(str.equals("into")){
							result.append(transCaps(split, locStart, loc + 1, isNotTable));
							locStart = loc + 1;
							result.append(transCaps(split, locStart, loc + 2, isTable, isTail));
							locStart = loc + 2;
							
						}else if(str.equals("update")){
							result.append(transCaps(split, locStart, loc + 1, isNotTable));
							locStart = loc + 1;
							
						}else if(str.equals("delete")){
							if(deleteBeforeWhere(split[locStart + 2])){
								result.append(transCaps(split, locStart, loc + 1, isNotTable));
								locStart = loc + 1;
								result.append(transCaps(split, locStart, loc + 1, isTable));
								locStart = loc + 1;
							}else{
								result.append(transCaps(split, locStart, loc + 1, isNotTable));
								locStart = loc + 1;
							}
						}
					}
										
					if(checkIsCaps){
						int len = split.length;
						result.append(transCaps(split, locStart, len, isTable, isTail));
					}else{
						int len = split.length;
						result.append(transCaps(split, locStart, len, isNotTable));
					}
					
					
					
					return result.toString();
				}
				
					private String transCaps(String[] split, int locS, int locE, boolean isTable){
						return transCaps(split, locS, locE, isTable, false);
					}
					
					private String transCaps(String[] split, int locS, int locE, boolean isTable, boolean isTail){
						String result = "";

						for(int i=locS;i<locE;i++){
							if(hasQuotation(split[i])){
								if(hasComma(split[i])){
									result += transQuotation(split[i]) + " ";
									i++;
									result += transCapSub(split, i, isTail);
							
								}else{
									result += transQuotation(split[i]) + " ";
								}
							}else{
								if(isTable){
									if(i == locS){
										result += transTableWhenCaps(split[i]) + " ";
										
									}else if(hasComma(split[i])){
										if(split[i].startsWith(",")){											
											result += transTableWhenCaps(split[i]) + " ";
										}else{
											result += transColumnWhenCaps(split[i]) + " ";
											i++;
											result += transCapSub(split, i, isTail);
										}
									}else{
										result += transColumnWhenCaps(split[i]) + " ";
									}
								}else{
									boolean isFrom = false;
									try{isFrom = split[i-1].equals("from");}catch(Exception e){}
									boolean isAnd = false;
									try{isAnd = split[i+1].equals("and");}catch(Exception e){}
									
									if(isFrom && isAnd){
										result += transTableWhenCaps(split[i]) + " ";
									}else{
										result += transColumnWhenCaps(split[i]) + " ";
									}
								}
							}
						}
						return result;
					}
					
						private String transCapSub(String[] split, int i, boolean isTail){
							String result = "";
							if(split[i-1].endsWith(",")){
								if(hasQuotation(split[i])){
									result += transQuotation(split[i]) + " ";
								}else{
									if(beforeJoin(split, i)){
										result += transTableWhenCaps(split[i]) + " ";
									}else if(beforeWhere(split, i)){
										if(hasFullPoint(split[i])){
											result += transQuotation(split[i]) + " ";
										}else if(beforeEqualSign(split[i+1])){
											result += transQuotation(split[i]) + " ";
										}else{
											if(checkHasUpdateBefore(split,i)){
												result += transQuotation(split[i]) + " ";
											}else{
												result += transTableWhenCaps(split[i]) + " ";
											}
										}
									}else{
										result += transQuotation(split[i]) + " ";
									}
								}
							}else if(isTail){
								if(hasQuotation(split[i])){
									result += transQuotation(split[i]) + " ";
								}else{
									result += transTableWhenCaps(split[i]) + " ";
								}	
							}else{
								result += transQuotation(split[i]) + " ";	
							}
							return result;
						}
					
					
						private boolean beforeJoin(String[] split, int numS){
							boolean result = false;
							for(int i=numS;i<split.length;i++){
								if(split[i].equals("join")){
									result = true;
									break;
								}
							}
							return result;
						}
					
						private boolean beforeWhere(String[] split, int numS){
							boolean result = false;
							for(int i=numS;i<split.length;i++){
								if(split[i].equals("where")){
									result = true;
									break;
								}
							}
							return result;
						}
						
						private boolean beforeEqualSign(String split){
							boolean result = false;
							if(split.equals("=")){
								result = true;
							}
							return result;
						}
						
						private boolean deleteBeforeWhere(String s){
							boolean result = false;
							if(s.equals("where")){
								result = true;
							}
							return result;
						}
					
				//========================================================
					//單引號處理
					private String transQuotation(String sql){
						String[] str = sql.split("'");
						String result = "";
						
						int i = 1;
						for(String s : str){
							if(i%2 == 0){
								result += "'" + s + "'";
							}else{
								result += s.toLowerCase();
							}
							i++;
						}
						
						if(sql.endsWith("''")){
							result += "''";
						}
						
						return result;
					}
						private boolean hasQuotation(String sql){
							if(sql.matches(".*'.*'.*")){	return true;
							}else{								return false;
							}
						}
					
					//逗號處理
						private boolean hasComma(String sql){
							if(sql.matches(".*\\,.*")){	return true;
							}else{						return false;
							}
						}
						
					//句號
						private boolean hasFullPoint(String sql){
							if(sql.matches(".*\\..*")){	return true;
							}else{						return false;
							}
						}
						
					
					private String transColumnWhenCaps(String s){
						if(columnIsCaps){	return s.toUpperCase();
						}else{				return s.toLowerCase();
						}
					}
					
					private String transTableWhenCaps(String s){
						if(tableIsCaps){	return s.toUpperCase();
						}else{				return s.toLowerCase();
						}
					}
					
					private boolean checkHasUpdateBefore(String[] split, int locE){
						boolean result = false;
						for(int i = 0; i < locE; i++){
							if(split[i].equals("update")){
								result = true;
								break;
							}
						}
						return result;
					}
					
					
		//================================================================			
		@Test
		public void testCase(){
			SQLtransfer st = new SQLtransfer();	
			String sql = "select * from table a";
			String result = st._transSQLFormat(sql);
		
			String answer = "select * from TABLE a ";

			assertEquals(result, answer);
		}
		
		@Test
		public void testCase02(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select * from table a, table b where a.col=b.col";
			String result = st._transSQLFormat(sql);
			
			String answer = "select * from TABLE a, TABLE b where a.col=b.col ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase03(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, b.col from table a, table b where a.col = 'Abc' and b.col='Abc' and a.col= 'Abc' and a.col=b.col";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, b.col from TABLE a, TABLE b where a.col = 'Abc' and b.col='Abc' and a.col= 'Abc' and a.col=b.col ";
		
			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase04(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, a.col from table a join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc'";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, a.col from TABLE a join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase05(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, a.col from table a join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc'";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, a.col from TABLE a join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' ";

			assertEquals(result, answer);	
		}

		@Test
		public void testCase06(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, a.col from table a join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' , table1 b where a.col=b.col";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, a.col from TABLE a join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc', TABLE1 b where a.col=b.col ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase07(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, a.col from table a join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' , table b join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' where a.col='Abc' and a.col = b.col";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, a.col from TABLE a join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc', TABLE b join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' where a.col='Abc' and a.col = b.col ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase08(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, a.col from table a join table b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' where (a.col, a.col ) in ( select b.col , b.col from table b where b.col = 'Abc' and b.col=a.col)";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, a.col from TABLE a join TABLE b on a.col=b.col and a.col= 'Abc' and a.col ='Abc' where (a.col, a.col ) in ( select b.col, b.col from TABLE b where b.col = 'Abc' and b.col=a.col) ";

			assertEquals(result, answer);	
		}
					
		@Test
		public void testCase09(){
			SQLtransfer st = new SQLtransfer();
			String sql = "insert into table a (a.col, a.col)values('Abc' , 'dBe');";
			String result = st._transSQLFormat(sql);

			String answer = "insert into TABLE a (a.col, a.col)values ('Abc', 'dBe'); ";

			assertEquals(result, answer);	
		}	

		@Test
		public void testCase10(){
			SQLtransfer st = new SQLtransfer();
			String sql = "insert into table (a.col, a.COL)(select a.COL, a.col from table a where a.COL = 'aBc');";
			String result = st._transSQLFormat(sql);

			String answer = "insert into TABLE (a.col, a.col) (select a.col, a.col from TABLE a where a.col = 'aBc'); ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase11(){
			SQLtransfer st = new SQLtransfer();
			String sql = "update table a set a.COL='aBc', a.COL=b.col where a.col='aBd'";
			String result = st._transSQLFormat(sql);

			String answer = "update TABLE a set a.col='aBc', a.col=b.col where a.col='aBd' ";

			assertEquals(result, answer);	
		}
					
		@Test
		public void testCase12(){
			SQLtransfer st = new SQLtransfer();
			String sql = "delete from table a where a.COL='Abd' and a.col = 'ABc'";
			String result = st._transSQLFormat(sql);

			String answer = "delete from TABLE a where a.col='Abd' and a.col = 'ABc' ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase13(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select * from SYSAP_DTREE order by sorted, PID, id";
			String result = st._transSQLFormat(sql);

			String answer = "select * from SYSAP_DTREE order by sorted, pid, id ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase14(){
			SQLtransfer st = new SQLtransfer();
			String sql = "update SYSAP_DTREE set  id = '100', name = '非消耗品增加單及主檔維護', pid = '15', url = '../unt/ne/untne001f.jsp', title = '', target = '', icon = '', iconopen = '', opened = '', btnread = '', btnwrite = '', sorted = '10' where id = '100'";
			String result = st._transSQLFormat(sql);

			String answer = "update SYSAP_DTREE set id = '100', name = '非消耗品增加單及主檔維護', pid = '15', url = '../unt/ne/untne001f.jsp', title = null, target = null, icon = null, iconopen = null, opened = null, btnread = null, btnwrite = null, sorted = '10' where id = '100' ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase15(){
			SQLtransfer st = new SQLtransfer();
			String sql = "insert into UNTMP_DOC (enterorg,docno,docname,notes) select 'A42000000G',docno,docname,notes from UNTMP_DOC_TEMP where ('A42000000G') not in (select enterorg from untmp_doc) and (docno) not in (select docno from UNTMP_DOC) ";
			String result = st._transSQLFormat(sql);
		
			String answer = "insert into UNTMP_DOC (enterorg, docno, docname, notes) select 'A42000000G', docno, docname, notes from UNTMP_DOC_TEMP where ('A42000000G') not in (select enterorg from UNTMP_DOC) and (docno) not in (select docno from UNTMP_DOC) ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase16(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select b.organsname, a.enterorg, a.ownership, a.caseno, a.casename, a.writedate, a.writeunit, a.proofdoc, a.proofno, a.manageno, a.summonsno, a.enterdate, a.mergedivision, a.verify, a.closing, a.notes, a.editid, a.editdate, a.editid, a.editdate, a.edittime from UNTLA_ADDPROOF a, SYSCA_ORGAN b,syspk_propertycode c where 1=1 and a.enterorg = 'A42000000G' and a.caseno = '1030626001' and a.ownership = '3' and b.organid = a.enterorg";
			String result = st._transSQLFormat(sql);
		
			String answer = "select b.organsname, a.enterorg, a.ownership, a.caseno, a.casename, a.writedate, a.writeunit, a.proofdoc, a.proofno, a.manageno, a.summonsno, a.enterdate, a.mergedivision, a.verify, a.closing, a.notes, a.editid, a.editdate, a.editid, a.editdate, a.edittime from UNTLA_ADDPROOF a, SYSCA_ORGAN b, SYSPK_PROPERTYCODE c where 1=1 and a.enterorg = 'A42000000G' and a.caseno = '1030626001' and a.ownership = '3' and b.organid = a.enterorg ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase17(){
			SQLtransfer st = new SQLtransfer();
			String sql = "delete untla_land where 1=1 and enterorg = 'A42000000G' and ownership = '3' and propertyno = '101010101' and serialno = '0000001' ";
			String result = st._transSQLFormat(sql);
		
			String answer = "delete UNTLA_LAND where 1=1 and enterorg = 'A42000000G' and ownership = '3' and propertyno = '101010101' and serialno = '0000001' ";

			assertEquals(result, answer);	
		}
					
		@Test
		public void testCase18(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, b.col from table a, table b where a.col = '測試     bc' and b.col='Abc' and a.col= 'Ab c' and a.col=b.col";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, b.col from TABLE a, TABLE b where a.col = '測試     bc' and b.col='Abc' and a.col= 'Ab c' and a.col=b.col ";

			assertEquals(result, answer);	
		}

		@Test
		public void testCase19(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select m.differencekind, m.propertyno, d.serialno, c.propertyname, m.propertyname1, m.nameplate, m.specification, d.bookamount, isnull (c.propertyunit, null) as propertyunit, m.sourcedate, m.buydate, c.limityear, d.place1, d.keepunit, d.useunit, d.userno, d.keeper, a.specification as specification1, a.nameplate as nameplate1, a.equipmentamount as equipmentamount1, a.totalvalue as totalvalue from UNTMP_MOVABLE m inner join UNTMP_MOVABLEDETAIL d on d.enterorg=m.enterorg and d.ownership=m.ownership and d.differencekind = m.differencekind and d.propertyno=m.propertyno and d.lotno=m.lotno left outer join UNTMP_ATTACHMENT2 a on a.enterorg = d.enterorg and a.ownership = d.ownership and a.differencekind = d.differencekind and a.propertyno = d.propertyno and a.serialno = d.serialno left outer join SYSPK_PROPERTYCODE c on c.enterorg in (d.enterorg, REPLACESTRING) and m.propertyno = c.propertyno where 1=1 and d.enterorg = '000000000A' and d.ownership = 'A42010000G' and d.datastate = '1' and d.verify = '1' order by d.keeper, d.propertyno, d.serialno"; 
			
			String result = st._transSQLFormat(sql);
			
			String answer = "select m.differencekind, m.propertyno, d.serialno, c.propertyname, m.propertyname1, m.nameplate, m.specification, d.bookamount," + 
							" isnull (c.propertyunit, null) as propertyunit, m.sourcedate, m.buydate, c.limityear, d.place1, d.keepunit, d.useunit, d.userno," +
							" d.keeper, a.specification as specification1, a.nameplate as nameplate1, a.equipmentamount as equipmentamount1, a.totalvalue as totalvalue" + 
							" from UNTMP_MOVABLE m" + 
							" inner join UNTMP_MOVABLEDETAIL d on d.enterorg=m.enterorg and d.ownership=m.ownership and d.differencekind = m.differencekind and d.propertyno=m.propertyno and d.lotno=m.lotno" + 
							" left outer join UNTMP_ATTACHMENT2 a on a.enterorg = d.enterorg and a.ownership = d.ownership and a.differencekind = d.differencekind and a.propertyno = d.propertyno and a.serialno = d.serialno" + 
							" left outer join SYSPK_PROPERTYCODE c on c.enterorg in (d.enterorg, '000000000A') and m.propertyno = c.propertyno" + 
							" where 1=1 and d.enterorg = '000000000A' and d.ownership = 'A42010000G' and d.datastate = '1' and d.verify = '1'" + 
							" order by d.keeper, d.propertyno, d.serialno ";

			assertEquals(result, answer);	
		}

		@Test
		public void testCase20(){
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, b.col from table a, table b where a.col = '測試     (bc)' and b.col='A(b)c' and a.col= '(Ab c)' and a.col= 'Ab c' and a.col=b.col";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, b.col from TABLE a, TABLE b where a.col = '測試     (bc)' and b.col='A(b)c' and a.col= '(Ab c)' and a.col= 'Ab c' and a.col=b.col ";

			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase21(){
			// 測試不可使用的符號：$ \ ' "
			
			SQLtransfer st = new SQLtransfer();
			String sql = "select a.col, b.col from table a, table b where a.col = '測試  _!#;:><}{[]?/|_-`~+=*&^%#@!,.  (bc)'";
			String result = st._transSQLFormat(sql);
			
			String answer = "select a.col, b.col from TABLE a, TABLE b where a.col = '測試  _!#;:><}{[]?/|_-`~+=*&^%#@!,.  (bc)' ";
			
			assertEquals(result, answer);	
		}
		
		@Test
		public void testCase22(){
			SQLtransfer st = new SQLtransfer();
			String sql = "update UNTNE_DealDetail set verify='Y', dealDate='20141118', aBc='1BvaG43' where 1=1  and enterOrg = 'A42000000G' and caseNo1 = ''";
			String result = st._transSQLFormat(sql);
			
			String answer = "update UNTNE_DEALDETAIL set verify='Y', dealdate='20141118', abc='1BvaG43' where 1=1 and enterorg = 'A42000000G' and caseno1 = null ";
			
			assertEquals(result, answer);	
		}
		
		

}