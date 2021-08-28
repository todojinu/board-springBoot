package org.zerock.boardspringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.boardspringBoot.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
