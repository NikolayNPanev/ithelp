package bg.tu_varna.sit.task_manager.service.impl;

import bg.tu_varna.sit.task_manager.exception.ApiException;
import bg.tu_varna.sit.task_manager.exception.ResourceNotFoundException;
import bg.tu_varna.sit.task_manager.model.Report;
import bg.tu_varna.sit.task_manager.model.Task;
import bg.tu_varna.sit.task_manager.payload.ReportDto;
import bg.tu_varna.sit.task_manager.payload.TaskDto;
import bg.tu_varna.sit.task_manager.repository.ReportRepository;
import bg.tu_varna.sit.task_manager.repository.TaskRepository;
import bg.tu_varna.sit.task_manager.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;
    private TaskRepository taskRepository;
    private ModelMapper mapper;

    public ReportServiceImpl(ReportRepository reportRepository,
                             TaskRepository taskRepository,
                             ModelMapper mapper) {
        this.reportRepository = reportRepository;
        this.taskRepository=taskRepository;
        this.mapper = mapper;
    }

    @Override
    public ReportDto createReport(Long taskId, ReportDto reportDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Report report = mapToEntity(reportDto);
        report.setTask(task);
        Report addedReport = reportRepository.save(report);
        return mapToDto(addedReport);
    }

    @Override
    public List<ReportDto> getReportsByTaskId(Long taskId) {
        List<Report> reports = reportRepository.findByTaskId(taskId);
        return reports.stream().map(report -> mapToDto(report)).collect(Collectors.toList());
    }

    @Override
    public ReportDto getReport(Long taskId, Long reportId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", reportId));

        if(!report.getTask().getId().equals(task.getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Отчетът не принадлежи към тази задача");
        }
        return mapToDto(report);
    }

    @Override
    public ReportDto updateReport(Long taskId, Long reportId, ReportDto reportDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", reportId));

        if(!report.getTask().getId().equals(task.getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Отчетът не принадлежи към тази задача");
        }
        Report newDataReport = mapToEntity(reportDto);
        report.setContent(newDataReport.getContent());
        report.setHoursWorked(newDataReport.getHoursWorked());

        Report updatedReport = reportRepository.save(report);
        return mapToDto(updatedReport);
    }

    @Override
    public void deleteReport(Long taskId, Long reportId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", reportId));

        if(!report.getTask().getId().equals(task.getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Отчетът не принадлежи към тази задача");
        }
        reportRepository.delete(report);
    }

    @Override
    public List<ReportDto> getReportsWithDurationBetween(Long taskId, double min, double max) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        List<Report> reports = reportRepository.findByTaskIdAndHoursWorkedBetween(taskId,min,max);
        return reports.stream().map(report -> mapToDto(report)).collect(Collectors.toList());
    }

    @Override
    public ReportDto getMaxLongedReports(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        Report report = reportRepository.findLongestReport(taskId);
        return mapToDto(report);
    }

    @Override
    public double getTaskWorkingHours(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        double hours = reportRepository.getAllReportsLength(taskId);
        return hours;
    }

    private ReportDto mapToDto(Report report) {
        return mapper.map(report, ReportDto.class);
    }

    private Report mapToEntity(ReportDto reportDto) {
        return mapper.map(reportDto, Report.class);
    }
}
