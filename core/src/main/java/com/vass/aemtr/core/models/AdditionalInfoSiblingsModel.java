/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.vass.aemtr.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AdditionalInfoSiblingsModel {
    @ScriptVariable
    private Page currentPage;

    @SlingObject
    private Resource resource;

    private List<String> additionalInfoList;

    @PostConstruct
    protected void init() {
        additionalInfoList = new ArrayList<>();

        Iterator<Page> pageIterator = currentPage.getParent().listChildren();

        while (pageIterator.hasNext()) {
            Page p = pageIterator.next();
            String additionalInfo = p.getProperties().get("additionalInfo", "No Additional Info");
            additionalInfoList.add(additionalInfo);
        }
    }

    public List<String> getAdditionalInfoList() {
        return additionalInfoList;
    }

}
