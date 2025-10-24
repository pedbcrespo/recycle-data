package com.recycle.data.model.dto;

public abstract class LocationDto<E, P> {
    public abstract E generate(P parent);
}
