package org.zerock.boardspringBoot.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {  //DTO, Entity 타입

    //DTO 리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지번호 목록
    private List<Integer> pageList;

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int endPage = (int)(Math.ceil(page / 10.0)) * 10;  //마지막 페이지 계산

        start = endPage - 9;

        end = totalPage > endPage ? endPage : totalPage;

        prev = start > 1;

        next = totalPage > endPage;

        pageList = IntStream.rangeClosed(start, end)
                .boxed()                        //int를 Integer로 래핑
                .collect(Collectors.toList());  //Stream을 로 반환
    }

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

}
