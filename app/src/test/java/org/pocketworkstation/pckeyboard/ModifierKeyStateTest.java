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

import org.junit.Test;

public class ModifierKeyStateTest {

    @Test
    public void otherKeyOnlyStartsChordWhileModifierIsPressed() {
        ModifierKeyState state = new ModifierKeyState();

        state.onOtherKeyPressed();
        assertFalse(state.isChording());

        state.onPress();
        state.onOtherKeyPressed();
        assertTrue(state.isChording());

        state.onRelease();
        assertFalse(state.isChording());
    }

    @Test
    public void repeatedPressStartsANewNonChordingGesture() {
        ModifierKeyState state = new ModifierKeyState();
        state.onPress();
        state.onOtherKeyPressed();
        assertTrue(state.isChording());

        state.onPress();

        assertFalse(state.isChording());
        assertEquals("ModifierKeyState:1", state.toString());
    }
}
