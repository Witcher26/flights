package com.gridnine.testing.model;

import com.gridnine.testing.model.entity.Flight;
import com.gridnine.testing.model.entity.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * список рейсов
 */
public class FlightBuilder {
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)), // двухчасовой полёт

                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)), //многосегментный полёт (пересадка 1 час)

                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),   //вылет до текущего момента времени

                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),  //сегмент с датой прилёта раньше даты вылета

                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)), //пересадка более 2 часов (3 часа)

                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));  //пересадка более 2 часов (3 часа)
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}



