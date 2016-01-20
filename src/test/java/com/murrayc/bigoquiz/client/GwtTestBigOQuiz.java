package com.murrayc.bigoquiz.client;

import static org.junit.Assert.*;

import com.murrayc.bigoquiz.client.ui.QuestionView;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithEasyMock;

@GwtModule("org.glom.web.OnlineGlom")
public class GwtTestBigOQuiz extends GwtTestWithEasyMock {

	/**
	 * Tell gwt-test-utils to use this mock when trying to create this class via GWT.create() in OnlineGlom.
	 */
	// @Mock
	// private AppPlaceHistoryMapper mockAppPlaceHistoryMapper;

	private BigOQuiz app;

	@Test
	public void testSomething() {
		assertTrue(true);
		/*
		 * TODO: For instance: // Arrange Browser.fillText(app.nameField, "123");
		 * 
		 * // Act Browser.click(app.sendButton);
		 * 
		 * // Assert assertFalse(app.dialogBox.isShowing()); assertEquals("Please enter at least four characters",
		 * app.errorLabel.getText());
		 */
	}

	@Before
	public void beforeOnlineGlom() {
		app = new BigOQuiz();
		app.onModuleLoad();

		assertNotNull(app.clientFactory);
		final QuestionView view = app.clientFactory.getQuestionView();
		assertNotNull(view);
		assertNotNull(view.asWidget());
		assertTrue(view.asWidget().isVisible());

		// Some pre-assertions
		assertTrue(app.questionPanel.isVisible());
		// TODO: For instance: assertEquals("", app.docSelectionPanel.getText());
	}

}
