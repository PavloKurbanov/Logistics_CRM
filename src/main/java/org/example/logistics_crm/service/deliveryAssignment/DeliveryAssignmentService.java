package org.example.logistics_crm.service.deliveryAssignment;

import org.example.logistics_crm.dto.deliveryAssignment.request.CreateDeliveryAssignmentRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.request.DeliveryAssignmentSearchRequestDTO;
import org.example.logistics_crm.dto.deliveryAssignment.response.DeliveryAssignmentDetailsResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryAssignmentService {
    DeliveryAssignmentDetailsResponseDTO createDeliveryAssignment(CreateDeliveryAssignmentRequestDTO requestDTO);

    DeliveryAssignmentDetailsResponseDTO findDeliveryAssignmentById(Long deliveryAssignmentId);

    Page<DeliveryAssignmentDetailsResponseDTO> findAllDeliveryAssignments(Pageable pageable);

    Page<DeliveryAssignmentDetailsResponseDTO> search(DeliveryAssignmentSearchRequestDTO requestDTO, Pageable pageable);

    void deleteDeliveryAssignment(Long deliveryAssignmentId);
}
