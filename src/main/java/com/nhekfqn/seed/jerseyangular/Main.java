package com.nhekfqn.seed.jerseyangular;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

public class Main {

    private final PropertiesConfiguration propertiesConfiguration;

    public static void main(String[] args) throws Exception {
        PropertiesConfiguration propertiesConfiguration = loadPropertiesConfiguration(args);

        new Main(propertiesConfiguration).startServer();
    }

    private static PropertiesConfiguration loadPropertiesConfiguration(String[] args) throws ConfigurationException {
        PropertiesConfiguration propertiesConfiguration;
        if ((args == null) || (args.length == 0)) {
            propertiesConfiguration = new PropertiesConfiguration(ClassLoader.getSystemResource("conf/server.properties"));
        } else {
            propertiesConfiguration = new PropertiesConfiguration(args[0]);
        }

        return propertiesConfiguration;
    }

    public Main(PropertiesConfiguration propertiesConfiguration) {
        this.propertiesConfiguration = propertiesConfiguration;
    }

    public void startServer() throws Exception {
        int serverPort = propertiesConfiguration.getInt("server.port.http");
        Server server = new Server(serverPort);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(Resource.newClassPathResource("/web"));

        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/api");
        servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

        initGuiceInjector();

        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(resourceHandler);
        handlerList.addHandler(servletContextHandler);
        server.setHandler(handlerList);

        server.start();
        server.join();
    }

    private void initGuiceInjector() throws Exception {
        Guice.createInjector(new AppServletModule(propertiesConfiguration));
    }

}
