# Cinema Booking System

A comprehensive cinema booking system that manages movie schedules, seat bookings, and transactions.

## Core Entities

### Location Management
- **City**: Represents a city where cinemas are located
- **Cinema**: Represents a cinema in a city
- **CityCinema**: Join entity that manages the relationship between cities and cinemas

### Studio and Seating
- **Studio**: Represents a movie theater within a cinema
- **StudioSeat**: Master data for seat layout in a studio
  - Contains seat position (row, number)
  - Contains coordinates (x, y) for visual layout
  - Can have price adjustments for special seats

### Movie Management
- **Movie**: Contains movie information
  - Title
  - Description
  - Duration
  - Rating

### Schedule and Booking
- **MovieSchedule**: Represents a movie showing
  - Links movie to a studio
  - Contains show time (start and end)
  - Has base price for the schedule

- **MovieScheduleSeat**: Seat information for a specific schedule
  - Copied from StudioSeat for layout
  - Tracks seat status (AVAILABLE, BOOKED, WAITING_PAYMENT, NOT_SALE, RESERVED)
  - Can have price adjustments

- **Booking**: Represents a booking transaction
  - One booking per schedule
  - Contains transaction date
  - Tracks booking status (PENDING, WAITING_PAYMENT, PAID, CANCELLED, EXPIRED)
  - Has payment deadline
  - Stores total amount

- **BookingSeat**: Links bookings to seats
  - Connects Booking to MovieScheduleSeat
  - Stores actual price paid for the seat
  - Allows tracking of individual seat bookings

## Key Features

### Seat Management
- Master seat layout in StudioSeat
- Independent seat status tracking per schedule
- Support for special seat pricing
- Visual layout using coordinates

### Booking Process
1. User selects a movie schedule
2. System shows available seats
3. User selects seats
4. System creates booking with selected seats
5. User completes payment
6. System updates seat and booking status

### Status Tracking
- **Seat Status**:
  - AVAILABLE: Seat can be booked
  - BOOKED: Seat is booked and paid
  - WAITING_PAYMENT: Seat is reserved but payment pending
  - NOT_SALE: Seat is not available for sale
  - RESERVED: Seat is temporarily reserved

- **Booking Status**:
  - PENDING: Initial booking state
  - WAITING_PAYMENT: Waiting for payment confirmation
  - PAID: Payment confirmed
  - CANCELLED: Booking cancelled
  - EXPIRED: Booking expired

## Business Rules

1. **One Schedule per Booking**
   - Each booking must be for a single movie schedule
   - Different schedules require separate bookings
   - Ensures clear transaction boundaries

2. **Seat Management**
   - Seat layout is copied from master data to schedule
   - Each schedule has independent seat status
   - Seat prices can be adjusted per schedule

3. **Payment Handling**
   - Each booking has a payment deadline
   - Booking status tracks payment state
   - Supports cancellation and refund processes

4. **Price Management**
   - Base price set at schedule level
   - Individual seat price adjustments possible
   - Total amount calculated per booking