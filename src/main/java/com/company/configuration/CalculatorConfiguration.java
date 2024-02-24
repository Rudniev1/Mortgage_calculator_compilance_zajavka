package com.company.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.company.model.Rate;
import com.company.model.RateAmounts;
import com.company.model.Summary;
import com.company.service.SummaryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Configuration
@ComponentScan(basePackages = "com.company")
public class CalculatorConfiguration {

    @Bean
    public static SummaryService create() {
        return rates -> {
            BigDecimal interestSum = calculate(rates, rate -> rate.rateAmounts().interestAmount());
            BigDecimal overpaymentProvisionSum = calculate(rates, rate -> rate.rateAmounts().overpayment().provisionAmount());
            BigDecimal totalLostSum = interestSum.add(overpaymentProvisionSum);
            BigDecimal totalCapital = calculate(rates, rate -> totalCapital(rate.rateAmounts()));
            return new Summary(interestSum, overpaymentProvisionSum, totalLostSum, totalCapital);
        };
    }

    private static BigDecimal totalCapital(final RateAmounts rateAmounts) {
        return rateAmounts.capitalAmount().add(rateAmounts.overpayment().amount());
    }

    private static BigDecimal calculate(final List<Rate> rates, Function<Rate, BigDecimal> function) {

        return rates.stream()
                .reduce(BigDecimal.ZERO,(sum, rate) ->  sum.add(function.apply(rate)), BigDecimal::add);
    }

}
