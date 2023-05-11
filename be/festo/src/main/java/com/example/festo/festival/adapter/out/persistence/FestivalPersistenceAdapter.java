package com.example.festo.festival.adapter.out.persistence;

import com.example.festo.common.exception.CustomNoSuchException;
import com.example.festo.common.exception.ErrorCode;
import com.example.festo.festival.adapter.in.web.model.FestivalResponse;
import com.example.festo.festival.application.port.out.*;
import com.example.festo.festival.domain.Festival;
import com.example.festo.festival.domain.FestivalStatus;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class FestivalPersistenceAdapter implements SaveFestivalPort, LoadFestivalListPort, LoadFestivalIdPort, LoadInviteCodePort,LoadFestivalDetailPort {
    private final MemberRepository memberRepository;
    private final FestivalRepository festivalRepository;

    @Override
    public Long saveFestival(SaveFestivalCommand saveFestivalCommand, Long managerId) {
        Member manager = memberRepository.findById(managerId)
                                         .orElseThrow(() -> new CustomNoSuchException(ErrorCode.MEMBER_NOT_FOUND));

        String inviteCode = randomCode();

        FestivalEntity festivalEntity = FestivalEntity.builder()
                                                      .name(saveFestivalCommand.getFestivalName())
                                                      .description(saveFestivalCommand.getDescription())
                                                      .address(saveFestivalCommand.getLocation())
                                                      .startDate(saveFestivalCommand.getStartDate())
                                                      .endDate(saveFestivalCommand.getEndDate())
                                                      .imageUrl(saveFestivalCommand.getImageUrl())
                                                      .inviteCode(inviteCode)
                                                      .manager(manager)
                                                      .festivalStatus(FestivalStatus.CLOSE)
                                                      .build();

        FestivalEntity loadFestivalEntity = festivalRepository.save(festivalEntity);

        return loadFestivalEntity.getFestivalId();
    }

    @Override
    public Long updateSetImg(Long festivalId, String imgUrl) {
        FestivalEntity festivalEntity = festivalRepository.findById(festivalId)
                                                          .orElseThrow(() -> new CustomNoSuchException(ErrorCode.FESTIVAL_NOT_FOUND));
        festivalEntity.setImageUrl(imgUrl);
        festivalRepository.save(festivalEntity);
        return festivalEntity.getFestivalId();
    }

    private String randomCode() {
        boolean flag = false;
        String uniqueCode = "";
        while (!flag) {
            Random random = new Random();
            int randomNum = random.nextInt(900000) + 100000; // 100000 ~ 999999 범위의 랜덤한 정수

            uniqueCode = Integer.toString(randomNum);

            if (!festivalRepository.existsByInviteCode(uniqueCode)) {
                flag = true;
            }
        }
        return uniqueCode;
    }


    @Override
    public List<FestivalResponse.MainPage> findAllFestivals() {
        List<MainFestivalProjection> mainFestivalProjectionList = festivalRepository.findAllProjectedBy();

        List<FestivalResponse.MainPage> commandList = new ArrayList<>();
        for (MainFestivalProjection mainFestivalProjection : mainFestivalProjectionList) {
            FestivalResponse.MainPage command = FestivalResponse.MainPage.builder()
                                                                         .festivalId(mainFestivalProjection.getFestivalId())
                                                                         .imageUrl(mainFestivalProjection.getImageUrl())
                                                                         .name(mainFestivalProjection.getName())
                                                                         .build();

            commandList.add(command);
        }

        return commandList;
    }

    @Override
    public List<FestivalResponse.Search> findAllFestivalsBySearch(String keyword) {
        List<SearchFestivalProjection> searchFestivalProjectionList = festivalRepository.findByNameContaining(keyword);

        List<FestivalResponse.Search> commandList = new ArrayList<>();
        for (SearchFestivalProjection searchFestivalProjection : searchFestivalProjectionList) {
            FestivalResponse.Search command = FestivalResponse.Search.builder()
                                                                     .festivalId(searchFestivalProjection.getFestivalId())
                                                                     .imageUrl(searchFestivalProjection.getImageUrl())
                                                                     .name(searchFestivalProjection.getName())
                                                                     .build();

            commandList.add(command);
        }

        return commandList;
    }

    @Override
    public List<FestivalResponse.Manager> findAllFestivalsByManagerId(Long managerId) {
        List<MainFestivalProjection> mainFestivalProjectionList =festivalRepository.findAllProjectedByManagerId(managerId);
        List<FestivalResponse.Manager> commandList = new ArrayList<>();
        for(MainFestivalProjection mainFestivalProjection : mainFestivalProjectionList){
            FestivalResponse.Manager command = FestivalResponse.Manager.builder()
                    .festivalId(mainFestivalProjection.getFestivalId())
                    .imageUrl(mainFestivalProjection.getImageUrl())
                    .name(mainFestivalProjection.getName())
                    .build();

            commandList.add(command);
        }

        return commandList;
    }

    @Override
    public Long loadFestivalIdByInviteCode(String inviteCode) {
        FestivalEntity festivalEntity =festivalRepository.findByInviteCode(inviteCode).orElseThrow(() -> new CustomNoSuchException(ErrorCode.INVITE_CODE_NOT_FOUND));
        return festivalEntity.getFestivalId();
    }

    @Override
    public String loadInviteCodeByFestivalId(Long festivalId) {
        FestivalEntity festivalEntity = festivalRepository.findById(festivalId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.FESTIVAL_NOT_FOUND));
        return festivalEntity.getInviteCode();
    }

    @Override
    public Festival loadFestivalDetailByFestivalId(Long festivalId) {
        FestivalEntity entity = festivalRepository.findById(festivalId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.FESTIVAL_NOT_FOUND));
        Festival domain = new Festival(entity.getFestivalId(),entity.getName(),entity.getDescription(),entity.getInviteCode(),entity.getAddress(),entity.getStartDate(),entity.getEndDate(),entity.getImageUrl());
        return domain;
    }
}
