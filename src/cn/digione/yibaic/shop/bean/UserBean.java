package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-20 Time: 上午10:00
 */
public class UserBean implements Serializable {
	private static final long serialVersionUID = 8625559125623619751L;
    private Integer id;//用户id
    private String remark;	//个人说明
    private String nickname;	//昵称
    private String email;	//邮箱
    private Integer state;	//用户状态 -1：删除，0：正常，1：冻结
    private String userName;	//用户名
    private Integer areaId;	//地区编码
    private String mobile	;	//手机号码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
