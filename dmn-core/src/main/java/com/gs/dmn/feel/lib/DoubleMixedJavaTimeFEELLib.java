/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.lib;

import com.gs.dmn.feel.lib.type.bool.DefaultBooleanLib;
import com.gs.dmn.feel.lib.type.context.DefaultContextType;
import com.gs.dmn.feel.lib.type.list.DefaultListLib;
import com.gs.dmn.feel.lib.type.list.DefaultListType;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericLib;
import com.gs.dmn.feel.lib.type.numeric.DoubleNumericType;
import com.gs.dmn.feel.lib.type.string.DefaultStringLib;
import com.gs.dmn.feel.lib.type.string.DefaultStringType;
import com.gs.dmn.feel.lib.type.time.mixed.*;
import com.gs.dmn.feel.lib.type.time.xml.DoubleDurationType;
import com.gs.dmn.runtime.LambdaExpression;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoubleMixedJavaTimeFEELLib extends BaseFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> implements StandardFEELLib<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    private static final DatatypeFactory DATA_TYPE_FACTORY = XMLDatataypeFactory.newInstance();

    private final DoubleNumericLib numberLib = new DoubleNumericLib();
    private final DefaultStringLib stringLib = new DefaultStringLib();
    private final DefaultBooleanLib booleanLib = new DefaultBooleanLib();
    private final LocalDateLib dateLib = new LocalDateLib();
    private final OffsetTimeLib timeLib = new OffsetTimeLib(DATA_TYPE_FACTORY);
    private final ZonedDateTimeLib dateTimeLib = new ZonedDateTimeLib();
    private final DefaultDurationLib durationLib = new DefaultDurationLib(DATA_TYPE_FACTORY);
    private final DefaultListLib listLib = new DefaultListLib();

    public DoubleMixedJavaTimeFEELLib() {
        super(new DoubleNumericType(LOGGER),
                new DefaultBooleanType(LOGGER),
                new DefaultStringType(LOGGER),
                new LocalDateType(LOGGER, DATA_TYPE_FACTORY),
                new OffsetTimeType(LOGGER, DATA_TYPE_FACTORY),
                new ZonedDateTimeType(LOGGER, DATA_TYPE_FACTORY),
                new DoubleDurationType(LOGGER),
                new DefaultListType(LOGGER),
                new DefaultContextType(LOGGER)
        );
    }

    //
    // Conversion functions
    //

    @Override
    public Double number(String literal) {
        try {
            return this.numberLib.number(literal);
        } catch (Exception e) {
            String message = String.format("number(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double number(String from, String groupingSeparator, String decimalSeparator) {
        try {
            return this.numberLib.number(from, groupingSeparator, decimalSeparator);
        } catch (Exception e) {
            String message = String.format("number(%s, %s, %s)", from, groupingSeparator, decimalSeparator);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String string(Object from) {
        try {
            return this.stringLib.string(from);
        } catch (Exception e) {
            String message = String.format("string(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(String literal) {
        try {
            return this.dateLib.date(literal);
        } catch (Exception e) {
            String message = String.format("date(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(Double year, Double month, Double day) {
        try {
            return this.dateLib.date(year, month, day);
        } catch (Exception e) {
            String message = String.format("date(%s, %s, %s)", year, month, day);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate date(ZonedDateTime from) {
        try {
            return this.dateLib.date(from);
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public LocalDate date(LocalDate from) {
        try {
            return this.dateLib.date(from);
        } catch (Exception e) {
            String message = String.format("date(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(String literal) {
        try {
            return this.timeLib.time(literal);
        } catch (Exception e) {
            String message = String.format("time(%s)", literal);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(Double hour, Double minute, Double second, Duration offset) {
        try {
            return this.timeLib.time(hour, minute, second, offset);
        } catch (Exception e) {
            String message = String.format("time(%s, %s, %s, %s)", hour, minute, second, offset);
            logError(message, e);
            return null;
        }
    }

    @Override
    public OffsetTime time(ZonedDateTime from) {
        try {
            return this.timeLib.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public OffsetTime time(LocalDate from) {
        try {
            return this.timeLib.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }
    public OffsetTime time(OffsetTime from) {
        try {
            return this.timeLib.time(from);
        } catch (Exception e) {
            String message = String.format("time(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateAndTime(String from) {
        try {
            return this.dateTimeLib.dateAndTime(from);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public ZonedDateTime dateAndTime(LocalDate date, OffsetTime time) {
        try {
            return this.dateTimeLib.dateAndTime(date, time);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }
    public ZonedDateTime dateAndTime(Object date, OffsetTime time) {
        try {
            return this.dateTimeLib.dateAndTime(date, time);
        } catch (Exception e) {
            String message = String.format("dateAndTime(%s, %s)", date, time);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration duration(String from) {
        try {
            return this.durationLib.duration(from);
        } catch (Exception e) {
            String message = String.format("duration(%s)", from);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Duration yearsAndMonthsDuration(ZonedDateTime from, ZonedDateTime to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(LocalDate from, LocalDate to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(ZonedDateTime from, LocalDate to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    public Duration yearsAndMonthsDuration(LocalDate from, ZonedDateTime to) {
        try {
            return this.durationLib.yearsAndMonthsDuration(from, to);
        } catch (Exception e) {
            String message = String.format("yearsAndMonthsDuration(%s, %s)", from, to);
            logError(message, e);
            return null;
        }
    }

    @Override
    public LocalDate toDate(Object object) {
        return this.dateLib.toDate(object);
    }

    @Override
    public OffsetTime toTime(Object object) {
        return this.timeLib.toTime(object);
    }

    //
    // Numeric functions
    //
    @Override
    public Double decimal(Double n, Double scale) {
        try {
            return this.numberLib.decimal(n, scale);
        } catch (Exception e) {
            String message = String.format("decimal(%s, %s)", n, scale);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double floor(Double number) {
        try {
            return this.numberLib.floor(number);
        } catch (Exception e) {
            String message = String.format("floor(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double ceiling(Double number) {
        try {
            return this.numberLib.ceiling(number);
        } catch (Exception e) {
            String message = String.format("ceiling(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double abs(Double number) {
        try {
            return this.numberLib.abs(number);
        } catch (Exception e) {
            String message = String.format("abs(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double intModulo(Double dividend, Double divisor) {
        try {
            return this.numberLib.intModulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double modulo(Double dividend, Double divisor) {
        try {
            return this.numberLib.modulo(dividend, divisor);
        } catch (Exception e) {
            String message = String.format("modulo(%s, %s)", dividend, divisor);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sqrt(Double number) {
        try {
            return this.numberLib.sqrt(number);
        } catch (Exception e) {
            String message = String.format("sqrt(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double log(Double number) {
        try {
            return this.numberLib.log(number);
        } catch (Exception e) {
            String message = String.format("log(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double exp(Double number) {
        try {
            return this.numberLib.exp(number);
        } catch (Exception e) {
            String message = String.format("exp(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean odd(Double number) {
        try {
            return this.numberLib.odd(number);
        } catch (Exception e) {
            String message = String.format("odd(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean even(Double number) {
        try {
            return this.numberLib.even(number);
        } catch (Exception e) {
            String message = String.format("even(%s)", number);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double mean(List list) {
        try {
            return this.numberLib.mean(list);
        } catch (Exception e) {
            String message = String.format("mean(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double mean(Object... args) {
        try {
            return this.numberLib.mean(args);
        } catch (Exception e) {
            String message = String.format("mean(%s)", args);
            logError(message, e);
            return null;
        }
    }

    //
    // String functions
    //
    @Override
    public Boolean contains(String string, String match) {
        try {
            return this.stringLib.contains(string, match);
        } catch (Exception e) {
            String message = String.format("contains(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean startsWith(String string, String match) {
        try {
            return this.stringLib.startsWith(string, match);
        } catch (Exception e) {
            String message = String.format("startsWith(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean endsWith(String string, String match) {
        try {
            return this.stringLib.endsWith(string, match);
        } catch (Exception e) {
            String message = String.format("endsWith(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stringLength(String string) {
        try {
            return string == null ? null : Double.valueOf(this.stringLib.stringLength(string));
        } catch (Exception e) {
            String message = String.format("stringLength(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, Double startPosition) {
        try {
            return this.stringLib.substring(string, startPosition);
        } catch (Exception e) {
            String message = String.format("substring(%s, %s)", string, startPosition);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substring(String string, Double startPosition, Double length) {
        try {
            return this.stringLib.substring(string, startPosition, length);
        } catch (Exception e) {
            String message = String.format("substring(%s, %s, %s)", string, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String upperCase(String string) {
        try {
            return this.stringLib.upperCase(string);
        } catch (Exception e) {
            String message = String.format("upperCase(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String lowerCase(String string) {
        try {
            return this.stringLib.lowerCase(string);
        } catch (Exception e) {
            String message = String.format("lowerCase(%s)", string);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringBefore(String string, String match) {
        try {
            return this.stringLib.substringBefore(string, match);
        } catch (Exception e) {
            String message = String.format("substringBefore(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String substringAfter(String string, String match) {
        try {
            return this.stringLib.substringAfter(string, match);
        } catch (Exception e) {
            String message = String.format("substringAfter(%s, %s)", string, match);
            logError(message, e);
            return null;
        }
    }

    @Override
    public String replace(String input, String pattern, String replacement) {
        return replace(input, pattern, replacement, "");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) {
        try {
            return this.stringLib.replace(input, pattern, replacement, flags);
        } catch (Exception e) {
            String message = String.format("replace(%s, %s, %s, %s)", input, pattern, replacement, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean matches(String input, String pattern) {
        return matches(input, pattern, "");
    }

    @Override
    public Boolean matches(String input, String pattern, String flags) {
        try {
            return this.stringLib.matches(input, pattern, flags);
        } catch (Exception e) {
            String message = String.format("matches(%s, %s, %s)", input, pattern, flags);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List split(String string, String delimiter) {
        try {
            return this.stringLib.split(string, delimiter);
        } catch (Exception e) {
            String message = String.format("split(%s, %s)", string, delimiter);
            logError(message, e);
            return null;
        }
    }

    //
    // Boolean functions
    //
    @Override
    public Boolean and(List list) {
        try {
            return this.booleanLib.and(list);
        } catch (Exception e) {
            String message = String.format("and(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean and(Object... args) {
        try {
            return this.booleanLib.and(args);
        } catch (Exception e) {
            String message = String.format("and(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(List list) {
        try {
            return this.booleanLib.all(list);
        } catch (Exception e) {
            String message = String.format("all(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean all(Object... args) {
        try {
            return this.booleanLib.all(args);
        } catch (Exception e) {
            String message = String.format("all(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(List list) {
        try {
            return this.booleanLib.or(list);
        } catch (Exception e) {
            String message = String.format("or(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean or(Object... args) {
        try {
            return this.booleanLib.or(args);
        } catch (Exception e) {
            String message = String.format("or(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(List list) {
        try {
            return this.booleanLib.any(list);
        } catch (Exception e) {
            String message = String.format("any(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean any(Object... args) {
        try {
            return this.booleanLib.any(args);
        } catch (Exception e) {
            String message = String.format("any(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Boolean not(Boolean operand) {
        try {
            return this.booleanType.booleanNot(operand);
        } catch (Exception e) {
            String message = String.format("not(%s)", operand);
            logError(message, e);
            return null;
        }
    }

    //
    // Date functions
    //
    public Double year(LocalDate date) {
        try {
            return Double.valueOf(this.dateLib.year(date));
        } catch (Exception e) {
            String message = String.format("year(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double year(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.dateLib.year(dateTime));
        } catch (Exception e) {
            String message = String.format("year(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double month(LocalDate date) {
        try {
            return Double.valueOf(this.dateLib.month(date));
        } catch (Exception e) {
            String message = String.format("month(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double month(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.dateLib.month(dateTime));
        } catch (Exception e) {
            String message = String.format("month(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double day(LocalDate date) {
        try {
            return Double.valueOf(this.dateLib.day(date));
        } catch (Exception e) {
            String message = String.format("day(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double day(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.dateLib.day(dateTime));
        } catch (Exception e) {
            String message = String.format("day(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(LocalDate date) {
        try {
            return Double.valueOf(this.dateLib.weekday(date));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", date);
            logError(message, e);
            return null;
        }
    }
    public Double weekday(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.dateLib.weekday(dateTime));
        } catch (Exception e) {
            String message = String.format("weekday(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Time functions
    //
    public Double hour(OffsetTime time) {
        try {
            return Double.valueOf(this.timeLib.hour(time));
        } catch (Exception e) {
            String message = String.format("hour(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Double hour(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.timeLib.hour(dateTime));
        } catch (Exception e) {
            String message = String.format("hour(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double minute(OffsetTime time) {
        try {
            return Double.valueOf(this.timeLib.minute(time));
        } catch (Exception e) {
            String message = String.format("minute(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Double minute(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.timeLib.minute(dateTime));
        } catch (Exception e) {
            String message = String.format("minute(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Double second(OffsetTime time) {
        try {
            return Double.valueOf(this.timeLib.second(time));
        } catch (Exception e) {
            String message = String.format("second(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Double second(ZonedDateTime dateTime) {
        try {
            return Double.valueOf(this.timeLib.second(dateTime));
        } catch (Exception e) {
            String message = String.format("second(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public Duration timeOffset(OffsetTime time) {
        try {
            return this.timeLib.timeOffset(time);
        } catch (Exception e) {
            String message = String.format("timeOffset(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public Duration timeOffset(ZonedDateTime dateTime) {
        try {
            return this.timeLib.timeOffset(dateTime);
        } catch (Exception e) {
            String message = String.format("timeOffset(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    public String timezone(OffsetTime time) {
        try {
            return this.timeLib.timezone(time);
        } catch (Exception e) {
            String message = String.format("timezone(%s)", time);
            logError(message, e);
            return null;
        }
    }
    public String timezone(ZonedDateTime dateTime) {
        try {
            return this.timeLib.timezone(dateTime);
        } catch (Exception e) {
            String message = String.format("timezone(%s)", dateTime);
            logError(message, e);
            return null;
        }
    }

    //
    // Duration functions
    //
    public Double years(Duration duration) {
        try {
            return Double.valueOf(this.durationLib.years(duration));
        } catch (Exception e) {
            String message = String.format("years(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    public Double months(Duration duration) {
        try {
            return Double.valueOf(this.durationLib.months(duration));
        } catch (Exception e) {
            String message = String.format("months(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    public Double days(Duration duration) {
        try {
            return Double.valueOf(this.durationLib.days(duration));
        } catch (Exception e) {
            String message = String.format("days(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    public Double hours(Duration duration) {
        try {
            return Double.valueOf(this.durationLib.hours(duration));
        } catch (Exception e) {
            String message = String.format("hours(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    public Double minutes(Duration duration) {
        try {
            return Double.valueOf(this.durationLib.minutes(duration));
        } catch (Exception e) {
            String message = String.format("minutes(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    public Double seconds(Duration duration) {
        try {
            return Double.valueOf(this.durationLib.seconds(duration));
        } catch (Exception e) {
            String message = String.format("seconds(%s)", duration);
            logError(message, e);
            return null;
        }
    }

    //
    // List functions
    //
    @Override
    public Boolean listContains(List list, Object element) {
        try {
            return this.listLib.listContains(list, element);
        } catch (Exception e) {
            String message = String.format("listContains(%s, %s)", list, element);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List append(List list, Object... items) {
        try {
            return this.listLib.append(list, items);
        } catch (Exception e) {
            String message = String.format("append(%s, %s)", list, items);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double count(List list) {
        try {
            return this.numberLib.count(list);
        } catch (Exception e) {
            String message = String.format("count(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double min(List list) {
        try {
            return this.numberLib.min(list);
        } catch (Exception e) {
            String message = String.format("min(%s)", list);
            logError(message, e);
            return null;
        }
    }

    public Double min(Object... args) {
        try {
            return this.numberLib.min(args);
        } catch (Exception e) {
            String message = String.format("min(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double max(List list) {
        try {
            return this.numberLib.max(list);
        } catch (Exception e) {
            String message = String.format("max(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double max(Object... args) {
        try {
            return this.numberLib.max(args);
        } catch (Exception e) {
            String message = String.format("max(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(List list) {
        try {
            return this.numberLib.sum(list);
        } catch (Exception e) {
            String message = String.format("sum(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double sum(Object... args) {
        try {
            return this.numberLib.sum(args);
        } catch (Exception e) {
            String message = String.format("sum(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, Double startPosition) {
        try {
            return this.listLib.sublist(list, startPosition.intValue());
        } catch (Exception e) {
            String message = String.format("sublist(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List sublist(List list, Double startPosition, Double length) {
        try {
            return this.listLib.sublist(list, startPosition.intValue(), length.intValue());
        } catch (Exception e) {
            String message = String.format("sublist(%s, %s, %s)", list, startPosition, length);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List concatenate(Object... lists) {
        try {
            return this.listLib.concatenate(lists);
        } catch (Exception e) {
            String message = String.format("concatenate(%s)", lists);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List insertBefore(List list, Double position, Object newItem) {
        try {
            return this.listLib.insertBefore(list, position.intValue(), newItem);
        } catch (Exception e) {
            String message = String.format("insertBefore(%s, %s, %s)", list, position, newItem);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List remove(List list, Object position) {
        try {
            return this.listLib.remove(list, ((Number)position).intValue());
        } catch (Exception e) {
            String message = String.format("remove(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List reverse(List list) {
        try {
            return this.listLib.reverse(list);
        } catch (Exception e) {
            String message = String.format("reverse(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List indexOf(List list, Object match) {
        List result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                if (o == null && match == null || o!= null && o.equals(match)) {
                    result.add(Double.valueOf((double) i + 1));
                }
            }
        }
        return result;
    }

    @Override
    public List union(Object... lists) {
        try {
            return this.listLib.union(lists);
        } catch (Exception e) {
            String message = String.format("union(%s)", lists);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List distinctValues(List list) {
        try {
            return this.listLib.distinctValues(list);
        } catch (Exception e) {
            String message = String.format("distinctValues(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List flatten(List list) {
        try {
            return this.listLib.flatten(list);
        } catch (Exception e) {
            String message = String.format("flatten(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(List list) {
        try {
            return this.numberLib.product(list);
        } catch (Exception e) {
            String message = String.format("product(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double product(Object... args) {
        try {
            return this.numberLib.product(args);
        } catch (Exception e) {
            String message = String.format("product(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double median(List list) {
        try {
            return this.numberLib.median(list);
        } catch (Exception e){
            String message = String.format("median(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double median(Object... args) {
        try {
            return this.numberLib.median(args);
        } catch (Exception e) {
            String message = String.format("median(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stddev(List list) {
        try {
            return this.numberLib.stddev(list);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public Double stddev(Object... args) {
        try {
            return this.numberLib.stddev(args);
        } catch (Exception e) {
            String message = String.format("stddev(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(List list) {
        try {
            return this.numberLib.mode(list);
        } catch (Exception e) {
            String message = String.format("mode(%s)", list);
            logError(message, e);
            return null;
        }
    }

    @Override
    public List mode(Object... args) {
        try {
            return this.numberLib.mode(args);
        } catch (Exception e) {
            String message = String.format("mode(%s)", args);
            logError(message, e);
            return null;
        }
    }

    @Override
    public void collect(List result, List list) {
        try {
            this.listLib.collect(result, list);
        } catch (Exception e) {
            String message = String.format("collect(%s, %s)", result, list);
            logError(message, e);
        }
    }

    @Override
    public <T> List<T> sort(List<T> list, LambdaExpression<Boolean> comparator) {
        try {
            return this.listLib.sort(list, comparator);
        } catch (Exception e) {
            String message = String.format("sort(%s)", list);
            logError(message, e);
            return null;
        }
    }
}
