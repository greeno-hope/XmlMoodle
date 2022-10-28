package uk.ac.hope.csc.greeno.quizparser.moodlexml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.hope.csc.greeno.quizparser.moodlexml.category.Category;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.question.Question;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MoodleQuiz {

    protected Document doc;
    protected Element quizRoot;
    protected List<Question> questions;

    /**
     * Constructor.
     * Creates the skeleton of the XML in the constructor.
     * The questions will be inserted as children under the
     * quizRoot (<quiz>) element recursively in the toString()
     * function.
     * @param doc
     * @param category
     */
    public MoodleQuiz(Document doc, String category) {
        // Set the Document root
        this.doc = doc;
        // Instantiate the list object
        questions = new ArrayList<>();
        // Create the root element
        quizRoot = doc.createElement("quiz");
        doc.appendChild(quizRoot);
        // Create the category and append that to the doc as well
        Category cat = new Category(doc, category);
        quizRoot.appendChild(cat.asDocumentElement());
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    /**
     * This implementation of the toString() function will
     * return the MoodleQuiz as an XML string (in Moodle
     * XML format)
     * @return
     */
    @Override
    public String toString() {

        for(Question question : questions) {
            quizRoot.appendChild(question.asDocumentElement());
        }

        Writer out = new StringWriter();

        try {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

}
