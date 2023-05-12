package com.example.festo.festival.adapter.in.web;

import com.example.festo.common.exception.CustomIsPresentException;
import com.example.festo.festival.adapter.in.web.model.FestivalRequest;
import com.example.festo.festival.adapter.in.web.model.FestivalResponse;
import com.example.festo.festival.application.port.in.*;
import com.example.festo.festival.domain.Festival;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FestivalController {

    private final RegisterFestivalUseCase registerFestivalUseCase;
    private final GetFestivalsUseCase getFestivalsUseCase;
    private final GetFestivalIdUseCase getFestivalIdUseCase;
    private final GetInviteCodeUseCase getInviteCodeUseCase;
    private final GetFestivalDetailUseCase getFestivalDetailUseCase;

    @PostMapping("festivals")
    public ResponseEntity<FestivalResponse.Creation> createFestival(@Valid @RequestPart("request") FestivalRequest request, @RequestPart("festivalImg") MultipartFile festivalImg) {
        log.info("페스티벌 등록 컨트롤러 시작");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();

        RegisterFestivalCommand command = RegisterFestivalCommand.builder()
                                                                 .festivalName(request.getFestivalName())
                                                                 .location(request.getLocation())
                                                                 .description(request.getDescription())
                                                                 .startDate(request.getStartDate())
                                                                 .endDate(request.getEndDate())
                                                                 .img(festivalImg)
                                                                 .managerId(Long.parseLong(user.getUsername()))
                                                                 .build();

        Long festivalId = registerFestivalUseCase.registerFestival(command);

        return new ResponseEntity<>(new FestivalResponse.Creation(festivalId), HttpStatus.OK);
    }

    @GetMapping("festivals")
    public ResponseEntity<List<FestivalResponse.MainPage>> getFestivals() {
        log.info("메인 페이지 페스티벌 조회 컨트롤러 시작");
        List<FestivalResponse.MainPage> festivalList = getFestivalsUseCase.getFestivalByMain();
        return new ResponseEntity<List<FestivalResponse.MainPage>>(festivalList, HttpStatus.OK);
    }

    @GetMapping("festivals/search")
    public ResponseEntity<List<FestivalResponse.Search>> getFestivalsBySearch(@RequestParam(value = "keyword") String keyword) {
        log.info("페스티벌 검색 조회 컨트롤러 시작");
        List<FestivalResponse.Search> festivalList = getFestivalsUseCase.getFestivalBySearch(keyword);
        return new ResponseEntity<List<FestivalResponse.Search>>(festivalList, HttpStatus.OK);
    }

    @GetMapping("festivals/invitation")
    public ResponseEntity<?> getFestivalIdByInviteCode(@RequestParam("inviteCode") String inviteCode) {
        log.info("페스티벌 초대코드 입력 컨트롤러 시작");
        if(inviteCode.length() != 6) {
            return new ResponseEntity<>("초대코드는 총 6자리입니다.", HttpStatus.BAD_REQUEST);
        }

        Long festivalId = getFestivalIdUseCase.getFestivalIdByInviteCode(inviteCode);

        return new ResponseEntity<>(new FestivalResponse.Invitation(festivalId), HttpStatus.OK);
    }


    @GetMapping("festivals/{festival_id}/invitecode")
    public ResponseEntity<?> getFestivalIdByInviteCode(@PathVariable("festival_id") Long festivalId) {
        log.info("페스티벌 초대코드 조회 컨트롤러 시작");
        String inviteCode = getInviteCodeUseCase.getInviteCodeById(festivalId);
        FestivalResponse.InviteCode inviteCodeResponse = new FestivalResponse.InviteCode(inviteCode);
        return new ResponseEntity<FestivalResponse.InviteCode>(inviteCodeResponse, HttpStatus.OK);
    }

    @GetMapping("festivals/manager")
    public ResponseEntity<?> getFestivalsBymanagerId() {
        log.info("페스티벌 초대코드 조회 컨트롤러 시작");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();
        List<FestivalResponse.Manager> festivalList = getFestivalsUseCase.getFestivalByManager(Long.parseLong(user.getUsername()));
        return new ResponseEntity<List<FestivalResponse.Manager>>(festivalList, HttpStatus.OK);
    }

    @GetMapping("festivals/{festival_id}")
    public ResponseEntity<?> getFestivalDetail(@PathVariable("festival_id") Long festivalId) {
        log.info("페스티벌 상세정보 조회 컨트롤러 시작");

        Festival domain = getFestivalDetailUseCase.getFestivalDetailByFestivalId(festivalId);

        FestivalResponse.Detail detail = FestivalResponse.Detail.builder()
                                                                .name(domain.getName())
                                                                .address(domain.getAddress())
                                                                .imageUrl(domain.getImageUrl())
                                                                .festivalId(festivalId)
                                                                .startDate(domain.getStartDate())
                                                                .endDate(domain.getEndDate())
                                                                .description(domain.getDescription())
                                                                .build();

        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
    @GetMapping("festival/{manager_id}/manager")
    public ResponseEntity<?> isOpenFestival(@PathVariable("manager_id") Long managerId) {
        log.info("manager가 축제 개설 여부 조회 컨트롤러 시작");

        boolean isOpen = getFestivalDetailUseCase.isOpenByManagerId(managerId);
        FestivalResponse.IsOpen isOpenResponse = new FestivalResponse.IsOpen(isOpen);
        return new ResponseEntity<>(isOpenResponse, HttpStatus.OK);
    }



}
