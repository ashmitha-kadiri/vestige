package com.vestige.service;

import com.vestige.model.enums.BookingStatusType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Explicit, centralized service-layer state machine for Repair Bookings.
 * Enforces valid forward workflow transitions and prevents invalid/backward status shifts.
 */
@Component
public class RepairStateMachine {

    private static final Map<BookingStatusType, Set<BookingStatusType>> ALLOWED_TRANSITIONS;

    static {
        Map<BookingStatusType, Set<BookingStatusType>> map = new EnumMap<>(BookingStatusType.class);

        // PENDING -> ACCEPTED, REJECTED, CANCELLED
        map.put(BookingStatusType.PENDING, EnumSet.of(
                BookingStatusType.ACCEPTED,
                BookingStatusType.REJECTED,
                BookingStatusType.CANCELLED
        ));

        // ACCEPTED -> IN_PROGRESS, CANCELLED
        map.put(BookingStatusType.ACCEPTED, EnumSet.of(
                BookingStatusType.IN_PROGRESS,
                BookingStatusType.CANCELLED
        ));

        // IN_PROGRESS -> COMPLETED, CANCELLED
        map.put(BookingStatusType.IN_PROGRESS, EnumSet.of(
                BookingStatusType.COMPLETED,
                BookingStatusType.CANCELLED
        ));

        // Terminal states: COMPLETED, CANCELLED, REJECTED
        map.put(BookingStatusType.COMPLETED, Collections.emptySet());
        map.put(BookingStatusType.CANCELLED, Collections.emptySet());
        map.put(BookingStatusType.REJECTED, Collections.emptySet());

        ALLOWED_TRANSITIONS = Collections.unmodifiableMap(map);
    }

    public boolean isValidTransition(BookingStatusType fromStatus, BookingStatusType toStatus) {
        if (fromStatus == null || toStatus == null) {
            return false;
        }
        if (fromStatus == toStatus) {
            return true; // Idempotent no-op
        }
        Set<BookingStatusType> allowed = ALLOWED_TRANSITIONS.getOrDefault(fromStatus, Collections.emptySet());
        return allowed.contains(toStatus);
    }

    public void validateTransition(BookingStatusType fromStatus, BookingStatusType toStatus) {
        if (!isValidTransition(fromStatus, toStatus)) {
            throw new IllegalStateException(String.format(
                    "Invalid repair status transition from %s to %s. Permitted next states: %s",
                    fromStatus, toStatus, ALLOWED_TRANSITIONS.getOrDefault(fromStatus, Collections.emptySet())
            ));
        }
    }
}
