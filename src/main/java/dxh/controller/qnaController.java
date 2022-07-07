package dxh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.suggest.Suggester;
import dxh.Service.qnaService;
import dxh.pojo.qna;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/qna")
public class qnaController {
    private final qnaService qnaservice;

    public qnaController(dxh.Service.qnaService qnaService) {
        this.qnaservice = qnaService;
    }

    //根据文本相似度
    @GetMapping("/queryBySimilarity")
    @ResponseBody
    public List<qna> queryQuesBySimilarity(@RequestParam("ques") String ques) throws JsonProcessingException {
        List<qna> res = qnaservice.listAll();
        //请求头中的token
        String token="52c70a78d39846aebdf63982530d94ba1657123323029token";
        //申请的接口地址
        String url="http://comdo.hanlp.com/hanlp/v1/word2vec/docSimilarity";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("first", ques);
        params.put("modelName", "msr_training");
//        System.out.println(res);
        for(int i = 0; i < res.size(); i++) {
            qna temp = res.get(i);
            params.put("second", temp.getQues());
            //执行HanLP词语类比接口，result为返回的结果
            String result=doHanlpApi(token,url,params);
//            System.out.println("111");
//            System.out.println(result);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);
            String score = root.findValue("data").asText();
            res.get(i).setScore(Double.parseDouble(score));
        }
        res = res.stream().sorted(Comparator.comparing(qna::getScore).reversed()).collect(Collectors.toList());
        List<qna> ans = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).getScore() > 0.7) {
                ans.add(res.get(i));
            }
        }
        return ans;
    }

    //根据
    @GetMapping("/queryByRecommend")
    @ResponseBody
    public qna queryQuesByRecommend(@RequestParam("ques") String ques) throws JsonProcessingException {
        List<qna> res = qnaservice.listAll();
//        List<String> sens = res.stream().map(qna::getQues).collect(Collectors.toList());
//        String[] param = sens.toArray(new String[0]);
//        //请求头中的token
//        String token="08e114895471402ca33ba10ee2f5d6f41657155391555token";
//        //申请的接口地址
//        String url="http://comdo.hanlp.com/hanlp/v1/suggester/textSuggest";
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("text", ques);
////        params.put("sentenceArray", sens.toString());
//        params.put("sentenceArray", param.toString());
//        params.put("size", "1");
////        System.out.println("111");
////        System.out.println(sens.toString());
//        //执行api
//        String result=doHanlpApi(token,url,params);
//        System.out.println(result);
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(result);
//        String q = root.findValue("data").asText();
        Suggester suggester = new Suggester();
        for (qna s : res) {
            suggester.addSentence(s.getQues());
        }
        String qq = suggester.suggest(ques, 3).get(0);
        qna ans = new qna();
        ans.setQues(qq);
        for (int i = 0; i < res.size(); i++) {
            String tmp = res.get(i).getQues();
            if (tmp.equals(qq)) {
                ans.setAns(res.get(i).getAns());
            }
        }
        return ans;
    }

    public static String doHanlpApi(String token, String url, Map<String,Object> params) {
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
