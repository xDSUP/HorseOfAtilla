package ru.xdsup.HorseOfAtilla.dto;

import lombok.Data;
import ru.xdsup.HorseOfAtilla.core.Statistic;

@Data
public class AtillaResponse {
    String[] fire;
    String king;
    String[] path;
    Boolean isResolved;
    Statistic statistic;
}
