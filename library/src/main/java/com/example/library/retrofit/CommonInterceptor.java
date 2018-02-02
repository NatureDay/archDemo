package com.example.library.retrofit;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 拦截器：参数签名验证
 * Author: Administrator
 * Time: 2017/2/28 16:06
 */

class CommonInterceptor implements Interceptor {

    private static final String TAG = "CommonInterceptor";
    private static final String API_REQUEST = "/api/";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String MEDIA_TYPE_FORM = "application/x-www-form-urlencoded";
    private static final String MEDIA_TYPE_JSON = "application/json;charset=UTF-8";
    private static final String NONCESTR = "noncestr";
    private static final String TIMESTAMP = "timestamp";
    private static final String SIGNATURE = "signature";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request userRequest = chain.request();
        Request.Builder requestBuilder = userRequest.newBuilder();
        HttpUrl url = userRequest.url();
        if (!url.toString().contains(API_REQUEST)) {
            return chain.proceed(userRequest);
        }
        String parameters = url.query();
        if (!TextUtils.isEmpty(parameters)) {
            Request request = createRequestForRequestLine(requestBuilder, url, parameters);
            if (request != null) {
                return chain.proceed(request);
            } else {
                return chain.proceed(userRequest);
            }
        } else {
            RequestBody body = userRequest.body();
            if (body != null) {
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    if (contentType.toString().equals(MEDIA_TYPE_FORM)) {
                        Request request = createRequestForRequestBodyByForm(userRequest, body);
                        if (request != null) {
                            return chain.proceed(request);
                        }
                    } else if (contentType.toString().equals(MEDIA_TYPE_JSON)) {
                        Request request = createRequestForRequestBodyByJson(userRequest, body);
                        if (request != null) {
                            return chain.proceed(request);
                        }
                    }
                }
                return chain.proceed(userRequest);
            } else {
                return chain.proceed(userRequest);
            }
        }
    }

    /**
     * 以request-line方式请求
     */
    private Request createRequestForRequestLine(Request.Builder requestBuilder, HttpUrl url, String parameters) {
        try {
            String[] parameter = parameters.split("&");
            HttpUrl.Builder httpUrlBuilder = url.newBuilder();
            Arrays.sort(parameter);
            StringBuilder newParameter = new StringBuilder();
            for (String param : parameter) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && !TextUtils.isEmpty(keyValue[0]) && !TextUtils.isEmpty(keyValue[1])) {
                    httpUrlBuilder.setQueryParameter(keyValue[0], keyValue[1]);
                }
                newParameter.append(param);
                newParameter.append("&");
            }
            String uuid = UUID.randomUUID().toString();
            String time = String.valueOf(System.currentTimeMillis());
            newParameter.append(NONCESTR);
            newParameter.append("=");
            newParameter.append(uuid);
            newParameter.append("&");
            newParameter.append(TIMESTAMP);
            newParameter.append("=");
            newParameter.append(time);
            String signature = sha1(newParameter.toString());
            if (TextUtils.isEmpty(signature)) return null;
            httpUrlBuilder.setQueryParameter(NONCESTR, uuid);
            httpUrlBuilder.setQueryParameter(TIMESTAMP, time);
            httpUrlBuilder.setQueryParameter(SIGNATURE, signature);
            return requestBuilder.url(httpUrlBuilder.build()).build();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 以request-body方式请求,application/x-www-form-urlencoded
     */
    private Request createRequestForRequestBodyByForm(Request request, RequestBody body) {
        try {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String parameters = buffer.readString(charset);
            if (TextUtils.isEmpty(parameters)) return null;
            String[] parameter = parameters.split("&");
            Arrays.sort(parameter);
            StringBuilder newParameter = new StringBuilder();
            for (String param : parameter) {
                newParameter.append(URLDecoder.decode(param, "UTF-8"));
                newParameter.append("&");
            }
            String uuid = UUID.randomUUID().toString();
            String time = String.valueOf(System.currentTimeMillis());
            newParameter.append(NONCESTR);
            newParameter.append("=");
            newParameter.append(uuid);
            newParameter.append("&");
            newParameter.append(TIMESTAMP);
            newParameter.append("=");
            newParameter.append(time);
            String signature = sha1(newParameter.toString());
            if (TextUtils.isEmpty(signature)) return null;
            newParameter.append("&");
            newParameter.append(SIGNATURE);
            newParameter.append("=");
            newParameter.append(signature);
            RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE_FORM), newParameter.toString().getBytes());
            return request.newBuilder().method(request.method(), requestBody).build();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 以request-body方式请求,application/json;charset=UTF-8
     */
    private Request createRequestForRequestBodyByJson(Request request, RequestBody body) {
        try {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String parameters = buffer.readString(charset);
            if (TextUtils.isEmpty(parameters)) return null;
            JSONObject parameter = new JSONObject(parameters);
            String params = sortJsonObject(parameter);
            if (TextUtils.isEmpty(params)) return null;
            StringBuilder newParameter = new StringBuilder();
            newParameter.append(params);
            String uuid = UUID.randomUUID().toString();
            String time = String.valueOf(System.currentTimeMillis());
            newParameter.append(NONCESTR);
            newParameter.append("=");
            newParameter.append(uuid);
            newParameter.append("&");
            newParameter.append(TIMESTAMP);
            newParameter.append("=");
            newParameter.append(time);
            String signature = sha1(newParameter.toString());
            if (TextUtils.isEmpty(signature)) return null;
            parameter.put(NONCESTR, uuid);
            parameter.put(TIMESTAMP, time);
            parameter.put(SIGNATURE, signature);
            RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE_JSON), parameter.toString().getBytes());
            return request.newBuilder().method(request.method(), requestBody).build();
        } catch (Exception e) {
            return null;
        }
    }

    private String sortJsonObject(JSONObject obj) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        formatJsonByMap(map, obj);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        return sb.toString();
    }

    private void formatJsonByMap(TreeMap<String, Object> map, JSONObject obj) {
        Iterator<String> it = obj.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object value = obj.opt(key);
            try {
                JSONObject object = new JSONObject(value.toString());
                formatJsonByMap(map, object);
            } catch (JSONException e) {
                try {
                    new JSONArray(value.toString());
                    map.remove(key);
                } catch (JSONException e1) {
                    map.put(key, value);
                }
            }
        }
    }

    /**
     * string十六进制SHA1加密
     *
     * @param s
     * @return
     */
    private String sha1(String s) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(s.getBytes(UTF8));
            byte[] ss = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : ss) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
