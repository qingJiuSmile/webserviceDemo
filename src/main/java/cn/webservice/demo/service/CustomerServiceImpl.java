package cn.webservice.demo.service;

import cn.webservice.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
 

    @Override
    public User findById(Integer id) {
       User user = new User();
       user.setId(id);
       user.setName("tjy");
       user.setSex("男");
       return user;
    }
}