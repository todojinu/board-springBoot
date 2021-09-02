package org.zerock.boardspringBoot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {  // 화면에 전달하거나 전달받는 데이터를 다루기 위한 DTO 생성

    private Long bno;

    private String title;

    private String content;

    private String writerEmail;  //작성자의 이메일(id)

    private String writerName;  //작성자의 이름

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount;  //해당 게시글의 댓글 수

}
