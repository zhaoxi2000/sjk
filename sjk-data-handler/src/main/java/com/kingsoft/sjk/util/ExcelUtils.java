/*
 * Excel工具类
 */
package com.kingsoft.sjk.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 从Excel文件得到二维数组，每个sheet的第一行为标题
     * 
     * @param file
     *            Excel文件
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getData(File file) throws FileNotFoundException, IOException {
        return getData(file, 1);
    }

    /**
     * 从Excel文件得到二维数组
     * 
     * @param file
     *            Excel文件
     * @param ignoreRows
     *            忽略的行数，通常为每个sheet的标题行数
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {
        ArrayList result = new ArrayList();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(fs);
        } catch (Exception e) {
            logger.error("error.ExcelData");
            // throw new Exception("error.ExcelData");
        }
        HSSFCell cell = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringBuilder value = new StringBuilder();
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    value.setLength(0);
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码

                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                value.append(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value.append(dateFormat.format(date));
                                    }
                                } else {
                                    String end = decimalFormat.format(cell.getNumericCellValue()).substring(
                                            decimalFormat.format(cell.getNumericCellValue()).indexOf(".") + 1);
                                    if ("00".equals(end)) {
                                        value.append(decimalFormat.format(cell.getNumericCellValue()).substring(0,
                                                decimalFormat.format(cell.getNumericCellValue()).indexOf(".")));
                                    } else {
                                        value.append(decimalFormat.format(cell.getNumericCellValue()));
                                    }
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (cell.getStringCellValue().equals("")) {
                                    value.append(cell.getNumericCellValue());
                                } else {
                                    value.append(cell.getStringCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value.append((cell.getBooleanCellValue() == true ? "Y" : "N"));
                                break;
                            default:
                                value.setLength(0);
                        }
                    }
                    String s = value.toString().trim();
                    if (columnIndex == 0 && s.equals("")) {// 若第一列为空，则向后判断5列
                                                           // 若都为空则不导入该行数据
                        if (row.getCell(new Short("1").shortValue()) == null
                                && row.getCell(new Short("2").shortValue()) == null
                                && row.getCell(new Short("3").shortValue()) == null
                                && row.getCell(new Short("4").shortValue()) == null
                                && row.getCell(new Short("5").shortValue()) == null) {
                            break;
                        }
                    }
                    values[columnIndex] = StringUtils.trim(value.toString());
                    hasValue = true;

                    // 到每行的第五列的时候开始判断前5列是否都为空,若都为空则不导入该行数据 (该方法需要与以上方法同时使用)
                    if (columnIndex == 4) {
                        if ("".equals(values[0]) && "".equals(values[1]) && "".equals(values[2])
                                && "".equals(values[3]) && "".equals(values[4])) {
                            hasValue = false;
                            break;
                        }
                    }
                }

                if (hasValue) {
                    result.add(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    /**
     * 解析一个Excel文件的某个特定的sheet sheet号码从1开始
     * 
     * @param file
     *            excel文件
     * @param ignoreRows
     *            忽略的行数
     * @param index
     *            sheet的页码
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getData(File file, int ignoreRows, int index) throws FileNotFoundException, IOException {
        ArrayList result = new ArrayList();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        HSSFSheet st = wb.getSheetAt(index - 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        // 第一行为标题，不取
        for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
            HSSFRow row = st.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            int tempRowSize = row.getLastCellNum() + 1;
            if (tempRowSize > rowSize) {
                rowSize = tempRowSize;
            }
            String[] values = new String[rowSize];
            Arrays.fill(values, "");
            boolean hasValue = false;
            StringBuilder value = new StringBuilder();
            for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                value.setLength(0);
                cell = row.getCell(columnIndex);
                if (cell != null) {
                    // 注意：一定要设成这个，否则可能会出现乱码
                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            value.append(cell.getStringCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                if (date != null) {
                                    value.append(dateFormat.format(date));
                                }
                            } else {
                                value.append(decimalFormat.format(cell.getNumericCellValue()));
                            }
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            // 导入时如果为公式生成的数据则无值
                            if (cell.getStringCellValue().equals("")) {
                                value.append(cell.getNumericCellValue());
                            } else {
                                value.append(cell.getStringCellValue());
                            }
                            break;
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        case HSSFCell.CELL_TYPE_ERROR:
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            value.append(cell.getBooleanCellValue() == true ? "Y" : "N");
                            break;
                        default:
                            value.setLength(0);
                    }
                }
                if (columnIndex == 0 && value.toString().trim().equals("")) {
                    break;
                }
                values[columnIndex] = StringUtils.trim(value.toString());
                hasValue = true;
            }

            if (hasValue) {
                result.add(values);
            }
        }

        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    /**
     * @param file
     * @param ignoreRows
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getDataForClaim(File file, int ignoreRows) throws FileNotFoundException, IOException {
        ArrayList result = new ArrayList();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                StringBuilder value = new StringBuilder();
                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    value.setLength(0);
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码
                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                value.append(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value.append(dateFormat.format(date));
                                    }
                                } else {
                                    value.append(decimalFormat.format(cell.getNumericCellValue()));
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (cell.getStringCellValue().equals("")) {
                                    value.append(cell.getNumericCellValue());
                                } else {
                                    value.append(cell.getStringCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value.append((cell.getBooleanCellValue() == true ? "Y" : "N"));
                                break;
                            default:
                                value.setLength(0);
                        }
                    }
                    if (columnIndex == 0 && value.toString().trim().equals("")) {
                        break;
                    }
                    values[columnIndex] = StringUtils.trim(value.toString());
                    hasValue = true;
                }

                if (hasValue) {
                    result.add(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    /**
     * 从Excel文件得到二维数组,和getDataForMotor的不同在于对数值型数据的处理上本方法默认格式为0.000 用于深圳车型导入功能
     * 
     * @param file
     *            Excel文件
     * @param ignoreRows
     *            忽略的行数，通常为每个sheet的标题行数
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getDataForMotor(File file, int ignoreRows) throws FileNotFoundException, IOException {
        ArrayList result = new ArrayList();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(fs);
        } catch (Exception e) {
            logger.error("error.ExcelData:", e);
            // throw new BusinessException("error.ExcelData", true);
        }
        HSSFCell cell = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        StringBuffer value = new StringBuffer();
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    value.setLength(0);
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码
                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                value.append(cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value.append(dateFormat.format(date));
                                    }
                                } else {
                                    value.append(new Double(cell.getNumericCellValue()));
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (cell.getStringCellValue().equals("")) {
                                    value.append(cell.getNumericCellValue());
                                } else {
                                    value.append(cell.getStringCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value.append((cell.getBooleanCellValue() == true ? "Y" : "N"));
                                break;
                            default:
                                value.setLength(0);
                        }
                    }
                    String s = value.toString().trim();
                    if (columnIndex == 0 && s.equals("")) {// 若第一列为空，则看第3列数据（车型代码）是否为空,为空则不导入该行数据
                        if (row.getCell(new Short("2").shortValue()) == null) {
                            break;
                        }
                    }
                    values[columnIndex] = StringUtils.trim(value.toString());
                    hasValue = true;
                }

                if (hasValue) {
                    result.add(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }
}
