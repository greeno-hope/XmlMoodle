package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.question.Question;

public class TrueFalseQuestion extends Question {

    private boolean answer;

    public TrueFalseQuestion(Document doc, Q_TYPE type, String questionText, boolean answer) {
        super(doc, type, questionText);
        this.answer = answer;
    }

    @Override
    public Element asDocumentElement() {
        return null;
    }
}
