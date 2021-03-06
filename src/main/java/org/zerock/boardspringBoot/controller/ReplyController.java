package org.zerock.boardspringBoot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.boardspringBoot.dto.ReplyDTO;
import org.zerock.boardspringBoot.service.ReplyService;

import java.util.List;

/* @RestController
 * -데이터를 JSON 으로 처리시 편리
 * -모든 메서드의 리턴타입은 기본으로 JSON 을 사용
 * -메서드의 반환 타입은 ReponseEntity 라는 객체를 이용->이를 이용하면 HTTP 의 상태 코드 등을 같이 전달할 수 있음
 */
@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;  //자동 주입을 위해 final 선언

    @GetMapping(value = "/board/{bno}",                   //{}로 묶은 변수를 사용하면 메서드 내에서 @PathVariable 로 처리할 수 있음
            produces = MediaType.APPLICATION_JSON_VALUE)  //produce 로 응답의 content-type 을 설정
    // '/replies/board/100'과 같이 요청이 들어올떄 '100'이라는 데이터를 변수로 처리할 수 있다.
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("bno: " + bno);

        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {  //@RequestBody 는 JSON으로 들어오는 데이터를 자동으로 해당 탑입의 객체로 매핑해줌
        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("RNO: " + rno);

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
