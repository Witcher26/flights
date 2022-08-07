package com.gridnine.testing;

import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.model.Flights;
import com.gridnine.testing.model.entity.Flight;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

public class Tests {

    @Test
    public void test() {
        List<Flight> flights = FlightBuilder.createFlights();
        Flights filters = new Flights();

        //фильтр №1
        List<Flight> departureUntilNow = filters.departureUntilNow(flights, LocalDateTime.now());
        int expectDepartureUntilNow = 5;

        //фильтр №2
        List<Flight> arriveSegmentsUntilDeparture = filters.arriveSegmentsUntilDeparture(flights);
        int expectArriveSegmentsUntilDeparture = 5;

        //фильтр №3
        List<Flight> transferTime = filters.departureUntilNow(flights, LocalDateTime.now());
        transferTime = filters.arriveSegmentsUntilDeparture(transferTime);
        transferTime = filters.transferTime(transferTime, 120);

        int expectTransferTime = 2;

        Assertions.assertEquals(expectDepartureUntilNow, departureUntilNow.size());
        Assertions.assertEquals(expectArriveSegmentsUntilDeparture, arriveSegmentsUntilDeparture.size());
        Assertions.assertEquals(expectTransferTime, transferTime.size());
    }
}
