package org.zerock.boardspringBoot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.boardspringBoot.dto.PageRequestDTO;
import org.zerock.boardspringBoot.service.BoardService;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor  //초기화되지 않은 final 필드나 @NonNull 이 붙은 필드에 대해 생성자를 생성
public class BoardController {

    private final BoardService boardService;  //final로 선언

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list............" + pageRequestDTO);

        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

}
