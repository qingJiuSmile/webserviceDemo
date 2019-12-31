package cn.webservice.demo.web;

import cn.webservice.demo.entity.QrCode;
import cn.webservice.demo.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.util.*;

@RestController
@RequestMapping("/service")
@Api(tags = "webService",description = "webService调用外部接口")
public class WebServiceController {

    // 注入配置的转json工具
    @Autowired
    private List<JacksonJaxbJsonProvider> jsonProvider;

    @PostMapping("/gotoService")
    @ApiOperation(value = "wsdl 请求接口测试")
    public String gotoService(String path,String methodName,String codeStr){
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(path);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke(methodName);
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

    @GetMapping("/getData")
    @ApiOperation(value = "测试调用接口")
    public QrCode getData(String path, String key, String name) {
        //调用webservice获取查询数据
        System.out.println("path:"+path+"?"+key+"="+name);
     String data = WebClient
                .create(path+"?"+key+"="+name, jsonProvider)
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(data);
        return null;
    }


    @PostMapping("/getTianQi")
    @ApiOperation("调用天气预报")
    public String getTianQi(String name){
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl");
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
    public static void main(String[] args) {
        try {
            //字符集
            String encodingStyle = "utf-8";
            //WSDL的地址
            String endpoint = "http://www.webxml.com.cn/WebServices/RandomFontsWebService.asmx?wsdl";
            //命名空间，在WSDL中对应的标签是：参见说明第3条                                    
            String targetNamespace = "http://WebXml.com.cn/";
            //具体方法的调用URI，在WSDL中对应的标签是：参见说明第4条
            String soapActionURI = "http://WebXml.com.cn/getCharFonts";
            //具体调用的方法名，在WSDL中对应的标签是：参见说明第5条
            String method = "getCharFonts";
            //调用接口的参数的名字
            String[] paramNames = {"byFontsLength"};
            //调用接口的参数的值
            Integer[] paramValues = {1};

            Service service = new Service();
            Call call = (Call) service.createCall();
//            call.setTimeout(new Integer(20000));  //设置超时时间
            call.setSOAPActionURI(soapActionURI);
            call.setTargetEndpointAddress(new java.net.URL(endpoint));  //设置目标接口的地址
            call.setEncodingStyle(encodingStyle);//设置传入服务端的字符集格式如utf-8等
            call.setOperationName(new QName(targetNamespace,method));// 具体调用的方法名，可以由接口提供方告诉你，也可以自己从WSDL中找
            call.setUseSOAPAction(true);
            call.addParameter(new QName(targetNamespace,paramNames[0]),
                    org.apache.axis.encoding.XMLType.XSD_INTEGER,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
//            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型  ，如String
            call.setReturnClass(java.lang.String[].class); //返回字符串数组类型
            // 给方法传递参数，并且调用方法 ，如果无参，则new Obe
            String[] result = (String[]) call.invoke(new Object[] {paramValues[0]});
            // 打印返回值
            System.out.println("result is " + result.toString());
            if (result != null && result.length > 0) {
                for (int i = 0; i < result.length; i++) {
                    System.out.println(result[i]);
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }



    /**
     * 获取axis请求形式的加密头
     * @return SOAPHeaderElement
     */
    public SOAPHeaderElement getHoapHeader(Map<String,String> map){

        //将签到获得的请求头等信息传入，并设置加密头
        int thirdType = Integer.valueOf(map.get("thirdType"));
        int secret1 = Integer.valueOf(map.get("secret1"));
        String secret2 = map.get("secret2");

        //开始添加认证头
        SOAPHeaderElement head = new SOAPHeaderElement(new QName(map.get("targetNamespace"),"SecurityHeader"));
        try {
            SOAPElement a1 = head.addChildElement("ThirdType");
            a1.addTextNode(thirdType+"");
            a1 = head.addChildElement("Secret1");
            a1.addTextNode(secret1+"");
            a1 = head.addChildElement("Secret2");
            a1.addTextNode(secret2+"");

            head.setPrefix("");
            head.setActor(null);
            //head.setMustUnderstand(true);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        System.out.println("===============================");
        System.out.println("getHoapHeader========="+head);
        return head;
    }


    @PostMapping("/getSignInfo")
    @ApiOperation(value = "测试请求")
    public  Map<String,String> getSignInfo(String endpoint,String targetNamespace,String method,String code){
        Map<String,String> singInfoCache = new HashMap<String,String>();
        //传入targetNamespace 方便复用
        singInfoCache.put("targetNamespace",targetNamespace);
        //传入endpoint
        singInfoCache.put("endpoint",endpoint);
        try{

            // 创建一个服务(service)调用(call)
            Service service = new Service();
            Call call = (Call) service.createCall();// 通过service创建call对象

            // 设置service所在URL
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            call.setOperationName(new QName(targetNamespace, method));
            call.setUseSOAPAction(true);

            Object ret = call.invoke(new Object[] {null});
            System.out.println(ret.toString());
            Map<String,String> out = call.getOutputParams();
            if (out!=null){
                Iterator<Map.Entry<String,String>> it = out.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String,String> entry = (Map.Entry<String,String>) it.next();
                    Object key = entry.getKey();
                    String parm = key.toString().replace("{"+targetNamespace+"}", "");
                    System.out.println("key:"+parm);
                    System.out.println("value:"+entry.getValue());
                    if("stanum".equalsIgnoreCase(parm)){
                        singInfoCache.put("nStaNum",entry.getValue());
                    }else if("thirdType".equalsIgnoreCase(parm)){
                        singInfoCache.put("thirdType",entry.getValue());
                    }else if("secret1".equalsIgnoreCase(parm)){
                        singInfoCache.put("secret1",entry.getValue());
                    }else if("secret2".equalsIgnoreCase(parm)){
                        singInfoCache.put("secret2",entry.getValue());
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        getPerInfo(singInfoCache,code);
        return singInfoCache;
    }



    public void getPerInfo(Map<String,String> map,String code){

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        try{
            // 指出service所在URL
            String method = "GetPerInfo";

            // 创建一个服务(service)调用(call)
            Service service = new Service();
            Call call = (Call) service.createCall();// 通过service创建call对象
            // 设置service所在URL
            call.setTargetEndpointAddress(new java.net.URL(map.get("endpoint")));
            call.setOperationName(new QName(map.get("targetNamespace"), method));
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(map.get("targetNamespace")+method);
            SOAPHeaderElement head = getHoapHeader(map);  //添加加密头验证加密因子
            call.addHeader(head);

            call.addParameter(new QName(map.get("targetNamespace"),"sQRCode"), Constants.XSD_STRING, ParameterMode.IN);

            call.setReturnType(XMLType.XSD_STRING);
            //请求对应接口 并加入请求参数
            Object ret = call.invoke(new Object[] {code});
            System.out.println("ret========="+ret);
            Map<String,Object> out = call.getOutputParams();
            System.out.println("out======"+out);
/*
            if (out!=null) {
                Iterator ite = out.values().iterator();
                System.out.println("ite============="+ite);
                while (ite.hasNext()) {
                     //System.out.println("-------------"+(String)ite.next());
                    //JSONObject jsonObject = JSONObject.fromObject((String)ite.next());
                    JSONObject jsonObject = JSONObject.parseObject((String)ite.next());
                    System.out.println("jsb===================="+jsonObject);
                    //返回json数据解析
                    if (jsonObject.containsKey("Table1")) {
                        JSONArray transitListArray = jsonObject.getJSONArray("Table1");
                        for (int i = 0; i < transitListArray.size(); i++) {
                            System.out.println(transitListArray.get(i));
                            Map<String,Object> innerMap = new HashMap<String,Object>();
                            JSONObject obj = JSONObject.fromObject(transitListArray.get(i));
                            Iterator it = obj.entrySet().iterator();
                            while(it.hasNext()){
                                Map.Entry entry = (Map.Entry) it.next();
                                innerMap.put(entry.getKey()+"",entry.getValue());
                            }
                            dataList.add(innerMap);
                        }
                    }
                }
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("===============================");

/*
        renderJson(dataList);
*/
    }
    @PostMapping("/error")
    @ApiOperation(value = "错误码提取")
    public void getPerInfoError(String code){

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        try{
            // 指出service所在URL
            String method = "GetErrTxt";
            String endpoint = "http://10.201.1.3:8081/ThirdWebservice.asmx";
            String targetNamespace = "http://www.hzsun.com/";

            // 创建一个服务(service)调用(call)
            Service service = new Service();
            Call call = (Call) service.createCall();// 通过service创建call对象
            // 设置service所在URL
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            call.setOperationName(new QName(targetNamespace, method));
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(targetNamespace+method);

            call.addParameter(new QName(targetNamespace,"sErrCode"), Constants.XSD_STRING, ParameterMode.IN);

            call.setReturnType(XMLType.XSD_STRING);
            //请求对应接口 并加入请求参数
            Object ret = call.invoke(new Object[] {code});
            System.out.println("ret========="+ret);
            Map out = call.getOutputParams();
            System.out.println("out======"+out);

           /* if (out!=null) {
                Iterator ite = out.values().iterator();
                System.out.println("ite============="+ite);
                while (ite.hasNext()) {
                     //System.out.println("-------------"+(String)ite.next());
                    //JSONObject jsonObject = JSONObject.fromObject((String)ite.next());
                    JSONObject jsonObject = JSONObject.parseObject((String)ite.next());
                    System.out.println("jsb===================="+jsonObject);
                    //返回json数据解析
                    if (jsonObject.containsKey("Table1")) {
                        JSONArray transitListArray = jsonObject.getJSONArray("Table1");
                        for (int i = 0; i < transitListArray.size(); i++) {
                            System.out.println(transitListArray.get(i));
                            Map<String,Object> innerMap = new HashMap<String,Object>();
                            JSONObject obj = JSONObject.fromObject(transitListArray.get(i));
                            Iterator it = obj.entrySet().iterator();
                            while(it.hasNext()){
                                Map.Entry entry = (Map.Entry) it.next();
                                innerMap.put(entry.getKey()+"",entry.getValue());
                            }
                            dataList.add(innerMap);
                        }
                    }
                }
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("===============================");

/*
        renderJson(dataList);
*/
    }
}
