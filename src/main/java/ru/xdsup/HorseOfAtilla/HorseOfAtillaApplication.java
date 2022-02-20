package ru.xdsup.HorseOfAtilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.xdsup.HorseOfAtilla.core.Board;

@SpringBootApplication
public class HorseOfAtillaApplication {
	public static void main(String[] args) {
		SpringApplication.run(HorseOfAtillaApplication.class, args);
		Board.from(8,8 , "" +
				"12345678" +
				"12345678" +
				"123K5678" +
				"12345678" +
				"1234W678" +
				"12345678" +
				"12345678" +
				"12345678");
	}

}
