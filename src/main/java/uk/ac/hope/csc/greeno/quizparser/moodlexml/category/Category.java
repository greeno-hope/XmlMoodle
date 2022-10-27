package uk.ac.hope.csc.greeno.quizparser.moodlexml.category;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

/* Example output from the asElement() if returned as XML

    <question type="category">
        <category>
            <text>$course$/top/Default for TUTORTKAZ2020//1/JAVA-TEST</text>
        </category>
        <info format="html">
            <text></text>
        </info>
        <idnumber></idnumber>
    </question>

*/

public class Category implements QuizElement {

    protected Document doc;
    protected String category;

    public Category(Document doc, String category) {
        this.doc = doc;
    }

    /**
     * This function creates a category for the questions that follow.
     * The Moodle XML is a bit shitty because it uses a question tag
     * to store a category which is less than good XML/design
     * @return
     */
    @Override
    public Element asDocumentElement() {
        // Question root
        Element element = doc.createElement("question");
        element.setAttribute("type", "category");

        // Category child
        Element cat = doc.createElement("category");
        element.appendChild(cat);

        // text child (of category)
        Element txt = doc.createElement("text");
        txt.appendChild(doc.createTextNode(category));
        cat.appendChild(txt);

        //  info child (of question)
        Element info = doc.createElement("info");
        info.setAttribute("format", "html");
        info.appendChild(doc.createElement("text"));
        element.appendChild(info);

        // idnumber child (of question)
        Element idNumber = doc.createElement("idnumber");
        element.appendChild(idNumber);

        return element;
    }
}
