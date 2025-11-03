package util.filestore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Common;

 public class FileDownloadSimpleServlet extends javax.servlet.http.HttpServlet {
        
	public final String PARM_FILE_ID = "fileID";
	private static final long serialVersionUID = 1L;
	
	public FileDownloadSimpleServlet() {
		super();
	}

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doProcess(req,res);       
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doProcess(req,res);
    }
        
    protected void doProcess(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        ServletContext context = getServletContext();
        String filestoreLocation = context.getInitParameter("filestoreLocation");
        
        String fileName = request.getParameter("fileName");        
        String fileID = request.getParameter(PARM_FILE_ID);
        
        String[] arrFileName;        
        File sFile = null;

        if(fileID == null && fileName == null) {
            response.sendError(404,"Parameter Not Found !");
            return;
        } else if (fileID != null) {
        	//if (context.getServerInfo().toLowerCase().indexOf("tomcat")>0) fileID = Common.isoToBig5(fileID);        	
        	//fileID = new String(fileID.getBytes("ISO-8859-1"), "BIG5");
        	arrFileName = fileID.split(":;:");
        	sFile = new File(filestoreLocation+File.separator+arrFileName[0]+File.separator+arrFileName[1]);
        } else {
        	if (context.getServerInfo().toLowerCase().indexOf("tomcat") > 0) {
//        		fileID = Common.isoToBig5(fileName);
//        		sFile = new File(Common.isoToBig5(fileName));
        		fileID = fileName;
        		sFile = new File(fileName);
        	} else {
        		fileID = fileName;
        		sFile = new File(fileName);
        	}        	
        } 
        
        if(sFile != null && sFile.exists()) {	        
	        String ct = ContentTypeConfiguration.getContentType(fileID);
	        if(ct!=null){
	            response.setContentType(ct);
	        }	        
	        
	        if ((int)sFile.length()>0) {
	        	/**
				FileInputStream in = new FileInputStream(sFile);
	        	response.setHeader("Content-Disposition", "attachment; filename=\"" + sFile.getName() + "\"");
	        	int i; 
	        	OutputStream os = null;	   
	        	try{	        	
		        	while ((i=in.read()) != -1) { 
		        		os.write(i); 
		        	}
		        }catch(Exception x){
		        	try{
		        		response.sendError(500,x.getMessage());
		        	}catch(Exception e){}
		        }finally{
		            os.close();
		            in.close();
		        }	
		        **/	        
	        	
	        	//response.setContentType("application/download");
		        response.setContentLength((int)sFile.length());		        
		        //response.setHeader("Content-Type", "application/download");
		        //response.setHeader("Content-Disposition", "inline; filename=" + sFile.getName());
		        //response.setHeader("Content-Disposition", "attachment; filename=\"" + sFile.getName() + "\"");
		        
		        String fname = URLEncoder.encode(sFile.getName(), "UTF-8");
		        response.setHeader("Content-disposition", "attachment; filename=\"" + fname + "\"");
		        
		        OutputStream os = null;
		        InputStream is = null;
		        
		        try{
		        	byte b[] = new byte[(int)sFile.length()];
		        	if (sFile.isFile())
		        	{
		        		response.flushBuffer();
		        	    is = new FileInputStream(sFile);
		        	    os = response.getOutputStream();
		        	    int read = 0;
		        	    while ((read = is.read(b)) != -1)
		        	    {
		        	        os.write(b,0,read);
		        	    }
		        	}
		        }catch(Exception x){
		        	try{
		        		response.sendError(500,x.getMessage());
		        	}catch(Exception e){}
		        }finally{
		            os.close();
		            is.close();
		        }
		        
	        } else {
	            response.sendError(404,"File Is Empty !");
	            return;
	        }
        } else {
            response.sendError(404,"File Not Found !");
            return;        	
        }       
    }
	
}