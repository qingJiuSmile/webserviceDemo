package cn.webservice.demo.web;

import cn.webservice.demo.entity.User;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service")
public class WebServiceController {

    // 注入配置的转json工具
    @Autowired
    private List<JacksonJaxbJsonProvider> jsonProvider;

    @PostMapping("/gotoService")
    public String gotoService(String name){
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/spring_demo/myWebservice/UserServiceWS?wsdl");
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("hello", name);
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return objects[0].toString();
    }



    @GetMapping("/findByName")
    public List<User> findByName(String path,String key,String name) {
        //调用webservice获取查询数据
        System.out.println("path:"+path+"?"+key+"="+name);
        JSONObject customer = WebClient
                .create(path+"?"+key+"="+name, jsonProvider)
                .accept(MediaType.APPLICATION_JSON).get(JSONObject.class);
        System.out.println(customer.getString("name"));
        System.out.println(customer.get("id"));
        List<User> ls = new ArrayList<>();
        ls.add(null);
        return ls;
    }

}
