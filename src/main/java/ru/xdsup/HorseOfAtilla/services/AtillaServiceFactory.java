package ru.xdsup.HorseOfAtilla.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;

@Service
@Log4j2
public class AtillaServiceFactory {
    public AtillaSolverService getQueueService(){
        return new AtillaSolverWithQueueService();
    }

    public AtillaSolverService getStackService(int maxCountSteps){
        return new AtillaSolverWithStackService(maxCountSteps);
    }

    public AtillaSolverService getHeuristicsService(){
        int KING_NOT_DEFEAT_POINT = 15;
        // эвристика манхэттенское расстояние
        return new AtillaSolverWithHeuristicsService(50, board -> {
            Coord start = board.getKnight().getCoords();
            Coord end = board.isKingDefeated()
                    ? board.getStartPosition()
                    : board.getKing().getCoords();
            int length = Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
            int kingDefeatedPoints = board.isKingDefeated() ? 0 : KING_NOT_DEFEAT_POINT;
            int result = board.getMoveCount() + length + kingDefeatedPoints;
            //log.info("board " + board.toString() + " оцнека - " + result);
            return result;
        });
    }

    public AtillaSolverService getHeuristicsService2(){
        // такая эвристика даёт обычный поиск в ширину
        return new AtillaSolverWithHeuristicsService(12, board -> {
            return board.getMoveCount();
        });
    }

    public AtillaSolverService getHeuristicsService3(){
        // эвристика "Метрика коня" http://kvant.mccme.ru/1981/10/metrika_konya.htm
        return new AtillaSolverWithHeuristicsService(1200, board -> {
            Coord start = board.getKnight().getCoords();
            Coord end = board.isKingDefeated()
                    ? board.getStartPosition()
                    : board.getKing().getCoords();
            // TODO: доделать
            return start.getHorseRange(end);
            //return board.getMoveCount();
        });
    }
}
