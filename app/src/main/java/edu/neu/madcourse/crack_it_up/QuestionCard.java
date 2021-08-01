package edu.neu.madcourse.crack_it_up;

public class QuestionCard {
    private String questionId;
    private String questionText;
    private String idealAnswer;

    public QuestionCard(){}

    public String getIdealAnswer() {
        return idealAnswer;
    }

    public void setIdealAnswer(String idealAnswer) {
        this.idealAnswer = idealAnswer;
    }

    public QuestionCard(String questionId, String questionText, String idealAnswer) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.idealAnswer = idealAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}

