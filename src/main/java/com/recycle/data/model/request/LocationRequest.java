package com.recycle.data.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class LocationRequest {
    private List<String> ufs;
    private List<Long> stateIds;
    private List<Long> cityIds;

    public boolean hasStateListToGet() {
        return (ufs != null && !ufs.isEmpty()) || (stateIds != null && !stateIds.isEmpty());
    }

    public boolean hasCityListToGet() {
        return cityIds != null && !cityIds.isEmpty();
    }
}
