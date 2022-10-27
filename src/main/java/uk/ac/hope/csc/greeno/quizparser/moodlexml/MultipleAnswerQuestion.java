package uk.ac.hope.csc.greeno.quizparser.moodlexml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class MultipleAnswerQuestion extends MultiChoiceQuestion {

    // List of correct answer identifiers
    protected List<Character> answers;

    public MultipleAnswerQuestion(Document doc, Q_TYPE type, String questionText, List<String> potentialAnswers, List<Character> answers) {
        super(doc, type, questionText, potentialAnswers);
        this.answers = answers;
    }

    public void putAnswer(Character ch) {
        answers.add(ch);
    }

    @Override
    public Element asDocumentElement() {
        return null;
    }

}
