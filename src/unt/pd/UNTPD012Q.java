/*
*<br>程式目的：動產資料維護
*<br>程式代號：untpd012q
*<br>程式日期：1030911
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import util.*;

public class UNTPD012Q extends QueryBean{

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_propertyNoS;
String q_propertyNoE;
String q_differenceKind;
String q_propertyType;
String q_propertyNoSName;
String q_propertyNoEName;
String q_serialNoS;
String q_serialNoE;
String q_propertyKind;
String q_fundType;
String q_keepUnit;
String q_keeper;
String q_useUnit;
String q_userNo;
String q_usernote;
String q_place1;
String q_place1Name;
String q_place;
String q_keepBureau;
String q_useBureau;
String q_barcode;
String q_checkResult;
String q_oldPropertyNo;
String q_oldSerialNoS;
String q_oldSerialNoE;
String q_propertyName1;
String q_buyDateS;
String q_buyDateE;
String q_scrappedNote;
String q_labelNote;
String q_moveNote;

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
public String getQ_fundType(){ return checkGet(q_fundType); }
public void setQ_fundType(String s){ q_fundType=checkSet(s); }
public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
public String getQ_keeper(){ return checkGet(q_keeper); }
public void setQ_keeper(String s){ q_keeper=checkSet(s); }
public String getQ_useUnit(){ return checkGet(q_useUnit); }
public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
public String getQ_userNo(){ return checkGet(q_userNo); }
public void setQ_userNo(String s){ q_userNo=checkSet(s); }
public String getQ_keepBureau(){ return checkGet(q_keepBureau); }	
public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
public String getQ_useBureau(){ return checkGet(q_useBureau); }
public void setQ_useBureau(String s){ q_useBureau=checkSet(s); }
public String getQ_barcode(){ return checkGet(q_barcode); }
public void setQ_barcode(String s){ q_barcode=checkSet(s); }
public String getQ_checkResult(){ return checkGet(q_checkResult); }
public void setQ_checkResult(String s){ q_checkResult=checkSet(s); }
public String getQ_oldPropertyNo(){ return checkGet(q_oldPropertyNo); }
public void setQ_oldPropertyNo(String s){ q_oldPropertyNo=checkSet(s); }
public String getQ_oldSerialNoS(){ return checkGet(q_oldSerialNoS); }
public void setQ_oldSerialNoS(String s){ q_oldSerialNoS=checkSet(s); }
public String getQ_oldSerialNoE(){ return checkGet(q_oldSerialNoE); }
public void setQ_oldSerialNoE(String s){ q_oldSerialNoE=checkSet(s); }
public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }
public String getQ_buyDateS(){ return checkGet(q_buyDateS); }
public void setQ_buyDateS(String s){ q_buyDateS=checkSet(s); }
public String getQ_buyDateE(){ return checkGet(q_buyDateE); }
public void setQ_buyDateE(String s){ q_buyDateE=checkSet(s); }
public String getQ_differenceKind() {return checkGet(q_differenceKind);}
public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}
public String getQ_propertyType() {return checkGet(q_propertyType);}
public void setQ_propertyType(String qPropertyType) {q_propertyType = checkSet(qPropertyType);}
public String getQ_usernote() {return checkGet(q_usernote);}
public void setQ_usernote(String qUsernote) {q_usernote = checkSet(qUsernote);}
public String getQ_place1() {return checkGet(q_place1);}
public void setQ_place1(String qPlace1) {q_place1 = checkSet(qPlace1);}
public String getQ_place1Name() {return checkGet(q_place1Name);}
public void setQ_place1Name(String qPlace1Name) {q_place1Name = checkSet(qPlace1Name);}
public String getQ_place() {return checkGet(q_place);}
public void setQ_place(String qPlace) {q_place = checkSet(qPlace);}
public String getQ_scrappedNote() {return checkGet(q_scrappedNote);}
public void setQ_scrappedNote(String qScrappedNote) {q_scrappedNote = checkSet(qScrappedNote);}
public String getQ_labelNote() {return checkGet(q_labelNote);}
public void setQ_labelNote(String qLabelNote) {q_labelNote = checkSet(qLabelNote);}
public String getQ_moveNote() {return checkGet(q_moveNote);}
public void setQ_moveNote(String qMoveNote) {q_moveNote = checkSet(qMoveNote);}






}


