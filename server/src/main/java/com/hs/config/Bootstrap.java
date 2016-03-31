package com.hs.config;

import com.google.inject.Singleton;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerContextService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@Singleton
public class Bootstrap extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8888");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.hs.user");
        beanConfig.setScan(true);
        new SwaggerContextService()
                .withServletConfig(servletConfig)
                .setSwaggerConfig(beanConfig);
    }
}
