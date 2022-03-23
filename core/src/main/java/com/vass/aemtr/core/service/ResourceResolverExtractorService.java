package com.vass.aemtr.core.service;

import org.apache.sling.api.resource.ResourceResolver;

public interface ResourceResolverExtractorService {
    ResourceResolver retrieveResourceResolverSystemUser();

    ResourceResolver retrieveResourceResolverSystemWriteUser();

    void closeResourceResolver(ResourceResolver resourceResolver);

}
