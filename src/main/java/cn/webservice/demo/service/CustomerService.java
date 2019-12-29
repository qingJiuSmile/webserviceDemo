package cn.webservice.demo.service;

import cn.webservice.demo.entity.User;

import javax.ws.rs.*;

//访问当前服务接口对应的路径
@Path("/customerService")
public interface CustomerService {
 
    /**
     * 客户服务:根据id查询客户
     */
    @Path("/findById")
    @GET
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public User findById(@QueryParam("id")Integer id);
}