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
import uk.ac.hope.csc.greeno.quizparser.moodlexml.question.Question;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.question.QuestionFactory;

import static java.lang.System.exit;

/**
 *
 */
public class TextFileParser {

    // String to hold the input (.txt) file path
    protected String inPath;
    protected String outPath;
    // String to hold the quiz category
    protected String category;
    // String to hold the question name for this block of questions)
    protected String questionName;
    // List to hold the individual lines of the input file
    protected List<String> lines;
    // We'll use a ListIterator interface so that we can
    // move forwards and backwards over the lines list
    protected ListIterator<String> linesIterator;
    // String to hold the last line read
    protected String readLine;
    // List<String> to hold individual lines of a
    // (potentially) multiline question
    protected List<String> questionLines;
    // List<String> to hold the array of question answers
    protected List<String> answers;
    // String to hold the answer (Key) line.
    protected String keyLine;
    // The (sort of) global org.w3c.dom.Document object
    protected Document doc;
    // The MoodleQuiz object
    protected MoodleQuiz quiz;

    /**
     * Main entry point (eventually will take a file name argument)
     * @param args
     *              arg0 - path of the inut text file
     *              arg1 - category (String) for the generated XML
     */
    public static void main(String[] args) {

//        if(args.length != 2) {
//            System.out.println("Utility takes 2 arguments and infile name (.txt) and an outfile name (.xml)");
//            exit(-1);
//        }

        String inFile = "./input/chapter8.txt";
        String outFile = "./output/lecture_08.xml";
        String category = "JAVA_DS_SE_BLOCK1";
        String questionName = "08_2DARRAYS";

        TextFileParser parser = new TextFileParser(inFile, outFile, questionName, category);
        try {
            // Parse the input file
            parser.parseFile();
            // Write the output file
            parser.writeXML();

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
     *
     * @param inPath
     * @param outPath
     * @param category
     */
    public TextFileParser(String inPath, String outPath, String questionName, String category) {
        // Category
        this.category = category;
        // Q Names
        this.questionName = questionName;

        // Set the file paths
        this.inPath = inPath;
        this.outPath = outPath;
        // Read the input file
        lines = new FileReader(inPath).readFile();

        try {
            // Create the DOM root for the output XML
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // create a new quiz
        quiz = new MoodleQuiz(doc, category);
    }

    public void writeXML() {

        // Parse to XML
        String xml = quiz.toString();

        // Write to file
        boolean ok = new FileWriter(outPath).writeFile(xml);

        // TODO - Output to console for dev test ONLY
        System.out.println(xml);
    }

    /**
     * Main parsing function. Reads input *.txt file and
     * calls nested parsing functions as it hits keywords
     * @throws Exception
     */
    public void parseFile() throws Exception {

        linesIterator = lines.listIterator();
        while(linesIterator.hasNext()) {
            readLine = linesIterator.next();
            if (isStartOfQuestion(readLine)) {
                parseQuestion();
                Question question = buildQuestion();
                quiz.addQuestion(question);
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
        return QuestionFactory.getInstance(doc).createQuestion(getQuestionType(), questionName, questionLines, answers, keyLine);
    }

    /**
     * Uses a regex to find the question type
     * @return
     */
    private Question.Q_TYPE getQuestionType() {
        // TODO - this is for testing only (implement properly)
        return Question.Q_TYPE.Q_TYPE_MULTI_CHOICE;
    }
}
