package com.ooplab.abbank;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Document(collection = "logs")
public class Log {

    @Id
    private String id;
    private LogType logType;
    private boolean logEnabled;
    private String logMessage;
    private LocalDateTime logDate;

    public Log(LogType logType, String[] logMessage) {
        this.logType = logType;
        switch (logType) {
            case REQUEST_ACCOUNT ->
                    this.logMessage = String.format("%s has requested to open an `%s` account. (Acc no. %s)", logMessage[0], logMessage[1], logMessage[2]);
            case APPROVE_ACCOUNT ->
                    this.logMessage = String.format("%s has approved to open an `%s` account. (Acc no. %s)", logMessage[0], logMessage[1], logMessage[2]);
            case REQUEST_LOAN ->
                    this.logMessage = String.format("%s has requested a loan of `%s` AED to (Acc no. %s)", logMessage[0], logMessage[1], logMessage[2]);
            case APPROVE_LOAN ->
                    this.logMessage = String.format("%s has approved a loan of `%s` AED to (Acc no. %s)", logMessage[0], logMessage[1], logMessage[2]);
            case TRANSFER ->
                    this.logMessage = String.format("%s has transferred an amount of `%s` AED from (Acc no. %s) to (Acc no. %s)", logMessage[0], logMessage[1], logMessage[2], logMessage[3]);
            default ->
                    this.logMessage = Arrays.toString(logMessage);
        }
        this.logDate = LocalDateTime.now();
        this.logEnabled = true;
    }

}
