package bg.tu_varna.sit.task_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name="task")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name="Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="task_title", nullable = false)
    private String title;

    @Column(name="task_description", nullable = false)
    private String description;

    @Column(name="task_deadline", nullable = false)
    private Date deadline;

    @CreationTimestamp
    @Column(name="date_created")
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name="date_updated")
    private LocalDateTime dateUpdated;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Report> reports = new HashSet<>();
}
