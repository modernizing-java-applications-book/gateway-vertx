package com.redhat.cloudnative.gateway;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/api")
@ApplicationScoped
public class GatewayResource {

    private static final Logger LOG = Logger.getLogger(GatewayResource.class);

    @RestClient
    CatalogService catalogService;

    @RestClient
    InventoryService inventoryService;

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Product>> products() {
        return catalogService.getCatalog()
            .onItem().transformToMulti(list ->
                io.smallrye.mutiny.Multi.createFrom().iterable(list))
            .onItem().transformToUniAndMerge(product ->
                inventoryService.getAvailability(product.getItemId())
                    .onItem().transform(availability -> {
                        product.setAvailability(availability);
                        return product;
                    })
                    .onFailure().recoverWithItem(() -> {
                        LOG.warnv("Inventory error for {0}", product.getItemId());
                        return product;
                    })
            )
            .collect().asList();
    }
}
