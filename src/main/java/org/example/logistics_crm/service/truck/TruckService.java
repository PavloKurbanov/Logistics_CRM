package org.example.logistics_crm.service.truck;

import org.example.logistics_crm.dto.truck.request.CreateTruckRequestDTO;
import org.example.logistics_crm.dto.truck.response.TruckDetailsResponseDTO;

public interface TruckService {
    TruckDetailsResponseDTO createTruck(CreateTruckRequestDTO request);

}
