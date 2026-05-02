package org.example.logistics_crm.dto.error;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        LocalDateTime time,
        int status,
        String error,
        String message,
        String path
) {
}
