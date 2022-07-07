package dxh.Service;

import dxh.pojo.qna;

import java.util.List;

public interface qnaService {
    //查询问题
    public List<qna> query(String ques);
    //返回所有问题以及回答
    public List<qna> listAll();
}
