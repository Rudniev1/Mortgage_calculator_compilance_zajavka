package service;

import com.company.service.TimePointCalculationService;
import com.company.service.TimePointCalculationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.company.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.*;

class TimePointCalculationServiceImplTest {

    private TimePointCalculationService timePointCalculationService;

    @BeforeEach
    public void setup() {
        this.timePointCalculationService = new TimePointCalculationServiceImpl();
    }

    @Test
    @DisplayName("Should calculate first rate time point successfully")
    void calculateTimePointForFirstRate() {
        // given
        InputData inputData = TestData.someInputData();
        TimePoint expected = TestData.someTimePoint();

        // when
        TimePoint result1 = timePointCalculationService.calculate(BigDecimal.valueOf(1), inputData);

        // then
        Assertions.assertEquals(expected, result1);
        Assertions.assertThrows(RuntimeException.class,() ->
                timePointCalculationService.calculate(BigDecimal.valueOf(8), inputData));
    }

    @ParameterizedTest
    @MethodSource(value = "testMortgageData")
    @DisplayName("Should calculate other rate time point than first successfully")
    void calculateTimePointForOtherRates(LocalDate expectedDate, BigDecimal rateNumber, BigDecimal year, BigDecimal month, LocalDate date) {
        // given
        TimePoint timePoint = TestData.someTimePoint()
            .withYear(year)
            .withMonth(month)
            .withDate(date);
        Rate rate = TestData.someRate().withTimePoint(timePoint);
        TimePoint expected = timePoint.withDate(expectedDate);

        // when
        TimePoint result = timePointCalculationService.calculate(rateNumber, rate);

        // then
        Assertions.assertEquals(expected, result);
    }

    public static Stream<Arguments> testMortgageData() {
        return Stream.of(
            arguments(
                LocalDate.of(2010, 2, 1),
                BigDecimal.valueOf(12),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(12),
                LocalDate.of(2010, 1, 1)),
            arguments(
                LocalDate.of(2010, 2, 1),
                BigDecimal.valueOf(15),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                LocalDate.of(2010, 1, 1)),
            arguments(
                LocalDate.of(2013, 10, 1),
                BigDecimal.valueOf(76),
                BigDecimal.valueOf(7),
                BigDecimal.valueOf(4),
                LocalDate.of(2013, 9, 1))
        );
    }

}