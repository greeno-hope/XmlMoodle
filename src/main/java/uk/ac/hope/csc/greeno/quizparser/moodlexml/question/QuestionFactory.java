package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class QuestionFactory {

    public static QuestionFactory instance;

    // The Document. Questions need to be able to
    // write themselves to this doc when we create
    // the output XML
    protected Document doc;

    /**
     * private constructor
     * @param doc
     */
    private QuestionFactory(Document doc) {
        this.doc = doc;
    }

    /**
     * A static instance accessor
     */
    public static QuestionFactory getInstance(Document doc) {
        if(instance == null) {
            instance = new QuestionFactory(doc);
        }
        return instance;
    }

    /**
     *
     * @param type
     * @param questionLines
     * @param choices
     * @param keyLine
     * @return
     */
    public Question createQuestion(Question.Q_TYPE type, String questionName, List<String> questionLines, List<String> choices, String keyLine) {

        Question ret = null;

        // Use a StringBuilder to concatenate the lines of the question into a single line
        // TODO will need to add additional whitespace and/or LFCR here
        StringBuilder sb = new StringBuilder();
        for(String s : questionLines) {
            sb.append(s);
            sb.append('\n');
        }

        switch(type) {
            case Q_TYPE_MULTI_CHOICE:
                // The Key (answer) line has the structure 'Key:ab' etc.
                String[] keys = keyLine.split(":");
                List<Character> answers = new ArrayList<>();
                for(int i=0; i<keys[1].length(); i++) {
                    if( keys[1].charAt(i) == ' ') {
                        // TODO will probably have to make safe
                        break;
                    } else {
                        answers.add(keys[1].charAt(i));
                    }
                }
                ret = new MultiChoiceQuestion(doc, type, questionName, sb.toString(), choices, answers);
                break;
            default:
                break;
        }

        return ret;
    }

}
