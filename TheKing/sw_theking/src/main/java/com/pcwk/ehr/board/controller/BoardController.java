package com.pcwk.ehr.board.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.SearchDTO;

@Controller
@RequestMapping("/board")
public class BoardController {

    final Logger log = LogManager.getLogger(getClass());

    @Autowired
    BoardService boardService;

    // 목록 조회
    @GetMapping("/list")
    public String list(SearchDTO search, Model model) {
        log.debug("board/list 호출: {}", search);

        List<BoardDTO> list = boardService.doRetrieve(search);
        model.addAttribute("list", list);

        return "board/list";
    }

    // 글 상세 보기 
    @GetMapping("/view")
    public String view(@RequestParam("boardNo") int boardNo, Model model) {
        log.debug("board/view?boardNo={} 호출", boardNo);

        BoardDTO param = new BoardDTO();
        param.setBoardNo(boardNo);

        boardService.increaseViews(param);
        BoardDTO dto = boardService.doSelectOne(param);

        model.addAttribute("board", dto);
        return "board/view";
    }

    // 글쓰기 폼
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    // 글 등록 처리
    @PostMapping("/write")
    public String write(BoardDTO dto) {
        log.debug("글쓰기 처리: {}", dto);
        boardService.doSave(dto);
        return "redirect:/board/list";
    }

    // 수정 폼
    @GetMapping("/edit")
    public String editForm(@RequestParam("boardNo") int boardNo, Model model) {
        BoardDTO param = new BoardDTO();
        param.setBoardNo(boardNo);

        BoardDTO dto = boardService.doSelectOne(param);
        model.addAttribute("board", dto);
        return "board/edit";
    }

    // 글 수정 처리
    @PostMapping("/edit")
    public String edit(BoardDTO dto) {
        boardService.doUpdate(dto);
        return "redirect:/board/view?boardNo=" + dto.getBoardNo();
    }

    // 글 삭제 처리
    @PostMapping("/delete")
    public String delete(@RequestParam("boardNo") int boardNo) {
        BoardDTO param = new BoardDTO();
        param.setBoardNo(boardNo);

        boardService.doDelete(param);
        return "redirect:/board/list";
    }
}
