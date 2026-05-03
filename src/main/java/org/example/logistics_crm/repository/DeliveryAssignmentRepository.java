package org.example.logistics_crm.repository;

import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long> {
    List<DeliveryAssignment> findAllByDriverIdAndDeliveryStatus(Long driverId, DeliveryStatus deliveryStatus);

    boolean existsByDriverIdAndDeliveryStatus(Long driverId, DeliveryStatus deliveryStatus);

    boolean existsByTruckIdAndDeliveryStatus(Long truckId, DeliveryStatus deliveryStatus);

    boolean existsByOrderIdAndDeliveryStatusIn(Long orderId, List<DeliveryStatus> deliveryStatus);
}
