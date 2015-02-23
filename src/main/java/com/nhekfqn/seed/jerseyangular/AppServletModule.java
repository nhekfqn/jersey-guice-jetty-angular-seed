package com.nhekfqn.seed.jerseyangular;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.nhekfqn.seed.jerseyangular.resource.LoginResource;
import com.nhekfqn.seed.jerseyangular.resource.DataResource;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration.PropertiesConfiguration;

public class AppServletModule extends ServletModule {

    private final PropertiesConfiguration propertiesConfiguration;

    public AppServletModule(PropertiesConfiguration propertiesConfiguration) {
        this.propertiesConfiguration = propertiesConfiguration;
    }

    // todo: requireExplicitBindings? (remove singleton annotations)
    @Override
    protected void configureServlets() {
        bind(PropertiesConfiguration.class).toInstance(propertiesConfiguration);

        bind(LoginResource.class).in(Scopes.SINGLETON);
        bind(DataResource.class).in(Scopes.SINGLETON);

        Map<String, String> options = new HashMap<>();
        options.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        serve("/*").with(GuiceContainer.class, options);
    }

}
