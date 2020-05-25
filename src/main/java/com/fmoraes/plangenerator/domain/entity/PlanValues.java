package com.fmoraes.plangenerator.domain.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 *
 * @author fmoraes
 */
public final class PlanValues {
    private final BigDecimal loanAmount;
    private final BigDecimal interestRate;
    private final int duration;
    private final ZonedDateTime startDate;

    public PlanValues(BigDecimal loanAmount, BigDecimal interestRate, int duration, ZonedDateTime startDate) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.duration = duration;
        this.startDate = startDate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public int getDuration() {
        return duration;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "PlanValues{" +
                "loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", duration=" + duration +
                ", startDate=" + startDate +
                '}';
    }
}
