package com.fmoraes.plangenerator.resources;

import com.fmoraes.plangenerator.resources.mapper.PlanGeneratorMapper;
import com.fmoraes.plangenerator.resources.validators.ValidPlan;
import com.fmoraes.plangenerator.service.PlanGeneratorService;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoraes
 */
@Path("generate-plan")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlanGeneratorResource {

    private final PlanGeneratorService planGeneratorService;

    @Inject
    public PlanGeneratorResource(PlanGeneratorService planGeneratorService) {
        this.planGeneratorService = planGeneratorService;
    }

    @POST
    public Response generatePlan(@Valid @ValidPlan JsonObject payload) {
        var generatedPlan = planGeneratorService.createRepaymentPlan(PlanGeneratorMapper.fromDTO(payload));
        
        return generatedPlan.isEmpty() ? 
                Response.status(422).build() : 
                Response.status(Response.Status.CREATED).entity(generatedPlan).build();
    }

}
