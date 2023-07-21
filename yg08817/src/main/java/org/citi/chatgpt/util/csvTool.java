package org.citi.chatgpt.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.citi.chatgpt.model.Holiday;

import java.io.FileReader;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class csvTool {

    // write csv file
    public static void writeCsvFile(String fileName, List<Holiday> holidayList) {
        // write to file
        try (FileWriter writer = new FileWriter(fileName)) {
            StatefulBeanToCsv<Holiday> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(holidayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Holiday> readCsv(String fileName) {

        try (FileReader reader = new FileReader(fileName)) {
            // create csvReader object passing
            // file reader as a parameter
            CsvToBean<Holiday> csvToBean = new CsvToBeanBuilder(reader).withType(Holiday.class).build();
            List<Holiday> holidayList = csvToBean.parse();
            return holidayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public static void initFile(String fileName) {
        List<Holiday> holidayList = new ArrayList<>();
        // put 4 us holidays in 2023 into list
        holidayList.add(Holiday.builder().countryCode("US").countryDesc("United States").holidayDate(LocalDateTime.of(2023, 01, 1, 0 , 0).toLocalDate()).holidayName("New Year's Day").build());
        holidayList.add(Holiday.builder().countryCode("US").countryDesc("United States").holidayDate(LocalDateTime.of(2023, 01, 16, 0 , 0).toLocalDate()).holidayName("Martin Luther King Jr. Day").build());
        holidayList.add(Holiday.builder().countryCode("US").countryDesc("United States").holidayDate(LocalDateTime.of(2023, 02, 20, 0 , 0).toLocalDate()).holidayName("Washington's Birthday").build());
        holidayList.add(Holiday.builder().countryCode("US").countryDesc("United States").holidayDate(LocalDateTime.of(2023, 04, 29, 0 , 0).toLocalDate()).holidayName("Memorial Day").build());
        // put 4 China holidays in 2024 into list
        holidayList.add(Holiday.builder().countryCode("CN").countryDesc("China").holidayDate(LocalDateTime.of(2024, 01, 1, 0, 0).toLocalDate()).holidayName("New Year's Day").build());
        holidayList.add(Holiday.builder().countryCode("CN").countryDesc("China").holidayDate(LocalDateTime.of(2024, 01, 31, 0, 0).toLocalDate()).holidayName("Chinese New Year").build());
        holidayList.add(Holiday.builder().countryCode("CN").countryDesc("China").holidayDate(LocalDateTime.of(2024, 04, 4, 0, 0).toLocalDate()).holidayName("Qingming Festival").build());
        holidayList.add(Holiday.builder().countryCode("CN").countryDesc("China").holidayDate(LocalDateTime.of(2024, 05, 1, 0, 0).toLocalDate()).holidayName("Labour Day").build());
        // put 4 Japan holidays in 2025 into list
        holidayList.add(Holiday.builder().countryCode("JP").countryDesc("Japan").holidayDate(LocalDateTime.of(2025, 01, 1, 0, 0).toLocalDate()).holidayName("New Year's Day").build());
        holidayList.add(Holiday.builder().countryCode("JP").countryDesc("Japan").holidayDate(LocalDateTime.of(2025, 01, 13, 0, 0).toLocalDate()).holidayName("Coming of Age Day").build());
        holidayList.add(Holiday.builder().countryCode("JP").countryDesc("Japan").holidayDate(LocalDateTime.of(2025, 02, 11, 0, 0).toLocalDate()).holidayName("National Foundation Day").build());
        holidayList.add(Holiday.builder().countryCode("JP").countryDesc("Japan").holidayDate(LocalDateTime.of(2025, 04, 29, 0, 0).toLocalDate()).holidayName("Showa Day").build());
        // put 4 India holidays in 2023 into list
        holidayList.add(Holiday.builder().countryCode("IN").countryDesc("India").holidayDate(LocalDateTime.of(2023, 01, 26, 0, 0).toLocalDate()).holidayName("Republic Day").build());
        holidayList.add(Holiday.builder().countryCode("IN").countryDesc("India").holidayDate(LocalDateTime.of(2023, 03, 14, 0, 0).toLocalDate()).holidayName("Maha Shivaratri").build());
        holidayList.add(Holiday.builder().countryCode("IN").countryDesc("India").holidayDate(LocalDateTime.of(2023, 04, 29, 0, 0).toLocalDate()).holidayName("Good Friday").build());
        holidayList.add(Holiday.builder().countryCode("IN").countryDesc("India").holidayDate(LocalDateTime.of(2023, 05, 1, 0, 0).toLocalDate()).holidayName("Labour Day").build());
        // put 4 uk holidays in 2024 into list
        holidayList.add(Holiday.builder().countryCode("UK").countryDesc("United Kingdom").holidayDate(LocalDateTime.of(2024, 01, 1, 0, 0).toLocalDate()).holidayName("New Year's Day").build());
        holidayList.add(Holiday.builder().countryCode("UK").countryDesc("United Kingdom").holidayDate(LocalDateTime.of(2024, 03, 29, 0, 0).toLocalDate()).holidayName("Good Friday").build());
        holidayList.add(Holiday.builder().countryCode("UK").countryDesc("United Kingdom").holidayDate(LocalDateTime.of(2024, 05, 6, 0, 0).toLocalDate()).holidayName("Early May Bank Holiday").build());
        holidayList.add(Holiday.builder().countryCode("UK").countryDesc("United Kingdom").holidayDate(LocalDateTime.of(2024, 05, 27, 0, 0).toLocalDate()).holidayName("Spring Bank Holiday").build());

        // write to file
        try (FileWriter writer = new FileWriter(fileName)) {
            StatefulBeanToCsv<Holiday> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(holidayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
