package uk.ac.hope.csc.greeno.quizparser.moodlexml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ShortAnswerQuestion extends Question {

    public ShortAnswerQuestion(Document doc, Q_TYPE type, String questionText) {
        super(doc, type, questionText);
    }

    @Override
    public Element asDocumentElement() {
        return null;
    }

}
