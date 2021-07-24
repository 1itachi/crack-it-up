package edu.neu.madcourse.crack_it_up;

public class QuestionCard {
    private int questionId;
    private String questionText;

    public QuestionCard(){}

    public QuestionCard(int questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}

