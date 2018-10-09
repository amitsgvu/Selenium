/**
 * 
 */
package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author amisharm25
 *
 */
public class WriteExecl {

	public static void writetoexcel(String excelPath, Map<Integer, List<String>> map) throws Exception {
		// TODO Auto-generated method stub
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook()

		// Create a Sheet
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Product");

		Set<Integer> set = map.keySet();
		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("New Price");
		headerRow.createCell(1).setCellValue("Old Price");
		headerRow.createCell(2).setCellValue("Discount");
		int rowNumber = 1;
		// System.out.println("key size is" + set.size());

		for (Integer i : set) {
			System.out.println("inside loop");
			Row row = sheet.createRow(rowNumber);
			List<String> list = map.get(i);
			int columnvalue = 0;
			for (String str : list) {
				// System.out.println("inside other loop");

				row.createCell(columnvalue).setCellValue(str);
				// System.out.println("=======excel value" + str);
				columnvalue++;

			}
			rowNumber++;

		}
		FileOutputStream fileOut = new FileOutputStream(excelPath);
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}
	
	
}
