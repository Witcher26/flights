package com.gridnine.testing.model;

import com.gridnine.testing.model.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

/**
 * текущий набор фильтров
 */
public interface Filter {

    /**
     * вылет до текущего момента времени
     */
    List<Flight> departureUntilNow(List<Flight> storage, LocalDateTime localDateTime);

    /**
     * сегменты с датой прилёта раньше даты вылета
     */
    List<Flight> arriveSegmentsUntilDeparture(List<Flight> storage);

    /**
     * общее время, проведённое на земле превышает два часа
     */
    List<Flight> transferTime(List<Flight> storage, int minutes);

}
