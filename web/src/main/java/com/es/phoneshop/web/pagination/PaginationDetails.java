package com.es.phoneshop.web.pagination;

import com.es.core.model.phone.Phone;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@PropertySource("WEB-INF/properties/pagination.properties")
public class PaginationDetails {
    @Value("${pagination.maxProductsOnPage}")
    private Integer maxProductsOnPage;
    @Value("${pagination.maxVisiblePages}")
    private Integer maxVisiblePages;
    private List<Phone> pagePhones;
    private Long totalNumberOfPhonesFound;
    private List<Integer> pageIndices;
    private Integer leftPageBound;
    private Integer lastPage;
    private Integer currentPageIndex;
    private String search;
}
