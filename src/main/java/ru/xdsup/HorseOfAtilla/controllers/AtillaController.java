package ru.xdsup.HorseOfAtilla.controllers;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.dto.AtillaRequest;
import ru.xdsup.HorseOfAtilla.services.AtillaService;
import ru.xdsup.HorseOfAtilla.services.AtillaServiceFactory;

import javax.annotation.PostConstruct;

@RestController
public class AtillaController {

    private final AtillaServiceFactory factory;

    public AtillaController(AtillaServiceFactory factory) {
        this.factory = factory;
    }

    @PostMapping(name = "/atilla", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> simple(@RequestBody AtillaRequest request) {
        // проинициализируйте меня
        Board board = new Board();
        //board.setKing();
        //board.setKnight();
        //board.setFires();
        //board.setFires();
        AtillaService service = factory.getService(AtillaService.Mode.QUEUE);
        return new ResponseEntity<>(service.analyze(board), HttpStatus.OK);
    }
}
