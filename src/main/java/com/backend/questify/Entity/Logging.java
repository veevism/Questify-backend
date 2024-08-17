package com.backend.questify.Entity;

import com.backend.questify.Model.ActionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "loggings")
public class Logging {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID loggingId;

    @Enumerated(EnumType.STRING)
    private ActionName actionName;

    @Column(updatable = false)
    private LocalDateTime timeStamp;

    @PrePersist
    public void prePersist() {
        this.timeStamp = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;
}