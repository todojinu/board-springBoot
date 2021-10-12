package org.zerock.boardspringBoot.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.QBoard;

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

        JPQLQuery<Board> jpqlQuery = from(board);  //Querydsl 라이브러리의 JPQLQuery 인터페이스로 JQPL을 작성

        jpqlQuery.select(board).where(board.bno.eq(1L));

        log.info("---------------------------");
        log.info(jpqlQuery);
        log.info("---------------------------");

        List<Board> result = jpqlQuery.fetch();  //실제 동작하는 SQL

        return null;
    }
}
