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
        // Appent the text format element
        question.appendChild(getQuestionTextElement());

        return question;
    }

    protected Element getQuestionNameElement() {
        // Create the <name> tag element
        Element questionNameElement = doc.createElement("name");
        // Create the <text> tag element (sigh!)
        Element txt = doc.createElement("text");
        // Add the text tag data
        txt.appendChild(doc.createTextNode(questionName));
        // Append to the question element
        questionNameElement.appendChild(txt);
        // Return the element
        return questionNameElement;
    }

    protected Element getQuestionTextElement() {
        // Create the <questiontext> tag element
        Element questionTextElement = doc.createElement("questiontext");
        questionTextElement.setAttribute( "format", "html");
        // Create the <text> tag element (sigh!)
        Element txt = doc.createElement("text");
        // Add the text tag data TODO - fix the CDATA here ...
        txt.appendChild(doc.createTextNode("<![CDATA[<p dir=\"ltr\" style=\"text-align: left;\">Which number?</p>]]\>"));
        // Append to the question element
        questionTextElement.appendChild(txt);
        return questionTextElement;
    }



}
