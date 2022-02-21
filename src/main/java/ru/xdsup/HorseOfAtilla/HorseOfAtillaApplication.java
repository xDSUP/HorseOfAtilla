package ru.xdsup.HorseOfAtilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.xdsup.HorseOfAtilla.core.Board;

@SpringBootApplication
public class HorseOfAtillaApplication {
	public static void main(String[] args) {
		SpringApplication.run(HorseOfAtillaApplication.class, args);
	}

}
