package com.es.core.model.phone;

import java.util.Comparator;

public enum SortBy {
    BRAND,
    MODEL,
    PRICE,
    DISPLAY_SIZE,
    NONE;

    private boolean asc;

    public static Comparator<Phone> getComparator(SortBy sortBy) {
        switch (sortBy) {
            case BRAND:
                return Comparator.comparing(Phone::getBrand);
            case MODEL:
                return Comparator.comparing(Phone::getModel);
            case PRICE:
                return Comparator.comparing(Phone::getPrice);
            case DISPLAY_SIZE:
                return Comparator.comparing(Phone::getDisplaySizeInches);
            default:
                return Comparator.comparing(Phone::getId);
        }
    }

    public void setSortOrder(boolean asc) {
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }
}
