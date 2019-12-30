package cn.webservice.demo.entity;

import javax.xml.bind.annotation.XmlRootElement;

//正元接口返回信息实体
@XmlRootElement(name = "QrCode")
public class QrCode {

    private String sPerCode; //个人编号
    private Integer nCardID; //卡号
    private Integer nAccNum; //账号
    private String sAccName; //姓名
    private String sDealerName; //商户名
    private Integer nMonDeal; //应付金额
    private Integer nMonDealCur; //实付金额
    private Integer nConcessionsMon;//优惠金额
    private Integer nConsumeMgFee; //管理费
    private String sDealTime; //交易时间
    private String sRectime; //二维码生成时间

    public String getsPerCode() {
        return sPerCode;
    }

    public void setsPerCode(String sPerCode) {
        this.sPerCode = sPerCode;
    }

    public Integer getnCardID() {
        return nCardID;
    }

    public void setnCardID(Integer nCardID) {
        this.nCardID = nCardID;
    }

    public Integer getnAccNum() {
        return nAccNum;
    }

    public void setnAccNum(Integer nAccNum) {
        this.nAccNum = nAccNum;
    }

    public String getsAccName() {
        return sAccName;
    }

    public void setsAccName(String sAccName) {
        this.sAccName = sAccName;
    }

    public String getsDealerName() {
        return sDealerName;
    }

    public void setsDealerName(String sDealerName) {
        this.sDealerName = sDealerName;
    }

    public Integer getnMonDeal() {
        return nMonDeal;
    }

    public void setnMonDeal(Integer nMonDeal) {
        this.nMonDeal = nMonDeal;
    }

    public Integer getnMonDealCur() {
        return nMonDealCur;
    }

    public void setnMonDealCur(Integer nMonDealCur) {
        this.nMonDealCur = nMonDealCur;
    }

    public Integer getnConcessionsMon() {
        return nConcessionsMon;
    }

    public void setnConcessionsMon(Integer nConcessionsMon) {
        this.nConcessionsMon = nConcessionsMon;
    }

    public Integer getnConsumeMgFee() {
        return nConsumeMgFee;
    }

    public void setnConsumeMgFee(Integer nConsumeMgFee) {
        this.nConsumeMgFee = nConsumeMgFee;
    }

    public String getsDealTime() {
        return sDealTime;
    }

    public void setsDealTime(String sDealTime) {
        this.sDealTime = sDealTime;
    }

    public String getsRectime() {
        return sRectime;
    }

    public void setsRectime(String sRectime) {
        this.sRectime = sRectime;
    }
}
