package Push_Notification;
  
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
  
public class PushToAPP{
	
	// 应用专属参数，在网页端创建后自动生成，不可修改
    static String appId = "bm8vNdXFUZ5UAyO4D0VUn7";
    static String appkey = "4Ta71zR9638chSbPBjERJ4";
    static String master = "2ljofWMtaVAZjjXQZ7gin6";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) throws Exception{
        IGtPush push = new IGtPush(host, appkey, master);
        //建立连接，开始鉴权
        push.connect();
        

        AppMessage message = new AppMessage();
        
        
        //从以下模板中选择其中一个
        
        //透传模板
//        TransmissionTemplate template = TransmissionTemplateDemo();
        //链接模板
//        LinkTemplate template = linkTemplateDemo();
        //通知模板
//        NotificationTemplate template = NotificationTemplateDemo();
        //下载应用模板
        NotyPopLoadTemplate template = NotyPopLoadTemplateDemo();
            
        message = setMessage(template);
        
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
        

    }
    
    public static AppMessage setMessage(ITemplate template){
      //新建消息
      AppMessage message = new AppMessage();  
//      选择模板
      message.setData(template);
    //设置消息离线，并设置离线时间
      message.setOffline(true);
      //离线有效时间，单位为毫秒，可选
      message.setOfflineExpireTime(24*1000*3600);
      //设置推送目标条件过滤
      List appIdList = new ArrayList();
      List phoneTypeList = new ArrayList();
      List provinceList = new ArrayList();
//    List tagList = new ArrayList();
      appIdList.add(appId);
      //设置机型
      phoneTypeList.add("ANDROID");
      //设置省份
      provinceList.add("广东");
      //设置标签内容
//    tagList.add("开心");
      message.setAppIdList(appIdList);
      message.setPhoneTypeList(phoneTypeList);
      message.setProvinceList(provinceList);
//    message.setTagList(tagList);
	return message;
    }
    
    public static void Notification()throws IOException{
    	IGtPush push = new IGtPush(host, appkey, master);
        //建立连接，开始鉴权
        push.connect();
        AppMessage message = new AppMessage();
    	NotyPopLoadTemplate template = NotyPopLoadTemplateDemo();
    	message = setMessage(template);
    	IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
    
    
    //透视传输模板
    public static TransmissionTemplate TransmissionTemplateDemo() {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionType(1);
        template.setTransmissionContent("请输入需要透传的内容");
        return template;
    }
    
    
    
    //点击通知打开网页模板
    public static LinkTemplate linkTemplateDemo() {
	    LinkTemplate template = new LinkTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setTitle("链接推送");
	    template.setText("点击打开百度");
	    // 配置通知栏图标
	    template.setLogo("baidu_72.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 设置打开的网址地址
	    template.setUrl("http://www.baidu.com");
	    return template;
	}
    
    //点击通知打开应用模板
	public static NotificationTemplate NotificationTemplateDemo() {
	    NotificationTemplate template = new NotificationTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setTitle("这是一条通知");
	    template.setText("点击打开应用");
	    // 配置通知栏图标
	    template.setLogo("icon.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 透传消息设置
	    template.setTransmissionType(1);
	    template.setTransmissionContent("请输入您要透传的内容");
	    return template;
	}
	
	
    //点击通知下载应用模板
	public static NotyPopLoadTemplate NotyPopLoadTemplateDemo() {
	    NotyPopLoadTemplate template = new NotyPopLoadTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setNotyTitle("应用下载");
	    template.setNotyContent("点击下载最新应用");
	    // 配置通知栏图标
	    template.setNotyIcon("icon.png");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setBelled(true);
	    template.setVibrationed(true);
	    template.setCleared(true);
	  
	    // 设置弹框标题与内容
	    template.setPopTitle("弹框标题");
	    template.setPopContent("弹框内容");
	    // 设置弹框显示的图片
	    template.setPopImage("http://www-igexin.qiniudn.com/wp-content/uploads/2013/08/logo_getui1.png");
	    template.setPopButton1("下载");
	    template.setPopButton2("取消");
	  
	    // 设置下载标题
	    template.setLoadTitle("下载标题");
	    template.setLoadIcon("file://icon.png");
	    //设置下载地址        
	    template.setLoadUrl("http://wap.igexin.com/android_download/Gexin_android_2.0.apk");
	    return template;
	}
    
    
    
    	
}