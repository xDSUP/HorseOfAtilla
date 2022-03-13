package ru.xdsup.HorseOfAtilla.services;

import org.springframework.stereotype.Service;
import ru.xdsup.HorseOfAtilla.core.figures.Coord;

@Service
public class AtillaServiceFactory {
    public AtillaSolverService getQueueService(){
        return new AtillaSolverWithQueueService();
    }

    public AtillaSolverService getStackService(int maxCountSteps){
        return new AtillaSolverWithStackService(maxCountSteps);
    }

    public AtillaSolverService getHeuristicsService(){
        // эвристика манхэттенское расстояние
        return new AtillaSolverWithHeuristicsService(12, board -> {
            Coord start = board.getKnight().getCoords();
            Coord end = board.isKingDefeated()
                    ? board.getStartPosition()
                    : board.getKing().getCoords();
            int length = Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
            return board.getMoveCount() + length;
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
        return new AtillaSolverWithHeuristicsService(12, board -> {
            Coord start = board.getKnight().getCoords();
            Coord end = board.isKingDefeated()
                    ? board.getStartPosition()
                    : board.getKing().getCoords();
            // TODO: доделать
            return board.getMoveCount();
        });
    }
}
