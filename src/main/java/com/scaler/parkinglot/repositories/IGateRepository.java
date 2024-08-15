package com.scaler.parkinglot.repositories;

import com.scaler.parkinglot.models.Gate;

import java.util.Optional;

public interface IGateRepository {
    Optional<Gate> findGateById(Long gateId);
}
