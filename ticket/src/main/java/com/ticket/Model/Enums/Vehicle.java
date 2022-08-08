package com.ticket.Model.Enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Vehicle {
    BUS(Capacity.BUS),
    PLANE(Capacity.PLANE);

    private final Integer capacity;
    public interface Capacity{
        Integer BUS=45;
        Integer PLANE=189;
    }
}
