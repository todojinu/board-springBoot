package org.zerock.boardspringBoot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardspringBoot.dto.BoardDTO;
import org.zerock.boardspringBoot.dto.PageRequestDTO;
import org.zerock.boardspringBoot.dto.PageResultDTO;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Member;
import org.zerock.boardspringBoot.repository.BoardRepository;
import org.zerock.boardspringBoot.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor  //final필드나 @NonNull이 붙은 필드에 대해 자동으로 생성자 생성
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {  // 게시물 등록

        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {  //게시물 목록 조회

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDto((Board)en[0], (Member)en[1], (Long)en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount((pageRequestDTO.getPageable(Sort.by("bno").descending())));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {  //게시물 조회

        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDto((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {  //게시물 삭제

        //댓글 삭제
        replyRepository.deleteByBno(bno);

        //게시물 삭제
        repository.deleteById(bno);
    }
}
