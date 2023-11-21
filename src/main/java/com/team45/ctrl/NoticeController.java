package com.team45.ctrl;


import com.team45.entity.Member;
import com.team45.entity.Notice;
import com.team45.service.MemberService;
import com.team45.service.NoticeSerivce;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Path;
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

    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpSession session;

    @GetMapping("/List")
    public String NoticeList(Model model) {
        List<Notice> noticeList = noticeSerivce.boardList();
        model.addAttribute("noticeList", noticeList);
        return "/board/notice/noticeList";
    }

    @GetMapping("/Get")
    public String Noticeget(Model model, @RequestParam int no) throws Exception {

        Notice notice = noticeSerivce.boardGet(no);
        String par = notice.getTitle();

        model.addAttribute("notice", notice);
        return "/board/notice/noticeGet";
    }

    ;

    @GetMapping("/Edit")
    public String NoticeEditform(@RequestParam int no, Model model) {
        Notice notice = noticeSerivce.boardGet(no);
        model.addAttribute("notice", notice);
        return "/board/notice/noticeEdit";
    }


    @PostMapping("/Edit")
    public String NoticeEdit(MultipartFile uploadFiles, HttpServletRequest request, Model model) throws Exception {
        int no = Integer.parseInt(request.getParameter("no"));
        Notice notice = noticeSerivce.boardGet(no);
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String img = request.getParameter("img");
        notice.setTitle(title);
        notice.setContent(content);

        if (uploadFiles != null) {
//            ServletContext application = request.getSession().getServletContext();
//            String realPath = application.getRealPath("classpath/static/");          // 운영 서버 저장폴더
            String realPath = "";                                              //application.yml location 적용시 폴더
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);
            String originalThumbnailname = uploadFiles.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String uploadThumbnailname = uuid.toString() + "_" + originalThumbnailname;
            uploadFiles.transferTo(new File(realPath, uploadThumbnailname));     //파일 등록
            notice.setImg(uploadThumbnailname);
        } else {
            notice.setImg(img);
        }

        noticeSerivce.boardEdit(notice);
        return "redirect:/notice/List";
    }

    @GetMapping("/Add")
    public String Noticeform(Model model) {
        String id = (String) session.getAttribute("sid");
        Member mem = memberService.memberGet(id);
        model.addAttribute("mem", mem);
        return "/board/notice/noticeForm";
    }


    @PostMapping("/Add")
    public String NoticeAdd(Notice notice, MultipartFile uploadFiles, HttpServletRequest request, Model model) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        notice.setTitle(title);
        notice.setContent(content);

        if (uploadFiles != null) {
//            ServletContext application = request.getSession().getServletContext();
//            String realPath = application.getRealPath("classpath/static/");          // 운영 서버 저장폴더
            String realPath = "";                                              //application.yml location 적용시 폴더

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String dateFolder = sdf.format(date);
            String originalThumbnailname = uploadFiles.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String uploadThumbnailname = uuid.toString() + "_" + originalThumbnailname;
            uploadFiles.transferTo(new File(realPath, uploadThumbnailname));     //파일 등록
            notice.setImg(uploadThumbnailname);
        }
        noticeSerivce.boardAdd(notice);
        return "redirect:/notice/List";
    }

    @GetMapping("/Del")
    public String NoticeDel(int no) {
        noticeSerivce.boardDel(no);
        return "redirect:/notice/List";
    }

}
