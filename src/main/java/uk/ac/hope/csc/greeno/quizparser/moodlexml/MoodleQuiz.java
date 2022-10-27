package uk.ac.hope.csc.greeno.quizparser.moodlexml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.category.Category;

public class MoodleQuiz {

    protected Document doc;
    protected Element quiz;

    public MoodleQuiz(Document doc) {
        this.doc = doc;
        // Create the root element
        quiz = doc.createElement("quiz");
        doc.appendChild(quiz);
    }

    public void addCategory(String category) {
        Category cat = new Category(doc, category);
        quiz.appendChild(cat.asDocumentElement());
    }

    public void addTrueFalseQuestion() {

    }

}
