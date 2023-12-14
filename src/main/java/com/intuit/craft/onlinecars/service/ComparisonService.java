package com.intuit.craft.onlinecars.service;

import com.google.gson.JsonElement;

import java.util.List;

public interface ComparisonService {

    List<JsonElement> compare(List<String> carIDs, boolean isHide);

}
