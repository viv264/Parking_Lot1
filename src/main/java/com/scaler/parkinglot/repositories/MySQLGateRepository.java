package com.scaler.parkinglot.repositories;

import com.scaler.parkinglot.models.Gate;

import java.util.Optional;

public class MySQLGateRepository implements IGateRepository{
    @Override
    public Optional<Gate> findGateById(Long gateId) {
        return Optional.empty();
    }
}
