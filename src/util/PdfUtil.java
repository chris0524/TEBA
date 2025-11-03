package util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class PdfUtil {
	
	private PdfUtil() {}
	
    /**
     * 將A3格式切割成兩張A4格式
     * @param src
     * @param newFile
     * @return
     * @throws java.lang.Exception
     */
    public static java.io.File getDividePdf(File src, File newFile, boolean isVeritical) throws Exception {
        PdfReader reader = new PdfReader(src.getAbsolutePath());
        int n = reader.getNumberOfPages();
        Rectangle pageSize = reader.getPageSize(1);
        Rectangle newSize = new Rectangle(pageSize.getWidth() / 2, pageSize.getHeight());
        if (isVeritical) {
             newSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight() / 2);
        }
        Document document = new Document(newSize, 0, 0, 0, 0);
        PdfWriter out = PdfWriter.getInstance(document, new FileOutputStream(newFile));
        document.open();
        PdfContentByte cb = out.getDirectContent();
        int i = 0;
        float offsetX, offsetY;
        PdfImportedPage page;
        while (i < n) {
            document.newPage();
            i++;
            pageSize = reader.getPageSize(i);
            newSize = new Rectangle(pageSize.getWidth() / (isVeritical?1:2), pageSize.getHeight()/(isVeritical?2:1));
            document.newPage();
            offsetX = 0;
            offsetY = 0;
            page = out.getImportedPage(reader, i);
            cb.addTemplate(page, 1, 0, 0, 1, offsetX, offsetY);
            document.newPage();
            offsetX = isVeritical?0:-newSize.getWidth();
            offsetY = isVeritical?-newSize.getHeight():0;
            page = out.getImportedPage(reader, i);
            cb.addTemplate(page, 1, 0, 0, 1, offsetX, offsetY);
        }
        document.close();
        return newFile;
    }
    
    private static synchronized java.io.File writeImageToFolder(File srcPdf) throws Exception{
        PDDocument document = PDDocument.load( srcPdf );	
        java.io.File tempDirectory = Common.getTempDirectory();
		String tempDirID = Common.getTimeBasedUniqueId();
		tempDirectory = new java.io.File(tempDirectory,tempDirID);			
		tempDirectory.mkdirs();	
        PDFImageWriter imageWriter = new PDFImageWriter();
        imageWriter.writeImage(document, "jpg", null, 1, Integer.MAX_VALUE, tempDirectory.getAbsolutePath()+File.separator+"IMG_", BufferedImage.TYPE_INT_RGB, 96);
        document.close();
        return tempDirectory;
    }
    
    /**
     * 將pdf轉成圖片格式後再壓成一個zip檔
     * @param srcPdf
     * @return
     */
    public static java.io.File transPDFToImage(File srcPdf) {
    	java.io.File dir = null;
		try {
			dir = writeImageToFolder(srcPdf);
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
    
    
	
    /**
     * Convert a TIFF file to a PDF.
     * 
     * @param tiffFile
     * @return
     * @throws DocumentException
     * @throws MalformedURLException
     * @throws IOException
     */
    public static byte[] convertTiffToPdf(byte[] tiffFile) 
            throws DocumentException, MalformedURLException, IOException {
        ByteArrayOutputStream outfile = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, outfile);
        writer.setStrictImageSequence(true);
        document.open();
        Image tiff = Image.getInstance(tiffFile);
        //tiff.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth());  
        tiff.scaleAbsoluteHeight(PageSize.A4.getHeight());
        tiff.scaleAbsoluteWidth(PageSize.A4.getWidth());        
        document.add(tiff);
        document.close();
        outfile.flush();
        return outfile.toByteArray();
    }
    
//    public static byte[] convertBmpToPdf(byte[] bmpFile) 
//		    throws DocumentException, MalformedURLException, IOException {
//		ByteArrayOutputStream outfile = new ByteArrayOutputStream();
//		Document document = new Document(PageSize.A4, 0, 0, 0, 0);
//		PdfWriter writer = PdfWriter.getInstance(document, outfile);
//		writer.setStrictImageSequence(true);
//		document.open();		
//		Image tiff = BmpImage.getImage(bmpFile);		
//		//tiff.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth());  
//		tiff.scaleAbsoluteHeight(PageSize.A4.getHeight());
//		tiff.scaleAbsoluteWidth(PageSize.A4.getWidth());        
//		document.add(tiff);
//		document.close();
//		outfile.flush();
//		return outfile.toByteArray();
//    }    

    /**
     * Combine multiple PDFs into a single PDF.
     *
     * @param pdfs
     * @param combinedPdfFile
     * @throws IOException
     * @see http://itext.ugent.be/library/com/lowagie/examples/general/copystamp/Concatenate.java
     */
    public static void combinePdfFiles(List<byte[]> pdfs, File combinedPdfFile) throws Exception {
        PdfReader reader = null;
        Document document = null;
        PdfCopy  writer = null;
        List master = new ArrayList<Object>();
        int pageOffset = 0;

        for (byte[] pdf : pdfs) {
            reader = new PdfReader(pdf);
            reader.consolidateNamedDestinations();
            int n = reader.getNumberOfPages();
            List bookmarks = SimpleBookmark.getBookmark(reader);
            if (bookmarks != null) {
                if (pageOffset != 0) {
                    SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
                }
                master.addAll(bookmarks);
            }
            pageOffset += n;

            if (document == null) {
                // step 1: creation of a document-object
                document = new Document(reader.getPageSizeWithRotation(1));
                // step 2: we create a writer that listens to the document
                writer = new PdfCopy(document, new FileOutputStream(combinedPdfFile));
                // step 3: we open the document
                document.open();
            }
            // step 4: we add content
            PdfImportedPage page;
            for (int i = 0; i < n; ) {
                ++i;
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            PRAcroForm form = reader.getAcroForm();
            if (form != null) {
                writer.copyAcroForm(reader);
            }
        }
        if (!master.isEmpty()) {
            writer.setOutlines(master);
        }
        if (document != null) {
            document.close();
        }
    }
    
    /**
     * Combine multiple PDFs into a single PDF.
     */
    public static void combinePdfFiles(java.io.File[] pdfFiles, File combinedPdfFile) throws Exception {
    	if (pdfFiles!=null && pdfFiles.length>0) {
    		List<byte[]> pdfs = new java.util.ArrayList<byte[]>();
    		for (java.io.File f : pdfFiles) {
    			System.out.println(f.getName());
    			pdfs.add(FileUtils.readFileToByteArray(f));
    		}
    		combinePdfFiles(pdfs, combinedPdfFile);
    	}
    }   
    
    /**
     * Pdf 加密浮水印
     * @param srcPdf
     * @param waterMarkImage = 浮水印圖片
     * @return
     * @throws Exception
     */
    public static java.io.File addWaterMark(java.io.File srcPdf, java.io.File waterMarkImage) throws Exception {
    	return addWaterMark(srcPdf, waterMarkImage, null);
    }
    
    
    /**
     * Pdf 加密浮水印
     * @param srcPdf
     * @param waterMarkImage
     * @param destPdf
     * @return
     * @throws Exception
     */
	public static java.io.File addWaterMark(java.io.File srcPdf, java.io.File waterMarkImage, java.io.File destPdf) throws Exception {
		if (srcPdf!=null && srcPdf.isFile() && srcPdf.exists() && waterMarkImage!=null && waterMarkImage.isFile() && waterMarkImage.exists()) {
			PdfReader reader= null;
			PdfStamper pdfStamper= null;
			java.io.File resultPdf = destPdf==null?new java.io.File(Common.getTempDirectory(), Common.getTimeBasedUniqueId()+".pdf"):destPdf;
			try {
				reader= new PdfReader(srcPdf.getAbsolutePath());

				pdfStamper = new PdfStamper(reader, new FileOutputStream(resultPdf));
				Image wmImage = Image.getInstance(waterMarkImage.getAbsolutePath());
				//浮水印置中
				float pw = reader.getPageSize(1).getWidth();
				float ph = reader.getPageSize(1).getHeight();
				float ww = wmImage.getWidth();
				float wh = wmImage.getHeight();
				float percentX= 100f, percentY= 100f;
				if(pw < ww) percentX= (pw/ww)*100;
				if(ph < wh) percentY= (ph/wh)*100;
				if(percentX < percentY) {
					//正常PDF檔pw小於ph
					wmImage.scalePercent(percentX, percentY);
				} else if(percentX >= percentY) {
					//有些PDF檔pw大於ph，原因待查
					wmImage.scalePercent(percentY, percentX);
				}
				wmImage.setAbsolutePosition(0, 0);//從左下角算起
				if (reader.getPageRotation(1) == PdfPage.LANDSCAPE.intValue() ) {
					wmImage.setRotationDegrees(PdfPage.LANDSCAPE.intValue());					
				}
				PdfContentByte cb;
				int number_of_pages= reader.getNumberOfPages();
				for(int i=1; i<=number_of_pages; i++) {
					cb= pdfStamper.getOverContent(i);//從1算起
					cb.addImage(wmImage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try { if(null != reader) reader.close(); } catch (Exception e) {e.printStackTrace();}
				try { if(null != pdfStamper) pdfStamper.close(); } catch (Exception e) {e.printStackTrace();}
			}
			return resultPdf;
		}
		return null;
	}    
	
	public static byte[] addWatermarkAndPassword(java.io.File src, String watermarkText, String userPassword, String ownerPassword) throws IOException, DocumentException {
		return addWatermarkAndPassword((Object)src, watermarkText, userPassword, ownerPassword);
	}
	public static byte[] addWatermarkAndPassword(byte[] src, String watermarkText, String userPassword, String ownerPassword) throws IOException, DocumentException {
		return addWatermarkAndPassword((Object)src, watermarkText, userPassword, ownerPassword);
	}
	
    private static byte[] addWatermarkAndPassword(Object src, String watermarkText, String userPassword, String ownerPassword) throws IOException, DocumentException {
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (src instanceof File) {
            reader = new PdfReader(((File) src).getAbsolutePath());
        } else if (src instanceof byte[]) {
            reader = new PdfReader(new java.io.ByteArrayInputStream((byte[]) src));
        } else {
            throw new IllegalArgumentException("Source must be a File or byte[]");
        }

        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        // Set encryption
        stamper.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);

        
        // Font settings for watermark
        if (watermarkText!=null && watermarkText.length()>0) {
            //Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new java.awt.Color(200, 200, 200));
            PdfContentByte content;

            // Loop over every page and add a watermark text
            for (int i = 1; i <= n; i++) {
                content = stamper.getUnderContent(i);
                content.beginText();
                BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
                content.setFontAndSize(baseFont, 30);
                content.setColorFill(new java.awt.Color(200, 200, 200));
                content.showTextAligned(Element.ALIGN_CENTER, watermarkText, 297.5f, 421, 45);
                content.endText();
            }
        }
        stamper.close();
        reader.close();

        return outputStream.toByteArray();
    }
	
	
}    