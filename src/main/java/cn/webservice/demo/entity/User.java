package cn.webservice.demo.entity;

import javax.xml.bind.annotation.XmlRootElement;
/*
  @XmlRootElement 指定对象序列化为XML或JSON时的根节点名称
 */
@XmlRootElement(name = "User")
public class User {

    private String name;
    private String sex;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
