package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;

import java.util.List;

public class QuestionFactory {

    public static QuestionFactory instance;

    // The Document. Questions need to be able to
    // write themselves to this doc when we create
    // the output XML
    protected Document doc;

    // private constructor
    private QuestionFactory(Document doc) {
        this.doc = doc;
    }

    // The static accessor for the question factory
    public static QuestionFactory getInstance(Document doc) {
        if(instance == null) {
            instance = new QuestionFactory(doc);
        }
        return instance;
    }

    // Document doc, Q_TYPE type, String questionText, List<String> potentialAnswers, Character answer

    public Question createQuestion(Question.Q_TYPE type, List<String> questionLines, List<String> answers, String keyLine) {

        Question ret = null;

        // Use a StringBuilder to concatenate the lines of the question into a single line
        // TODO will need to add additional whitespace and/or LFCR here
        StringBuilder sb = new StringBuilder();
        for(String s : questionLines) {
            sb.append(s);
        }

        // Tokenize the Key line
        String[] keyTokens = keyLine.split(":");

        if(type == Question.Q_TYPE.Q_TYPE_SINGLE_ANSWER) {
            SingleAnswerQuestion saq = new SingleAnswerQuestion(doc, type, sb.toString(), answers, keyTokens[1].charAt(0));
        } else if (type == Question.Q_TYPE.Q_TYPE_MULTI_ANSWER) {

        }

        return ret;
    }

}
