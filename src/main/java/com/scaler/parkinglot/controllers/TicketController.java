package com.scaler.parkinglot.controllers;

import com.scaler.parkinglot.dtos.GenerateTicketResponseDto;
import com.scaler.parkinglot.dtos.GenerateTiicketRequestDto;
import com.scaler.parkinglot.dtos.ResponseStatus;
import com.scaler.parkinglot.exception.InvalidGateException;
import com.scaler.parkinglot.exception.NoAvailableSpotException;
import com.scaler.parkinglot.models.Ticket;
import com.scaler.parkinglot.models.VehicleType;
import com.scaler.parkinglot.services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public GenerateTicketResponseDto generateTicket(GenerateTiicketRequestDto request){
        String vehicleNumber = request.getVehicleNumber();
        VehicleType vehicleType = request.getVehicleType();
        Long gateId = request.getGateId();

        Ticket ticket = new Ticket();
        GenerateTicketResponseDto response = new GenerateTicketResponseDto();

        try{
            ticket = ticketService.generateTicket(
                    gateId, vehicleType, vehicleNumber
            );
        }catch(InvalidGateException e){
            response.setResponseStatus(ResponseStatus.FAILURE);
            response.setMessage("Gate ID is invalid");
            return response;
        }catch(NoAvailableSpotException e){
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("NO Parking Spot Available");
            return response;
        }

        response.setResponseStatus(ResponseStatus.SUCCESS);
        response.setTicketId(ticket.getId());
        response.setOperatorName(ticket.getOperator().getName());
        response.setSpotNumber(ticket.getParkingSpot().getNumber());

        return response;
    }
}
