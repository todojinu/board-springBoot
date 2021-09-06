package org.zerock.boardspringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.boardspringBoot.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying  //JPQL을 이용해서 update, delete를 실행하기위해 추가해야하는 어노테이션션
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);

}
