package org.zerock.boardspringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.boardspringBoot.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
