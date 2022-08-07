package com.gridnine.testing.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gridnine.testing.model.entity.Flight;
import com.gridnine.testing.model.entity.Segment;

public class Flights implements Filter {
    private static final Logger LOGGER = Logger.getLogger(Flight.class.getName());

    public Flights() {
    }

    //вылет до текущего момента времени
    @Override
    public List<Flight> departureUntilNow(List<Flight> storage, LocalDateTime localDateTime) {

        List<Flight> storageTmp = new ArrayList<>();
        for (Flight flight : storage) {
            if (flight.getSegments().size() == 0) {
                LOGGER.log(Level.WARNING, "No segments in flight: " + flight);
                break;
            }
            Segment segment = flight.getSegments().get(0);
            if (!segment.getDepartureDate().isBefore(localDateTime)) {
                storageTmp.add(flight);
            }
        }
        return storageTmp;
    }

    //сегменты с датой прилёта раньше даты вылета
    @Override
    public List<Flight> arriveSegmentsUntilDeparture(List<Flight> storage) {

        List<Flight> storageTmp = new ArrayList<>();
        for (Flight flight : storage) {
            if (flight.getSegments().size() == 0) {
                LOGGER.log(Level.WARNING, "No segments in flight: " + flight);
                break;
            }
            if (!arriveUntil(flight.getSegments())) {
                storageTmp.add(flight);
            }
        }
        return storageTmp;
    }

    private static boolean arriveUntil(List<Segment> flight) {
        for (Segment segment : flight) {
            if (segment.getArrivalDate().isBefore(segment.getDepartureDate()))
                return true;
        }
        return false;
    }

    //общее время, проведённое на земле превышает два часа
    @Override
    public List<Flight> transferTime(List<Flight> storage, int minutes) {

        List<Flight> storageTmp = new ArrayList<>();
        for (Flight flight : storage) {
            if (flight.getSegments().size() == 0) {
                LOGGER.log(Level.WARNING, "No segments in flight: " + flight);
            }

            if (flight.getSegments().size() == 1) {
                LOGGER.log(Level.INFO, "1 segments in flight (without transfer): " + flight);
                storageTmp.add(flight);
            }
            int minutesTmp = 0;
            if (flight.getSegments().size() >= 2) {
                for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                    long diffInMinutes = ChronoUnit.MINUTES.between(flight.getSegments().get(i).getArrivalDate(),
                            flight.getSegments().get(i + 1).getDepartureDate());
                    minutesTmp += diffInMinutes;
                }
                if (minutesTmp < minutes) {
                    storageTmp.add(flight);
                }
            }
        }
        return storageTmp;
    }
}
