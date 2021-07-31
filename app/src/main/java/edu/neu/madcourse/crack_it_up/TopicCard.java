package edu.neu.madcourse.crack_it_up;

public class TopicCard {
    private String name;
    private String topicId;
    private String type;

    public TopicCard(){}

    public String getName() {
        return name;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public TopicCard(String topicName, String topicId, String type) {
        this.name = topicName;
        this.topicId = topicId;
        this.type = type;
    }

}

