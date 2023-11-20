package com.example.LoggerProject.domain.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Table(name = "logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_log")
    private String userLog;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "log_type")
    private LogType logType;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIME)
    @CreatedDate
    private java.util.Date createdDate;

    @ManyToOne
    private UserEntity user;

//    https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogEntity otherLog)) return false;
        return id != null && id.equals(otherLog.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
