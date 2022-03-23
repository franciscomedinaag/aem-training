package com.vass.aemtr.core.models;

import com.vass.aemtr.core.service.SimpleSearchService;
import com.vass.aemtr.core.service.impl.SimpleSearchServiceImpl;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class)
public class SearchComponentModel {

    @OSGiService
    private SimpleSearchService simpleSearchService;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "/content/aemtr")
    private String path;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "/conf/aemtr/settings/wcm/templates/page-content")
    private String template;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private Integer limit;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String additionalInfoFilter;

    private List<SimpleSearchServiceImpl.PageDataPojo> resultList;

    @PostConstruct
    protected void init() {
        /* if (path != null)
            resultList = simpleSearchService.getSearchedPages(path, template, limit != null ? limit.toString() : "5"); */
        if (path != null)
            resultList = simpleSearchService.getSearchedPagesByParam(path, template, additionalInfoFilter, limit != null ? limit.toString() : "5");
    }

    public List<SimpleSearchServiceImpl.PageDataPojo> getResultList() {
        return new ArrayList<>(resultList);
    }

    public String getAdditionalInfoFilter() {
        return additionalInfoFilter;
    }

    public void setAdditionalInfoFilter(String additionalInfoFilter) {
        this.additionalInfoFilter = additionalInfoFilter;
    }
}
