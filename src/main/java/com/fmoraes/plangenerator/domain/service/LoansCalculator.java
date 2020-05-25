package com.fmoraes.plangenerator.domain.service;

import static java.lang.System.Logger.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.enterprise.context.Dependent;

@Dependent
public class LoansCalculator {

    private static final System.Logger LOG = System.getLogger(LoansCalculator.class.getName());

    private static final int MONTHS_IN_YEAR = 12;
    private static final int DAYS_IN_MONTH = 30;
    private static final int DAYS_IN_YEAR = 360;

    public BigDecimal calculateAnnuity(BigDecimal loanAmount, BigDecimal interestRate, int duration) {
        if (isNullOrZero(loanAmount) || isNullOrZero(interestRate) || duration == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyRate = getMonthlyRate(interestRate);
        BigDecimal loanDivisor = BigDecimal.ONE.subtract(monthlyRate.add(BigDecimal.ONE).pow(-duration, MathContext.DECIMAL32));
        return loanAmount
                .multiply(monthlyRate)
                .divide(loanDivisor, 2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculateInterest(BigDecimal loanAmount, BigDecimal interestRate) {
        if (isNullOrZero(loanAmount) || isNullOrZero(interestRate)) {
            return BigDecimal.ZERO;
        }

        
        LOG.log(Level.DEBUG, "Calculating Interest for values: loanAmount={0} and interestRate={1}", loanAmount, interestRate);
        BigDecimal interest = loanAmount
                .multiply(getDecimalRate(interestRate))
                .multiply(BigDecimal.valueOf(DAYS_IN_MONTH))
                .divide(BigDecimal.valueOf(DAYS_IN_YEAR), 2, RoundingMode.HALF_EVEN);
        LOG.log(Level.DEBUG, "Interest={0}", interest);

        return interest;
    }

    public BigDecimal calculatePrincipal(BigDecimal annuity, BigDecimal interest) {
        if (annuity == null) {
            return BigDecimal.ZERO;
        }

        if (interest == null) {
            interest = BigDecimal.ZERO;
        }

        LOG.log(Level.DEBUG, "Calculating Principal for values: annuity={0} and interest={1}", annuity, interest);
        BigDecimal principal = annuity.subtract(interest);
        LOG.log(Level.DEBUG, "Principal={0}", principal);

        return principal;
    }

    public BigDecimal calculateBorrowerPaymentAmount(BigDecimal principal, BigDecimal interest) {
        if (principal == null) {
            return BigDecimal.ZERO;
        }

        if (interest == null) {
            interest = BigDecimal.ZERO;
        }

        LOG.log(Level.DEBUG, "Calculating BorrowerPaymentAmount for values: principal={0} and interest={1}", principal, interest);
        BigDecimal borrowerPaymentAmount = principal.add(interest);
        LOG.log(Level.DEBUG, "BorrowerPaymentAmount={0}", borrowerPaymentAmount);

        return borrowerPaymentAmount;
    }

    private BigDecimal getMonthlyRate(BigDecimal interestRate) {
        return getDecimalRate(interestRate).divide(BigDecimal.valueOf(MONTHS_IN_YEAR), 6, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getDecimalRate(BigDecimal interestRate) {
        return interestRate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_EVEN);
    }

    private boolean isNullOrZero(BigDecimal bigDecimal) {
        return bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) == 0;
    }
}
