package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.question.Question;

public class ShortAnswerQuestion extends Question {

    public ShortAnswerQuestion(Document doc, Q_TYPE type, String questionName, String questionText) {
        super(doc, type, questionName, questionText);
    }

    @Override
    public Element asDocumentElement() {
        return null;
    }

}
