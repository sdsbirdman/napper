package com.hs.config;

import com.google.inject.Singleton;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerContextService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Dung on 3/31/2016.
 */
@Singleton
public class Bootstrap extends HttpServlet {

    /*--------------------------------------------
	|             C O N S T A N T S             |
	============================================*/

	/*--------------------------------------------
	|    I N S T A N C E   V A R I A B L E S    |
	============================================*/

	/*--------------------------------------------
	|         C O N S T R U C T O R S           |
	============================================*/

    /*--------------------------------------------
    |   P U B L I C    A P I    M E T H O D S   |
    ============================================*/
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("Hello Swagger");
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("swagger-1266.appspot.com");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.hs.user");
        beanConfig.setScan(true);
        new SwaggerContextService()
                .withServletConfig(servletConfig)
                .setSwaggerConfig(beanConfig);
    }

    /*--------------------------------------------
	|    N O N - P U B L I C    M E T H O D S   |
	============================================*/

	/*--------------------------------------------
	|  A C C E S S O R S / M O D I F I E R S    |
	============================================*/

	/*--------------------------------------------
	|       I N L I N E    C L A S S E S        |
	============================================*/
}
