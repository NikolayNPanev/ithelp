package bg.tu_varna.sit.task_manager.controller;

import bg.tu_varna.sit.task_manager.payload.ReportDto;
import bg.tu_varna.sit.task_manager.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/{task_id}/reports")
    public ResponseEntity<ReportDto> createReport(@PathVariable("task_id") Long id, @Valid @RequestBody ReportDto reportDto) {
        ReportDto addedReport = reportService.createReport(id, reportDto);
        return new ResponseEntity<>(addedReport, HttpStatus.CREATED);
    }
    @GetMapping("{task_id}/reports")
    public ResponseEntity<List<ReportDto>> getReportsByTaskId(@PathVariable("task_id") Long id) {
        List<ReportDto> reports = reportService.getReportsByTaskId(id);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("{task_id}/reports/{report_id}")
    public ResponseEntity<ReportDto> getReport(@PathVariable("task_id") Long taskId,
                                               @PathVariable("report_id") Long reportId) {
        ReportDto reportDto = reportService.getReport(taskId, reportId);
        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @PutMapping("{task_id}/reports/{report_id}")
    public ResponseEntity<ReportDto> updateReport(@PathVariable("task_id") Long taskId,
                                                  @PathVariable("report_id") Long reportId,
                                                  @RequestBody ReportDto reportDto) {
        ReportDto updatedReport = reportService.updateReport(taskId, reportId,reportDto);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    @DeleteMapping("{task_id}/reports/{report_id}")
    public ResponseEntity<String> deleteReport(@PathVariable("task_id") Long taskId,
                                               @PathVariable("report_id") Long reportId) {
        reportService.deleteReport(taskId, reportId);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    @GetMapping("{task_id}/reports/max")
    public ResponseEntity<ReportDto> getMaxReport(@PathVariable("task_id") Long id) {
        ReportDto report = reportService.getMaxLongedReports(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("{task_id}/reports/hours")
    public ResponseEntity<Double> getTaskHours(@PathVariable("task_id") Long id) {
        Double hours = reportService.getTaskWorkingHours(id);
        return new ResponseEntity<>(hours, HttpStatus.OK);
    }

    @GetMapping("{task_id}/reports/between")
    public ResponseEntity<List<ReportDto>> getReportsBetweenDurations(
            @PathVariable("task_id") Long id,
            @RequestParam(name="min") double min,
            @RequestParam(name="max") double max) {
        List<ReportDto> reports = reportService.getReportsWithDurationBetween(id, min, max);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}
