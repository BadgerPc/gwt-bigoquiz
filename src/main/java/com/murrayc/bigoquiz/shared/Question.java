package com.murrayc.bigoquiz.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by murrayc on 1/21/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Question {
    private String id;
    private String sectionId;
    private String subSectionId;
    private Question.Text text; //The actual question text.
    private String link; //An informative URL.
    private List<Text> choices; //Possible answers, only one of which is correct.
    private String note;
    private String videoUrl;
    private String codeUrl;

    // These are just caches, from the Quiz, as a convenience.
    // They are only set when something has called setTitles().
    private String quizTitle;
    private HasIdAndTitle section;
    private QuizSections.SubSection subSection;

    private boolean quizUsesMathML;

    public static class Text {
        Text() {
            this.text = null;
            this.isHtml = false;
        }

        public Text(final String text, boolean isHtml) {
            this.text = text;
            this.isHtml = isHtml;
        }

        public String text;
        public boolean isHtml;

        // We override equals() and hashode() so we can use this class in a Set.
        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Text text1 = (Text) o;

            if (isHtml != text1.isHtml) return false;
            return text != null ? text.equals(text1.text) : text1.text == null;

        }

        @Override
        public int hashCode() {
            int result = text != null ? text.hashCode() : 0;
            result = 31 * result + (isHtml ? 1 : 0);
            return result;
        }
    }

    public Question() {
    }

    public Question(final String id, final String sectionId, final String subSectionId, final Text text,
                    final String link, final List<Text> choices, final String note, final String videoUrl, String codeUrl) {
        this.id = id;
        this.sectionId = sectionId;
        this.subSectionId = subSectionId;
        this.text = text;
        this.link = link;
        this.choices = choices;
        this.note = note;
        this.videoUrl = videoUrl;
        this.codeUrl = codeUrl;
    }

    public String getId() {
        return id;
    }

    /** Without this, the client code will not set this from the JSON.
     */
    public void setId(final String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    /** This is only used for the JSON input.
     */
    public void setSectionId(final String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSubSectionId() {
        return subSectionId;
    }

    /** Without this, the client code will not set this from the JSON.
     */
    public void setSubSectionId(final String subSectionId) {
        this.subSectionId = subSectionId;
    }

    public Question.Text getText() {
        return text;
    }

    /** Without this, the client code will not set this from the JSON.
     */
    public void setText(final Question.Text text) {
        this.text = text;
    }

    @Nullable
    public String getLink() {
        return link;
    }

    /** Without this, the client code will not set this from the JSON.
     */
    public void setLink(final String link) {
        this.link = link;
    }

    public List<Text> getChoices() {
        return choices;
    }

    public boolean hasChoices() {
        return (choices != null) && !choices.isEmpty();
    }

    public void setChoices(final List<Text> choices) {
        this.choices = choices;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    /** This is only used for the JSON input.
     */
    public void setNote(final String note) {
        this.note = note;
    }

    public boolean hasNote() {
        return !StringUtils.isEmpty(note);
    }

    @Nullable
    public String getVideoUrl() {
        return videoUrl;
    }

    /** This is only used for the JSON input.
     */
    public void setVideoUrl(final String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Nullable
    public String getCodeUrl() {
        return codeUrl;
    }

    /** This is only used for the JSON input.
     */
    public void setCodeUrl(final String codeUrl) {
        this.codeUrl = codeUrl;
    }

    /** Set titles, just to save users of Question the bother of having to get them from the Quiz class.
     *
     * @param quizTitle The title of the quiz that this Question is from.
     * @param section The title (and link) of the quiz's section that this Question is from.
     * @param subSection The title (and link) of the quiz's sub-section that this Question is from.
     * @param question
     */
    public void setTitles(final String quizTitle, final HasIdAndTitle section, QuizSections.SubSection subSection) {
        this.quizTitle = quizTitle;
        this.section = section;
        this.subSection = subSection;
    }

    /** This is just to save users of Question the bother of having to get it from the Quiz class.
     */
    public void setQuizUsesMathML(boolean quizUsesMathML) {
        this.quizUsesMathML = quizUsesMathML;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    /** This is only used for the JSON input.
     */
    public void setQuizTitle(final String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public HasIdAndTitle getSection() {
        return section;
    }

    /** This is only used for the JSON input.
     */
    public void setSection(final HasIdAndTitle section) {
        this.section = section;
    }

    public QuizSections.SubSection getSubSection() {
        return subSection;
    }

    /** This is only used for the JSON input.
     */
    public void setSubSection(final QuizSections.SubSection subSection) {
        this.subSection = subSection;
    }

    public boolean getQuizUsesMathML() {
        return quizUsesMathML;
    }
}