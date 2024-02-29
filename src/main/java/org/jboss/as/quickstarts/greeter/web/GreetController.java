/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.greeter.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.as.quickstarts.greeter.domain.User;
import org.jboss.as.quickstarts.greeter.domain.UserDao;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/greet")
@RequestScoped
public class GreetController {

    @Inject
    Template greet;

    @Inject
    private UserDao userDao;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response renderGreetPage() {
        TemplateInstance greetInstance = greet
                .data("greeting", null);

        return Response
                .ok(greetInstance.render())
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response greet(@FormParam("username") String username) {
        String greeting;

        User user = userDao.getForUsername(username);
        if (user != null) {
            greeting = "Hello, " + user.getFirstName() + " " + user.getLastName() + "!";
        } else {
            greeting = "No such user exists! Use 'emuster' or 'jdoe'";
        }

        TemplateInstance greetInstance = greet
                .data("greeting", greeting);

        return Response
                .ok(greetInstance.render())
                .build();
    }
}
