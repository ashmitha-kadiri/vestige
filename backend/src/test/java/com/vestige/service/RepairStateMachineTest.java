package com.vestige.service;

import com.vestige.model.enums.BookingStatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepairStateMachineTest {

    private final RepairStateMachine stateMachine = new RepairStateMachine();

    @Test
    @DisplayName("Valid forward repair transitions are permitted")
    void testValidTransitions() {
        assertTrue(stateMachine.isValidTransition(BookingStatusType.PENDING, BookingStatusType.ACCEPTED));
        assertTrue(stateMachine.isValidTransition(BookingStatusType.PENDING, BookingStatusType.REJECTED));
        assertTrue(stateMachine.isValidTransition(BookingStatusType.PENDING, BookingStatusType.CANCELLED));

        assertTrue(stateMachine.isValidTransition(BookingStatusType.ACCEPTED, BookingStatusType.IN_PROGRESS));
        assertTrue(stateMachine.isValidTransition(BookingStatusType.ACCEPTED, BookingStatusType.CANCELLED));

        assertTrue(stateMachine.isValidTransition(BookingStatusType.IN_PROGRESS, BookingStatusType.COMPLETED));
        assertTrue(stateMachine.isValidTransition(BookingStatusType.IN_PROGRESS, BookingStatusType.CANCELLED));

        // Same-state idempotency
        assertTrue(stateMachine.isValidTransition(BookingStatusType.ACCEPTED, BookingStatusType.ACCEPTED));
    }

    @Test
    @DisplayName("Disallowed and backward repair transitions throw IllegalStateException")
    void testInvalidTransitions() {
        assertFalse(stateMachine.isValidTransition(BookingStatusType.COMPLETED, BookingStatusType.PENDING));
        assertFalse(stateMachine.isValidTransition(BookingStatusType.COMPLETED, BookingStatusType.IN_PROGRESS));
        assertFalse(stateMachine.isValidTransition(BookingStatusType.CANCELLED, BookingStatusType.ACCEPTED));
        assertFalse(stateMachine.isValidTransition(BookingStatusType.REJECTED, BookingStatusType.IN_PROGRESS));
        assertFalse(stateMachine.isValidTransition(BookingStatusType.PENDING, BookingStatusType.COMPLETED));

        assertThrows(IllegalStateException.class, () ->
                stateMachine.validateTransition(BookingStatusType.COMPLETED, BookingStatusType.PENDING)
        );

        assertThrows(IllegalStateException.class, () ->
                stateMachine.validateTransition(BookingStatusType.CANCELLED, BookingStatusType.ACCEPTED)
        );
    }
}
