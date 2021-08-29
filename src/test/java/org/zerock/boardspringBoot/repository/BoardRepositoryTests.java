package org.zerock.boardspringBoot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Member;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
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

    @Transactional  //해당 메서드를 하나의 트랜잭션으로 처리하는 어노테이션
    @Test
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();  //@ManyToOne 관계가 설정되어 조회시 자동으로 member를 LEFT OUTER JOIN 하여 결과를 가져온다.

        System.out.println(board);
        System.out.println(board.getWriter());
        /*
         * Lazy Loading인 경우 board 테이블만 가져오온 후 session이 종료된 상태이기 때문에 member테이블을 로딩하지 못해 에러 발생
         * - could not initialize proxy [org.zerock.boardspringBoot.entity.Member#user100@aaa.com] - no Session
         * => 메서드 선언부에 @Transactional 어노테이션 선언하여 메소드를 하나의 트랜잭션으로 연결
         *    트랜잭션으로 처리하면 속성에 따라 다르게 동작하지만, 기본적으로 필요할 때 다시 데이터베이스와 연결됨
         */

    }

    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------");
        System.out.println(arr[0]);
        System.out.println(arr[1]);

        //System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testReadWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(1L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }
}
