package com.scaler.parkinglot.services;

import com.scaler.parkinglot.exception.InvalidGateException;
import com.scaler.parkinglot.exception.NoAvailableSpotException;
import com.scaler.parkinglot.models.*;
import com.scaler.parkinglot.repositories.IGateRepository;
import com.scaler.parkinglot.repositories.ParkingLotRepository;
import com.scaler.parkinglot.repositories.TicketRepository;
import com.scaler.parkinglot.repositories.VehicleRepository;
import com.scaler.parkinglot.strategies.spotassignmentstrategy.SpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;

public class TicketService {
    private IGateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private TicketRepository ticketRepository;
    private ParkingLotRepository parkingLotRepository;
    private SpotAssignmentStrategy spotAssignmentStrategy;

    public TicketService(IGateRepository gateRepository,
                         VehicleRepository vehicleRepository,
                         TicketRepository ticketRepository,
                         ParkingLotRepository parkingLotRepository,
                         SpotAssignmentStrategy spotAssignmentStrategy) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.spotAssignmentStrategy = spotAssignmentStrategy;
    }

    public Ticket generateTicket(
            Long gateId, VehicleType vehicleType, String vehicleNumber
    ) throws InvalidGateException, NoAvailableSpotException{

        Optional<Gate> gateOptional = gateRepository.findGateById(gateId);

        if(gateOptional.isEmpty()){
            throw new InvalidGateException();
        }

        Gate gate = gateOptional.get();
        Operator operator = gate.getCurrentOperator();
        Optional<Vehicle> vehicleOptional = vehicleRepository.findVehicleByNumber(vehicleNumber);

        Vehicle vehicle;

        if(vehicleOptional.isEmpty()){
            vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicle = vehicleRepository.save(vehicle);
        }else{
            vehicle = vehicleOptional.get();
        }

        Optional<ParkingLot> parkingLot = parkingLotRepository.getParkingLotOfGate(gate);

        if(parkingLot.isEmpty()){
            throw new RuntimeException();
        }

        Optional<ParkingSpot> parkingSpotOptional = spotAssignmentStrategy.findSpot(vehicleType, parkingLot.get(), gate);

        if(parkingSpotOptional.isEmpty()){
            throw new NoAvailableSpotException();
        }

        ParkingSpot parkingSpot = parkingSpotOptional.get();

        Ticket ticket = new Ticket();
        ticket.setParkingSpot(parkingSpot);
        ticket.setGate(gate);
        ticket.setEntryTime(new Date());
        ticket.setVehicle(vehicle);
        ticket.setOperator(operator);

        return ticketRepository.save(ticket);
    }
}
