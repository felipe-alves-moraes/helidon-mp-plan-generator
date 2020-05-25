package com.fmoraes.plangenerator.service;

import static java.lang.System.Logger.*;

import com.fmoraes.plangenerator.domain.entity.PlanValues;
import com.fmoraes.plangenerator.domain.entity.RepaymentPlan;
import com.fmoraes.plangenerator.domain.service.LoansCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PlanGeneratorService {

    private static final System.Logger LOG = System.getLogger(PlanGeneratorService.class.getName());

    private final LoansCalculator loansCalculator;

    @Inject
    public PlanGeneratorService(LoansCalculator loansCalculator) {
        this.loansCalculator = loansCalculator;
    }

    public List<RepaymentPlan> createRepaymentPlan(PlanValues values) {
        LOG.log(Level.INFO, "Received plan to calculate: {0}", values);

        BigDecimal annuity = loansCalculator.calculateAnnuity(values.getLoanAmount(),
                values.getInterestRate(), values.getDuration());
        LOG.log(Level.DEBUG, "Annuity calculated: {0}€", annuity);
        if (isEqualTo(annuity, BigDecimal.ZERO)) {
            LOG.log(Level.INFO, "Not possible to calculate repayment plan with Zero values");
            return Collections.emptyList();
        }

        BigDecimal outstandingAmount = values.getLoanAmount();
        List<RepaymentPlan> plan = new ArrayList<>();
        for (int i = 0; i < values.getDuration(); i++) {
            BigDecimal interest = loansCalculator.calculateInterest(outstandingAmount, values.getInterestRate());
            BigDecimal principal = loansCalculator.calculatePrincipal(annuity, interest);
            BigDecimal remaining = outstandingAmount.subtract(principal);

            if (i == values.getDuration() - 1 && isGreaterThan(remaining, BigDecimal.ZERO)) {
                LOG.log(Level.DEBUG, "It's the last month of payment, and we still needs to receive: {0}€", remaining);
                LOG.log(Level.DEBUG, "Adding remaining to principal: {0} + {1}", principal, remaining);
                principal = principal.add(remaining.abs());
                remaining = BigDecimal.ZERO;
            }

            BigDecimal borrowerPaymentAmount = loansCalculator.calculateBorrowerPaymentAmount(principal, interest);

            if (isGreaterThan(principal, outstandingAmount)) {
                LOG.log(Level.DEBUG, "Calculated principal exceeds outstanding amount, considering outstanding amount as principal");
                principal = outstandingAmount;
                borrowerPaymentAmount = borrowerPaymentAmount.subtract(remaining.abs());
                remaining = BigDecimal.ZERO;
            }

            plan.add(new RepaymentPlan(values.getStartDate().plusMonths(i),
                    borrowerPaymentAmount,
                    principal, interest,
                    outstandingAmount,
                    remaining));

            outstandingAmount = remaining;
        }

        LOG.log(Level.DEBUG, "Generated plan: {0}", plan);
        LOG.log(Level.INFO, "Plan generated!");
        return plan;
    }

    private boolean isGreaterThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    private boolean isEqualTo(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }

}
