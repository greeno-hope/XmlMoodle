package uk.ac.hope.csc.greeno.quizparser.moodlexml.answer;

import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

public class SingleAnswer implements QuizElement {

    protected Character answer;

    @Override
    public Element asDocumentElement() {
        return null;
    }
}
