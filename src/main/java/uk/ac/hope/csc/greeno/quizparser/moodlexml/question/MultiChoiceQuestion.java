package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * MultiChoiceQuestion
 *
 */
public class MultiChoiceQuestion extends Question {

    // Holds the possible answer(s) for this question
    protected List<String> potentialAnswers;
    protected List<Character> answers;

    public MultiChoiceQuestion(Document doc, Q_TYPE type,String questionText, List<String> potentialAnswers, List<Character> answers) {
        super(doc, type, questionText);
        this.potentialAnswers = potentialAnswers;
        this.answers = answers;
    }

    @Override
    public Element asDocumentElement() {
        // Create the question root
        Element question = doc.createElement("question");
        question.setAttribute("type", "multichoice");
        // Append the name element
        question.appendChild(getQuestionNameElement());
        // Append the text format element
        question.appendChild(getQuestionTextElement());
        // Append the general feedback element
        question.appendChild(getGeneralFeedbackElement());
        // Append the default grade element
        // TODO - check this for questions with mre than one correct answer
        question.appendChild(getFlatTagElement("defaultgrade", Double.valueOf(1.0).toString()));
        // Append <penalty> element
        question.appendChild(getFlatTagElement("penalty", Double.valueOf(0.333333).toString()));
        // Append <hidden> tag element
        question.appendChild(getFlatTagElement("hidden", Integer.valueOf(0).toString()));
        // Append <idnumber>
        question.appendChild(getEmptyTagElement("idnumber"));



        return question;
    }

}
