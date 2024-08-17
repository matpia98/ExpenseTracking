package com.example.expensetracking.infrastructure.reporting;

import com.example.expensetracking.domain.reporting.ReportingFacade;
import com.example.expensetracking.domain.reporting.dto.ReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Endpoints for generating expense reports")
public class ReportingController {

    private final ReportingFacade reportingFacade;

    public ReportingController(ReportingFacade reportingFacade) {
        this.reportingFacade = reportingFacade;
    }

    @GetMapping("/weekly")
    @Operation(summary = "Generate weekly report", description = "Generates a report for the week containing the given date")
    @ApiResponse(responseCode = "200", description = "Successfully generated report")
    public ResponseEntity<ReportDto> getWeeklyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "Any date within the desired week", example = "2024-08-08") LocalDate date) {
        ReportDto report = reportingFacade.generateWeeklyReport(date);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly/{year}/{month}")
    @Operation(summary = "Generate monthly report", description = "Generates a report for the specified month and year")
    @ApiResponse(responseCode = "200", description = "Successfully generated report")
    public ResponseEntity<ReportDto> getMonthlyReport(
            @PathVariable @Parameter(description = "Year", example = "2024") int year,
            @PathVariable @Parameter(description = "Month (1-12)", example = "8") int month) {
        ReportDto report = reportingFacade.generateMonthlyReport(year, month);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/custom")
    @Operation(summary = "Generate custom report", description = "Generates a report for a custom date range")
    @ApiResponse(responseCode = "200", description = "Successfully generated report")
    @ApiResponse(responseCode = "400", description = "Invalid date range")
    public ResponseEntity<ReportDto> getCustomReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "Start date of the report", example = "2024-08-01") LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "End date of the report", example = "2024-08-31") LocalDate endDate) {

            ReportDto report = reportingFacade.generateReport(startDate, endDate);
            return ResponseEntity.ok(report);
    }
}