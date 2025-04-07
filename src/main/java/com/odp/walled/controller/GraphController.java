package com.odp.walled.controller;

import com.odp.walled.dto.common.GraphSummaryResponse;
import com.odp.walled.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class GraphController {
    private final GraphService graphService;

    @GetMapping("/summary")
    public List<GraphSummaryResponse> getSummaryByWallet(@RequestParam Long walletId, @RequestParam(required = false) String period) {
        return graphService.getSummaryByWallet(walletId, period);
    }

    // @GetMapping("/bar-chart")
    // public ResponseEntity<ApiResponse<List<GraphDataResponse>>> getGraphDataByWallet(@RequestParam Long walletId,
    //         @RequestParam(required = false) String period) {

    //     List<GraphDataResponse> graphData = graphService.getGraphDataByWallet(walletId, period);

    //     ApiResponse<List<GraphDataResponse>> response = new ApiResponse<>(
    //             "success",
    //             HttpStatus.OK.value(),
    //             graphData);

    //     return ResponseEntity.ok(response);
    // }

}
