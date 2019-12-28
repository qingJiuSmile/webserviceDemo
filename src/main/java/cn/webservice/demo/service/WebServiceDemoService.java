package cn.webservice.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Title: WebServiceDemoService.java
 * @Description: TODO()
 * @Author: 爱飘de小子  上午9:39
 * @Date: 2018年10月26日 09点39分
 */
@WebService
public interface WebServiceDemoService {
 
    @WebMethod
    String hello(@WebParam(name = "name") String name);
    
}