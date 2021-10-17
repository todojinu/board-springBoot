package org.zerock.boardspringBoot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.boardspringBoot.dto.ReplyDTO;
import org.zerock.boardspringBoot.entity.Board;
import org.zerock.boardspringBoot.entity.Reply;
import org.zerock.boardspringBoot.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);

        return reply.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return result.stream()
                .map(reply -> entityToDto(reply))  //reply를 replyDTO로 변환
                .collect(Collectors.toList());     //스트림에서 작업한 결과를 담은 리스트 반환
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);

        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
