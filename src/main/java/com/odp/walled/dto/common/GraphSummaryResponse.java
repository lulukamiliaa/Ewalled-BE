package com.odp.walled.dto.common;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GraphSummaryResponse {
    private Long walletId;
    private String period;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal savingPercentage;
}
