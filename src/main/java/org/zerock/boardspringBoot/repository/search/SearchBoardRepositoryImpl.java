package org.zerock.boardspringBoot.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.QBoard;
import org.zerock.boardspringBoot.entity.QMember;
import org.zerock.boardspringBoot.entity.QReply;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl
        extends QuerydslRepositorySupport  //Spring JPA 에 포함된 QuerydslRepositorySupport 는 Querydsl 라이브러리를 통해 추가된 Repository 의 기능을 추가할 수 있음
        implements SearchBoardRepository
{

//    public SearchBoardRepositoryImpl(Class<?> domainClass) {
//        super(domainClass);
//    }

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1.....................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //조회테스트
        //Querydsl 라이브러리의 JPQLQuery 인터페이스로 JQPL을 작성
//        JPQLQuery<Board> jpqlQuery = from(board);  //from으로 쿼리할 대상을 지정
//        jpqlQuery.select(board).where(board.bno.eq(1L));

        //JOIN 테스트
//        JPQLQuery<Board> jpqlQuery = from(board);
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));  //join(), leftJoin(), rightJoin() 으로 조인처리 하고 on()으로 조인조건을 작성

        //GROUP BY 테스트
//        JPQLQuery<Board> jpqlQuery = from(board);
//        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
//        jpqlQuery.select(board, member, reply.count()).groupBy(board);


//        log.info("---------------------------");
//        log.info(jpqlQuery);
//        log.info("---------------------------");
//
//        List<Board> result = jpqlQuery.fetch();  //실제 동작하는 SQL


        //Tuple 객체 테스트
        //엔티티 객체 단위가 아닌 각각의 데이터를 추출하는 경우 Tuple 객체를 이용
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);

        log.info("---------------------------");
        log.info(tuple);
        log.info("---------------------------");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }
}
