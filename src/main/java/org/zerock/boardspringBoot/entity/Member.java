package org.zerock.boardspringBoot.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {

    @Id  //엔티티의 기본키를 설정
    private String email;

    private String password;

    private String name;

}
