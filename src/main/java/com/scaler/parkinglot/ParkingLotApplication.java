package com.scaler.parkinglot;

import com.scaler.parkinglot.controllers.TicketController;
import com.scaler.parkinglot.repositories.GateRepository;
import com.scaler.parkinglot.repositories.ParkingLotRepository;
import com.scaler.parkinglot.repositories.TicketRepository;
import com.scaler.parkinglot.repositories.VehicleRepository;
import com.scaler.parkinglot.services.TicketService;
import com.scaler.parkinglot.strategies.spotassignmentstrategy.RandomSpotAssignmentStrategy;
import com.scaler.parkinglot.strategies.spotassignmentstrategy.SpotAssignmentStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingLotApplication {

	public static void main(String[] args) {
		GateRepository gateRepository = new GateRepository();
		ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
		TicketRepository ticketRepository = new TicketRepository();
		VehicleRepository vehicleRepository = new VehicleRepository();
		SpotAssignmentStrategy spotAssignmentStrategy = new RandomSpotAssignmentStrategy();

		TicketService ticketService = new TicketService(
                        gateRepository,
                        vehicleRepository,
                        ticketRepository,
				        parkingLotRepository,
                        spotAssignmentStrategy
                );

		TicketController ticketController = new TicketController(ticketService);
		System.out.println("Application has started on port: 8080");
	}

}
