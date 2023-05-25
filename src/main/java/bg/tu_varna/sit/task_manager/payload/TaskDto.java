package bg.tu_varna.sit.task_manager.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskDto {
    private Long id;
    @NotBlank(message="Заглавието не може да бъде празно")
    private String title;
    @Size(min=10, message="Описанието трябва да бъде поне 10 символа")
    private String description;
    @NotNull(message="Крайният срок не трябва да липсва")
    private Date deadline;
}