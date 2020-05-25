package com.fmoraes.plangenerator.domain.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public final class RepaymentPlan {
    private final ZonedDateTime date;
    private final BigDecimal borrowerPaymentAmount;
    private final BigDecimal principal;
    private final BigDecimal interest;
    private final BigDecimal initialOutstandingPrincipal;
    private final BigDecimal remainingOutstandingPrincipal;

    public RepaymentPlan(ZonedDateTime date, BigDecimal borrowerPaymentAmount, BigDecimal principal, BigDecimal interest,
                         BigDecimal initialOutstandingPrincipal, BigDecimal remainingOutstandingPrincipal) {
        this.date = date;
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.principal = principal;
        this.interest = interest;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public BigDecimal getBorrowerPaymentAmount() {
        return borrowerPaymentAmount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal;
    }

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    @Override
    public String toString() {
        return "RepaymentPlan{" +
                "date=" + date +
                ", borrowerPaymentAmount=" + borrowerPaymentAmount +
                ", principal=" + principal +
                ", interest=" + interest +
                ", initialOutstandingPrincipal=" + initialOutstandingPrincipal +
                ", remainingOutstandingPrincipal=" + remainingOutstandingPrincipal +
                '}';
    }
}
