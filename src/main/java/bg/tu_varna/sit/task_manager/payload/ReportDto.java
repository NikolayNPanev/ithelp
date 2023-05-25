package bg.tu_varna.sit.task_manager.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReportDto {

    private Long id;
    @NotBlank(message="Съдържанието не може да бъде празно")
    private String content;
    private double hoursWorked;

}
