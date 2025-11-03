/*
*<br>程式目的：行政院函知新增項目清冊
*<br>程式代號：syspk003r
*<br>撰寫日期：97/04/29
*<br>程式作者：shan(97/04/29)修
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.pk;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class SYSPK003R extends SuperBean{

String q_highestLevelDateS;
String q_highestLevelDateE;
public String getQ_highestLevelDateS(){ return checkGet(q_highestLevelDateS); }
public void setQ_highestLevelDateS(String s){ q_highestLevelDateS=checkSet(s); }
public String getQ_highestLevelDateE(){ return checkGet(q_highestLevelDateE); }
public void setQ_highestLevelDateE(String s){ q_highestLevelDateE=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"highestLevelDateSE","propertyno","propertyname","editkind","propertyunit", "material","limityear","highestleveldate","highestleveldoc"};
    	String execSQL = " select b.propertyno ,b.propertyname ,decode(b.editkind ,'N','新增','U','修改','D','刪除') as editkind ,b.propertyunit ,b.material " + "\n"
    			       + "		  ,b.limityear ,a.highestleveldate ,a.highestleveldoc " + "\n"
    			       + "		  from syspk_notice a ,syspk_noticeproperty b " + "\n"
    			       + "		  where 1=1 " + "\n"
    			       + "		  and a.noticeno=b.noticeno ";
    	if(!"".equals(getQ_highestLevelDateS())){
    		execSQL += " and highestleveldate >= " + Common.sqlChar(getQ_highestLevelDateS());
    	}
    	if(!"".equals(getQ_highestLevelDateS())){
    		execSQL += " and highestleveldate <= " + Common.sqlChar(getQ_highestLevelDateE());
    	}
    		execSQL += " order by propertyno ,highestleveldate" ;
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
            Object[] data = new Object[9];
            data[0]="行政院副本函日期："+Common.MaskDate(getQ_highestLevelDateS())+"~"+Common.MaskDate(getQ_highestLevelDateS());
            data[1]=rs.getString("propertyno");
            data[2]=rs.getString("propertyname");
            data[3]=rs.getString("editkind");
            data[4]=rs.getString("propertyunit");
            data[5]=rs.getString("material");
            data[6]=rs.getString("limityear");
            data[7]=rs.getString("highestleveldate");
            data[8]=rs.getString("highestleveldoc");
            
            rowData.addElement(data);
        }
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}
}