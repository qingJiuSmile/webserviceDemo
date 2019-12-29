package cn.webservice.demo.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @Title: WebServiceDemoServiceImpl.java
 * @Description: TODO()
 * @Author: 爱飘de小子  上午9:39
 * @Date: 2018年10月26日 09点39分
 */
@Service
@WebService(serviceName = "WebServiceDemoService", // 与接口中指定的name一致
        //这里最后如果不加 "/" 则会报错
        targetNamespace = "http://service.demo.webservice.cn/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "cn.webservice.demo.service.WebServiceDemoService" // 接口地址
)
public class WebServiceDemoServiceImpl implements WebServiceDemoService {
 
    @Override
    public String hello(String name) {
        return "hello"+name;
    }
 

}
