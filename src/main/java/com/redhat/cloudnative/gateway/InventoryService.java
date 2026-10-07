package com.redhat.cloudnative.gateway;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "inventory-service")
public interface InventoryService {

    @GET
    @Path("/inventory/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Availability> getAvailability(@PathParam("itemId") String itemId);
}
