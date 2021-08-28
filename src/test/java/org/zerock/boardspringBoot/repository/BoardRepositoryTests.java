package org.zerock.boardspringBoot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Member;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);

        });

    }

    @Test
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();  //@ManyToOne 관계가 설정되어 조회시 자동으로 member를 LEFT OUTER JOIN 하여 결과를 가져온다.

        System.out.println(board);
        System.out.println(board.getWriter());
    }
}
