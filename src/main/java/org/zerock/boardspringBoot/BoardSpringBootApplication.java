package org.zerock.boardspringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  //JPA의 Auditing 어노테이션들을 활성화 시킨다.
public class BoardSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardSpringBootApplication.class, args);
	}

}
