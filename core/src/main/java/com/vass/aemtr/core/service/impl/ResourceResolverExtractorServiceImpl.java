package com.vass.aemtr.core.service.impl;

import com.vass.aemtr.core.service.ResourceResolverExtractorService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = {ResourceResolverExtractorService.class}, immediate = true)
public class ResourceResolverExtractorServiceImpl implements ResourceResolverExtractorService {
    public static final String SLASH = "/";
    private static final String systemUser = "aemtr-system";
    private static final String systemWriteUser = "aemtr-write-system";
    private final Logger logger = LoggerFactory.getLogger(ResourceResolverExtractorServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public ResourceResolver retrieveResourceResolverSystemUser() {
        return retrieveResourceResolver(systemUser);
    }

    @Override
    public ResourceResolver retrieveResourceResolverSystemWriteUser() {
        return retrieveResourceResolver(systemWriteUser);
    }

    @Override
    public void closeResourceResolver(ResourceResolver resourceResolver) {
        if (resourceResolver != null && resourceResolver.isLive())
            resourceResolver.close();
    }

    private Map<String, Object> retrieveSystemUser() {
        return retrieveUser(systemUser);
    }

    private Map<String, Object> retrieveSystemWriteUser() {
        return retrieveUser(systemWriteUser);
    }

    private ResourceResolver retrieveResourceResolver(String user) {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, user);
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            logger.error("Unable to get the ".concat(user).concat("ResourceResolver: {}"), e.getMessage());
        }
        return resourceResolver;
    }

    public Map<String, Object> retrieveUser(String user) {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, user);
        return param;
    }

}
