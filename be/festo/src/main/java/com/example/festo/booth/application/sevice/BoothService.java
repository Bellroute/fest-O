package com.example.festo.booth.application.sevice;

import com.example.festo.booth.adapter.in.web.model.FiestaResponse;
import com.example.festo.booth.application.port.in.*;
import com.example.festo.booth.application.port.out.*;
import com.example.festo.booth.domain.Booth;
import com.example.festo.booth.domain.BoothStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class BoothService implements RegisterBoothUseCase, GetFiestaListUseCase, ChangeBoothStatusUseCase, GetBoothDetailUseCase,GetBoothListUseCase {
    private final SaveImgPort saveImgPort;
    private final SaveBoothPort saveBoothPort;
    private final LoadFiestaListPort loadFiestaListPort;
    private final LoadBoothStatusPort loadBoothStatusPort;
    private final LoadBoothPort loadBoothPort;
    @Override
    public Long registerBooth(RegisterBoothCommand registerBoothCommand) {
        SaveBoothCommand saveBoothCommand = SaveBoothCommand.builder()
                .boothName(registerBoothCommand.getBoothName())
                .description(registerBoothCommand.getDescription())
                .location(registerBoothCommand.getLocation())
                .openTime(registerBoothCommand.getOpenTime())
                .closeTime(registerBoothCommand.getCloseTime())
                .imageUrl("")
                .build();

        Long saveBoothId = saveBoothPort.saveBooth(saveBoothCommand, registerBoothCommand.getOwnerId(), registerBoothCommand.getFestivalId());
        String imgUrl=null;
        if(!registerBoothCommand.getImg().isEmpty()){
            imgUrl = saveImgPort.saveBoothImg(registerBoothCommand.getImg(),saveBoothId);
        }

        saveBoothId = saveBoothPort.updateSetImg(saveBoothId,imgUrl);

        return saveBoothId;
    }

    @Override
    public List<FiestaResponse.Owner> getFiestaListByOwner(Long ownerId) {
        return loadFiestaListPort.loadFiestaListByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public boolean changeBoothStatus(String status,Long boothId) {
        log.info("확인 "+status + " 체크"  + loadBoothStatusPort.loadBoothStatus(boothId).name());
        if(status.equals(loadBoothStatusPort.loadBoothStatus(boothId).name())){
            return false;
        }
        else{
            if(status.equals("OPEN")){
                loadBoothStatusPort.setBoothStatus(BoothStatus.OPEN,boothId);
            }
            else if (status.equals("CLOSE")){
                loadBoothStatusPort.setBoothStatus(BoothStatus.CLOSE,boothId);
            }
            else{
                return false;
            }
            return true;
        }
    }

    @Override
    public Booth getBoothDetail(Long boothId) {
        return loadBoothPort.loadBoothById(boothId);
    }

    @Override
    public List<Booth> getBoothList(Long fiestaId) {
        return loadBoothPort.loadBoothByFiestaId(fiestaId);
    }

    @Override
    public List<Booth> getBoothListByOwner(Long ownerId) {
        return loadBoothPort.loadBoothByOwnerId(ownerId);
    }
}
