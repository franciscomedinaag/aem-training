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
package com.vass.aemtr.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value={"/bin/pokemonfinder"}
)
@ServiceDescription("Poke Api Servlet using path")
public class PokeApiPath extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final String API_URL="https://pokeapi.co/api/v2/pokemon/";

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {

        String pokemonName = req.getParameter("pokemonName");

        URL url = new URL(API_URL + pokemonName);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HttpConstants.METHOD_GET);
        conn.setRequestProperty("Accept", "application/json");

        if(conn.getResponseCode()==200){
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();

            String output;
            while((output = br.readLine())!=null){
                response.append(output.trim());
            }

            String responseStr = String.valueOf(response);
            responseStr = responseStr.replaceFirst("official-artwork", "official_artwork");

            Gson gson = new Gson();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(responseStr));
        }
    }
}
