package com.decipher.agriculture.service.farm.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class UploadExcelServiceImpl{


    ArrayList stateName = new ArrayList();
    ArrayList stateLink = new ArrayList();

    public void saveStateNameAndLink() {

        try {
            File file = ResourceUtils.getFile("classpath:Links for State Farm Data.xlsx");
            //File file = ResourceUtils.getFile("classpath:Links for State Farm Data.xlsx");
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(String.valueOf(file.toPath()));

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    if (cell.getCellType() == cell.CELL_TYPE_BLANK){
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            break;
                        case Cell.CELL_TYPE_STRING:
                            String state = cell.getStringCellValue();
                            if(state.length() == 2){
                                stateName.add(state);
                            }else {
                                stateLink.add(state);
                            }
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList getStateName() {
       return stateName;
    }
    public ArrayList getStateLink() {
         return stateLink;
    }
}
