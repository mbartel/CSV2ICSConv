package org.mbartel.csv2icsconv;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.ValidationException;
import org.mbartel.csv2icsconv.cal.Calendar;

/**
 * CSV to ISC converter
 *
 * @author mbartel
 */
public class CSV2ICSConv {

    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_FAILED_READ_INFILE = 1;
    public static final int EXIT_FAILED_WRITE_OUTFILE = 2;
    public static final int EXIT_FAILED_UID_GEN = 3;

    public static final char CSV_SEPARATOR = ';';

    // turn off "INFO: ical4j.properties not found." log message
    static {
        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
    }

    private static Reader getReader(final String inputfile) {
        try {
            return Files.newBufferedReader(Paths.get(inputfile));
        } catch (final IOException | IllegalStateException ex) {
            System.out.println("Error parsing the inputfile " + inputfile);
            System.exit(EXIT_FAILED_READ_INFILE);
        }
        return Reader.nullReader();
    }

    public static void main(final String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Usage: java -jar " + CSV2ICSConv.class.getSimpleName() + ".jar inputfile.csv outfile.ics");
            System.exit(EXIT_SUCCESS);
        }
        final String inputfile = args[0], outputfile = args[1];

        System.out.println("Start reading lines from CSV file " + inputfile + " ...");
        final Reader reader = getReader(inputfile);

        final Calendar calendar = new Calendar();
        CSVBean.parseCSV(reader, CSV_SEPARATOR).forEach(csvBean -> calendar.addEvent(
                csvBean.getSummary(),
                csvBean.getStartDate(),
                csvBean.getEndDate(),
                csvBean.getCreationDate(),
                csvBean.getDescription()
        ));

        System.out.println("Writing " + calendar.getEventCount() + " events to " + outputfile + " ...");
        try {
            new CalendarOutputter().output(calendar, new FileOutputStream(outputfile));
        } catch (final IOException | ValidationException ex) {
            System.out.println("Error writing to outputfile " + outputfile);
            System.exit(EXIT_FAILED_READ_INFILE);
        }
    }
}
