package org.zerock.boardspringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.boardspringBoot.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
