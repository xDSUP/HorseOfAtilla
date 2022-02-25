package ru.xdsup.HorseOfAtilla.dto;

import lombok.Data;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;
@Data
public class AtillaRequest {
    String[] fire;
    String king;
    String horse;
    String findType;
}
