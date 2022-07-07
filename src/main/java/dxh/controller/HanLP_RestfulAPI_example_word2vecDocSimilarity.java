package dxh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档相似度分析范例
 */
public class HanLP_RestfulAPI_example_word2vecDocSimilarity {
    public static void main(String[] args) throws JsonProcessingException {
        //请求头中的token
        String token="52c70a78d39846aebdf63982530d94ba1657123323029token";
        //申请的接口地址
        String url="http://comdo.hanlp.com/hanlp/v1/word2vec/docSimilarity";

        Map<String,Object> params=new HashMap<String,Object>();
        //first文章一
        //secend 文章二
        params.put("first", "上海");
        params.put("second", "上海市长是谁？");
//
//        params.put("second", "中国男足在亚冠联赛胜出,获得胜利");
        //该参数是msr语料训练的word2vec模型，如果需要最新的模型请联系我们哦 ~ support@hanlp.com
        params.put("modelName", "msr_training");

        //执行HanLP词语类比接口，result为返回的结果
        String result=doHanlpApi(token,url,params);
        // first 文本内容关于农业的.
        // second 文本内容关于 农业的.
        // 第二个second 文本内容关于 体育 足球的.
        //first 与second 相似度0.6632305  较为相似文本
        //first 与second2 相似度0.11072569  即不相似的文本
        //另外呢 输出 我要看足球比赛 和 要不做饭吧 输出相似度值为-1 意义为风马牛不相及~
        //打印输出词语类比内容结果
        System.out.println(result);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(result);
        String score = root.findValue("data").asText();
//        JsonNode data1 = root.path("data");
//        Map<String, String> map = mapper.convertValue(data1, Map.class);
        System.out.println(score);
    }
    public static String doHanlpApi(String token,String url,Map<String,Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头，token请放在header里
            httpPost.setHeader("token", token);
            // 创建参数列表
            List<NameValuePair> paramList = new ArrayList<>();
            if (params != null) {
                for (String key : params.keySet()) {
                    //所有参数依次放在paramList中
                    paramList.add(new BasicNameValuePair(key, (String) params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
//            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(response!=null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
