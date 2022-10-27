package uk.ac.hope.csc.greeno.quizparser.moodlexml.answer;

import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

import java.util.List;

public class MultiAnswer implements QuizElement {

    protected List<Character> answers;



    @Override
    public Element asDocumentElement() {
        return null;
    }


}
