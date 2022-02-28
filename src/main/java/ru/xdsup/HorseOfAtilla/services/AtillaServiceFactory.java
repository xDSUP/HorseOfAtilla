package ru.xdsup.HorseOfAtilla.services;

import org.springframework.stereotype.Service;

@Service
public class AtillaServiceFactory {
    public AtillaService getService(AtillaService.Mode mode){
        return new AtillaService(mode);
    }
}
