package org.jabref.model.entry;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Optional;
import java.util.stream.Stream;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTest {

    @Test
    void parseCorrectlyYearRangeDate() throws Exception {
        Date expectedDataRange = new Date(Year.of(2014), Year.of(2017));
        assertEquals(Optional.of(expectedDataRange), Date.parse("2014/2017"));
    }

    @Test
    void parseCorrectlyDayMonthYearDate() throws Exception {
        Date expected = new Date(LocalDate.of(2014, 6, 19));
        assertEquals(Optional.of(expected), Date.parse("19-06-2014"));
    }

    @Test
    void parseCorrectlyMonthYearDate() throws Exception {
        Date expected = new Date(YearMonth.of(2014, 6));
        assertEquals(Optional.of(expected), Date.parse("06-2014"));
    }

    @Test
    void parseCorrectlyYearMonthDate() throws Exception {
        Date expected = new Date(YearMonth.of(2014, 6));
        assertEquals(Optional.of(expected), Date.parse("2014-06"));
    }

    @Test
    void parseCorrectlyYearDate() throws Exception {
        Date expected = new Date(Year.of(2014));
        assertEquals(Optional.of(expected), Date.parse("2014"));
    }

    @Test
    void parseDateNull() {
        assertThrows(NullPointerException.class, () -> Date.parse(null));
    }

    // Caso de Teste 1: Valores das datas diferentes
    @Test
    void equalsDifferentDates() throws Exception {
        assertEquals(false, new Date(2022, 01, 15).equals(new Date(2020, 1, 19)));
    }

    // Caso de Teste 2: Valores das datas iguais
    @Test
    void equalsSameDates() throws Exception {
        assertEquals(true, new Date(2022, 04, 25).equals(new Date(2022, 04, 25)));
    }

    // Caso de Teste 3: Data comparada com null
    @Test
    void equalsNullDate() throws Exception {
        assertEquals(false, new Date(2022, 01, 19).equals(null));
    }

    // Caso de Teste 4: Valores das datas diferentes, 
    // com data a ser comparada sendo anterior.
    @Test
    void equalsPastDate() throws Exception {
        assertEquals(false, new Date(2022, 03, 17).equals(new Date(2020, 02, 02)));
    }

    // Caso de Teste 5: Valores das datas diferentes,
    // com a data a ser comparada sendo atual.
    @Test
    void equalsCurrentDate() throws Exception {
        assertEquals(false, new Date(2021, 03, 17).equals(LocalDateTime.now()));
    }

    // Caso de Teste 6: Valores das datas diferentes,
    // com o dia do mês diferente
    @Test
    void equalsDifferentDayDates() throws Exception {
        assertEquals(false, new Date(2022, 03, 19).equals(new Date(2022, 03, 13)));
    }

    // Caso de Teste 7: Valores das datas diferentes,
    // com o mês diferente
    @Test
    void equalsDifferentMonthDates() throws Exception {
        assertEquals(false, new Date(2022, 01, 17).equals(new Date(2022, 02, 17)));
    }

    // Caso de Teste 8: Valores das datas diferentes,
    // com o ano diferente
    @Test
    void equalsDifferentYearDates() throws Exception {
        assertEquals(false, new Date(2021, 03, 17).equals(new Date(2020, 03, 17)));
    }

    // Caso de Teste 9: Valores das datas iguais,
    // comparado com um Objeto ao invés de um Date
    @Test
    void equalsObject() throws Exception {
        Object comparedTo = new Date(2022, 03, 17);
        assertEquals(true, new Date(2022, 03, 17).equals(comparedTo));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCornerCaseArguments")
    public void nonExistentDates(String invalidDate, String errorMessage) {
        assertEquals(Optional.empty(), Date.parse(invalidDate), errorMessage);

    }

    private static Stream<Arguments> provideInvalidCornerCaseArguments() {
        return Stream.of(
                Arguments.of("", "input value not empty"),
                Arguments.of("32-06-2014", "day of month exists [1]"),
                Arguments.of("00-06-2014", "day of month exists [2]"),
                Arguments.of("30-13-2014", "month exists [1]"),
                Arguments.of("30-00-2014", "month exists [2]")
        );
    }
}