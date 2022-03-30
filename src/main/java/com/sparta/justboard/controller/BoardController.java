package com.sparta.justboard.controller;

import com.sparta.justboard.dto.BoardRequestDto;
import com.sparta.justboard.dto.CommentRequestDto;
import com.sparta.justboard.model.Board;
import com.sparta.justboard.model.Comment;
import com.sparta.justboard.repository.BoardRepository;
import com.sparta.justboard.repository.CommentRepository;
import com.sparta.justboard.security.UserDetailsImpl;
import com.sparta.justboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    //게시글 작성 페이지
    @GetMapping("/api/board")
    public String getBoard(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = new Board();
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{

            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        model.addAttribute("board", board);
        return "board";
    }

    //form 을 받을 때 @RequestBody 안먹힘
    @PostMapping("/api/board")
    public String createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute BoardRequestDto requestDto){
        System.out.println("POST/api/board 들어옴");
        requestDto.setUser(userDetails.getUser());
        System.out.println(requestDto.getTitle());
        System.out.println(requestDto.getText());
        System.out.println(requestDto.getUser());
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return "redirect:/";
    }

    @GetMapping("/api/board/{id}")
    public String detailBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        Board board = boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("잘못된 접근입니다.")
        );
        if(userDetails == null){
            model.addAttribute("user","null");
        }else{
            model.addAttribute("user",userDetails.getUser().getUsername());
        }

        model.addAttribute("board", board);
        model.addAttribute("id", id);

        return "detail";
    }

    @RequestMapping(value="/api/board/{id}", method=RequestMethod.DELETE)
    public String deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("잘못된 접근입니다.")
        );
        System.out.println("DELETE API BOARD ID");
        System.out.println("userDetail = " + userDetails.getUser().getUsername());
        System.out.println("board = " + board.getUser().getUsername());
        if(userDetails.getUser().getId().equals(board.getUser().getId())){
            System.out.println("ㅇㅇㅇㅋㅋㅋ");
            boardRepository.deleteById(id);
        }
        return "redirect:/";
    }


    @PostMapping("/api/board/{id}/comment")
    public String createComment(@PathVariable Long id, @ModelAttribute CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        System.out.println("POST /api/board/{id}/comment 들어옴");
        System.out.println(requestDto.getText());
        Board board = boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("잘못된 접근입니다.")
        );
        if(userDetails == null){
            return "redirect:/user/login";
        }else{
            requestDto.setUser(userDetails.getUser());
        }
        if(requestDto.getText().isEmpty()){
            return "redirect:/api/board/{id}";
        }
        Comment comment = new Comment(requestDto);
        comment.setBoard(board);
        System.out.println(comment.getText());
        System.out.println(comment.getUser());
        commentRepository.save(comment);

        return "redirect:/api/board/{id}";
    }
}
