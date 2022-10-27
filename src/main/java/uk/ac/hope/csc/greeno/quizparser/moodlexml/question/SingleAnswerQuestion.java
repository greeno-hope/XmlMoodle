package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.question.MultiChoiceQuestion;

import java.util.List;

public class SingleAnswerQuestion extends MultiChoiceQuestion {

    protected Character answer;

    public SingleAnswerQuestion(Document doc, Q_TYPE type, String questionText, List<String> potentialAnswers, Character answer) {
        super(doc, type, questionText, potentialAnswers);
        this.answer = answer;
    }

    @Override
    public Element asDocumentElement() {
        return null;
    }

}
