package com.redhat.cloudnative.gateway;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/api")
@RegisterRestClient(configKey = "catalog-service")
public interface CatalogService {

    @GET
    @Path("/catalog")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<Product>> getCatalog();
}
