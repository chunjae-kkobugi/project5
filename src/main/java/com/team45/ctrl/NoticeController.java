package com.team45.ctrl;


import com.team45.entity.Notice;
import com.team45.service.NoticeSerivce;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/notice/*")
public class NoticeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NoticeSerivce noticeSerivce;


    @GetMapping("/List")
    public String NoticeList(Model model) {
        List<Notice> noticeList = noticeSerivce.boardList();
        model.addAttribute("noticeList", noticeList);
        return "/board/notice/noticeList";
    }

    @GetMapping("/Get")
    public String Noticeget(Model model, @RequestParam int no) throws Exception{

        Notice notice = noticeSerivce.boardGet(no);
        String par = notice.getTitle();

        model.addAttribute("notice", notice);
        return "/board/notice/noticeGet";
    };

    @GetMapping("/Add")
    public String Noticeform(){
        return "/board/notice/noticeForm";
    };

    @PostMapping("/Add")
    public String NoticeAdd(Notice notice, MultipartFile uploadFiles, HttpServletRequest request, Model model) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        notice.setTitle(title);
        notice.setContent(content);

        logger.info("uploadFiles--------------" + uploadFiles);

        if (uploadFiles != null) {
//            ServletContext application = request.getSession().getServletContext();
//            String realPath2 = application.getRealPath("classpath/static/");       // 운영 서버
            String realPath = "C:/upload";       // 개발 서버
            String realPath2 = "classpath/static/";
            String realPath3 = "D:\\kyo\\team45\\src\\main\\resources\\static";
            String realPath4 = "D:/kyo/team45/src/main/resources/static";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);
            logger.info("realPath--------------" + realPath);
            logger.info("realPath2--------------" + realPath2);
            logger.info("realPath3--------------" + realPath3);
            logger.info("realPath4--------------" + realPath4);


                String originalThumbnailname = uploadFiles.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String uploadThumbnailname = uuid.toString() + "_" + originalThumbnailname;
                uploadFiles.transferTo(new File(realPath4, uploadThumbnailname));     //파일 등록
                notice.setImg(uploadThumbnailname);
            logger.info("uploadThumbnailname--------------" + uploadThumbnailname);

            }
        logger.info("Notice--------------" + notice);

        noticeSerivce.boardAdd(notice);
        model.addAttribute("notice", notice);
        return "redirect:/notice/List";
    };

    @GetMapping("/Del")
    public String NoticeDel(int no){
        noticeSerivce.boardDel(no);
        return "redirect:/notice/List";
    };

}
