package dxh.ServiceImp;

import dxh.Service.qnaService;
import dxh.dao.qnaDao;
import dxh.pojo.qna;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class qnaServiceImp implements qnaService {

    private final qnaDao qnadao;

    public qnaServiceImp(qnaDao qnadao) {
        this.qnadao = qnadao;
    }

    @Override
    public List<qna> query(String ques) {
        return qnadao.query(ques);
    }

    @Override
    public List<qna> listAll() {
        return qnadao.listAll();
    }
}
