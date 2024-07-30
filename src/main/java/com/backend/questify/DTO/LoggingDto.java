package com.backend.questify.DTO;

import com.backend.questify.Model.ActionName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggingDto {

    private ActionName actionName;

    private LocalDateTime timeStamp;

}
