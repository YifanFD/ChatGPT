package org.citi.chatgpt.controller;

import com.google.common.collect.Sets;
import org.citi.chatgpt.model.Holiday;
import org.citi.chatgpt.model.HolidayResponse;
import org.citi.chatgpt.util.csvTool;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RestController
public class Controller {

    public static final String csvPath = "src/main/resources/holiday.csv";

    @PostConstruct
    public void init() {
        csvTool.initFile(csvPath);
    }

    // FYI use below curl command to test
    //curl -H "Content-Type: application/json" -X POST -d '[{"countryCode":"CN", "countryDesc":"Kargopolov", "holidayDate":"2023-07-21", "holidayName":"12345"}]' http://localhost:8080/api/update/
    @PostMapping("/api/addNewHoliday")
    public String addNewHolidays(
            @RequestBody List<Holiday> holidays) {

        List<Holiday> existingHolidays = csvTool.readCsv(csvPath);
        String errMsg = "";
        int count = 0;
        for (Holiday holiday : holidays) {
            // filter holiday based on country code and holiday date
            if (existingHolidays.stream()
                    .filter(h -> h.getCountryCode().equals(holiday.getCountryCode()))
                    .filter(h -> h.getHolidayDate().equals(holiday.getHolidayDate()))
                    .findAny().isPresent()) {
                errMsg = errMsg + "Holiday already exists" + holiday.getHolidayName() + " " + holiday.getHolidayDate() + "\n";
            } else {
                // add new holiday to existing holiday list
                existingHolidays.add(holiday);
                count++;
            }
            csvTool.writeCsvFile(csvPath, existingHolidays);
        }

        return "Added " + count + " new holidays. \n" + errMsg;
    }


    @PostMapping("/api/update")
    public String update(
            @RequestBody List<Holiday> holidays) {

        Set<Holiday> existingHolidays = csvTool.readCsv(csvPath).stream().collect(Collectors.toSet());
        String errMsg = "";
        int count = 0;
        for (Holiday holiday : holidays) {
            // filter holiday based on country code and holiday date
            Optional<Holiday> opt = existingHolidays.stream()
                    .filter(h -> h.getCountryCode().equals(holiday.getCountryCode()))
                    .filter(h -> h.getHolidayDate().equals(holiday.getHolidayDate()))
                    .findAny();
            if (opt.isPresent()) {
                // update holiday
                Holiday existingHoliday = opt.get();
                existingHolidays.remove(existingHoliday);
                existingHolidays.add(holiday);
                count++;
            } else {
                errMsg = errMsg + "Holiday doesn't exist" + holiday.getHolidayName() + " " + holiday.getHolidayDate() + "\n";
            }
            csvTool.writeCsvFile(csvPath, existingHolidays.stream().collect(Collectors.toList()));
        }

        return "Updated " + count + " holidays. \n" + errMsg;
    }

    @PostMapping("/api/remove")
    public String remove(
            @RequestBody List<Holiday> holidays) {

        Set<Holiday> existingHolidays = csvTool.readCsv(csvPath).stream().collect(Collectors.toSet());
        String errMsg = "";
        int count = 0;
        for (Holiday holiday : holidays) {
            // filter holiday based on country code and holiday date
            Optional<Holiday> opt = existingHolidays.stream()
                    .filter(h -> h.getCountryCode().equals(holiday.getCountryCode()))
                    .filter(h -> h.getHolidayDate().equals(holiday.getHolidayDate()))
                    .findAny();
            if (opt.isPresent()) {
                // remove holiday
                Holiday existingHoliday = opt.get();
                existingHolidays.remove(existingHoliday);
                count++;
            } else {
                errMsg = errMsg + "Holiday doesn't exist, can't remove" + holiday.getHolidayName() + " " + holiday.getHolidayDate() + "\n";
            }
            csvTool.writeCsvFile(csvPath, existingHolidays.stream().collect(Collectors.toList()));
        }

        return "Removed " + count + " holidays. \n" + errMsg;
    }

    @GetMapping("/api/getNextYearHolidays")
    public List<Holiday> getNextYearHolidays() {
        List<Holiday> existingHolidays = csvTool.readCsv(csvPath);
        List<Holiday> nextYearHolidays = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();
        for (Holiday holiday : existingHolidays) {
            if (holiday.getHolidayDate().isAfter(nextYear.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate())) {
                nextYearHolidays.add(holiday);
            }
        }
        return nextYearHolidays;
    }

    @GetMapping("/api/getNextHoliday/{countryCode}")
    public Holiday getNextHoliday(@PathVariable String countryCode) {
        List<Holiday> existingHolidays = csvTool.readCsv(csvPath);
        Holiday nextHoliday = null;
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        for (Holiday holiday : existingHolidays) {
            if (holiday.getCountryCode().equals(countryCode) && holiday.getHolidayDate().isAfter(today.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate())) {
                if (nextHoliday == null) {
                    nextHoliday = holiday;
                } else {
                    if (holiday.getHolidayDate().isBefore(nextHoliday.getHolidayDate())) {
                        nextHoliday = holiday;
                    }
                }
            }
        }
        return nextHoliday;
    }

    @GetMapping("/api/checkIfHoliday/{date}")
    // date is String in format yyyy-MM-dd
    public HolidayResponse checkIfHoliday(@PathVariable String date) {
        Set<String> yes = new HashSet<>();
        Set<String> no = new HashSet<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = sdf.parse(date);
        } catch (Exception e) {
            return HolidayResponse.builder()
                    .statusMsg("Invalid date format")
                    .yes(yes)
                    .no(no)
                    .build();
        }

        List<Holiday> existingHolidays = csvTool.readCsv(csvPath);
        for (Holiday holiday : existingHolidays) {
            if (holiday.getHolidayDate().equals(dateObj.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate())) {
                yes.add(holiday.getCountryCode());
            }
            else {
                no.add(holiday.getCountryCode());
            }
        }

        return HolidayResponse.builder()
                .statusMsg("Success")
                .yes(yes)
                .no(Sets.difference(no, yes))
                .build();
    }


}



