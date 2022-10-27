package uk.ac.hope.csc.greeno.quizparser.moodlexml.choice;

import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

public class Choice implements QuizElement {

    protected String text;
    protected Character key;

    @Override
    public Element asDocumentElement() {
        return null;
    }
}
