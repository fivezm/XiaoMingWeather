package test.study.wzm.xiaomingweather.Utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    /**
     * 用Okhttp进行网络连接
     * @param address  url地址
     * @param callback 回调函数,网络异步请求,不在主线程中执行,等数据返回之后再处理数据
     */
    public static void sendRequestWithOKhttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
