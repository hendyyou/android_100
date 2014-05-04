package cn.digione.yibaic.shop.http;


import cn.digione.yibaic.shop.bean.JsonCommonOutBean;
import cn.digione.yibaic.shop.bean.RequestErrorBean;

/**
 * JsonHttpResponse请求的处理接口类
 *
 * @param <T> 接口返回的数据Bean类型
 * @author Simpson
 */
public interface JsonHttpResponseInterface<T> {
    /**
     * 调用processHttpSuccess前的处理方法
     */
    public void processBeforeHttpSuccess();

    /**
     * 调用成功结果后的处理方法
     *
     * @param outBean 接口得到的通用的JsonCommonOutBean<T> outBean
     */
    public void processHttpSuccess(JsonCommonOutBean<T> outBean);

    /**
     * 调用processHttpSuccess后的处理方法
     *
     * @param outBean     返回的数据entity的bean
     * @param msgCode     错误编码
     * @param failureCode 自定义错误编码
     * @param msg         返回的消息
     */
    public void processAfterHttpSuccess(T outBean, Integer msgCode, String failureCode, String msg);

    /**
     * 调用接口失败的处理方法
     *
     * @param e       异常
     * @param outBean 错误的信息的RequestErrorBean outBean
     */
    public void processHttpFailure(Throwable e, RequestErrorBean outBean);

    /**
     * 开始调用接口前的处理方法
     *
     * @param outBean 错误的信息的RequestErrorBean outBean
     */
    public void processHttpStart(RequestErrorBean outBean);

    /**
     * 结束调用接口后的处理方法
     *
     * @param outBean 错误的信息的RequestErrorBean outBean
     */
    public void processHttpFinish(RequestErrorBean outBean);

    /**
     * 接口返回成功的处理方法，该方法必须实现
     *
     * @param outBean 返回的数据entity的bean
     * @param msg     返回的消息
     */
    public void processCallSuccess(T outBean, String msg);

    /**
     * 接口返回失败的处理方法，该方法可不实现，有默认的处理逻辑
     *
     * @param outBean     返回的数据entity的bean
     * @param failureCode 错误代码
     * @param msg         错误消息
     */
    public void processCallFailure(T outBean, String failureCode, String msg);

    /**
     * 接口返回超时的处理方法，该方法可不实现，有默认的处理逻辑
     *
     * @param outBean 返回的数据entity的bean
     * @param msg     错误消息
     */
    public void processCallTimeout(T outBean, String failureCode, String msg);

    /**
     * 接口返回成功的异常时的处理方法，该方法可不实现，有默认的处理逻辑
     *
     * @param e 异常信息
     */
    public void processCallSuccessException(Exception e);

}
