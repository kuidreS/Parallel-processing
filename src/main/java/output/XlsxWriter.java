package output;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by vserdiuk
 */

public class XlsxWriter implements Writer {

    final static Logger logger = Logger.getLogger(Util.class);

    @Override
    public void saveToFile(Map<Element, Long> linkOccurrenceMap, String fileName) {
        XSSFWorkbook workbook = createWorkbook(linkOccurrenceMap);
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            logger.info("The links occurrence statistics have been saved in a file " + fileName);
            workbook.close();
        } catch (IOException e) {
            logger.error("An error with saving an XLSX file: " + e.getMessage());
        }
    }

    private XSSFWorkbook createWorkbook(Map<Element, Long> linkUsageMap) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Links");
        createHeaderRow(sheet);
        setCellValues(sheet, linkUsageMap);
        logger.debug("The workbook has been prepared");
        return workbook;
    }

    private void createHeaderRow(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Page");
        headerRow.createCell(1).setCellValue("Count");
    }

    private void setCellValues(XSSFSheet sheet, Map<Element, Long> linkUsageMap) {
        int i = 0;
        Iterator<Map.Entry<Element, Long>> iterator = linkUsageMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(iterator.next().getKey().attr("abs:href"));
            row.createCell(1).setCellValue(iterator.next().getValue());
            i++;
        }
    }
}
