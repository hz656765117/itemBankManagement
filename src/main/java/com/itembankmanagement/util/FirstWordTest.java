package com.itembankmanagement.util;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FirstWordTest {
    
        //模板文件地址
        private static String inputUrl = "d:\\test.docx";
        //新生产的模板文件
        private static String outputUrl = "d:\\test2.docx";
        
        /**
         * 
         * @param inputUrl 模板路径
         * @param outputUrl 模板保存路径
         */
        public static void changeWord(String inputUrl, String outputUrl ){
            
            try {
                //获取word文档解析对象
                XWPFDocument doucument = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
                //获取段落文本对象
                List<XWPFParagraph> paragraph = doucument.getParagraphs();
                //获取首行run对象
                XWPFRun run = paragraph.get(0).getRuns().get(0);
                //设置文本内容
                run.setText("修改了的word");
                //生成新的word
                File file = new File(outputUrl);
                
                FileOutputStream stream = new FileOutputStream(file);
                doucument.write(stream);
                stream.close();
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM");
            String date=sim.format(new Date());
            System.out.println(date);
            String year=date.substring(0,4);
            String month=date.substring(5,7);
            System.out.println(year);
            System.out.println(month);
        }
}
