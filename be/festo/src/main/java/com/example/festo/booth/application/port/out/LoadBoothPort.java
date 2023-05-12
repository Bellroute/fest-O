package com.example.festo.booth.application.port.out;

import com.example.festo.booth.domain.Booth;

import java.util.List;

public interface LoadBoothPort {
    List<Booth> loadBoothByFiestaId(Long fiestaId);
    Booth loadBoothById(Long boothId);
    List<Booth> loadBoothByOwnerId(Long ownerId);
}
