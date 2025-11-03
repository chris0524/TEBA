package util.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;

import sys.web.*;
import util.*;
import util.filestore.ContentTypeConfiguration;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.*;
import net.sf.jasperreports.engine.export.ooxml.*;
import net.sf.jasperreports.engine.fill.JRParameterDefaultValuesEvaluator;
import net.sf.jasperreports.engine.util.JRElementsVisitor;
import net.sf.jasperreports.export.*;

public abstract class ReportGenerator
{
	
    public static final String IMAGE_SESSION_KEY = "ReportImageSession";
    public static final String PRINT_REQUEST_PARAMETER_KEY = "ReportPrintSession";    
    public static final String IMAGE_REQUEST_PARAMETER_KEY = "image_index";
    

    protected ReportEnvironment env;

    protected String format;

    protected String httpFileName;

    protected String jasperPrintSessionId;
    
    public String getJasperPrintSessionId() {
		return jasperPrintSessionId;
	}
	public void setJasperPrintSessionId(String jasperPrintSessionId) {
		this.jasperPrintSessionId = jasperPrintSessionId;
	}

	protected String htmlImagePattern;

    protected Long httpImageCacheTimeout;

    protected String jasperFile;
    protected java.util.List jasperFileList;
    protected java.util.List jasperParamList;
    protected boolean addPdfWaterMark; //Pdf 是否要加上浮水印
    protected java.io.File waterMarkImage;
    
    public ReportGenerator(ReportEnvironment env) {
        this.env = env;
    }

    private Logger logger = Logger.getLogger(ReportGenerator.class);

    abstract protected JasperPrint getJasperPrint(Map<String,Object> parms) throws Exception;
    abstract protected java.util.List<JasperPrint> getJasperPrintList(Map<String,Object> parms) throws Exception;
    
    Hashtable exportParameters;
    
    /**
     * Pdf 是否要加上浮水印
     * @return
     */
    public boolean isAddPdfWaterMark() { return addPdfWaterMark; }   
	public void setAddPdfWaterMark(boolean addPdfWaterMark) { this.addPdfWaterMark = addPdfWaterMark; }
    public java.io.File getWaterMarkImage() { return waterMarkImage; }
	public void setWaterMarkImage(java.io.File waterMarkImage) { this.waterMarkImage = waterMarkImage; }
	private void loadEnvironment() {
        exportParameters = new Hashtable();        
        format = env.getFormat();
        httpFileName = env.getHttpFileName();
        htmlImagePattern = env.getHtmlImagePattern();
        httpImageCacheTimeout = env.getHttpImageCacheTimeout();
        jasperFile = env.getJasperFile();
        jasperFileList = env.getJasperFileList();
        jasperParamList = env.getJasperParamList();
        StringBuffer msg = new StringBuffer();
        msg.append("Environment[");
        msg.append("JasperFile:").append(jasperFile).append(",");
        msg.append("Format:").append(format).append(",");
        msg.append("httpFileName:").append(httpFileName).append(",");
        msg.append("httpImageCacheTimeout:").append(httpImageCacheTimeout).append(",");
        msg.append("htmlImagePattren:").append(htmlImagePattern).append(",");
        msg.append("]");
        logger.debug(msg.toString());
    }
    
    private String getResponseFileName(String filename) {
		String rptFile = ""; 
		if (jasperFileList!=null && jasperFileList.size()>0) {
			rptFile = (String) jasperFileList.get(0);
		} else {
			rptFile = jasperFile;
		}
		java.io.File f = new java.io.File(rptFile);
		int i = filename.lastIndexOf('.');
		int j = f.getName().indexOf('.');
		if (i>0 && i!=-1 && j>0 && j!=-1) {
			return f.getName().substring(0,j) + filename.substring(i).toLowerCase();				
		}
		return filename;
    }
    
    public java.io.File getDividePdf(File src) {
        try {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader(src.getAbsolutePath());
            // we retrieve the total number of pages
            int n = reader.getNumberOfPages();                
			Rectangle pageSize = reader.getPageSize(1);
			Rectangle newSize = new Rectangle(pageSize.getWidth() / 2, pageSize.getHeight());    
			java.io.File newFile = new File(System.getProperty("java.io.tmpdir")+java.io.File.separator+Common.getUUID()+".pdf");
			//String newFileName = System.getProperty("java.io.tmpdir")+java.io.File.separator+Common.getVMID()+".pdf";    			
            Document d1 = new Document(newSize, 0, 0, 0, 0);
            PdfWriter out = PdfWriter.getInstance(d1, new FileOutputStream(newFile));
            d1.open();
            PdfContentByte sb = out.getDirectContent();
            int i = 0;   
            float offsetX, offsetY;
            PdfImportedPage page;
            while (i < n) {
            	d1.newPage();
            	i++;
				pageSize = reader.getPageSize(i);
				newSize = new Rectangle(pageSize.getWidth() / 2, pageSize.getHeight());                	
				d1.newPage();
				offsetX = 0;
				offsetY = 0;
				page = out.getImportedPage(reader, i);
				sb.addTemplate(page, 1, 0, 0, 1, offsetX, offsetY);
				d1.newPage();
				offsetX = -newSize.getWidth();
				offsetY = 0;
				page = out.getImportedPage(reader, i);
				sb.addTemplate(page, 1, 0, 0, 1, offsetX, offsetY);               
            }
            d1.close();                
            return newFile;
        }
        catch (Exception de) {
            de.printStackTrace();
        }
        return src;
    }
    
    private synchronized java.io.File writeImageToFolder(File srcPdf, String imageFormat) throws Exception{
        PDDocument document = PDDocument.load( srcPdf );	
        java.io.File tempDirectory = new java.io.File(System.getProperty("java.io.tmpdir"));
		String tempDirID = Common.getTimeBasedUniqueId();
		tempDirectory = new java.io.File(tempDirectory,tempDirID);			
		tempDirectory.mkdirs();	
        PDFImageWriter imageWriter = new PDFImageWriter();
        imageWriter.writeImage(document, "JPG", null, 1, Integer.MAX_VALUE, tempDirectory.getAbsolutePath()+File.separator+"IMG_", BufferedImage.TYPE_INT_RGB, 96);
        document.close();
        
        if (!imageFormat.equals(ReportEnvironment.VAL_FORMAT_JPG)) {
        	File[] jpgFiles = tempDirectory.listFiles();
        	if (jpgFiles!=null && jpgFiles.length>0) {
        		for (File f : jpgFiles) {
        			f.renameTo(new File(tempDirectory, f.getName().substring(0,f.getName().lastIndexOf('.'))+"."+imageFormat));
        		}
        	}
        }
        return tempDirectory;
    }
    
    public java.io.File transPDFToImage(File srcPdf, String imageFormat) {
    	java.io.File dir = null;
		try {
			dir = writeImageToFolder(srcPdf, imageFormat);
	    	if (dir!=null && dir.isDirectory()) {
	        	ZipUtil zip = new ZipUtil();
	        	java.io.File f = new File(Common.getTempDirectory(), Common.getTimeBasedUniqueId()+".zip");
	        	zip.zipFile(dir,f);    			        	
	        	Common.RemoveDirectory(dir);
	        	return f;
	    	}			
		} catch (Exception e) {			
			e.printStackTrace();
		}
    	return srcPdf;    	
    	/**
    	
        PDDocument document = null;
        try {
            document = PDDocument.load( srcPdf );			
            java.io.File tempDirectory = new java.io.File(System.getProperty("java.io.tmpdir"));
			String tempDirID = Common.getVMID();
			tempDirectory = new java.io.File(tempDirectory,tempDirID);			
			tempDirectory.mkdirs();	
			
            PDFImageWriter imageWriter = new PDFImageWriter();
            boolean success = imageWriter.writeImage(document, "jpg", null, 1, Integer.MAX_VALUE, tempDirectory.getAbsolutePath()+File.separator+"IMG_", BufferedImage.TYPE_INT_RGB, 96);
            if (success) {
            	ZipUtil zip = new ZipUtil();
            	java.io.File f = new File(System.getProperty("java.io.tmpdir")+File.separator+Common.getVMID()+".zip");
            	zip.zipFile(tempDirectory,f);
            	//Common.RemoveDirectory(tempDirectory);
            	return f;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if( document != null )
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}            
        }
        return srcPdf;
        **/
    }
    
    private List<JasperPrint> getJasperReportPrinter(Map<String,Object> parms) {
		List<JasperPrint> printer = null;
		try {
	        if (jasperFileList!=null && jasperFileList.size()>0) {
	        	printer = getJasperPrintList(parms);
	        } else {
	        	printer = new ArrayList<JasperPrint>();
	        	printer.add(getJasperPrint(parms));        	
	        }			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return printer;
    }
    
    public java.io.File reportToFile(File output, Map<String,Object> parms) throws Exception {
        loadEnvironment();
        logger.debug("Output File:" + output.getName());
        FileOutputStream fos = new FileOutputStream(output);
        try {    
    		//if (ReportEnvironment.VAL_FORMAT_XLS.equals(format) || ReportEnvironment.VAL_FORMAT_XLSX.equals(format)) parms.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			List<JasperPrint> printer = getJasperReportPrinter(parms);
            doExport(fos, printer);
	        if (ReportEnvironment.VAL_FORMAT_JPG.equals(format) || ReportEnvironment.VAL_FORMAT_TIF.equals(format)) {
	        	return transPDFToImage(output, format);
	        }       
	        if (ReportEnvironment.VAL_FORMAT_PDF.equals(format) && isAddPdfWaterMark()) {
	        	if (this.getWaterMarkImage()==null) {
	        		java.io.File wmi = new java.io.File(MyServletContext.getInstance().getServletContext().getRealPath("/images/defaultWaterMark.png"));
	        		this.setWaterMarkImage(wmi);
	        	}
	        	return PdfUtil.addWaterMark(output, getWaterMarkImage());
	        }	        
        } catch (Exception x) {
            logger.error(x.getMessage(), x);
            throw x;
        } finally {
            try {
                fos.close();
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
        return output;
    }

    public void reportToHttpResponse(HttpServletRequest request, HttpServletResponse response, Map<String,Object> parms) throws Exception {
    	loadEnvironment();
		try {			
		    HttpSession session = request.getSession();
		    ImageCacheHandler handler = (ImageCacheHandler) session.getAttribute(IMAGE_SESSION_KEY);
		    if (handler != null) {
		        handler.clearNow();
		        session.removeAttribute(IMAGE_SESSION_KEY);
		    }
		    String filename = httpFileName;
		    if (filename == null) filename = Common.getUUID(); 	
		    if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
		        filename = filename + ".html";	
		        httpFileName = filename;
		        response.setContentType("text/html");		        		        
		    } else if (ReportEnvironment.VAL_FORMAT_PDF.equals(format)) {
		        filename = filename + ".pdf";		
		    } else if (ReportEnvironment.VAL_FORMAT_XLS.equals(format)) {
		    	//parms.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		        filename = filename + ".xls";
            } else if (ReportEnvironment.VAL_FORMAT_RTF.equals(format)) {
            	filename = filename + ".rtf";
            	//ct = "application/rtf";	
		    } else if (ReportEnvironment.VAL_FORMAT_CSV.equals(format)) {
		        filename = filename + ".csv";
		    } else if (ReportEnvironment.VAL_FORMAT_JPG.equals(format)) {
            	filename = filename + ".pdf";
		    } else if (ReportEnvironment.VAL_FORMAT_TIF.equals(format)) {
		    	filename = filename + ".pdf";
		    } else if (ReportEnvironment.VAL_FORMAT_DOCX.equals(format)) {
		    	filename = filename + ".docx";		
		    } else if (ReportEnvironment.VAL_FORMAT_XLSX.equals(format)) {
		    	//parms.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		    	filename = filename + ".xlsx";				    	
		    } else if (ReportEnvironment.VAL_FORMAT_PPTX.equals(format)) {
		    	filename = filename + ".pptx";		
	        } else if (ReportEnvironment.VAL_FORMAT_ODT.equals(format)) {
	        	filename = filename + ".odt";
	        } else if (ReportEnvironment.VAL_FORMAT_ODS.equals(format)) {
	        	filename = filename + ".ods";	
	        } else if (ReportEnvironment.VAL_FORMAT_XML.equals(format)) {
	        	filename = filename + ".xml";			    	
		    } else {
		        throw new IllegalArgumentException("Doesn't support format:" + format);
		    }	        
			List<JasperPrint> printer = this.getJasperReportPrinter(parms);
			/**
            if (jasperFileList!=null && jasperFileList.size()>0) {
                printer = getJasperPrintList(parms);
            } else {
            	printer = new ArrayList<JasperPrint>();
            	printer.add(getJasperPrint(parms));        	
            }	
            **/	    
            java.io.File outputFile = new java.io.File(Common.getTempDirectory(), filename);			    
	        FileOutputStream fos = new FileOutputStream(outputFile);	        
	        try {
	        	doExport(fos, printer);
	        } catch (Exception x) {
	            logger.error(x.getMessage(), x);
	            throw x;
	        } finally {
	            try {
	                fos.close();
	            } catch (Throwable e) {
	                logger.error(e.getMessage(), e);
	            }
	        }	   
	        if (ReportEnvironment.VAL_FORMAT_PDF.equals(format) && isAddPdfWaterMark()) {
	        	if (this.getWaterMarkImage()==null) {
	        		java.io.File wmi = new java.io.File(MyServletContext.getInstance().getServletContext().getRealPath("/images/defaultWaterMark.png"));
	        		this.setWaterMarkImage(wmi);
	        	}
	        	outputFile = PdfUtil.addWaterMark(outputFile, getWaterMarkImage());
	        	filename = outputFile.getName();
	        }	        
	        if (ReportEnvironment.VAL_FORMAT_JPG.equals(format) || ReportEnvironment.VAL_FORMAT_TIF.equals(format)) {		        	
	        	outputFile = transPDFToImage(outputFile, format);
	        	filename = outputFile.getName();
	        }		        	  
	        int len = new Long(outputFile.length()).intValue();
	        
	        if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
	        	response.setHeader("Content-disposition", "inline; filename=" + filename);
	        } else {	        	
		        String ct = ContentTypeConfiguration.getContentType(outputFile.getName());
		        if(ct!=null){
		            response.setContentType(ct);
		        }		        	
	        	response.setHeader("Cache-control","");
			    response.setContentLength(len);	        
			    response.setHeader("Content-Disposition", "attachment; filename=\"" + getResponseFileName(filename) + "\"");	        	
	        }			    
		    FileInputStream is = null;	      
		    OutputStream os = null;
		    try{
		    	byte b[] = new byte[len];
		    	if (outputFile.isFile()) {
		    		response.flushBuffer();
		    	    is = new FileInputStream(outputFile);
		    	    os = response.getOutputStream();
		    	    int read = 0;
		    	    while ((read=is.read(b)) != -1) {
		    	        os.write(b,0,read);
		    	    }
		    	}
		    }catch(Exception x){
		    	logger.error(x.getMessage(), x);
		    	x.printStackTrace();
		    	//try{
		    	//	response.sendError(500,"報表產製錯誤！");
		    	//}catch(Exception e){
		    	//	e.printStackTrace();
		    	//}
		    }finally{
		    	if (os!=null) os.close();
		        if (is!=null) is.close();
		        if (outputFile!=null && outputFile.exists()) outputFile.delete();
		    }							
		} catch (Exception x) {
		    logger.error(x.getMessage(), x);
		    throw x;
		}
		
	}  
    
    
    private Exporter getJRExporterAndFillParameter() throws Exception {
    	Exporter exporter = null;
        if (ReportEnvironment.VAL_FORMAT_HTML.equals(format)) {
            exporter = new HtmlExporter();
        } else if (ReportEnvironment.VAL_FORMAT_PDF.equals(format)) {
            exporter = new JRPdfExporter();
        } else if (ReportEnvironment.VAL_FORMAT_XLS.equals(format)) {
            exporter = new JRXlsExporter();
        } else if (ReportEnvironment.VAL_FORMAT_RTF.equals(format)) {
        	exporter = new JRRtfExporter();
        } else if (ReportEnvironment.VAL_FORMAT_CSV.equals(format)) {
        	exporter = new JRCsvExporter();        	
        } else if (ReportEnvironment.VAL_FORMAT_JPG.equals(format) || ReportEnvironment.VAL_FORMAT_TIF.equals(format)) {
            exporter = new JRPdfExporter();
        } else if (ReportEnvironment.VAL_FORMAT_DOCX.equals(format)) {
            exporter = new JRDocxExporter();
        } else if (ReportEnvironment.VAL_FORMAT_XLSX.equals(format)) {
            exporter = new JRXlsxExporter();
        } else if (ReportEnvironment.VAL_FORMAT_PPTX.equals(format)) {
            exporter = new JRPptxExporter();            
        } else if (ReportEnvironment.VAL_FORMAT_ODT.equals(format)) {
        	exporter = new JROdtExporter();
        } else if (ReportEnvironment.VAL_FORMAT_ODS.equals(format)) {
        	exporter = new JROdsExporter();        	
        } else if (ReportEnvironment.VAL_FORMAT_XML.equals(format)) {
        	exporter = new JRXmlExporter();			    	
	    } else {
            throw new IllegalArgumentException("Doesn't support format:" + format);
        }
        return exporter;
    }
    
    private void doExport(OutputStream output, List<JasperPrint> printer) throws Exception {
    	printableType(output, printer, ReportGenerator.ReportType.valueOf(format)); 
    }    

    /**
     * 報表種類
     *
     * @author wolf
     */
    public enum ReportType {
    	PDF, ODT, ODS, XLSX, DOCX, PPTX, XLS, TIF, CSV, XML, RTF, HTML, VIEWER
    }	   
    
    public JasperReport compileAndFillReport(String jrxmlPath) throws JRException, FileNotFoundException {
        return JasperCompileManager.compileReport(new FileInputStream(jrxmlPath));
    }

    public void printableType(OutputStream out, List<JasperPrint> jasperPrint, ReportType printType) {
        switch (printType) {
	    	case DOCX:
	    		printDocx(out, jasperPrint);
	    		break;       
        	case PPTX:
        		printPpTx(out, jasperPrint);
        		break;        		
            case XLSX:
            case XLS:
                printXls(out, jasperPrint);
                break;
            default:
            case PDF:
                printPdf(out, jasperPrint);
                break;
            case ODS:
                printODS(out, jasperPrint);
                break;
        	case XML:
        		printXML(out, jasperPrint);
        		break; 
        	case CSV:
        		printCsv(out, jasperPrint);
        		break;         		
            case HTML:
            case VIEWER:
                printHTML(out, jasperPrint);
                break;                
        }
    }   
    
    private void printPdf(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
            JRPdfExporter exporter = new JRPdfExporter();
            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            
            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            //exportConfig.setEncrypted(true);
            //exportConfig.set128BitKey(true);
            //exportConfig.setUserPassword("12377493");
            //exportConfig.setOwnerPassword("12377493");
            //exportConfig.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
            
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            //log.warn("wrong print PDF Exception:", e);
        }
    }

    public void printXls(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {          
            // 設定 Excel 配置
            SimpleXlsxReportConfiguration reportConfiguration = new SimpleXlsxReportConfiguration();
            reportConfiguration.setOnePagePerSheet(true);  // 每頁一個 Sheet
            reportConfiguration.setDetectCellType(false);   // 自動偵測單元格類型
            reportConfiguration.setCollapseRowSpan(false); // 禁止合併儲存格

            SimpleXlsxExporterConfiguration exportConfiguration = new SimpleXlsxExporterConfiguration();
            
            // 建立 Excel 輸出
            JRXlsxExporter exporter = new JRXlsxExporter();
            
            // 設定輸出格式
            exporter.setConfiguration(reportConfiguration);
            exporter.setConfiguration(exportConfiguration);

            if (jasperPrintList != null && !jasperPrintList.isEmpty()) {
                exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            } else {
                throw new IllegalArgumentException("JasperPrint list is empty!");
            }
            // output
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            //log.warn("wrong print Xls Exception:", e);
        }
    }
    
    private void printDocx(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
        	JRDocxExporter exporter = new JRDocxExporter();
        	SimpleDocxReportConfiguration reportConfig = new SimpleDocxReportConfiguration();
        	SimpleDocxExporterConfiguration exportConfiguration = new SimpleDocxExporterConfiguration();
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfiguration);
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            logger.info("wrong print PDF Exception:", e);
        }
    }
    
    private void printPpTx(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
        	JRPptxExporter exporter = new JRPptxExporter();
        	SimplePptxReportConfiguration reportConfig = new SimplePptxReportConfiguration();
        	SimplePptxExporterConfiguration exportConfiguration = new SimplePptxExporterConfiguration();
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfiguration);
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            logger.info("wrong print PDF Exception:", e);
        }
    }
    
    private void printODS(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
            SimpleOdsExporterConfiguration odsConfiguration = new SimpleOdsExporterConfiguration();
            SimpleOdsReportConfiguration reportConfiguration = new SimpleOdsReportConfiguration();
            reportConfiguration.setOnePagePerSheet(true);
            reportConfiguration.setDetectCellType(false);
            reportConfiguration.setCollapseRowSpan(false);

            JROdsExporter exporter = new JROdsExporter();
            exporter.setConfiguration(reportConfiguration);
            exporter.setConfiguration(odsConfiguration);
            exporter.setReportContext(null);
            // export
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            //log.warn("wrong print HTML Exception:", e);
        }
    }
    
    private void printHTML(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
            SimpleHtmlExporterConfiguration exportConfig = new SimpleHtmlExporterConfiguration();
            SimpleHtmlReportConfiguration reportConfig = new SimpleHtmlReportConfiguration();
            //reportConfig.setEmbedImage(true);
            
            HtmlExporter exporter = new HtmlExporter();
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            
            // export
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            //log.warn("wrong print HTML Exception:", e);
        }
    }
    
    private void printXML(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
        	SimpleReportExportConfiguration exportConfig = new SimpleReportExportConfiguration();
        	SimpleExporterConfiguration reportConfig = new SimpleExporterConfiguration();
        	
            JRXmlExporter exporter = new JRXmlExporter();
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);
            
            // export
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleXmlExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            //log.warn("wrong print HTML Exception:", e);
        }
    }    
    
    
    private void printCsv(OutputStream out, List<JasperPrint> jasperPrintList) {
        try {
        	SimpleCsvExporterConfiguration exportConfig = new SimpleCsvExporterConfiguration();
        	exportConfig.setRecordDelimiter("\r\n");
        	SimpleCsvReportConfiguration reportConfig = new SimpleCsvReportConfiguration();
        	
        	JRCsvExporter exporter = new JRCsvExporter();
            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);          
            
            // export
            if (jasperPrintList != null && jasperPrintList.size()>0) {
                if (jasperPrintList.size() > 1) {
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
                } else {
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrintList.get(0)));
                }
            }
            // output
            exporter.setExporterOutput(new SimpleWriterExporterOutput(out));
            exporter.exportReport();
            out.flush();
        } catch (JRException | IOException e) {
            e.printStackTrace();
            //log.warn("wrong print HTML Exception:", e);
        }
    }
    
}
