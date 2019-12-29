package cn.webservice.demo.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JsonProvider extends ArrayList<JacksonJaxbJsonProvider> {
    
    // 在构造方法中, 添加JacksonJaxbJsonProvider
    public JsonProvider(){
        this.add(new JacksonJaxbJsonProvider());
    }
}