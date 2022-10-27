package uk.ac.hope.csc.greeno.quizparser.moodlexml.answer;

import org.w3c.dom.Element;
import uk.ac.hope.csc.greeno.quizparser.moodlexml.QuizElement;

import java.util.List;

/**
 * Abstraction of an answer section that contains multiple correct answers
 * incorrect answers will be penalized proportionately eg:
 *      All correct selections => 100%
 *      All incorrect selections => -100%
 */
public class MultiChoiceAnswer implements QuizElement {

    protected Character answer;

    @Override
    public Element asDocumentElement() {
        return null;
    }


}


/*
General format of an answer XML element
Again: The Moodle XML is not well structured these elements
just appear as a list under the root of the question element

<answer fraction="0" format="html">
      <text><![CDATA[<p dir="ltr" style="text-align: left;">3</p>]]></text>
      <feedback format="html">
        <text></text>
      </feedback>
    </answer>

 */
