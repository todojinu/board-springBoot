package org.zerock.boardspringBoot.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.QBoard;
import org.zerock.boardspringBoot.entity.QMember;
import org.zerock.boardspringBoot.entity.QReply;



import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage....................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //SELECT b, w, count(r) FROM Board b
        //LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            //검색조건 설정
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t:typeArr) {
                switch(t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }

            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        //ORDER BY
        Sort sort = pageable.getSort();

//        tuple.orderBy(board.bno.desc());  //직접 코드로 처리하면
        //JPQL은 Sort객체를 지원하지 않으므로 orderBy()의 경우 OrderSpecifier<T extends Comparable>을 파라미터로 처리해야함
        sort.stream().forEach(order -> {  //Sort 객체는 내부적으로 여러개의 Sort객체를 연결할 수 있으므로 forEach()를 이용해 처리
            Order direction = order.isAscending()? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            //Sort 객체의 속성(bno, title) 등은 PathBuilder로 처리
            //PathBuilder를 생성할 때 문자열로 된 이름은 JPQLQuery를 생성할 때 이용하는 변수명과 동일해야 함
            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(board);

        //page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();  //JPQLQuery 로 동적으로 검색조건을 처리하면 한번의 개발로 count 쿼리도 같이 처리할 수 있음

        log.info("COUNT: " + count);

        //searchPage()의 리턴 타입은 Page<Object[]> 타입이므로 메서드 내부에서 Page 타입의 객체를 생성해야함
        //Page는 인터페이스 타입이므로 PageImpl 클래스를 이용해서 생성
        //PageImpl(List<T> content, Pageable pageable, long total)
        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
