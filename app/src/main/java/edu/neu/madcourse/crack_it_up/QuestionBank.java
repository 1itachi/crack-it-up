package edu.neu.madcourse.crack_it_up;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private static List<QuestionList> arrayQuestions() {
        final List<QuestionList> questionList = new ArrayList<>();

        //Create object of QuestionList class and pass a questions along with options and answer
        final QuestionList question1 = new QuestionList("Index values of an array range from", "0 to length – 1", "1 to length", "0 to length", "0 to length + 1", "0 to length – 1", "");
        final QuestionList question2 = new QuestionList("What is an array?", "A", "B", "correct answer", "D", "correct answer", "");
        final QuestionList question3 = new QuestionList("When?", "A", "B", "correct answer", "D", "correct answer", "");

        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);

        return questionList;
    }

    static List<QuestionList> getQuestions(String selectedTopicName) {
        return arrayQuestions();
    }
}
