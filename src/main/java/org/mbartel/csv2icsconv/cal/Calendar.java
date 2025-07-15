package org.mbartel.csv2icsconv.cal;

import java.util.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.TzId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.mbartel.csv2icsconv.CSV2ICSConv;

/**
 * iCal4j Calendar
 *
 * @author mbartel
 */
public class Calendar extends net.fortuna.ical4j.model.Calendar {

    private TzId timeZoneId;
    private UidGenerator uidGenerator;

    public Calendar() {
        getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        getProperties().add(Version.VERSION_2_0);
        getProperties().add(CalScale.GREGORIAN);
    }

    /**
     * Creates a new VEvent and adds it to the calendar and returns it to the caller
     * @param summary the summary or subject
     * @param startDate the start date and time
     * @param endDate the end date and time
     * @return the new event
     */
    public VEvent addEvent(final String summary, final Date startDate, final Date endDate) {
        final VEvent event = new VEvent(new net.fortuna.ical4j.model.Date(startDate), new net.fortuna.ical4j.model.Date(endDate), summary);
        addEvent(event);
        return event;
    }

    /**
     * Creates a new VEvent and adds it to the calendar
     * @param summary the summary or subject
     * @param startDate the start date and time
     * @param endDate the end date and time
     * @param creationDate the creation date and time of the event
     * @param description the description
     */
    public void addEvent(final String summary, final Date startDate, final Date endDate, final Date creationDate, final String description) {
        final VEvent event = addEvent(cleanUpStr(summary), startDate, endDate);

        event.getProperties().add(new net.fortuna.ical4j.model.property.Created(new DateTime(creationDate)));
        event.getProperties().add(new net.fortuna.ical4j.model.property.Description(cleanUpStr(description)));
    }

    /**
     * Returns the number of events added to the calendar
     * @return the size of the calendar components list
     */
    public int getEventCount() {
        return getComponents().size();
    }

    private String cleanUpStr(final String str) {
        return str.replaceAll("[^\\x00-\\xFF]", "").trim();
    }

    private TzId getTimeZoneId() {
        if (timeZoneId == null) {
            timeZoneId = TimeZoneRegistryFactory.getInstance().createRegistry().getTimeZone("Europe/Berlin").getVTimeZone().getTimeZoneId();
        }
        return timeZoneId;
    }

    private UidGenerator getUidGenerator() {
        if (uidGenerator == null) {
            uidGenerator = new UidGenerator(() -> RandomStringUtils.randomAlphanumeric(5), CSV2ICSConv.class.getSimpleName());
        }
        return uidGenerator;
    }

    private void addEvent(final VEvent vEvent) {
        vEvent.getProperties().add(getTimeZoneId());
        vEvent.getProperties().add(getUidGenerator().generateUid());
        getComponents().add(vEvent);
    }
}
