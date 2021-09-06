package org.zerock.boardspringBoot.service;

import org.zerock.boardspringBoot.dto.BoardDTO;
import org.zerock.boardspringBoot.dto.PageRequestDTO;
import org.zerock.boardspringBoot.dto.PageResultDTO;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);  // 게시물 등록

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);  // 목록처리

    BoardDTO get(Long bno);  // 게시물 조회

    default Board dtoToEntity(BoardDTO dto) {  //BoardDTO를 Board 엔티티 타입으로 변환하는 default메소드

        Member member = Member.builder().email(dto.getWriterEmail()).build();  // Member 엔티티객체 처리

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    default BoardDTO entityToDto(Board board, Member member, Long replyCount) {  //JPQL의 결과(Object[])를 DTO타입으로 변경

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;

    }

}
