package com.es.core.model.phone;

import java.util.Comparator;
import java.util.List;

public enum SortBy {
    BRAND(Comparator.comparing(Phone::getBrand)),
    MODEL(Comparator.comparing(Phone::getModel)),
    PRICE(Comparator.comparing(Phone::getPrice)),
    DISPLAY_SIZE(Comparator.comparing(Phone::getDisplaySizeInches));

    private final Comparator<Phone> comparator;
    private boolean asc;

    SortBy(Comparator<Phone> comparator) {
        this.comparator = comparator;
    }

    public void sort(List<Phone> phones) {
        phones.sort(asc ? comparator : comparator.reversed());
    }

    public Comparator<Phone> getComparator() {
        return comparator;
    }

    public void setSortOrder(boolean asc) {
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }
}
