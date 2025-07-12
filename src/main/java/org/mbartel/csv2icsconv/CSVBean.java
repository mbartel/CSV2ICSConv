package org.mbartel.csv2icsconv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Date;
import java.util.List;

/**
 * CSV reader bean
 *
 * @author mbartel
 */
public class CSVBean {

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

    public String getSummary() {
        return summary;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    private boolean isNotEmpty() {
        return summary != null
                || description != null
                || creationDate != null
                || startDate != null
                || endDate != null;
    }

    @Override
    public String toString() {
        return "creationDate: " + creationDate + 
                ", startDate: " + startDate + 
                ", endDate: " + endDate + 
                ", summary: " + summary +
                ", description: " + description;
    }

    public static List<CSVBean> parseCSV(final Reader reader, final char separator) {
        final CsvToBean<CSVBean> csvToBean = new CsvToBeanBuilder<CSVBean>(reader)
                .withType(CSVBean.class)
                .withSeparator(separator)
                .build();
        return csvToBean.stream()
                .filter(CSVBean::isNotEmpty)
                .toList();
    }
}
