package cat.iundarigun.boaleitura.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Distance {
    @JsonProperty("distance-in-km")
    KILOMETER("km", 1000),
    @JsonProperty("distance-in-miles")
    MILE("miles", 1609.34);
    private Distance(String a, double b) {

    }
}