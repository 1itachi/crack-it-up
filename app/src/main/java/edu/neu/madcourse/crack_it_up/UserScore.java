package edu.neu.madcourse.crack_it_up;

public class UserScore {

        private String topicId;
        private String topicName;
        private int score;
        private String scoreType;

    public UserScore(String topicId, String topicName, int score, String scoreType) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.score = score;
        this.scoreType = scoreType;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
