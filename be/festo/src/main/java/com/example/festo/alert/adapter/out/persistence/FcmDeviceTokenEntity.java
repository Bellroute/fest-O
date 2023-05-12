package com.example.festo.alert.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm_device_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmDeviceTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    private String token;

    public FcmDeviceTokenEntity(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}

