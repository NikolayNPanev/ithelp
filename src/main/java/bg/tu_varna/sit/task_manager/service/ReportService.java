package bg.tu_varna.sit.task_manager.service;

import bg.tu_varna.sit.task_manager.payload.ReportDto;

import java.util.List;

public interface ReportService {
    ReportDto createReport(Long taskId, ReportDto reportDto);
    List<ReportDto> getReportsByTaskId(Long taskId);
    ReportDto getReport(Long taskId, Long reportId);
    ReportDto updateReport(Long taskId, Long reportId, ReportDto reportDto);
    void deleteReport(Long taskId, Long reportId);

    List<ReportDto> getReportsWithDurationBetween(Long taskId, double min, double max);
    ReportDto getMaxLongedReports(Long taskId);
    double getTaskWorkingHours(Long taskId);
}
