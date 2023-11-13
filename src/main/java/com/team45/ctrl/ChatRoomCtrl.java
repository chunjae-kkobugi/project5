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
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/chat/")
public class ChatRoomCtrl {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ChatService service;

    @GetMapping("chat")
    public String loadHome(Model model, HttpServletRequest request){
        int usedNo = Integer.parseInt(request.getParameter("usedNo"));
        List<ChatRoom> chatRooms = service.chatRoomProductList(usedNo);

        model.addAttribute("chatRooms", chatRooms);
        return "/chat/chat";
    }

    @GetMapping("chat2")
    public String loadHome2(Model model, HttpServletRequest request){
        int usedNo = Integer.parseInt(request.getParameter("usedNo"));
        List<ChatRoom> chatRooms = service.chatRoomProductList(usedNo);

        model.addAttribute("chatRooms", chatRooms);
        return "/chat/chat2";
    }

    @PostMapping("createRoom")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String userId, @RequestParam int usedNo){

        return service.chatRoomInsert(userId, usedNo);
    }

    @PostMapping("getRoom")
    @ResponseBody
    public List<ChatMessage> getRoom(HttpServletRequest request){
        int roomNo = Integer.parseInt(request.getParameter("roomNo"));
        String sender = request.getParameter("userId");
        service.chatMessageReadUpdates(roomNo, sender);

        return service.chatMessageList(roomNo, sender);
    }

    @PostMapping("blockRoom")
    @ResponseBody
    public String blockRoom(HttpServletRequest request){
        int roomNo = Integer.parseInt(request.getParameter("roomNo"));
        int returnNo = service.chatRoomBlockUpdate(roomNo);
        if(returnNo>0){
            return "Block Successfully";
        }

        return "Something went wrong";
    }

    @PostMapping("readRoom")
    @ResponseBody
    public String readRoom(HttpServletRequest request){
        int roomNo = Integer.parseInt(request.getParameter("roomNo"));
        String sender = request.getParameter("userId");

        int returnNo = service.chatMessageReadUpdates(roomNo, sender);
        if(returnNo>0){
            return "Success";
        }

        return "Something went wrong";
    }

    @PostMapping("insertChat")
    @ResponseBody
    public ChatMessage insertChat(@RequestParam String message) throws JsonProcessingException {
        ChatMessage chat = mapper.readValue(message, ChatMessage.class);

        return service.chatMessageInsert(chat);
    }

    @PostMapping("readChat")
    @ResponseBody
    public String readChat(@RequestParam String message, @RequestParam String user) throws JsonProcessingException {
        ChatMessage chat = mapper.readValue(message, ChatMessage.class);
        System.out.println(chat);
        service.chatMessageReadUpdate(chat.getChatNo(), user);

        return "readChat Completed";
    }
}