package com.team45.ctrl;

import com.team45.entity.Category;
import com.team45.entity.ChatRoom;
import com.team45.entity.Product;
import com.team45.entity.ProductVO;
import com.team45.service.ChatService;
import com.team45.service.ProductService;
import com.team45.service.WishService;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product/**")
public class ProductCtrl {
    @Autowired
    private ProductService productService;
    @Autowired
    private WishService wishService;

    @Autowired
    private HttpSession session;

    @Autowired
    private ChatService chatService;

    // 전체 상품 리스트
    @GetMapping("list")
    public String productList(HttpServletRequest request, Model model) throws Exception {

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("cate");

        Page page = new Page();
        page.setPageNow(curPage);
        page.setCategory(category);                                        // 카테고리 데이터
        page.setSearchKeyword(request.getParameter("keyword"));     // 검색 키워드
        page.setSearchType(request.getParameter("type"));           // 검색 타입
        page.setProaddr(request.getParameter("proaddr"));
        page.setStatus(request.getParameter("status"));

        // 페이징에 필요한 데이터 저장
        int total = productService.getCount(page);
        page.setPostTotal(total);
        page.makePage();

        List<ProductVO> productList = productService.productList(page);
        List<Category> categories = productService.categories();

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

        HttpSession session = request.getSession();
        String sid = (String) session.getAttribute("sid");
        int flag = wishService.wishFind(pno, sid);
        model.addAttribute("flag", flag);
        //System.out.println("flag : " + flag);
        model.addAttribute("uid", sid);
        if(sid.equals(detail.getSeller())){
            List<ChatRoom> roomList = chatService.chatRoomProductList(pno);
            model.addAttribute("roomList", roomList);
        }
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
    public String productInsertPro(Product product, @RequestParam("upfile") MultipartFile[] files, HttpServletRequest request, Model model, RedirectAttributes rttr) throws IOException {
        HttpSession session = request.getSession();

        String realPath = "C://upload/";
//        String realPath = "D:/kim/project/tproj/project05/team45/src/main/resources/static/images";
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = realPath + today;
        System.out.println(saveFolder);

        File folder = new File(saveFolder);
        if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
            folder.mkdirs();
        }

        List<FileData> fileDataList = new ArrayList<>();

        for (MultipartFile file : files) {
            FileData fileData = new FileData();
            String originalFileName = file.getOriginalFilename();   // 첨부파일의 실제 이름

            if (!file.isEmpty()) { // 파일이 비어있지 않은 경우에만 처리

                String saveFilename = UUID.randomUUID().toString() + "_" + originalFileName;
                fileData.setTableName("product");
                fileData.setColumnNo(product.getPno());
                fileData.setOriginName(originalFileName);
                fileData.setSaveName(saveFilename);
                fileData.setSavePath(today);
                fileData.setFileType(" ");
                fileData.setStatus("ACTIVE");
                file.transferTo(new File(saveFolder, saveFilename)); // 파일을 업로드 폴더에 저장
            }
            fileDataList.add(fileData);
        }

        product.setFileDataList(fileDataList);
        String sid = (String) session.getAttribute("sid");
        product.setSeller(sid);
        product.setImage((long) fileDataList.size());

        productService.productInsert(product);
        return "redirect:list";
    }

    @GetMapping("edit")
    public String productEditForm(HttpServletRequest request, @RequestParam("pno") Long pno, Model model) {
        ProductVO product = productService.productDetail(pno);
        List<Category> categories = productService.categories();

        model.addAttribute("detail", product);
        model.addAttribute("cateList", categories);

        return "product/productEdit";
    }

    @PostMapping("edit")
    public String productEditPro(Product product, @RequestParam("upfile") MultipartFile[] files, HttpServletRequest request, Model model, RedirectAttributes rttr) throws IOException {
        HttpSession session = request.getSession();

        String realPath = "C://upload/";
//        String realPath = "D:/kim/project/tproj/project05/team45/src/main/resources/static/images";
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = realPath + today;
        System.out.println(saveFolder);

        File folder = new File(saveFolder);
        if (!folder.exists()) {        // 폴더가 존재하지 않으면 폴더 생성
            folder.mkdirs();
        }

        // 파일이 새롭게 업로드 되지 않았다면 삭제하지 않도록 처리
        if (files[0].getSize() != 0) {
            List<FileData> fileDataList = null;
        }
        return  null;
    }

    @GetMapping("delete")
    public String productDelete (@RequestParam("pno") Long pno) {
        productService.productRemove(pno);
        return "redirect:list";
    }

    @PostMapping("wish")
    @ResponseBody
    public Map<String, Integer> wishProduct(@ModelAttribute Wish wish) {
        int result = wishService.checkWish(wish);
        System.out.println("result : " + result);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("res", result);
        resultMap.put("heartCnt", wishService.wishCount(wish.getPno()));
        return resultMap;
    }
}
