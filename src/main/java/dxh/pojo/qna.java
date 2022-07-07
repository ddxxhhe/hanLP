package dxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class qna {
    private Integer id;
    private String ques;
    private String ans;
    private double score;

    public void setScore(double score) {
        this.score = score;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public double getScore() {
        return score;
    }

    public Integer getId() {
        return id;
    }

    public String getQues() {
        return ques;
    }

    public String getAns() {
        return ans;
    }
}
