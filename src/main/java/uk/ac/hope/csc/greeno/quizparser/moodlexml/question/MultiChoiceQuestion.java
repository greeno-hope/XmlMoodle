package uk.ac.hope.csc.greeno.quizparser.moodlexml.question;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.answer.MultiChoiceAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiChoiceQuestion
 */
public class MultiChoiceQuestion extends Question {

    // Holds the possible answer(s) for this question
    protected List<MultiChoiceAnswer> answerList;

    protected boolean singleAnswer = false;

    protected int totalCount = 0;
    protected int correctCount = 0;
    protected int incorrectCount = 0;
    protected double correctFraction = 0;
    protected double incorrectFraction = 0;

    /**
     * Constructor, does all the work!
     * @param doc
     * @param type
     * @param questionText
     * @param potentialAnswers
     * @param answers
     */
    public MultiChoiceQuestion(Document doc, Q_TYPE type,String questionText, List<String> potentialAnswers, List<Character> answers) {

        super(doc, type, questionText);

        totalCount = potentialAnswers.size();
        if(answers.size() == 1) {
            singleAnswer = true;
            correctCount = 1;
        } else {
            singleAnswer = false;
            correctCount = answers.size();
        }

        // Set the penalties TODO - poss single answer questions do not incur a penalty?
        correctFraction = 100.0 / correctCount;
        if(totalCount != correctCount) {
            // This is because a few questions have all correct answers - the students have
            // to be confident eenough to tick them all!! (and we want to avoid a divide-by-zero)
            incorrectFraction = -100 / (totalCount - correctCount);
        }

        // Create a list for answers
        answerList = new ArrayList<>();

        // Loop the potential answers, check in the answer key, create the MultipleChoiceAnswer objects
        for (String s : potentialAnswers) {
            // The answer still has the original letter up front a. b. etc
            String[] splitAnswer = s.split("[.]", 2);
            if(answers.contains(splitAnswer[0].charAt(0))) {
                // This is a correct answer
                answerList.add(new MultiChoiceAnswer(doc, splitAnswer[1].trim(), correctFraction));
            } else {
                // this is an incorrect answer
                answerList.add(new MultiChoiceAnswer(doc, splitAnswer[1].trim(), incorrectFraction));
            }
        }
    }

    /**
     *
     * @return the root Element of a Moodle XML <question> tag
     */
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

        // Append single tag
        if(singleAnswer) {
            question.appendChild(getFlatTagElement("single", "true"));
        } else {
            question.appendChild(getFlatTagElement("single", "false"));
        }

        // Append the answer tags
        for (MultiChoiceAnswer m : answerList) {
            question.appendChild(m.asDocumentElement());
        }

        // Return the Question as a Document Element
        return question;
    }

}
