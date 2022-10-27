package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.*;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

public abstract class Question implements QuizElement {

    public static enum Q_TYPE {
        Q_TYPE_UNDETERMINED,
        Q_TYPE_MULTI_CHOICE,
        Q_TYPE_TRUE_FALSE,
        Q_TYPE_SHORT_ANSWER
    };

    protected Document doc;

    protected String questionName;
    protected String questionText;

    protected Q_TYPE type;

    public Question(Document doc, Q_TYPE type, String questionLine) {
        String[] questionLineTokens = questionLine.split("[.]", 2);
        this.doc = doc;
        this.type = type;
        this.questionName = questionLineTokens[0];
        this.questionText = questionLineTokens[1].trim();
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Q_TYPE getType() {
        return type;
    }

}
