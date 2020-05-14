package com.example.questem.service.impl;

import com.example.questem.dao.QuestionOptionMapper;
import com.example.questem.dao.QuestionTemMapper;
import com.example.questem.entity.QuestionOption;
import com.example.questem.entity.QuestionTem;
import com.example.questem.service.QuestionService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.*;
import java.util.Date;

/**
 * @author Linmo
 * @create 2020/5/13 19:25
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    private Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Resource
    private QuestionOptionMapper questionOptionMapper;
    @Resource
    private QuestionTemMapper questionTemMapper;

    @Override
    @Async
    @Transactional
    public void questionImport(MultipartFile[] multipartFile) {
        if (multipartFile == null || multipartFile.length == 0) return;
        Workbook workBook = null;
        for (MultipartFile file : multipartFile) {
            try {
                workBook = getWorkBook(file);
                Assert.notNull(workBook, "读取不了的文件:" + file.getOriginalFilename());
                Sheet sheetAt = workBook.getSheetAt(0);
                readSheetImpotQuestion(sheetAt);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new IllegalStateException("导入题库出错", e);
            } finally {
                if (workBook != null) {
                    try {
                        workBook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    workBook = null;
                }
            }
        }
    }
    /**
     * xls文件格式
     * 题干   选项A     选项B     选项C..   答案(多个用|分割)
     */
    private void readSheetImpotQuestion(Sheet sheetAt) {
        int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
        for (int i = 0; i < physicalNumberOfRows; i++) {
            Row row = sheetAt.getRow(i);
            String title = row.getCell(0).getStringCellValue();
            int answerIndex = row.getPhysicalNumberOfCells() - 1;
            String answer = row.getCell(answerIndex).getStringCellValue();
            Long quesTemId = importQesTem(title, answer);
            importQuesOptions(quesTemId, 1, answerIndex - 1, row);
        }
        if (logger.isInfoEnabled())
            logger.info("导入{}道题", physicalNumberOfRows);
    }

    /**
     * 导入选项
     *
     * @param quesTemId
     * @param start
     * @param end
     * @param row
     */
    private void importQuesOptions(Long quesTemId, int start, int end, Row row) {
        for (int i = start; i <= end; i++) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuesId(quesTemId);
            questionOption.setLabel(Character.toString((char) (64 + i)));
            Cell cell = row.getCell(i);
            questionOption.setOptions(getCellContent(cell.getCellType(), cell));
            questionOptionMapper.insertSelective(questionOption);
        }
    }

    private String getCellContent(CellType cellType, Cell cell) {
        switch (cellType){
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BLANK:
                return " ";
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                logger.warn("未能识别的格子类型:{}",cellType);
                return "NULL";
        }
    }

    /**
     * 导入题干、答案
     *
     * @param title
     * @param answer
     * @return
     */
    private Long importQesTem(String title, String answer) {
        QuestionTem questionTem = new QuestionTem();
        questionTem.setAnswer(answer);
        questionTem.setTitle(title);
        questionTem.setLastTime(new Date());
        questionTemMapper.insertSelective(questionTem);
        return questionTem.getId();
    }

    private Workbook getWorkBook(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        int index = 0;
        if ((index = originalFilename.lastIndexOf('.')) == -1) return null;
        String ext = originalFilename.substring(index + 1);
        Workbook workbook = null;
        if (ext.equalsIgnoreCase("xls")) {
            if (logger.isInfoEnabled())
                logger.info("read file {}", originalFilename);
            workbook = new HSSFWorkbook(multipartFile.getInputStream());
        }else if(ext.equalsIgnoreCase("xlsx")){
            if (logger.isInfoEnabled())
                logger.info("read file {}", originalFilename);
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
        }
        return workbook;
    }

    private XSSFWorkbook getWorkBookFromXslx(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (logger.isInfoEnabled())
            logger.info("read file {}", originalFilename);
        return new XSSFWorkbook(multipartFile.getInputStream());
    }
}
