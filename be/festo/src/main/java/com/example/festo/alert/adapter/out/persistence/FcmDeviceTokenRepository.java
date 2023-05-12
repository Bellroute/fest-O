package com.example.festo.alert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmDeviceTokenRepository extends JpaRepository<FcmDeviceTokenEntity, Long> {
}
