package edu.ubb.rtgsimulator.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertyResolver;

@Configuration
@PropertySource("classpath:/rtgsimulator.properties")
public class PropertyProvider {

    @Autowired
    PropertyResolver resolver;
    public String  getProperty(String key)
    {
        return resolver.getProperty(key);
    }
}
