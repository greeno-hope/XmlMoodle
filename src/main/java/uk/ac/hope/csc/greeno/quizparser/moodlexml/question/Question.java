package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.*;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

/**
 *
 */
public abstract class Question implements QuizElement {

    public static final String cdata = "<p dir=\"ltr\" style=\"text-align: left;\">${QT}</p>";

    public static enum Q_TYPE {
        Q_TYPE_UNDETERMINED,
        Q_TYPE_MULTI_CHOICE,
        Q_TYPE_TRUE_FALSE,
        Q_TYPE_SHORT_ANSWER
    };

    protected Document doc;

    protected String questionName;
    protected String questionText;

    protected Q_TYPE type;

    /**
     *
     * @param doc
     * @param type
     * @param questionLine
     */
    public Question(Document doc, Q_TYPE type, String questionName, String questionLine) {
        String[] questionLineTokens = questionLine.split("[.]", 2);
        this.doc = doc;
        this.type = type;
        this.questionName = questionName;
        this.questionText = questionLineTokens[1].trim();
    }

    /**
     *
     * @return
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     *
     * @param questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     *
     * @return
     */
    public Q_TYPE getType() {
        return type;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    protected Element getQuestionTextElement() {
        // Create the <questiontext> tag element
        Element questionTextElement = doc.createElement("questiontext");
        questionTextElement.setAttribute( "format", "html");
        // Still need a <text> node
        Element textTag = doc.createElement("text");
        questionTextElement.appendChild(textTag);
        // Create the CDATA element
        Node cdataElement = doc.createCDATASection(cdata.replace("${QT}", questionText));
        textTag.appendChild(cdataElement);
        return questionTextElement;
    }

    /**
     *
     * @return
     */
    protected Element getGeneralFeedbackElement() {
        // Create the <generalfeedback> tag element
        Element generalFeedbackElement = doc.createElement("generalfeedback");
        generalFeedbackElement.setAttribute( "format", "html");
        // Create the <text> tag element (sigh!)
        Element txt = doc.createElement("text");
        generalFeedbackElement.appendChild(txt);
        return generalFeedbackElement;
    }

    /**
     *
     * @param tag
     * @param data
     * @return
     */
    protected Element getFlatTagElement(String tag, String data) {
        Element flatNodeElement = doc.createElement(tag);
        flatNodeElement.appendChild(doc.createTextNode(data));
        return flatNodeElement;
    }

    /**
     *
     * @param tag
     * @return
     */
    protected Element getEmptyTagElement(String tag) {
        return doc.createElement(tag);
    }

}
