package com.fmoraes.plangenerator.resources.mapper;

import com.fmoraes.plangenerator.domain.entity.PlanValues;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.json.JsonObject;

/**
 *
 * @author fmoraes
 */
public final class PlanGeneratorMapper {
    
    private PlanGeneratorMapper(){}
    
    public static PlanValues fromDTO(JsonObject payload) {
        return new PlanValues(new BigDecimal(payload.getString("loanAmount")),
                new BigDecimal(payload.getString("nominalRate")), payload.getInt("duration"),
                ZonedDateTime.parse(payload.getString("startDate")));
    }
    
}
