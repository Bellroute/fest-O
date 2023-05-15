package com.example.festo.mock;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.booth.adapter.out.persistence.BoothRepository;
import com.example.festo.booth.domain.BoothStatus;
import com.example.festo.common.model.Money;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.festival.adapter.out.persistence.FestivalRepository;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import com.example.festo.product.adapter.out.persistence.ProductEntity;
import com.example.festo.product.adapter.out.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MockDataController {

    private final MemberRepository memberRepository;

    private final BoothRepository boothRepository;

    private final ProductRepository productRepository;

    private final FestivalRepository festivalRepository;


    @PostMapping("/mock/booths/{memberId}/{festivalId}")
    public ResponseEntity<Void> addBooths(@PathVariable("memberId") Long memberId, @PathVariable("festivalId") Long festivalId, @RequestBody List<MockBoothRequest> mockBoothRequest) {

        Member member = memberRepository.findById(memberId)
                                        .get();
        FestivalEntity festival = festivalRepository.findById(festivalId)
                                                    .get();

        for (MockBoothRequest mockBooth : mockBoothRequest) {
            System.out.println(mockBooth.toString());
            int count = (int) (Math.random() * 10) + 1;

            BoothEntity booth = BoothEntity.builder()
                                           .name(mockBooth.getBoothName())
                                           // .category()
                                           .imageUrl(mockBooth.getBoothImageUrl())
                                           .openTime(LocalTime.of(10, 0))
                                           .closeTime(LocalTime.of(20, 0))
                                           .festival(festival)
                                           .owner(member)
                                           .boothDescription("소개 없음")
                                           .boothStatus(BoothStatus.CLOSE)
                                           .locationDescription("입구에서 " + count + "번 째 부스")
                                           .build();

            booth = boothRepository.save(booth);

            for (MenuRequest menuRequest : mockBooth.getMenu()) {

                ProductEntity product = ProductEntity.builder()
                                                     .booth(booth)
                                                     .price(new Money(Integer.parseInt(menuRequest.getPrice())))
                                                     .imageUrl(menuRequest.getImageUrl())
                                                     .name(menuRequest.getName())
                                                     .build();

                productRepository.save(product);
            }

        }
        return ResponseEntity.ok()
                             .build();
    }
}
