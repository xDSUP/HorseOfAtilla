package ru.xdsup.HorseOfAtilla.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.dto.AtillaRequest;
import ru.xdsup.HorseOfAtilla.services.AtillaService;
import ru.xdsup.HorseOfAtilla.services.AtillaServiceFactory;

@Controller
public class AtillaController {

    private final AtillaServiceFactory factory;

    public AtillaController(AtillaServiceFactory factory) {
        this.factory = factory;
    }

    @GetMapping
    public String get(){
        return "index";
    }

    @PostMapping(name = "/atilla", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
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
