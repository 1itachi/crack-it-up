package edu.neu.madcourse.crack_it_up;

public class FlashCard {
    private String content;
    private int pageNumber;
    private String heading;

    public FlashCard(){}

    public FlashCard(String content, int pageNumber, String heading) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
