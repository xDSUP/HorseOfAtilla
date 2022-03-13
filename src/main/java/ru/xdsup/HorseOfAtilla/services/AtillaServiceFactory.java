package ru.xdsup.HorseOfAtilla.services;

import org.springframework.stereotype.Service;

@Service
public class AtillaServiceFactory {
    public AtillaSolverService getQueueService(){
        return new AtillaSolverWithQueueService();
    }

    public AtillaSolverService getStackService(int maxCountSteps){
        return new AtillaSolverWithStackService(maxCountSteps);
    }
}
