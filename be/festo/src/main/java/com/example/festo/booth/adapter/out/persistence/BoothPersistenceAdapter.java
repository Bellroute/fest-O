package com.example.festo.booth.adapter.out.persistence;

import com.example.festo.booth.adapter.in.web.model.FiestaResponse;
import com.example.festo.booth.application.port.out.*;
import com.example.festo.booth.domain.Booth;
import com.example.festo.booth.domain.BoothStatus;
import com.example.festo.common.exception.CustomNoSuchException;
import com.example.festo.common.exception.ErrorCode;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.festival.adapter.out.persistence.FestivalRepository;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoothPersistenceAdapter implements SaveBoothPort, LoadFiestaListPort, LoadBoothStatusPort, LoadBoothPort {
    //리포지토리 가져오기
    private final BoothRepository boothRepository;
    private final MemberRepository memberRepository;
    private final FestivalRepository festivalRepository;

    @Override
    public Long saveBooth(SaveBoothCommand saveBoothCommand, Long ownerId, Long festivalId) {
        Member owner = memberRepository.findById(ownerId).orElseThrow(()-> new CustomNoSuchException(ErrorCode.MEMBER_NOT_FOUND));
        FestivalEntity festival = festivalRepository.findById(festivalId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.FESTIVAL_NOT_FOUND));

        BoothEntity boothEntity = BoothEntity.builder()
                .name(saveBoothCommand.getBoothName())
                .openTime(saveBoothCommand.getOpenTime())
                .closeTime(saveBoothCommand.getCloseTime())
                .locationDescription(saveBoothCommand.getLocation())
                .boothDescription(saveBoothCommand.getDescription())
                .imageUrl(saveBoothCommand.getImageUrl())
//                .category()
                .boothStatus(BoothStatus.CLOSE)
                .owner(owner)
                .festival(festival)
                .build();

        BoothEntity loadBoothEntity = boothRepository.save(boothEntity);

        return loadBoothEntity.getBoothId();
    }

    @Override
    public Long updateSetImg(Long boothId, String imgUrl) {
        BoothEntity boothEntity = boothRepository.findById(boothId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.BOOTH_NOT_FOUND));
        boothEntity.setImageUrl(imgUrl);
        boothRepository.save(boothEntity);
        return boothEntity.getBoothId();
    }

    @Override
    public List<FiestaResponse.Owner> loadFiestaListByOwnerId(Long ownerId) {
        List<FestivalEntity> festivalhList = boothRepository.findDistinctFestivalsByOwnerId(ownerId);
        List<FiestaResponse.Owner> fiestaList = new ArrayList<>();
        for(FestivalEntity festival : festivalhList){
            FiestaResponse.Owner domain = FiestaResponse.Owner.builder()
                    .festivalId(festival.getFestivalId())
                    .name(festival.getName())
                    .imageUrl(festival.getImageUrl())
                    .build();

            fiestaList.add(domain);
        }


        return fiestaList;
    }

    @Override
    public BoothStatus loadBoothStatus(Long boothId) {
        BoothEntity booth =boothRepository.findById(boothId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.BOOTH_NOT_FOUND));
        return booth.getBoothStatus();
    }

    @Override
    public void setBoothStatus(BoothStatus boothStatus,Long boothId) {
        BoothEntity booth = boothRepository.findById(boothId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.BOOTH_NOT_FOUND));
        booth.setBoothStatus(boothStatus);
        boothRepository.save(booth);
    }

    @Override
    public List<Booth> loadBoothByFiestaId(Long fiestaId) {
        List<BoothEntity> entityList = boothRepository.findAllByFestivalFestivalId(fiestaId);
        List<Booth> domainList = new ArrayList<>();
        for(BoothEntity entity : entityList){
            Booth domain = Booth.builder()
                    .boothId(entity.getBoothId())
                    .name(entity.getName())
                    .boothDescription(entity.getBoothDescription())
                    .imageUrl(entity.getImageUrl())
                    .build();

            domainList.add(domain);
        }

        return domainList;
    }

    @Override
    public Booth loadBoothById(Long boothId) {
        BoothEntity entity = boothRepository.findById(boothId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.BOOTH_NOT_FOUND));
        Booth domain = Booth.builder()
                .boothId(entity.getBoothId())
                .name(entity.getName())
                .boothDescription(entity.getBoothDescription())
                .imageUrl(entity.getImageUrl())
                .status(entity.getBoothStatus())
                .openTime(entity.getOpenTime())
                .closeTime(entity.getCloseTime())
                .locationDescription(entity.getLocationDescription())
                .build();
        return domain;
    }

    @Override
    public List<Booth> loadBoothByOwnerId(Long ownerId) {
        List<BoothEntity> entityList = boothRepository.findAllByOwnerId(ownerId);
        List<Booth> domainList = new ArrayList<>();
        for(BoothEntity entity : entityList){
            Booth domain = Booth.builder()
                    .boothId(entity.getBoothId())
                    .name(entity.getName())
                    .boothDescription(entity.getBoothDescription())
                    .imageUrl(entity.getImageUrl())
                    .build();

            domainList.add(domain);
        }

        return domainList;
    }
}
