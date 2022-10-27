package uk.ac.hope.csc.greeno.quizparser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import uk.ac.hope.csc.greeno.quizparser.moodlexml.MoodleQuiz;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.Question;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuestionFactory;

/**
 *
 */
public class TextFileParser {

    // String to hold the input (.txt) file path
    protected String path;
    // String to hold the quiz category
    protected String category;
    // List to hold the individual lines of the input file
    protected List<String> lines;
    // We'll use a ListIterator interface so that we can
    // move forwards and backwards over the lines list
    protected ListIterator<String> linesIterator;
    // String to hold the last line read
    protected String readLine;
    // List<String> to hold individual lines of a
    // (potentially) multiline question
    List<String> questionLines;
    // List<String> to hold the array of question answers
    List<String> answers;
    // String to hold the answer (Key) line.
    String keyLine;

    protected Document doc;
    protected MoodleQuiz quiz;

    /**
     * Main entry point (eventually will take a file name argument)
     * @param args
     *              arg0 - path of the inut text file
     *              arg1 - category (String) for the generated XML
     */
    public static void main(String[] args) {

        String currentFolder = System.getProperty("user.dir");

        String testFile = "./test/test_in.txt";
        String category = "JAVA-1";

        TextFileParser parser = new TextFileParser(testFile, category);
        try {
            parser.parseFile();
            // Print the XML
            prettyPrint(parser.getDoc());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return a org.w3c.dom.Document object holds the new XML
     */
    public Document getDoc() {
        return doc;
    }

    /**
     * Constructor
     * @param path - input text file
     * @param category - a category field for the generated XML
     */
    public TextFileParser(String path, String category) {
        // Category
        this.category = category;
        // Set the file path
        this.path = path;
        // Read the input file
        lines = new FileReader(path).readFile();

        try {
            // Create the DOM root for the output XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // create a new quiz
        quiz = new MoodleQuiz(doc);
    }

    /**
     * Main parsing function. Reads input *.txt file and
     * calls nested parsing functions as it hits keywords
     * @throws Exception
     */
    public void parseFile() throws Exception {

        // Create the root document (with my category)
        quiz.addCategory(category);

        linesIterator = lines.listIterator();
        while(linesIterator.hasNext()) {
            readLine = linesIterator.next();
            if(isStartOfQuestion(readLine)) {
                parseQuestion();
                Question question = buildQuestion();
            }
        }
    }

    /**
     * Parses a question element in the text file
     */
    private void parseQuestion() {
        // We have the first line of a question but there may be more
        // so we need a List<String>to hold the possible further lines
        // and we need to iterate until we hit the string "a." which
        // will be the first of the answer lines
        questionLines = new ArrayList<>();
        questionLines.add(readLine);
        while(linesIterator.hasNext()) {
            readLine = linesIterator.next();
            if(readLine.startsWith("a.")) {
                // We are at the start of the answers!
                parseAnswers();
                break;
            } else {
                questionLines.add(readLine);
            }
        }
    }

    /**
     * Parses the possible answers subsection of a question
     * in the input text file
     */
    private void parseAnswers() {
        answers = new ArrayList<>();
        answers.add(readLine);
        while(linesIterator.hasNext()) {
            readLine = linesIterator.next();
            if(readLine.startsWith("Key")) {
                parseKey();
                break;
            } else {
                answers.add(readLine);
            }
        }
    }

    /**
     * Parses the last line of the question with will be
     * the answer key (containing one or more possible
     * correct answers)
     */
    public void parseKey() {
        keyLine = new String(readLine);
        readLine = null;
    }

    /**
     * Uses a regex to find if the line just read is the start of a question
     * @param line
     * @return
     */
    private boolean isStartOfQuestion(String line) {
        return line.matches("\\d+.*");
    }

    /**
     * Calls the QuestionFactory to get an appropriate Question object.
     * The Question objects know how to represent themselves as XML and will
     * support a toXML() function that will add them to the global Document
     * @return
     */
    private Question buildQuestion() {
        return QuestionFactory.getInstance(doc).createQuestion(getQuestionType(), questionLines, answers, keyLine);
    }

    /**
     * Uses a regex to find the question type
     * @return
     */
    private Question.Q_TYPE getQuestionType() {

        Question.Q_TYPE ret =  Question.Q_TYPE.Q_TYPE_UNDETERMINED;

        String[] keyToken = keyLine.split(":");
        if(keyToken[0].equals("Key")) {
            if( keyToken[1].length() == 1 ) {
                ret = Question.Q_TYPE.Q_TYPE_MULTI_CHOICE;
            } else if (keyToken[1].length() >1 ) {
                ret = Question.Q_TYPE.Q_TYPE_MULTI_ANSWER;
            } else {
                // Do nothing yet!
            }
        }
        return ret;
    }

    /**
     * Mostly for testing purposes. Outputs the current Document as nicely formatted
     * XML
     * @param xml
     * @throws Exception
     */
    public static final void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        System.out.println(out.toString());
    }
}
