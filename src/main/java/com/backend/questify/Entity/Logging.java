package com.backend.questify.Entity;

import com.backend.questify.Model.ActionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "loggings")
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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