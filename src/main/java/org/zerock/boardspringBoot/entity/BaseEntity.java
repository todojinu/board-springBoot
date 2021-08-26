package org.zerock.boardspringBoot.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass  //객체의 입장에서 공통 매핑 정보가 필요할 때 사용
@EntityListeners(value = {AuditingEntityListener.class})  // JPA에서 엔티티 객체의 변경을 감지하는 리스너. AuditingEntityListener가 객체의 생성,변경의 감시를 수행하게 된다.
@Getter
abstract class BaseEntity {
    //BaseEntity는 모든 Entity의 상위 클래스가 되어 regDate와 modDate를 자동으로 관리한다.

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
