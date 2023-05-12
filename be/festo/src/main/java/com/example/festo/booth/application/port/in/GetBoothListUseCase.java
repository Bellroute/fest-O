package com.example.festo.booth.application.port.in;

import com.example.festo.booth.domain.Booth;

import java.util.List;

public interface GetBoothListUseCase {
    List<Booth> getBoothList(Long fiestaId);
    List<Booth> getBoothListByOwner(Long ownerId);
}
