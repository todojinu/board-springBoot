package org.zerock.boardspringBoot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")  // toString()으로 출력시 writer는 제외 -> 지연로딩시 연관 객체 출력으로 인해 에러 발생을 막기 위해 exclude 속성 추가
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

//    @ManyToOne  // fetch 속성을 지정하지 않으면 모든 연관관계에 있는 데이터를 가져오게 된다(Eager Loading, 즉시로딩)
    @ManyToOne(fetch = FetchType.LAZY)  // 명시적으로 Lazy 로딩 지정(Lazy Loading, 지연로딩). -> 지연 로딩을 기본으로 사용하고, 상황에 맞게 필요한 방법을 찾는것이 좋음
    private Member writer;  //연관관계 지정.

}
