package com.vass.aemtr.core.service;

import com.day.cq.wcm.api.Page;
import com.vass.aemtr.core.service.impl.SimpleSearchServiceImpl;

import java.util.List;

public interface SimpleSearchService {

    /**
     * This method will create and execute a query and will return the result as a List of Page Objects.
     *
     * @param path     String that contains the path where the search will take place.
     * @param template String that contains the template that should match on the search.
     * @param limit    The max amount of results that should return the query.
     * @return List of Page Objects containing the result.
     */
    List<SimpleSearchServiceImpl.PageDataPojo> getSearchedPages(String path, String template, String limit);

    /**
     * This method will create and execute a query and will return the result as a List of Page Objects.
     * WARNING! THIS METHOD IS NOT IMPLEMENTED YET.
     *
     * @param path     String that contains the path where the search will take place.
     * @param template String that contains the template that should match on the search.
     * @param param    String that should match some content properties of the searched page.
     * @param limit    The max amount of results that should return the query.
     * @return List of Page Objects containing the result.
     */
    List<SimpleSearchServiceImpl.PageDataPojo> getSearchedPagesByParam(String path, String template, String param, String limit);

}
