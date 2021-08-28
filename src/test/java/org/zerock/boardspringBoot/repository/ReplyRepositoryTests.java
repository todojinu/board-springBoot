package org.zerock.boardspringBoot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Reply;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......" + i)
                    .replyer("guest")
                    .board(board)
                    .build();

            replyRepository.save(reply);

        });
    }

    @Transactional  //해당 메서드를 하나의 트랜잭션으로 처리하는 어노테이션
    @Test
    public void readReply1() {

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();  // board와 member까지 LEFT OUTER JOIN되어 결과를 가져온다.

        System.out.println(reply);
        System.out.println(reply.getBoard());
        System.out.println(reply.getBoard().getWriter());

    }

}
