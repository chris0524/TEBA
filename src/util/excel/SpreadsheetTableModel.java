/*********************************************************************/
/*(c) Copyright IBM Corp. 2004  All rights reserved.                 */
/*                                                                   */
/*This sample program is owned by International Business Machines    */
/*Corporation or one of its subsidiaries ("IBM") and is copyrighted  */
/*and licensed, not sold.                                            */
/*                                                                   */
/*You may copy, modify, and distribute this sample program in any    */
/*form without payment to IBM,  for any purpose including developing,*/
/*using, marketing or distributing programs that include or are      */
/*derivative works of the sample program.                            */
/*                                                                   */
/*The sample program is provided to you on an "AS IS" basis, without */
/*warranty of any kind.  IBM HEREBY  EXPRESSLY DISCLAIMS ALL         */
/*WARRANTIES EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO*/
/*THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTIC-*/
/*ULAR PURPOSE. Some jurisdictions do not allow for the exclusion or */
/*limitation of implied warranties, so the above limitations or      */
/*exclusions may not apply to you.  IBM shall not be liable for any  */
/*damages you suffer as a result of using, modifying or distributing */
/*the sample program or its derivatives.                             */
/*                                                                   */
/*Each copy of any portion of this sample program or any derivative  */
/*work,  must include a the above copyright notice and disclaimer of */
/*warranty.                                                          */
/*                                                                   */
/*********************************************************************/

package util.excel;
 
/*********************************************************************/
/* Stores the contents of a database table                           */
/*********************************************************************/
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.List;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

/**
* Implements the TableModel interface so that a JTable can display the contents of ResultSet.
**/
public class SpreadsheetTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* number of rows in the ResultSet
	*/
	private int rowCount;

	/**
	* number of columns in ResultSet
	*/
	private int colCount;

	/**
	* The names of the columns in the ResultSet
	*/
	private List columnNames = new ArrayList();

	/**
	*  The class types of the columns being displayed.
	*/
	private List columnTypes = new ArrayList();

	/**
	*  Transcribes the data in the ResultSet into a List of lists
	*/
	private List result = new ArrayList();

	/**
	* Name of the spreadsheet table being displayed
	*/
	private String tableName;

	/**
	 * @see java.lang.Object#Object()
	 */
	public SpreadsheetTableModel() {
    }

// ----------------------------------------------------------------------------------------- //
// methods of javax.swing.table.TableModel interface
// ----------------------------------------------------------------------------------------- //
	/**
	* Method getRowCount.
	* @see javax.swing.table.TableModel#getRowCount()
	*/
	public int getRowCount() {
		return rowCount;
	}

	/**
	* Method getColumnCount.
	* @see javax.swing.table.TableModel#getColumnCount()
	*/
	public int getColumnCount() {
		if(rowCount > 0) {
			return colCount+1;
		} else {
			return 0;
		}
	}

	/**
	* Method getValueAt.
	* @see javax.swing.table.TableModel#getValueAt(int, int)
	*/
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return new Integer(rowIndex);
		} else {
			// We subtract one from the columnIndex since the first column is used for row numbers
            return ((ArrayList) result.get(rowIndex)).get(columnIndex-1);
		}
	}

	/**
	* Method getColumnName.
	* @see javax.swing.table.TableModel#getColumnName(int)
	*/
	public String getColumnName(int colIndex) {
		if(colIndex == 0) {
			// The first column is used for the row numbers, with a column name of "Row"
			return "Row";
		} else {
			// We subtract one from the columnIndex since the first column is used for row numbers
			return (String) columnNames.get(colIndex-1);
		}
	}

	/**
	* Method getColumnNames.
	* returns the list of column names
	*/
	public List getColumnNames() {
		return columnNames;
	}

	/**
	* Method getColumnTypes.
	* returns the list of column names
	*/
	public List getColumnTypes() {
		return columnTypes;
	}

	/**
	* Method getSpreadsheetName.
	* returns the name of the spreadsheet
	*/
	public String getSpreadsheetName() {
		return tableName;
	}

	/**
	* Method reloadSpreadsheetModelAlternate.
	* Reloads the TableModel with the contents of specified fileName.
	* This alternative version uses the iterators provided by POI/HSSF to demonstrate additional features.  It cannot guarantee correct column ordering.
	* @param tableName table with which to reload the TableModel
	* @exception SQLException if a database error occurs
	* @exception ClassNotFoundException
	*/
	public void reloadSpreadsheetModelAlternate(String fileName) throws IOException {
		// Use POI to read the selected Excel Spreadsheet
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
		// Extract the name of the first worksheet and use this for the tableName
		tableName =  wb.getSheetName(0);
		// Select the first worksheet
		HSSFSheet sheet = wb.getSheet(tableName); 		    
		try {
			clearAll();
			updateColumnModelAlternate(fileName);
			// Use the iterators provided by POI to step through the rows and columns
			Iterator rowiter = sheet.rowIterator();
			// Skip the first row, the column names are extracted from this row.
			if (rowiter.hasNext()) {
				HSSFRow row = (HSSFRow)(rowiter.next());
			}
//			for(row = (HSSFRow)(rowiter.next()); row != null; row = rowiter.hasNext() ? (HSSFRow)(rowiter.next()) : null) {
			while (rowiter.hasNext()) {
				HSSFRow row=(HSSFRow)(rowiter.next()); 
				// Store the row in a list
				ArrayList list = new ArrayList();
				Iterator celliter = row.cellIterator();
//				for(HSSFCell cell = (HSSFCell)(celliter.next()); cell != null; cell = celliter.hasNext() ? (HSSFCell)(celliter.next()) : null) {
				while (celliter.hasNext()) {
					Cell cell=(Cell)(celliter.next());
					// Add each cell to the row
					list.add(cell);
				}
				// Store the row in a list of lists
				result.add(list);
			}
			// Use the HFFS functions for the number of rows & columns, instead of computing them ourselves
			rowCount = sheet.getPhysicalNumberOfRows()-1;
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			fireTableStructureChanged();
		// Catch all Exceptions, most likely a POI error
		} catch (Exception e) {
			System.out.println("A POI error has occured.");
			e.printStackTrace();
		}
    }

	/**
	* Method reloadSpreadsheetModel.
	* Reloads the TableModel with the contents of specified fileName.
	* @param tableName table with which to reload the TableModel
	* @exception SQLException if a database error occurs
	* @exception ClassNotFoundException
	*/
	public void reloadSpreadsheetModel(String fileName) throws IOException {
		// Use POI to read the selected Excel Spreadsheet
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
		// Extract the name of the first worksheet and use this for the tableName
		tableName =  wb.getSheetName(0);
		// Select the first worksheet
		HSSFSheet sheet = wb.getSheet(tableName); 		    
		try {
			clearAll();
//			System.out.println("Updating model...");
			updateColumnModel(fileName);
			// Use the HFFS functions for the number of rows & columns, instead of computing them ourselves
//			System.out.println("Getting Spreadsheet Dimensions...");
			rowCount = sheet.getPhysicalNumberOfRows();
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
//			System.out.println("Number of rows ==" + rowCount);
//			System.out.println("Number of cols ==" + colCount);
			// Skip the first row, the column names are extracted from this row.
			for(int i = 1; i < rowCount; i++) {
				// Get row number i
//				System.out.println("Getting row  " + i);
				HSSFRow row = sheet.getRow(i); 
				// Store the row in a list
				ArrayList list = new ArrayList();
				for(short j = 0; j <colCount; j++) {
					// Add each cell to the row
//					System.out.println("Getting cell " + j);
					list.add(row.getCell(j));
				}
				// Store the row in a list of lists
				result.add(list);
			}
			// Remove one row from the rowCount, since the first row is assumed to be the column names 
			rowCount--;
//			System.out.println("Done");
			fireTableStructureChanged();
		// Catch all Exceptions, most likely a POI error
		} catch (Exception e) {
			System.out.println("A POI error has occured.");
			e.printStackTrace();
		}
    }

	/**
	* Method updateColumnModelAlternate.
	* Extracts column metadata from the specified fileName.
	* This alternative version uses the iterators provided by POI/HSSF to demonstrate additional features.  It cannot guarantee correct column ordering.
	* @param fileName
	* @throws IOException
	*/
	private void updateColumnModelAlternate(String fileName) throws IOException {
		// Use POI to read the selected Excel Spreadsheet
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
		// Extract the name of the first worksheet and use this for the tableName
		String tableName =  wb.getSheetName(0);
		// Select the first worksheet
		HSSFSheet sheet = wb.getSheet(tableName);
		// Extract column names from the first row
		HSSFRow row = sheet.getRow(0);
		// Use the iterators provided by POI to step through the rows and columns
		Iterator celliter = row.cellIterator();
		try {
//			for(HSSFCell cell = (HSSFCell)(celliter.next()); cell != null; cell = celliter.hasNext() ? (HSSFCell)(celliter.next()) : null) {
			while(celliter.hasNext()) {
			  Cell cell= (Cell)(celliter.next());
				// Get the Column names from each cell
				columnNames.add(cell.getStringCellValue());
			}
			// Extract column types from first non-empty row
			// Set a flag when we have found a non-empty row
			boolean found = false;
			// Use the iterators provided by POI to step through the rows and columns
			Iterator rowiter=sheet.rowIterator();
			// Skip the first row, the column names are extracted from this row.
			if (rowiter.hasNext()) {
				row = (HSSFRow)(rowiter.next());
			}
			
//			for(row = (HSSFRow)(rowiter.next()); row != null && !found; row = rowiter.hasNext() ? (HSSFRow)(rowiter.next()) : null) {
			while (rowiter.hasNext()) {
				row = (HSSFRow)(rowiter.next());
				ArrayList list = new ArrayList();
				// To check if the row is blank, inspect the first column only
				Cell firstCell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (firstCell != null && firstCell.getCellType() != CellType.BLANK) {
					found=true;
					celliter = row.cellIterator();
//					for(HSSFCell cell = (HSSFCell)(celliter.next()); cell != null; cell = celliter.hasNext() ? (HSSFCell)(celliter.next()) : null) {
					while (celliter.hasNext()) {
						Cell cell=(Cell)(celliter.next());
						//columnTypes.add(new Integer(cell.getCellType()));
						columnTypes.add(cell.getCellType().ordinal());
					}
				}
			}
		// Catch all Exceptions, most likely a POI error
		} catch (Exception e) {
			System.out.println("A POI error has occured.");
			e.printStackTrace();
		}
	}

	/**
	* Method updateColumnModel.
	* Extracts column metadata from the specified fileName.
	* @param fileName
	* @throws IOException
	*/
	private void updateColumnModel(String fileName) throws IOException {
		// Use POI to read the selected Excel Spreadsheet
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(fileName));
		// Extract the name of the first worksheet and use this for the tableName
		String tableName =  wb.getSheetName(0);
		// Select the first worksheet
		HSSFSheet sheet = wb.getSheet(tableName);
		// Extract column names from the first row
		HSSFRow row = sheet.getRow(0);
		try {
//			System.out.println("Updating model...");
			for(short j = 0; j < row.getPhysicalNumberOfCells(); j++) {
//			System.out.println("Getting column name " + j + row.getCell(j).getStringCellValue());
				// Get the Column names from each cell
				columnNames.add(row.getCell(j).getStringCellValue());
			}
			// Extract column types from first non-empty row
			// Set a flag when we have found a non-empty row
//			System.out.println("Setting Flag");
			boolean found = false;
			// Skip the first row, the column names are extracted from this row.
			for(int i = 1; (i < sheet.getPhysicalNumberOfRows()) && !found; i++) {
				// Get row number i 
//				System.out.println("Getting row " + i);
				row = sheet.getRow(i);
				ArrayList list = new ArrayList();
				// To check if the row is blank, inspect the first column only
	            Cell firstCell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            if (firstCell != null && firstCell.getCellType() != CellType.BLANK) {
//					System.out.println("Non-empty row found");
					found=true;
					for(int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
//						System.out.println("Getting cell " + j);
						//columnTypes.add(new Integer(row.getCell(j).getCellType()));
						columnTypes.add(row.getCell(j).getCellType());
					}
				}
			}
		// Catch all Exceptions, most likely a POI error
		} catch (Exception e) {
			System.out.println("A POI error has occured.");
			e.printStackTrace();
		}
	}

	/**
	* Method clearAll.
	* clears the TableModel
	*/
    private void clearAll() {
    	// Clear each List representing each row
		for (int i = 0; i < result.size(); i++) {
			ArrayList list = (ArrayList) result.get(i);
			list.clear();
		}
		// Clear all rows
		result.clear();
		// Clear Metadata
		columnNames.clear();
		columnTypes.clear();
		// Clear the row and column counts
		colCount = 0;
		rowCount = 0;
	}

	/**
	* Method getRow.
	* @param i
	* @return List
	*/
	public List getRow(int i) {
		return (List)(result.get(i));
	}
}