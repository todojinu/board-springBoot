package org.zerock.boardspringBoot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends
        JpaRepository<Board, Long>,
        SearchBoardRepository
{

    /* 엔티티 클래스에 연관관계가 있는 경우의 조인처리
     * - @Query는 SQL과 유사하게 엔티티 클래스의 정보를 이용해서 쿼리(JPQL: Java Persistence Query Language)를 작성할 수 있다.
     * - 한개의 로우(Object) 내에 Object[]로 나옴
     * - Board 클래스는 Member와 연관관계를 맺고 있으므로 b.writer와 같은 형태로 사용
     */
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);  // :bno와 @Param("bno")를 사용한 JPQL 파라미터 처리

    /* 엔티티 클래스에 연관관계가 없는 경우의 조인처리
     * - @Query는 SQL과 유사하게 엔티티 클래스의 정보를 이용해서 쿼리(JPQL: Java Persistence Query Language)를 작성할 수 있다.
     * - on을 사용하여 조인 조건을 직접 지정한다.
     */
    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "SELECT b, w, count(r)"
            + " FROM Board b "
            + " LEFT JOIN b.writer w "
            + " LEFT JOIN Reply r ON r.board = b "
            + " GROUP BY b",
            countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);  // 목록화면에 필요한 데이터

    @Query("SELECT b, w, count(r) "
            + " FROM Board b "
            + " LEFT JOIN b.writer w "
            + " LEFT JOIN Reply r ON r.board = b "
            + " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
