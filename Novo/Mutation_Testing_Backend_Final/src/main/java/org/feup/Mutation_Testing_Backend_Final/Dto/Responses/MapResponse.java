package org.feup.Mutation_Testing_Backend_Final.Dto.Responses;

import java.util.Map;

public class MapResponse <T> extends SimpleResponse {
    private Map<String,T> map;

    public <T> Map<String, T> getMap() {
        return (java.util.Map<String, T>) map;
    }

    public void setMap(Map<String, T> map) {
        this.map = map;
    }
}
