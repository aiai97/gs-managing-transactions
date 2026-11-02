package com.example.managingtransactions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingService {

	private final static Logger logger = LoggerFactory.getLogger(BookingService.class);

	private final JdbcTemplate jdbcTemplate;

	public BookingService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//Does a transaction already exist for the current business operation?	propagation
	//If a sub-operation fails, should the parent transaction also roll back?	propagation + rollback rules
	//Is it acceptable to read uncommitted data?	isolation
	//How long can the transaction run at most?	timeout
	//Does this method need to modify data?	readOnly
	//Which exceptions indicate that the business operation has failed?	rollbackFor
	//Which exceptions should not trigger a rollback?	noRollbackFor
	//Which database does the current operation belong to?	transactionManager
	@Transactional(
			isolation = Isolation.READ_COMMITTED,
			rollbackFor = Exception.class,
			timeout = 60
	)
	public void book(String... persons) {
		for (String person : persons) {
			logger.info("Booking {} in a seat...", person);
			jdbcTemplate.update("insert into BOOKINGS(FIRST_NAME) values (?)", person);
		}
	}

	public List<String> findAllBookings() {
		return jdbcTemplate.query("select FIRST_NAME from BOOKINGS",
				(rs, rowNum) -> rs.getString("FIRST_NAME"));
	}

}
