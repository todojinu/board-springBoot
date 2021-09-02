package org.zerock.boardspringBoot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.boardspringBoot.dto.BoardDTO;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.repository.BoardRepository;

@Service
@RequiredArgsConstructor  //final필드나 @NonNull이 붙은 필드에 대해 자동으로 생성자 생성
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;  //자동 주입 final

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }
}
