package sgr.com.sgrcoreapi.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.service.statistics.StatisticsService;
import sgr.com.sgrcoreapi.service.statistics.dto.PeriodStatisticsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<ApiResponse<PeriodStatisticsResponse>> getPeriodStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) UUID waiterId
    ) {

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        if (startDateTime != null && endDateTime != null && !startDateTime.isBefore(endDateTime)) {
            throw new BadRequestException("startDate must be before endDate");
        }


        var periodStatistics = statisticsService.getPeriodStatistics(startDateTime, endDateTime, waiterId);
        var response = new ApiResponse<>(200, periodStatistics);

        return ResponseEntity.ok(response);
    }
}
