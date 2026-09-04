/*
 * Copyright (C) 2026
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.pocketworkstation.pckeyboard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DictionaryTest {

    private final TestDictionary dictionary = new TestDictionary();

    @Test
    public void sameComparesOnlyTheDeclaredWordLength() {
        char[] reusableBuffer = {'t', 'e', 's', 't', 'x', 'x'};

        assertTrue(dictionary.same(reusableBuffer, 4, "test"));
    }

    @Test
    public void sameRejectsLengthAndCharacterMismatches() {
        char[] word = {'t', 'e', 's', 't'};

        assertFalse(dictionary.same(word, 3, "test"));
        assertFalse(dictionary.same(word, 4, "tent"));
        assertFalse(dictionary.same(word, 4, "Test"));
    }

    private static final class TestDictionary extends Dictionary {
        @Override
        public void getWords(WordComposer composer, WordCallback callback,
                int[] nextLettersFrequencies) {
            // No lookup is needed to exercise Dictionary.same().
        }

        @Override
        public boolean isValidWord(CharSequence word) {
            return false;
        }
    }
}
