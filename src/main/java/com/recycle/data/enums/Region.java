package com.recycle.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Region {
    NORTH("NORTE"),
    SOUTH("SUL"),
    SOUTHEAST("SUDESTE"),
    NORTHEAST("NORDESTE"),
    MIDWEST("CENTRO-OESTE");

    private final String name;
}
