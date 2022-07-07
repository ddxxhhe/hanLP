package dxh.dao;

import dxh.pojo.qna;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface qnaDao {
    //查询可能的问题及回答
    public List<qna> query(String ques);
    //返回所有问题以及回答
    public List<qna> listAll();
}
