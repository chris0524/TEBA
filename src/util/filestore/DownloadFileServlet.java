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

 public class DownloadFileServlet extends javax.servlet.http.HttpServlet {
        
	public final String PARM_FILE_ID = "fileID";
	private static final long serialVersionUID = 1L;
	
	public DownloadFileServlet() {
		super();
	}

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doProcess(req,res);       
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doProcess(req,res);
    }
        
    protected void doProcess(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        
        String filesRealPath =request.getRealPath("");
    
        String fileName = request.getParameter("fileName");        
 
        File sFile = null;

        if(fileName == null) 
        {
            response.sendError(404,"Parameter Not Found !");
            return;
        } 
        else
        {
        	 sFile = new File(filesRealPath+"/"+fileName);
        }	
        
        if(sFile!=null && sFile.exists())
        {	        
	        String ct = ContentTypeConfiguration.getContentType(fileName);
	        if(ct!=null)
	        {
	            response.setContentType(ct);
	        }	        
	        int len = new Long(sFile.length()).intValue();
	       
	        if (len>0) 
	        {	        	

	        	response.setContentLength(len);
	        	response.setHeader("Cache-control","");
	        	response.setHeader("Content-Disposition", "attachment; filename=\"" + Common.big5ToIso(sFile.getName()) + "\"");		        
	        	
				FileInputStream is = null;	        
				OutputStream os = response.getOutputStream();		        
		        try
		        {
		        	byte b[] = new byte[len];
		        	if (sFile.isFile()) {
		        		response.flushBuffer();
		        	    is = new FileInputStream(sFile);
		        	    os = response.getOutputStream();
		        	    int read = 0;
		        	    while ((read = is.read(b)) != -1){
		        	        os.write(b,0,read);
		        	    }
		        	}
		        }catch(Exception x){
		        	try{
		        		response.sendError(500,"File Output Error!");
		        	}catch(Exception e){}
		        }finally{
		        	if (os!=null) os.close();
		            if (is!=null) is.close();
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