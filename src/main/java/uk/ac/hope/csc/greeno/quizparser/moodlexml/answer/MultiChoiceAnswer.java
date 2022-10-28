package uk.ac.hope.csc.greeno.quizparser.moodlexml.answer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

import java.util.List;

/**
 * Abstraction of an answer section that contains multiple correct answers
 * incorrect answers will be penalized proportionately eg:
 *      All correct selections => 100%
 *      All incorrect selections => -100%
 *
 *      TODO - I might remove penalties for incorrect answers in single answer questions
 *
 */
public class MultiChoiceAnswer implements QuizElement {

    public static final String cdata = "<p dir=\"ltr\" style=\"text-align: left;\">${QT}</p>";

    protected Document doc;
    protected String answer;
    protected double fraction;

    public MultiChoiceAnswer(Document doc, String answer, double fraction) {
        this.doc = doc;
        this.answer = answer;
        this.fraction = fraction;
    }

    @Override
    public Element asDocumentElement() {
        // The root <answer> tag
        Element answerElement = doc.createElement("answer");
        answerElement.setAttribute("fraction", Double.valueOf(fraction).toString());
        answerElement.setAttribute("format", "html");

        // The answer is a CDATA field in a <text> tag
        Element textTag = doc.createElement("text");
        answerElement.appendChild(textTag);
        // Create the CDATA element
        Node cdataElement = doc.createCDATASection(cdata.replace("${QT}", answer));
        textTag.appendChild(cdataElement);

        // Create the <generalfeedback> tag element
        Element feedbackElement = doc.createElement("feedback");
        feedbackElement.setAttribute( "format", "html");
        // Create the <text> tag element (sigh!)
        Element txt = doc.createElement("text");
        feedbackElement.appendChild(txt);

        // Add the (empty) <feedback> tag
        answerElement.appendChild(feedbackElement);

        return answerElement;
    }
}