package src.util;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.poi.hssf.eventmodel.ERFListener;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.util.ResourceUtils;
import src.annotation.ExcelColumn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/*
 *@description: excel工具类
 *@author: tom.cui
 *@date: 2020/3/23 14:17
 */
public class ExcelUtil {


    public HSSFWorkbook exportExcel(List data, String sheetName, Class clas) throws IllegalAccessException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (StringUtil.isNullOrWhiteSpace(sheetName)) {
            sheetName = "sheet1";
        }

        //创建sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //获取源数据创建表头
        HSSFRow head = sheet.createRow(0);
        int headIndex = 0;
        for (Field field : clas.getDeclaredFields()) {

            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                String columnName = excelColumn.value();
                HSSFCell cell = head.createCell(headIndex);
                cell.setCellValue(columnName);
                headIndex++;
            }

        }
        //遍历集合数据创建表格
        int dataIndex = 1;
        Field[] fields = clas.getDeclaredFields();
        for (Object datum : data) {
            HSSFRow dataRow = sheet.createRow(dataIndex);
            int dataCellIndex = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    HSSFCell cell = dataRow.createCell(dataCellIndex);
                    field.setAccessible(true);
                    String value = "";
                    if (datum != null) {
                        Object obj = field.get(datum);
                        if (obj != null) {
                            value = obj.toString();
                        }

                    }
                    cell.setCellValue(value);
                    dataCellIndex++;
                }
            }
            dataIndex++;
        }
        return workbook;
    }

    /*
     *@author: tom.cui
     *@date: 2020/4/2
     *@description: 导入excel转化成指定集合
     */
    public List<Object> importExcel(HSSFWorkbook workbook, Class cla) throws IllegalAccessException, InstantiationException {
        //获取第一个sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取行数
        int rowsCount = sheet.getPhysicalNumberOfRows();
        //获取字段
        Field[] fields = cla.getDeclaredFields();
        //获取表头
        Map<Integer, String> headList = new HashMap<>();
        HSSFRow firstRow = sheet.getRow(0);
        for (int i = 0; i < firstRow.getLastCellNum(); i++) {
            HSSFCell cell = firstRow.getCell(i);
            cell.setCellType(CellType.STRING);
            headList.put(i, cell.getStringCellValue());
        }
        //便利集合
        for (int i = 1; i < rowsCount; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row != null) {
                Object object = cla.newInstance();
                //获取列数
                int cellCount = row.getLastCellNum();
                for (int j = 0; j < cellCount; j++) {
                    HSSFCell cell = row.getCell(j);
                    //遍历字段匹配值
                    getCellValue(fields, headList, cell, object);

                }


            }
        }
        return new ArrayList<>();


    }


    /*
     *@author: tom.cui
     *@date: 2020/4/2
     *@description: 获取cell返回值
     */
    private void getCellValue(Field[] fields, Map<Integer, String> headList, HSSFCell cell, Object obj) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                //获取列名
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                //遍历表头获取cell索引
                for (Map.Entry<Integer, String> integerStringEntry : headList.entrySet()) {
                    if (integerStringEntry.getValue().equals(excelColumn.value())) {
                        cell.setCellType(CellType.STRING);
                        String cellValue = cell.getStringCellValue();
                        //为对象赋值
                        field.setAccessible(true);
                        Object ObjValue=new Object();
                        if (Number.class.isAssignableFrom(field.getType())) {

                        }
                        if (String.class.isAssignableFrom(field.getType())) {
                            cell.setCellType(CellType.STRING);

                        }
                        field.set(obj, cellValue);
                        //类型转换

                    }
                }
            }
        }
    }


    /*
     *@author: tom.cui
     *@date: 2020/4/2
     *@description: 设置CellType
     */
    private CellType setCellType(HSSFCell cell, Field field) {
        if (Number.class.isAssignableFrom(field.getType())) {
            cell.setCellType(CellType.NUMERIC);
            return CellType.NUMERIC;
        }
        if (String.class.isAssignableFrom(field.getType())) {
            cell.setCellType(CellType.STRING);
            return CellType.NUMERIC;
        }
        return null;
    }


    public File convertWorkBookToFile(HSSFWorkbook workbook) throws IOException {
        String localDir = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath() + File.separator + "bufferFile";
        String fileName = UUID.randomUUID().toString() + ".xls";
        String localPath = localDir + File.separator + fileName;
        OutputStream os = new FileOutputStream(localPath);
        workbook.write(os);
        os.close();
        File file = new File(localPath);
        return file;
    }


}
