package org.nkjmlab.util.java.time;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

  public static final DateTimeFormatter EXCEL_DEFAULT_DATE_FORMATTER =
      DateTimeFormatter.ofPattern("uuuu/M/d");

  public static final DateTimeFormatter GOOGLE_SPREADSHEET_DEFAULT_DATE_FORMATTER =
      DateTimeFormatter.ofPattern("uuuu/MM/dd");

  public static final DateTimeFormatter GOOGLE_SPREADSHEET_DEFAULT_DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

  public static final DateTimeFormatter H2_TIMESTAMP_FORMATTTER =
      DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss[.nnnnnnnnn]");

  public static final DateTimeFormatter TIMESTAMP_FORMATTTER =
      DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss[.nnn]");

  public static final DateTimeFormatter HYPHENATED_TIMESTAMP_FORMATTTER =
      DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss");

  public static final DateTimeFormatter UTIL_DATE_FORMATTER =
      DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z uuuu", Locale.US);

  /**
   * @param date
   * @return
   */
  public static LocalDate parseGoogleSpreadsheetDateString(String date) {
    return parseToLocalDate("uuuu/MM/dd", date);
  }

  /**
   * @param dateTime
   * @return
   */
  public static LocalDateTime parseGoogleSpreadsheetDateTimeString(String dateTime) {
    return parse(GOOGLE_SPREADSHEET_DEFAULT_DATE_TIME_FORMATTER, dateTime);
  }

  /**
   * @param timestamp
   * @return
   */
  public static LocalDateTime parseH2TimestampString(String timestamp) {
    return parse(H2_TIMESTAMP_FORMATTTER, timestamp);
  }

  public static LocalDateTime parseTimestampString(String timestamp) {
    return parse(TIMESTAMP_FORMATTTER, timestamp);
  }

  /**
   * @return
   */
  public static LocalDateTime parseUtilDateString(String dateTime) {
    return parse(UTIL_DATE_FORMATTER, dateTime);
  }

  public static LocalDateTime parse(String format, String datetime) {
    return parse(DateTimeFormatter.ofPattern(format), datetime);
  }

  public static LocalDateTime parse(DateTimeFormatter formatter, String timestamp) {
    return LocalDateTime.from(formatter.parse(timestamp));
  }

  public static LocalDate parseToLocalDate(String format, String date) {
    return parseToLocalDate(DateTimeFormatter.ofPattern(format), date);
  }

  public static LocalDate parseToLocalDate(DateTimeFormatter formatter, String date) {
    return LocalDate.from(formatter.parse(date));
  }

  public static Date toDate(LocalDateTime localDateTime) {
    return Date.from(toInstant(localDateTime));
  }

  public static Instant toInstant(LocalDateTime localDateTime) {
    return toZonedDateTime(localDateTime).toInstant();
  }

  public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault());
  }

  public static Date toDate(LocalDate localDate) {
    return toDate(localDate.atStartOfDay());
  }

  public static Date toDate(LocalTime localTime) {
    return toDate(LocalDate.ofEpochDay(0L).atTime(localTime));
  }

  public static LocalDateTime toLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public static LocalDateTime toLocalDateTime(long epochMilli) {
    return Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static ZonedDateTime toZonedDateTime(Date date) {
    return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public static Timestamp toTimestamp(Date date) {
    return toTimestamp(toLocalDateTime(date));
  }

  public static Timestamp toTimestamp(LocalDateTime date) {
    return Timestamp.valueOf(date);
  }

  public static String formatToIsoLocalDate(LocalDate date) {
    return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
  }

  public static String formatToIsoLocalDateTime(LocalDateTime date) {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
  }

  public static String formatToHyphenatedTimestamp(LocalDateTime date) {
    return HYPHENATED_TIMESTAMP_FORMATTTER.format(date);
  }

  public static java.sql.Date toSqlDate(LocalDate date) {
    return java.sql.Date.valueOf(date);
  }

  public static String nowFormattedAsIsoLocalDateTime() {
    return formatToIsoLocalDateTime(LocalDateTime.now());
  }

  public static String nowFormattedAsHyphenatedTimestamp() {
    return formatToHyphenatedTimestamp(LocalDateTime.now());
  }

  public static String getDayOfWeekInJapanese(int dayOfWeek) {
    return DayOfWeek.of(dayOfWeek).getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
  }

  public static String getDayOfWeekInJapanese(LocalDate date) {
    return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
  }

  public static long toEpochMilli(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
}
