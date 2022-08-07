package com.gridnine.testing;

import com.gridnine.testing.model.Flights;
import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.model.entity.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();
        Flights filters = new Flights();
        List<Flight> filterFirst = new ArrayList<>(); //для фильтра № 1
        List<Flight> filterSecond = new ArrayList<>(); //для фильтра № 2
        List<Flight> filterThird = new ArrayList<>();//для фильтра № 3

        try {
            filterFirst = filters.departureUntilNow(flights, LocalDateTime.now());
            System.out.println("Вывод рейсов по фильтру №1: ");
            getFlights(filterFirst);

            filterSecond = filters.arriveSegmentsUntilDeparture(flights);
            System.out.println("Вывод рейсов по фильтру №2: ");
            getFlights(filterSecond);

            filterThird = filters.departureUntilNow(flights, LocalDateTime.now());
            filterThird = filters.arriveSegmentsUntilDeparture(filterThird);

            filterThird = filters.transferTime(filterThird, 120);
            System.out.println("Вывод рейсов по фильтру №3 (с учётом фильтров №1 и №2): ");
            getFlights(filterThird);

            Thread.sleep(300);
            LOGGER.log(Level.INFO, "Filters were applied successfully");
        } catch (NullPointerException | InterruptedException ex) {
            LOGGER.log(Level.WARNING, "No flights with the specified filters");
        }
    }

    public static void getFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println(flight.toString());
        }
        System.out.println("");
    }
}
