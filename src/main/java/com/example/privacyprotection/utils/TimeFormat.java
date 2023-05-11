package com.example.privacyprotection.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class TimeFormat {
    public String formatYMDHM(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(timestamp);
    }

    public String formatMDHM(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return dateFormat.format(timestamp);
    }

    public String formatMD(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        return dateFormat.format(timestamp);
    }

    public String formatYMDHMS(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }
}
