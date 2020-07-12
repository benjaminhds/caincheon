package kr.caincheon.church.common.poi;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 엑셀 파일 벌크 로더
 * @author benjamin
 * @since  2017.11.28
 */
public class ExcelBulkLoader {

	/*
	 * XLS(엑셀97-2003)를 읽은 후 row & columns 을 일괄 로드하여 List<Map> 으로 리턴한다.
	 */
	public static List<Map> loadXls(String xlsxFileName, int sheetNo) throws Exception {
		// 파일을 읽기위해 엑셀파일을 가져온다 : xxxx.xls
		File f = new File(xlsxFileName);
		if (!f.exists()) {
			throw new Exception("The file not found. (" + xlsxFileName + ")");
		}
		//
		FileInputStream fis = new FileInputStream(f);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		int rowindex = 0;
		int columnindex = 0;
		// 시트 수 (첫번째에만 존재하므로 0을 준다)
		// 만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		HSSFSheet sheet = workbook.getSheetAt(sheetNo);
		// 행의 수
		int rows = sheet.getPhysicalNumberOfRows();
		// 행 구분, sheetNo is from 0.
		List<Map> rtXlsxRows = new ArrayList<Map>();
		for (rowindex = 1; rowindex < rows; rowindex++) {
			//
			Map<String, String> cellMap = new LinkedHashMap<String, String>();
			int iCellNo = 0;
			String sCell = "";
			//
			HSSFRow row = sheet.getRow(rowindex);
			if (row != null) {
				// 셀(열, column)의 수
				int cells = row.getPhysicalNumberOfCells();
				for (columnindex = 0; columnindex <= cells; columnindex++) {
					// 셀값을 읽는다
					HSSFCell cell = row.getCell(columnindex);
					// 셀이 빈값일경우를 위한 널체크
					if (cell == null) {
						continue;
					} else {
						// 타입별로 내용 읽기
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA:
							sCell = cell.getCellFormula();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							sCell = cell.getNumericCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_STRING:
							sCell = cell.getStringCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							sCell = String.valueOf(cell.getBooleanCellValue());
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							sCell = String.valueOf(cell.getErrorCellValue());
							break;
						}
					}
					cellMap.put("" + iCellNo++, sCell);
				}
				// 한 행이 읽혀 지면 목록에 추가
				rtXlsxRows.add(cellMap);
			}
		}

		return rtXlsxRows;
	}

	/*
	 * XLSX를 읽은 후 row & columns 을 일괄 로드하여 List<Map> 으로 리턴한다.
	 */
	public static List<Map> loadXlsx(String xlsxFileName, int sheetNo) throws Exception {
		// 파일을 읽기위해 엑셀파일을 가져온다. :: xxxx.xlsx
		File f = new File(xlsxFileName);
		if (!f.exists()) {
			throw new Exception("The file not found. (" + xlsxFileName + ")");
		}
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		//
		List<Map> rtXlsxRows = new ArrayList<Map>();

		//
		boolean isNull = false;
		// 행 구분, sheetNo is from 0.
		for (Row row : workbook.getSheetAt(sheetNo)) {
			Map<String, String> cellMap = new LinkedHashMap<String, String>();
			int iCellNo = 0;
			String sCell = "";
			// 열
			for (Cell cell : row) {
				isNull = false;

				// 셀의 타입 따라 받아서 구분지어 받되 한 행을 하나의 스트링으로 저장
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					sCell = cell.getRichStringCellValue().getString();
					break;

				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell))
						sCell = cell.getDateCellValue().toString();
					else
						sCell = Integer.toString((int) cell.getNumericCellValue());
					break;

				case Cell.CELL_TYPE_BOOLEAN:
					sCell = String.valueOf(cell.getBooleanCellValue());
					break;

				case Cell.CELL_TYPE_FORMULA:
					sCell = cell.getCellFormula();
					break;

				default: // 값이 없는 열은 포함되지 않게 함.
					isNull = true;
				}

				if (isNull != true) {
					cellMap.put("" + iCellNo, sCell);
				}
				iCellNo++;
			}
			// 한 행이 읽혀 지면 목록에 추가
			rtXlsxRows.add(cellMap);
			// System.out.println( "line ["+rtXlsxRows.size()+"] >> " +
			// cells.toString() );
		}

		return rtXlsxRows;
	}
}
