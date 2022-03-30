package com.sparta.justboard.controller;

import com.sparta.justboard.dto.CommentRequestDto;
import com.sparta.justboard.model.Board;
import com.sparta.justboard.model.Comment;
import com.sparta.justboard.repository.BoardRepository;
import com.sparta.justboard.repository.CommentRepository;
import com.sparta.justboard.security.UserDetailsImpl;
import com.sparta.justboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentService commentService;


    //@RequestMapping(value="/api/{boardId}/comment/{commentId}", method={RequestMethod.PUT})
    @PutMapping("/api/{boardId}/comment/{commentId}")
    @ResponseBody
    public String editComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다.")
        );
        System.out.println("PUTMAPPING 들어옴");
        if(userDetails == null){
            return "redirect:/";
        }else{
            requestDto.setUser(userDetails.getUser());
        }
        if(!userDetails.getUser().getId().equals(comment.getUser().getId())){
            System.out.println("아이디 다르다 돌아가");
            return "redirect:/api/board/{boardId}";
        }
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()->new IllegalArgumentException("잘못된 접근입니다.")
        );
        requestDto.setBoard(board);

        commentService.update(commentId, requestDto);
        System.out.println(requestDto.getText());
        System.out.println("업데이트 완료!");

        return "redirect:/api/board/{boardId}";
    }


    @DeleteMapping("/api/{boardId}/comment/{commentId}")
    @ResponseBody
    public String deleteBoard(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다.")
        );
        System.out.println("DELETE API COMMENT ID");
        System.out.println("boardId " + boardId);
        System.out.println("userDetail = " + userDetails.getUser().getUsername());
        System.out.println("comment = " + comment.getUser().getUsername());
        if(userDetails.getUser().getId().equals(comment.getUser().getId())){
            System.out.println("ㅇㅇㅇㅋㅋㅋ");
            commentRepository.deleteById(commentId);
        }
        return "redirect:/api/board/{boardId}";
    }


}
