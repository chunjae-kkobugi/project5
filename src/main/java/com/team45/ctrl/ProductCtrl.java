package com.team45.ctrl;

import com.team45.entity.Category;
import com.team45.entity.FileData;
import com.team45.entity.Product;
import com.team45.entity.ProductVO;
import com.team45.service.ProductService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/product/**")
public class ProductCtrl {
    @Autowired
    private ProductService productService;

    // 전체 상품 리스트
    @GetMapping("list")
    public String productList(HttpServletRequest request, Model model) throws Exception {

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cate");

        Page page = new Page();
        page.setCategory(category);                                        // 카테고리 데이터
        page.setSearchKeyword(request.getParameter("keyword"));     // 검색 키워드
        page.setSearchType(request.getParameter("type"));           // 검색 타입
        page.setProaddr(request.getParameter("proaddr"));
        page.setStatus(request.getParameter("status"));

        // 페이징에 필요한 데이터 저장
        int total = productService.getCount(page);

        List<ProductVO> productList = productService.productList(page);
        List<Category> categories = productService.categories();


        // 로그인한 회원의 주소 정보 불러오기
        model.addAttribute("proaddr", request.getAttribute("addr3"));
        // 상품의 판매상태 불러오기
        String status = request.getParameter("status");

        model.addAttribute("status", status);
        model.addAttribute("productList", productList);
        model.addAttribute("categories", categories);
        model.addAttribute("curPage", curPage);
        model.addAttribute("curCategory", category);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        return "product/productList";
    }

    @GetMapping("detail")
    public String productDetial(@RequestParam Long pno, HttpServletRequest request, Model model) {
        ProductVO detail = productService.productDetail(pno);
        model.addAttribute("detail", detail);
        return "product/productDetail";
    }

    @GetMapping("insert")
    public String productInsertForm(HttpServletRequest request, Model model, Product product) {
        HttpSession session = request.getSession();
        List<Category> categories = productService.categories();
        model.addAttribute("categories", categories);
        model.addAttribute("proaddr", session.getAttribute("proaddr"));
        return "product/productInsert";
    }

    @PostMapping("insert")
    public String productInsertPro(HttpServletRequest request, Product product,
                                   MultipartHttpServletRequest files) {
        HttpSession session = request.getSession();
        String realFolder = "D:/kim/project/tproj/project05/team45/src/main/resources/static/images";

        Enumeration<String> e = files.getParameterNames();
        Map map = new HashMap();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String value = files.getParameter(name);
            map.put(name, value);
        }

        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = realFolder + File.separator + today;
        File folder = new File(saveFolder);

        if (!folder.exists()) {     // 폴더가 없으면 새로 생성
            folder.mkdirs();
        }

        List<MultipartFile> fileList = files.getFiles("uploadFiles");
        List<FileData> fileDataList = new ArrayList<>();
        FileData productImg = new FileData();

        int i = 0;
        for (MultipartFile multipartFile : fileList) {
            String originalName = multipartFile.getOriginalFilename();
            if (!originalName.isEmpty()) {
                String saveName = UUID.randomUUID().toString() + "_" + originalName;

                FileData f = new FileData();
                f.setOriginName(originalName);
                f.setSaveName(saveName);
                f.setSavePath(today);
                f.setFileType(multipartFile.getContentType());

                if (i == 0 ){
                    productImg = f;
                } else {
                    fileDataList.add(f);
                }

                File savefile = new File(saveFolder, saveName);
                i++;

                try {
                    multipartFile.transferTo(savefile);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        product.setSeller(session.getId());
        productService.productInsert(product);
        return "redirect:list";
    }

//    @PostMapping("insert")
//    public String productInsertPro(Product product, @RequestParam("upfile") MultipartFile[] files,
//                                   HttpServletRequest request, Model model, RedirectAttributes rttr) throws IOException {
//        HttpSession session = request.getSession();
//
//        String realPath = "C://upload/";
////        String realPath = "D:/kim/project/tproj/project05/team45/src/main/resources/static/images";
//        String today = new SimpleDateFormat("yyMMdd").format(new Date());
//        String saveFolder = realPath + today;
//
//        File folder = new File(saveFolder);
//        if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
//            folder.mkdirs();
//        }
//
//        List<FileData> fileDataList = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            FileData fileData = new FileData();
//            String originalFileName = file.getOriginalFilename();   // 첨부파일의 실제 이름
//
//            if (!file.isEmpty()) { // 파일이 비어있지 않은 경우에만 처리
//
//                String saveFilename = UUID.randomUUID().toString() + "_" + originalFileName;
//                fileData.setTableName("product");
//                fileData.setColumnNo(product.getPno());
//                fileData.setOriginName(originalFileName);
//                fileData.setSaveName(saveFilename);
//                fileData.setSavePath(saveFolder);
//                fileData.setFileType(" ");
//                fileData.setStatus("ACTIVE");
//                file.transferTo(new File(folder, saveFilename)); // 파일을 업로드 폴더에 저장
//            }
//            fileDataList.add(fileData);
//        }
//
//        product.setFileDataList(fileDataList);
//        product.setSeller(session.getId());
//        product.setImage((long) fileDataList.size());
//
//        productService.productInsert(product);
//        return "redirect:list";
//    }

}


//        if ( files != null) {
//            ServletContext application = request.getSession().getServletContext();
//            String realPath = "";                                                     //application.yml location 적용시 폴더
//            String realPath2 = "D:/kim/project/tproj/project05/team45/src/main/resources/static/images";      //application.yml 미적용시 폴더
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//            Date date = new Date();
//            String dateFolder = sdf.format(date);
//            String originalThumbname = files.getOriginalFilename();
//            UUID uuid = UUID.randomUUID();
//            String uploadThumbname = uuid.toString() + "_" + originalThumbname;
//
//            fileData.setColumnNo(pno);
//            fileData.setTableName("product");
//            fileData.setOriginName(originalThumbname);
//            fileData.setSaveName(uploadThumbname);
//            fileData.setSavePath(realPath2);
//        }
