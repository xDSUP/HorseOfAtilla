package ru.xdsup.HorseOfAtilla.dto;

import lombok.Data;

@Data
public class AtillaRequest {
    String[] fire;
    String king;
    String horse;
    String findType;
    Integer maxMoveCount;
}
