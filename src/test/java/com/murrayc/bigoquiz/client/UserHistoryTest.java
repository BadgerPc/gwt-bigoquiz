package com.murrayc.bigoquiz.client;

import com.murrayc.bigoquiz.shared.Question;
import com.murrayc.bigoquiz.shared.QuizSections;
import com.murrayc.bigoquiz.shared.db.UserQuestionHistory;
import com.murrayc.bigoquiz.shared.db.UserStats;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by murrayc on 1/27/16.
 */
public class UserHistoryTest {

    public static final String QUIZ_ID = "somequiz";
    public static final String SECTION_1 = "section1";
    public static final String SUBSECTION_1_1 = "subsection1.1";

    @Test
    public void testAddUserAnswerAtStart() throws Exception {
        //TODO: Break this up into smaller tests,
        //when the UserHistory API has settled down.
        @NotNull UserHistory history = createUserRecentHistory();

        @Nullable Question question = createQuestion("question4", SECTION_1, SUBSECTION_1_1);
        history.addUserAnswerAtStart(QUIZ_ID, question, false);

        final UserStats stats = history.getStats(SECTION_1);
        assertNotNull(stats);

        @NotNull Collection<UserQuestionHistory> problems = stats.getTopQuestionHistories();
        assertNotNull(problems);
        assertEquals(1, problems.size());

        history.addUserAnswerAtStart(QUIZ_ID, question, false);
        problems = stats.getTopQuestionHistories();
        assertNotNull(problems);
        assertEquals(1, problems.size());

        question = createQuestion("question5", SECTION_1, SUBSECTION_1_1);
        history.addUserAnswerAtStart(QUIZ_ID, question, false);

        problems = stats.getTopQuestionHistories();
        assertNotNull(problems);
        assertEquals(2, problems.size());

        //assertEquals("question5", problems.get(0).getQuestionId());
    }

    @NotNull
    private UserHistory createUserRecentHistory() {
        @NotNull final QuizSections sections = new QuizSections();
        sections.addSection(SECTION_1, "section 1", null);
        sections.addSection("section2", "section 2", null);

        @NotNull final UserStats stats = new UserStats();

        @NotNull LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId("userid 1");
        @NotNull UserHistory history = new UserHistory(loginInfo, sections, "some title");
        history.setSectionStats("section1", stats);

        return history;
    }

    @Nullable
    private Question createQuestion(final String questionId, final String sectionId, final String subSectionId) {
        return new Question(questionId, sectionId, subSectionId, "question text", "someurl", null);
    }
}