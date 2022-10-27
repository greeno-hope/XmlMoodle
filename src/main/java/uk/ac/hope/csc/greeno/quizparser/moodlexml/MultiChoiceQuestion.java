package uk.ac.hope.csc.greeno.quizparser.moodlexml;

import org.w3c.dom.Document;

import java.util.List;

/**
 * MultiChoiceQuestion
 *
 * Potential sub-classes are:
 *      SingleAnswerQuestion - only one answer is correct and that will carry 100% marks
 *      MultipleAnswerQuestion - more than one question is correct and incorrect answers
 *          will incur some penalty.
 */
public abstract class MultiChoiceQuestion extends Question {

    // Holds the possible answer(s) for this question
    protected List<String> potentialAnswers;

    public MultiChoiceQuestion(Document doc, Q_TYPE type,String questionText, List<String> potentialAnswers) {
        super(doc, type, questionText);
        this.potentialAnswers = potentialAnswers;
    }

}
