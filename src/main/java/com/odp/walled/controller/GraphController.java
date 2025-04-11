package com.odp.walled.controller;

import com.odp.walled.dto.common.GraphSummaryResponse;
import com.odp.walled.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles analytics and graph-related endpoints,
 * such as providing summarized financial data for a specific wallet.
 */
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class GraphController {

    /**
     * Service responsible for fetching graph summary data.
     */
    private final GraphService graphService;

    /**
     * Retrieves a summary of income and expenses grouped by category or time period
     * for the specified wallet.
     *
     * @param walletId the ID of the wallet to summarize
     * @param period   optional time period filter (e.g., "weekly", "monthly", "quarterly")
     * @return a list of {@link GraphSummaryResponse} objects representing summarized data
     */
    @GetMapping("/summary")
    public List<GraphSummaryResponse> getSummaryByWallet(@RequestParam Long walletId, @RequestParam(required = false) String period) {
        return graphService.getSummaryByWallet(walletId, period);
    }
}
