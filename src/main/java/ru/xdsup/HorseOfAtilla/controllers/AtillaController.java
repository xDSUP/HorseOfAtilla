package ru.xdsup.HorseOfAtilla.controllers;

import lombok.val;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.xdsup.HorseOfAtilla.Utils;
import ru.xdsup.HorseOfAtilla.core.Board;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
import ru.xdsup.HorseOfAtilla.core.figures.Figure;
import ru.xdsup.HorseOfAtilla.core.figures.King;
import ru.xdsup.HorseOfAtilla.core.figures.Knight;
import ru.xdsup.HorseOfAtilla.dto.AtillaRequest;
import ru.xdsup.HorseOfAtilla.dto.AtillaResponse;
import ru.xdsup.HorseOfAtilla.services.AtillaService;
import ru.xdsup.HorseOfAtilla.services.AtillaServiceFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    @PostMapping(value = "/atilla", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AtillaResponse> simple(@RequestBody AtillaRequest request) {
        Board board = new Board();
        board.setStartPosition(Utils.toChessNotation(request.getHorse()));
        board.setKing(new King(Utils.toChessNotation(request.getKing())));
        board.setKnight(new Knight(Utils.toChessNotation(request.getHorse())));
        board.setFires(Arrays.stream(request.getFire())
                .map(Utils::toChessNotation)
                .collect(Collectors.toMap(entry -> entry, entry -> new Figure((Coord) entry), (prev, next) -> next, HashMap::new)));
        AtillaService service = "queue".equals(request.getFindType()) ? factory.getService(AtillaService.Mode.QUEUE) : factory.getService(AtillaService.Mode.STACK);
        val response = new AtillaResponse();
        response.setKing(request.getKing());
        response.setFire(request.getFire());
        val stopwatch = new StopWatch();
        stopwatch.start();
        response.setPath(Utils.toArray(service.analyze(board)));
        stopwatch.stop();
        response.setNanoTime(stopwatch.getNanoTime());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
