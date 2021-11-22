/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;

public class UsageFileGenerator {
    public static final String USAGE_FILE = "UF.xlsx";

    public synchronized static String getNewUsageFileLocation() throws IOException {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        long time = System.currentTimeMillis();
        String baseName = time + ".xlsx";

        ClassLoader classLoader = UsageFileGenerator.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(USAGE_FILE);

        XSSFWorkbook workbook = new XSSFWorkbook(Objects.requireNonNull(is));
        XSSFSheet sheet = workbook.getSheet("records");

        XSSFWorkbook newWorkbook = new XSSFWorkbook();
        XSSFSheet newSheet = newWorkbook.createSheet("records");

        for (int count = 0; count < 6; count++) {
            XSSFRow sheetRow = sheet.getRow(count);
            XSSFRow newSheetRow = newSheet.createRow(count);
            int cellCount = 0;
            for (Iterator<Cell> it = sheetRow.cellIterator(); it.hasNext(); ) {
                Cell cell = it.next();
                XSSFCell newCell = newSheetRow.createCell(cellCount);
                if (cellCount++ == 0 && count != 0) {
                    newCell.setCellValue(String.valueOf(time + count));
                } else {
                    newCell.setCellValue(cell.getStringCellValue());
                }
            }
        }

        File outputFile = new File(baseDir, baseName);

        FileOutputStream outFileSt =new FileOutputStream(outputFile);
        newWorkbook.write(outFileSt);
        workbook.close();
        outFileSt.close();
        is.close();

        return outputFile.getAbsolutePath();
    }
}
