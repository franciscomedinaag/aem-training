package com.vass.aemtr.core.service.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.vass.aemtr.core.service.ResourceResolverExtractorService;
import com.vass.aemtr.core.service.SimpleSearchService;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SimpleSearchService.class, immediate = true)
public class SimpleSearchServiceImpl implements SimpleSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleSearchServiceImpl.class);

    @Reference
    private QueryBuilder queryBuilder;

    @Reference
    private ResourceResolverExtractorService resolverExtractorService;

    //TODO
    @Override
    public List<PageDataPojo> getSearchedPagesByParam(String path, String template, String param, String limit) {
        Map<String, String> queryMap = getAdditionalInfoQuery(path, template, param, limit);
        ResourceResolver resolver = resolverExtractorService.retrieveResourceResolverSystemUser();
        Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap), resolver.adaptTo(Session.class));
        SearchResult result = query.getResult();
        List<PageDataPojo> resultList = getResultAsList(result);
        resolverExtractorService.closeResourceResolver(resolver);
        return resultList;
    }

    @Override
    public List<PageDataPojo> getSearchedPages(String path, String template, String limit) {
        Map<String, String> queryMap = getBasicQuery(path, template, limit);
        ResourceResolver resolver = resolverExtractorService.retrieveResourceResolverSystemUser();
        Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap), resolver.adaptTo(Session.class));
        SearchResult result = query.getResult();
        List<PageDataPojo> resultList = getResultAsList(result);
        resolverExtractorService.closeResourceResolver(resolver);
        return resultList;
    }

    private List<PageDataPojo> getResultAsList(SearchResult searchResult) {
        ArrayList<PageDataPojo> resultList = new ArrayList<>();
        if (searchResult != null && searchResult.getTotalMatches() > 0) {
            PageDataPojo pageDataPojo;
            for (Hit hit : searchResult.getHits()) {
                try {
                    Page currentPage = hit.getResource().adaptTo(Page.class);
                    if (currentPage != null) {
                        pageDataPojo = new PageDataPojo(currentPage.getPath(), currentPage.getName(), currentPage.getTitle());
                        resultList.add(pageDataPojo);
                    }
                } catch (RepositoryException e) {
                    LOG.error("Error extracting result resource: {}", e.getMessage());
                }
            }
        }
        return resultList;
    }


    /**
     * This method generates a map with a query depending on the previously sent parameters.
     *
     * @param path     String that contains the path where the search will take place.
     * @param template String that contains the template that should match on the search.
     * @param limit    The max amount of results that should return the query.
     * @return Returns a Map with the final query.
     */
    private Map<String, String> getBasicQuery(String path, String template, String limit) {
        Map<String, String> map = new HashMap<>();
        map.put("path", path);
        map.put("type", NameConstants.NT_PAGE);
        if (template != null) {
            map.put("0_property", NameConstants.NN_CONTENT + "/" + NameConstants.NN_TEMPLATE);
            map.put("0_property.value", template);
        }
        map.put("1_orderby", NameConstants.NN_CONTENT + "/" + NameConstants.PN_CREATED);
        map.put("1_orderby.sort", "desc");
        map.put("p.limit", limit);
        return map;
    }

    /**
     * This method generates a map with a query depending on the previously sent parameters and the additionalInfo value on the page.
     *
     * @param path     String that contains the path where the search will take place.
     * @param template String that contains the template that should match on the search.
     * @param param String that contains the additionalInfo value to search.
     * @param limit    The max amount of results that should return the query.
     * @return Returns a Map with the final query.
     */
    private Map<String, String> getAdditionalInfoQuery(String path, String template, String param, String limit) {
        Map<String, String> map = new HashMap<>();
        map.put("path", path);
        map.put("type", NameConstants.NT_PAGE);
        if (template != null) {
            map.put("0_property", NameConstants.NN_CONTENT + "/" + NameConstants.NN_TEMPLATE);
            map.put("0_property.value", template);
        }
        map.put("1_orderby", NameConstants.NN_CONTENT + "/" + NameConstants.PN_CREATED);
        map.put("1_orderby.sort", "desc");
        map.put("p.limit", limit);
        if (param != null) {
            map.put("2_property", NameConstants.NN_CONTENT + "/" + "additionalInfo");
            map.put("2_property.value", param);
        }
        return map;
    }

    public static class PageDataPojo {
        private String path;
        private String name;
        private String title;

        public PageDataPojo(String path, String name, String title) {
            this.path = path;
            this.name = name;
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
