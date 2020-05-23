package com.itembankmanagement.service.serviceImpl;

import com.itembankmanagement.dao.*;
import com.itembankmanagement.model.TitleTypeVO;
import com.itembankmanagement.model.TitleVO;
import com.itembankmanagement.service.TitleService;
import com.itembankmanagement.util.Result;
import com.itembankmanagement.util.WorderToNewWordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@Transactional
public class TitleServiceImpl implements TitleService {



    @Autowired
    TitleRepository titleRepository;
    @Autowired
    TitleTypeRspository titleTypeRspository;
    @Autowired
    ChapterRspository chapterRspository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    WorderToNewWordUtils worderToNewWordUtils;

    String inputUrl = "src/main/java/com/itembankmanagement/testPaper/test.docx";


    @Override
    public Result add(String content, String answer, Long typeId, Long chapterId) {
        System.out.println(content+answer+typeId+chapterId);
        if(content==null||content.equals("")){
            return Result.error(3060,"为输入题目内容");
        }
        if (answer==null||answer.equals("")){
            return Result.error(3060,"未输入答案");
        }
        if(typeId==null){
            return Result.error(3060,"为输入体型");
        }
        if (chapterId==null){
            return Result.error(3060,"为输入所属章节");
        }
        TitleType titleType=titleTypeRspository.findById(typeId).get();
        Chapter chapter=chapterRspository.findById(chapterId).get();
        Title title=new Title();
        title.setContent(content);
        title.setAnswer(answer);
        //title.setTypeId(typeId);
        title.setSelectNum(0);
        //title.setChapterId(chapterId);
        title.setCreateDate(new Date());
        title.setChapter(chapter);
        title.setTitleType(titleType);
        title.setScore(0f);
        titleRepository.save(title);
        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if(id==null){
            return Result.error(3060,"未输入题目id");
        }
        titleRepository.deleteById(id);
        return Result.success();
    }

    @Override
    public Result modification(Long id, String content, String answer, Long typeId, Long chapterId) {
        System.out.println(typeId+"   231 ");
        if(id==null){
            return Result.error(3060,"未输入题目id");
        }
        Title title=titleRepository.findById(id).get();
        if(content!=null&&!content.equals("")){
            title.setContent(content);
        }
        if(answer!=null&&!answer.equals("")){
            title.setAnswer(answer);
        }
        if(typeId!=null){
            title.setTitleType( titleTypeRspository.findById(typeId).get());
            title.setTypeId(typeId);
        }
        if(chapterId!=null){
            title.setChapterId(chapterId);
        }
        titleRepository.save(title);
        return Result.success();
    }

    @Override
    public Result get(Long id) {
        if(id==null){
            return Result.error(3060,"未输入题目id");
        }
        Title title=titleRepository.findById(id).get();
        if(title==null){
            return Result.error(3060,"没有改题目");
        }
        return Result.success(new TitleVO(title));
    }

    @Override
    public Result getAll() {
        List<Title> titleList=titleRepository.findAll();
        List<TitleVO> titleVOList=new LinkedList<>();
        for(Title title:titleList){
            TitleVO titleVO=new TitleVO(title);
            titleVOList.add(titleVO);
        }
        return Result.success(titleVOList);
    }

    @Override
    public Result getByLesson(Long id) {
        List<TitleVO> titleVOList=new LinkedList<>();
        Lesson lesson=lessonRepository.findById(id).get();
        List<Chapter> chapterList=chapterRspository.findAllByLesson(lesson);
        for(Chapter chapter:chapterList){
            List<Title> titleList=titleRepository.findByChapter(chapter);
            for(Title title:titleList){
                TitleVO titleVO=new TitleVO(title);
                titleVOList.add(titleVO);
            }
        }
        return Result.success(titleVOList);
    }

    @Override
    public ResponseEntity GeneratingTestPapers(List<Long> chapterIdList, Map<Long,Integer> titleTypeIntegerMap) throws Exception{

        List<Title> paper=new LinkedList<>();     //最终生成的卷子
        Set<Title> titleSet=new HashSet<>();//选中章节的所有题目
        List<Title> titleList;
        for(Long i:chapterIdList){
            Chapter chapter=chapterRspository.findById(i).get();
            titleList=titleRepository.findByChapter(chapter);
            for(Title t:titleList){
                titleSet.add(t);
            }
        }
        for(Long i:titleTypeIntegerMap.keySet()){
            Integer num=titleTypeIntegerMap.get(i);
            for(int j=0;j<num;j++){
                for(Title t:titleSet){
                    if(t.getTitleType().getId()==i){
                        paper.add(t);
                    }
                }
            }
        }

        String test=new String();
        int i=1;
        for(Title t:paper){
            test+=i+"、"+t.getContent()+"\n"+t.getAnswer()+"\n";
            i++;
        }
        System.out.println(test);


        String fileUrl="src/main/java/com/itembankmanagement/testPaper/1.txt";
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(fileUrl);
            fileOutputStream.write(test.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileSystemResource fileSystemResource=new FileSystemResource(fileUrl);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Disposition","attachment;filename");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));

    }

    @Override
    public Result GeneratingByWord(List<String> chapterList, Map<Long, Integer> titleTypeIntegerMap) throws Exception {
        String[] chineseSum={"零、","一、","二、","三、","四、","五、","六、","七、","八、","九、","十、"};
        Map<String, String> replaceMap = new HashMap<String, String>();

        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM");
        String date=sim.format(new Date());
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        if(Integer.valueOf(month)>=2&&Integer.valueOf(month)<=7){
            Integer temp=Integer.valueOf(year)-1;
            replaceMap.put("time",temp.toString()+"-"+year);
            replaceMap.put("count","二");
        }else {
            Integer temp=Integer.valueOf(year)+1;
            replaceMap.put("time",year+"-"+temp.toString());
            replaceMap.put("count","一");
        }


        Random random = new Random();
        int c=1;//题型数量
        int sum=1;//题目数量

        List<Title> paper=new LinkedList<>();//最终的试卷
        List<Chapter> chapterList1=new ArrayList<>();
        for(String s:chapterList){
            chapterList1.addAll(chapterRspository.findAllByName(s));
        }//获取所选章节id
        List<Title> titleList=new ArrayList<>();
        for(Chapter chapter:chapterList1){
            titleList.addAll(titleRepository.findByChapter(chapter));
        }//获取所选章节下的所有题目


        for(Long i:titleTypeIntegerMap.keySet()){//遍历所有的题型
            Integer num=titleTypeIntegerMap.get(i);//这个题型所需要的数量
            TitleType type=titleTypeRspository.findById(i).get();//这个题型是啥
            replaceMap.put("type"+c,chineseSum[c]+type.getName());//word替换添加

            List<Title> titles=new ArrayList<>();
            for(Title title:titleList){
                if(title.getTitleType()==type){
                    titles.add(title);
                }
            }//获取某个题型的题目

            String titleContent=new String();
            for(int j=0;j<num;){
                int rand=random.nextInt(titles.size());
                Title title=titles.get(rand);
                if(!paper.contains(title)){
                    paper.add(title);
                    title.setSelectNum(title.getSelectNum()+1);
                    j++;

                    titleContent+=sum+"、"+title.getContent()+"\n\n";
                    sum++;
                }
            }//随机获取若干道题

            replaceMap.put("title"+c,titleContent);

            c++;
        }

        String fileName=chapterList1.get(0).getLesson().getName()+System.currentTimeMillis()+".docx";
        String fileUrl="src/main/java/com/itembankmanagement/testPaper/"+fileName;
        replaceMap.put("lesson",chapterList1.get(0).getLesson().getName());
        WorderToNewWordUtils.changWord(inputUrl, fileUrl, replaceMap, null);



        List<TitleVO> titleVOList=new ArrayList<>();
        for(Title title:paper){
            titleVOList.add(new TitleVO(title));
        }
        List list=new ArrayList();
        list.add(titleVOList);
        list.add(fileName);
        return Result.success(list);

    }

    @Override
    public ResponseEntity downloadTest(String fileName) throws Exception {
        String fileUrl="src/main/java/com/itembankmanagement/testPaper/"+fileName;

        FileSystemResource fileSystemResource=new FileSystemResource(fileUrl);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Disposition","attachment;filename=" + fileName);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(fileSystemResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
    }



    @Override
    public Result GeneratingTestPapers2(List<String> chapterList, Map<Long, Integer> titleTypeIntegerMap) throws Exception {

        List<Title> paper=new LinkedList<>();//最终的试卷
        Random random = new Random();
       // Lesson lesson =lessonRepository.findById(lessonId).get();//获取课程
        List<Chapter> chapterList1=new ArrayList<>();
        for(String s:chapterList){
            chapterList1.addAll(chapterRspository.findAllByName(s));
        }//获取所选章节id
        List<Title> titleList=new ArrayList<>();
        for(Chapter chapter:chapterList1){
            titleList.addAll(titleRepository.findByChapter(chapter));
        }//获取所选章节下的所有题目

        for(Long i:titleTypeIntegerMap.keySet()){//遍历所有的题型
            Integer num=titleTypeIntegerMap.get(i);
            TitleType type=titleTypeRspository.findById(i).get();
            List<Title> titles=new ArrayList<>();
            for(Title title:titleList){
                if(title.getTitleType()==type){
                    titles.add(title);
                }
            }//获取某个题型的题目
            for(int j=0;j<num;){
                int rand=random.nextInt(titles.size());
                Title title=titles.get(rand);
                if(!paper.contains(title)){
                    paper.add(title);
                    j++;
                }
            }//随机获取若干道题
        }

        String test=new String();
        int i=1;
        for(Title t:paper){
            test+=i+"、"+t.getContent()+"\n答案：     "+t.getAnswer()+"\n";
            i++;
        }//加题目号
        String fileName=chapterList1.get(0).getLesson().getName()+System.currentTimeMillis()+".txt";
        String fileUrl="src/main/java/com/itembankmanagement/testPaper/"+fileName;
        try{
            FileOutputStream fileOutputStream=new FileOutputStream(fileUrl);
            fileOutputStream.write(test.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<TitleVO> titleVOList=new ArrayList<>();
        for(Title title:paper){
            titleVOList.add(new TitleVO(title));
        }
        List list=new ArrayList();
        list.add(titleVOList);
        list.add(fileName);
        return Result.success(list);
    }

    @Override
    public Result getTitleByChapters(List<String> chapters) {
        Map<TitleTypeVO,Integer> typeAndSum=new HashMap();
        List<Chapter> chapterList=new ArrayList<>();
        for(String s:chapters){
            List<Chapter> chapterList1=chapterRspository.findAllByName(s);
            for(Chapter chapter:chapterList1){
                chapterList.add(chapter);
            }
        }

        List<Title> titleList=new ArrayList<>();
        for(Chapter chapter:chapterList){
            List<Title> titleList1=titleRepository.findByChapter(chapter);
            titleList.addAll(titleList1);
        }

        for(Title title:titleList){
            TitleTypeVO type=new TitleTypeVO(title.getTitleType());
            if(typeAndSum.containsKey(type)){
                typeAndSum.put(type,typeAndSum.get(type)+1);
            }
            else{
                typeAndSum.put(type,1);
            }
        }

        return Result.success(typeAndSum);
    }
}
