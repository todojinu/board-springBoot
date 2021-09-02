package org.zerock.boardspringBoot.service;

import org.zerock.boardspringBoot.dto.BoardDTO;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto) {  // BoardDTO를 Board 엔티티 타입으로 변환하는 default메소드

        Member member = Member.builder().email(dto.getWriterEmail()).build();  // Member 엔티티객체 처리

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

}
