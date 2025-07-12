package org.mbartel.csv2icsconv.csv;

import java.util.Date;

/**
 * Interface for CSV reader beans
 *
 * @author mbartel
 */
public interface CSVBean {

    /**
     * @return the summary or subject of the event
     */
    String getSummary();

    /**
     * @return the creation date and time of the event
     */
    Date getCreationDate();

    /**
     * @return the description or comment of the event
     */
    String getDescription();

    /**
     * @return the start date and time of the event
     */
    Date getStartDate();

    /**
     * @return the end date and time of the event
     */
    Date getEndDate();
    
    /**
     * @return true, if the all properties of the event are empty (means equal to NULL)
     */
    boolean isNotEmpty();
}
