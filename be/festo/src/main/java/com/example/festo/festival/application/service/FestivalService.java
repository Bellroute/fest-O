package com.example.festo.festival.application.service;


import com.example.festo.common.exception.CustomIsPresentException;
import com.example.festo.festival.adapter.in.web.model.FestivalResponse;

import com.example.festo.festival.application.port.in.*;
import com.example.festo.festival.application.port.out.*;
import com.example.festo.festival.domain.Festival;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalService implements RegisterFestivalUseCase, GetFestivalsUseCase, GetFestivalIdUseCase, GetInviteCodeUseCase,GetFestivalDetailUseCase {
    private final SaveImgPort saveImgPort;
    private final SaveFestivalPort saveFestivalPort;
    private final LoadFestivalListPort loadFestivalListPort;
    private final LoadFestivalIdPort loadFestivalIdPort;
    private final LoadInviteCodePort loadInviteCodePort;
    private final LoadFestivalDetailPort loadFestivalDetailPort;
    private final LoadIsOpenFestivalPort loadIsOpenFestivalPort;


    @Override
    public Long registerFestival(RegisterFestivalCommand registerFestivalCommand) {
        SaveFestivalCommand saveFestivalCommand = SaveFestivalCommand.builder()
                .festivalName(registerFestivalCommand.getFestivalName())
                .description(registerFestivalCommand.getDescription())
                .location(registerFestivalCommand.getLocation())
                .startDate(registerFestivalCommand.getStartDate())
                .endDate(registerFestivalCommand.getEndDate())
                .imageUrl("")
                .build();

        Long saveFestivalId = saveFestivalPort.saveFestival(saveFestivalCommand,registerFestivalCommand.getManagerId());

        String imgUrl=null;

        if(!registerFestivalCommand.getImg().isEmpty()){
            imgUrl = saveImgPort.saveFestivalImg(registerFestivalCommand.getImg(),saveFestivalId);
        }

        saveFestivalId = saveFestivalPort.updateSetImg(saveFestivalId,imgUrl);
        return saveFestivalId;
    }

    @Override
    public List<FestivalResponse.MainPage> getFestivalByMain() {
        return loadFestivalListPort.findAllFestivals();
    }

    @Override
    public List<FestivalResponse.Search> getFestivalBySearch(String keyword) {
        return loadFestivalListPort.findAllFestivalsBySearch(keyword);
    }

    @Override
    public List<FestivalResponse.Manager> getFestivalByManager(Long managerId) {
        return loadFestivalListPort.findAllFestivalsByManagerId(managerId);
    }

    @Override
    public Long getFestivalIdByInviteCode(String inviteCode) {
        return loadFestivalIdPort.loadFestivalIdByInviteCode(inviteCode);
    }

    @Override
    public String getInviteCodeById(Long festivalId) {
        return loadInviteCodePort.loadInviteCodeByFestivalId(festivalId);
    }

    @Override
    public Festival getFestivalDetailByFestivalId(Long festivalId) {
        Festival domain = loadFestivalDetailPort.loadFestivalDetailByFestivalId(festivalId);
        return domain;
    }

    @Override
    public boolean isOpenByManagerId(Long mangerId) {
        return loadIsOpenFestivalPort.isOpenFestivalByManagerId(mangerId);
    }
}
