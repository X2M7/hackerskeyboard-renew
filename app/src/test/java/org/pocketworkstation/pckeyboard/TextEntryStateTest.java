/*
 * Copyright (C) 2026
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.pocketworkstation.pckeyboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TextEntryStateTest {

    @Before
    public void resetState() {
        TextEntryState.reset();
    }

    @Test
    public void ordinaryTypingMovesBetweenWordAndStart() {
        assertEquals(TextEntryState.State.START, TextEntryState.getState());

        TextEntryState.typedCharacter('h', false);
        assertEquals(TextEntryState.State.IN_WORD, TextEntryState.getState());

        TextEntryState.typedCharacter('i', false);
        assertEquals(TextEntryState.State.IN_WORD, TextEntryState.getState());

        TextEntryState.typedCharacter('.', true);
        assertEquals(TextEntryState.State.START, TextEntryState.getState());
    }

    @Test
    public void acceptedDefaultTracksFollowingSpaceAndPunctuation() {
        TextEntryState.acceptedDefault("teh", "the");
        assertEquals(TextEntryState.State.ACCEPTED_DEFAULT, TextEntryState.getState());

        TextEntryState.typedCharacter(' ', true);
        assertEquals(TextEntryState.State.SPACE_AFTER_ACCEPTED, TextEntryState.getState());

        TextEntryState.backToAcceptedDefault("teh");
        TextEntryState.typedCharacter('.', true);
        assertEquals(TextEntryState.State.PUNCTUATION_AFTER_ACCEPTED,
                TextEntryState.getState());

        TextEntryState.backToAcceptedDefault("teh");
        assertEquals(TextEntryState.State.ACCEPTED_DEFAULT, TextEntryState.getState());
    }

    @Test
    public void backspaceCanUndoAnAutoCorrectionThenResumeTyping() {
        TextEntryState.acceptedDefault("teh", "the");

        TextEntryState.backspace();
        assertEquals(TextEntryState.State.UNDO_COMMIT, TextEntryState.getState());

        TextEntryState.backspace();
        assertEquals(TextEntryState.State.IN_WORD, TextEntryState.getState());
    }

    @Test
    public void separatorAfterUndoRestoresAcceptedDefaultState() {
        TextEntryState.acceptedDefault("teh", "the");
        TextEntryState.backspace();

        TextEntryState.typedCharacter(' ', true);

        assertEquals(TextEntryState.State.ACCEPTED_DEFAULT, TextEntryState.getState());
    }

    @Test
    public void correctionSelectionRemainsCorrectionUntilTypingContinues() {
        TextEntryState.selectedForCorrection();
        assertTrue(TextEntryState.isCorrecting());

        TextEntryState.acceptedSuggestion("helo", "hello");
        assertEquals(TextEntryState.State.PICKED_CORRECTION, TextEntryState.getState());
        assertTrue(TextEntryState.isCorrecting());

        TextEntryState.typedCharacter(' ', true);
        assertEquals(TextEntryState.State.SPACE_AFTER_PICKED, TextEntryState.getState());
        assertFalse(TextEntryState.isCorrecting());
    }

    @Test
    public void manualSuggestionUsesPickedSuggestionPath() {
        TextEntryState.acceptedSuggestion("helo", "hello");
        assertEquals(TextEntryState.State.PICKED_SUGGESTION, TextEntryState.getState());

        TextEntryState.typedCharacter('.', true);
        assertEquals(TextEntryState.State.PUNCTUATION_AFTER_ACCEPTED,
                TextEntryState.getState());
    }

    @Test
    public void nullAcceptedDefaultDoesNotChangeState() {
        TextEntryState.typedCharacter('a', false);

        TextEntryState.acceptedDefault(null, "anything");

        assertEquals(TextEntryState.State.IN_WORD, TextEntryState.getState());
    }
}
