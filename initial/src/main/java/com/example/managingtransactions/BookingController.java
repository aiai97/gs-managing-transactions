package com.example.managingtransactions;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping({"/", "/bookings"})
	public List<String> findAllBookings() {
		return bookingService.findAllBookings();
	}
}
