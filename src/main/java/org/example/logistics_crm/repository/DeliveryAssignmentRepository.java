package org.example.logistics_crm.repository;

import org.example.logistics_crm.entity.deliveryAssignment.DeliveryAssignment;
import org.example.logistics_crm.entity.deliveryAssignment.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long>
        , JpaSpecificationExecutor<DeliveryAssignment> {

    boolean existsByOrderIdAndDeliveryStatusIn(Long orderId, List<DeliveryStatus> deliveryStatus);
}
