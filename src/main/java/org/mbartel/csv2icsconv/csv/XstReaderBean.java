package org.mbartel.csv2icsconv.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.util.Date;

/**
 * CSV bean for XstReader (https://github.com/Dijji/XstReader)
 *
 * @author mbartel
 */
public class XstReaderBean implements CSVBean {

    // CSV date pattern for 31.10.2016 23:00:00
    private final static String DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";

    @CsvBindByName(column = "Subject")
    private String summary;

    @CsvBindByName(column = "CreationTime")
    @CsvDate(value = DATE_PATTERN)
    private Date creationDate;

    @CsvBindByName(column = "UserEntryId")
    private String description;

    @CsvBindByName(column = "PSETID_Appointment: AppointmentStartWhole")
    @CsvDate(value = DATE_PATTERN)
    private Date startDate;

    @CsvBindByName(column = "PSETID_Appointment: AppointmentEndWhole")
    @CsvDate(value = DATE_PATTERN)
    private Date endDate;

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public boolean isNotEmpty() {
        return summary != null
                || description != null
                || creationDate != null
                || startDate != null
                || endDate != null;
    }

    @Override
    public String toString() {
        return "creationDate: " + creationDate
                + ", startDate: " + startDate
                + ", endDate: " + endDate
                + ", summary: " + summary
                + ", description: " + description;
    }
}
