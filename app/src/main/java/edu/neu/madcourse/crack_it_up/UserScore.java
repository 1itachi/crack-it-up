package edu.neu.madcourse.crack_it_up;

public class UserScore {

        private String topicId;
        private int score;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public UserScore(String topicId, int score) {
        this.topicId = topicId;
        this.score = score;
    }
}
