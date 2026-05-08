package org.example.logistics_crm.service.deliveryAssignment;

import org.example.logistics_crm.dto.deliveryAssignment.CreateDeliveryAssignmentRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.DeliveryAssignmentDetailsResponseDTO;
import org.example.logistics_crm.dto.deliveryAssignment.DeliveryAssignmentSearchRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DeliveryAssignmentService {
    DeliveryAssignmentDetailsResponseDTO createDeliveryAssignment(CreateDeliveryAssignmentRequestDTO requestDTO);

    DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentById(Long deliveryAssignmentId);

    DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentByStatus(Long deliveryAssignmentId, Long deliveryId);

    Page<DeliveryAssignmentDetailsResponseDTO> findAllDeliveryAssignments(Pageable pageable);

    Page<DeliveryAssignmentDetailsResponseDTO> search(DeliveryAssignmentSearchRequestDTO requestDTO, Pageable pageable);
}
