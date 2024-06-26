package com.company.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;
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
            BigDecimal interestSum = calculate(rates, rate -> rate.installmentAmounts().interestAmount());
            BigDecimal overpaymentProvisionSum = calculate(rates, rate -> rate.installmentAmounts().overpayment().provisionAmount());
            BigDecimal totalLostSum = interestSum.add(overpaymentProvisionSum);
            BigDecimal totalCapital = calculate(rates, rate -> totalCapital(rate.installmentAmounts()));
            return new Summary(interestSum, overpaymentProvisionSum, totalLostSum, totalCapital);
        };
    }

    private static BigDecimal totalCapital(final InstallmentAmounts installmentAmounts) {
        return installmentAmounts.capitalAmount().add(installmentAmounts.overpayment().amount());
    }

    private static BigDecimal calculate(final List<Installment> installments, Function<Installment, BigDecimal> function) {

        return installments.stream()
                .reduce(BigDecimal.ZERO,(sum, rate) ->  sum.add(function.apply(rate)), BigDecimal::add);
    }

}
