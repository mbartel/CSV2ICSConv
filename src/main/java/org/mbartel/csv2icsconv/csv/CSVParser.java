package org.mbartel.csv2icsconv.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.List;

/**
 * Parser for CSV files
 *
 * @author mbartel
 */
public class CSVParser {

    public static final char CSV_SEPARATOR = ';';

    /**
     * Parse CSV file and returns a list of CSVBeans
     * @param <BEANCLASS> the subclass of CSVBean
     * @param reader the CSV file reader
     * @param clazz the subclass of CSVBean
     * @return the list of beans
     */
    public static <BEANCLASS extends CSVBean> List<BEANCLASS> parse(final Reader reader, final Class<BEANCLASS> clazz) {
        final CsvToBean<BEANCLASS> csvToBean = new CsvToBeanBuilder<BEANCLASS>(reader)
                .withType(clazz)
                .withSeparator(CSV_SEPARATOR)
                .build();
        return csvToBean.stream()
                .filter(CSVBean::isNotEmpty)
                .toList();
    }
}
