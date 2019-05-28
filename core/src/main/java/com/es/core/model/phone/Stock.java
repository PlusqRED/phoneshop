package com.es.core.model.phone;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Stock {
    private Phone phone;
    private Integer stock;
    private Integer reserved;
}
