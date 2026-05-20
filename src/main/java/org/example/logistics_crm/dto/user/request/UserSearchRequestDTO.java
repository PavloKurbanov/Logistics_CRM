package org.example.logistics_crm.dto.user.request;



import java.time.LocalDateTime;

public record UserSearchRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String userRole,
        LocalDateTime createdFrom,
        LocalDateTime createdTo
) {
}
