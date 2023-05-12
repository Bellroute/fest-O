package com.example.festo.festival.application.port.in;

import com.example.festo.festival.adapter.in.web.model.FestivalResponse;
import com.example.festo.festival.domain.Festival;

public interface GetFestivalDetailUseCase {
    Festival getFestivalDetailByFestivalId(Long festivalId);
    boolean isOpenByManagerId(Long mangerId);
}
