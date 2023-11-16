package com.team45.ctrl;

import com.team45.service.ChatService;
import com.team45.entity.ChatMessage;
import com.team45.entity.ChatRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/chat/")
public class ChatRoomCtrl {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private HttpSession session;

    @Autowired
    private ChatService chatService;

    // 채팅방 입장
    @GetMapping("roomEnter")
    public String roomEnter(HttpServletRequest request, Model model){
        String memId = request.getParameter("memId");
        int pno = Integer.parseInt(request.getParameter("pno"));

        // 없으면 새로 추가, 있으면 가져오기
        ChatRoom room = chatService.chatRoomInsert(memId, pno);
        model.addAttribute("room", room);

        // 기존의 채팅 내역 가져오기
        int roomNo = room.getRoomNo();
        List<ChatMessage> chats = chatService.chatMessageList(roomNo);
        model.addAttribute("chats", chats);

        // 읽지 않은 첫 채팅부터 시작하기 & 기존 채팅 읽음 처리
        for(ChatMessage c:chats){
            if(c.getStatus().equals("UNREAD")){
                model.addAttribute("firstChat", c);
                break;
            }
        }
        chatService.chatMessageReadUpdates(roomNo, memId);

        // 채팅방 상대 이름 띄우기
        // 채팅방은 구매자 기준으로 저장되므로, 구매자인 경우 product 에서 seller 가져오기

        return "chat/chat";
    }

    @GetMapping("roomList")
    public String roomList(HttpServletRequest request, Model model){
        int pno = Integer.parseInt(request.getParameter("pno"));
        model.addAttribute("pno", pno);

        List<ChatRoom> chatRooms = chatService.chatRoomProductList(pno);
        model.addAttribute("rooms", chatRooms);

        return "chat/chatList";
    }

    @PostMapping("blockRoom")
    @ResponseBody
    public String blockRoom(HttpServletRequest request){
        int roomNo = Integer.parseInt(request.getParameter("roomNo"));
        int returnNo = chatService.chatRoomBlockUpdate(roomNo);
        if(returnNo>0){
            return "Block Successfully";
        }

        return "Something went wrong";
    }

    @PostMapping("readRoom")
    @ResponseBody
    public String readRoom(HttpServletRequest request){
        int roomNo = Integer.parseInt(request.getParameter("roomNo"));
        String sender = request.getParameter("memId");

        int returnNo = chatService.chatMessageReadUpdates(roomNo, sender);
        if(returnNo>0){
            return "Success";
        }

        return "Something went wrong";
    }

    @PostMapping("insertChat")
    @ResponseBody
    public ChatMessage insertChat(@RequestParam String message) throws JsonProcessingException {
        ChatMessage chat = mapper.readValue(message, ChatMessage.class);
        
        return chatService.chatMessageInsert(chat);
    }

    @PostMapping("readChat")
    @ResponseBody
    public String readChat(@RequestParam String message, @RequestParam String user) throws JsonProcessingException {
        ChatMessage chat = mapper.readValue(message, ChatMessage.class);
        System.out.println(chat);
        chatService.chatMessageReadUpdate(chat.getChatNo(), user);

        return "readChat Completed";
    }
}