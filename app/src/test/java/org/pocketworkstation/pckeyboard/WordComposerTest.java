/*
 * Copyright (C) 2026
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.pocketworkstation.pckeyboard;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordComposerTest {

    @Test
    public void addKeepsTypedCaseButNormalizesSuggestionCodes() {
        WordComposer composer = new WordComposer();
        int[] nearbyCodes = {'x', 'A', 'B', -1};

        composer.add('A', nearbyCodes);

        assertEquals("A", composer.getTypedWord().toString());
        assertArrayEquals(new int[] {'a', 'x', 'b', -1}, composer.getCodesAt(0));
        assertEquals(1, composer.size());
        assertTrue(composer.isAllUpperCase());
    }

    @Test
    public void addDoesNotReorderCodesWhenPrimaryIsNotSecond() {
        WordComposer composer = new WordComposer();
        int[] nearbyCodes = {'x', 'y', 'A'};

        composer.add('A', nearbyCodes);

        assertArrayEquals(new int[] {'x', 'y', 'a'}, composer.getCodesAt(0));
    }

    @Test
    public void capitalizationFlagsFollowAddsAndDeletes() {
        WordComposer composer = new WordComposer();
        composer.add('A', new int[] {'A'});
        composer.add('B', new int[] {'B'});
        composer.add('c', new int[] {'c'});

        assertFalse(composer.isAllUpperCase());
        assertTrue(composer.isMostlyCaps());

        composer.deleteLast();
        assertTrue(composer.isAllUpperCase());
        assertTrue(composer.isMostlyCaps());

        composer.deleteLast();
        assertTrue(composer.isAllUpperCase());
        assertFalse(composer.isMostlyCaps());

        composer.deleteLast();
        assertFalse(composer.isAllUpperCase());
        assertNull(composer.getTypedWord());

        // Deleting an already empty composer must remain a safe no-op.
        composer.deleteLast();
        assertEquals(0, composer.size());
    }

    @Test
    public void preferredWordAndMetadataAreResetTogether() {
        WordComposer composer = new WordComposer();
        composer.add('H', new int[] {'H'});
        composer.add('i', new int[] {'i'});
        composer.setPreferredWord("Hello");
        composer.setFirstCharCapitalized(true);
        composer.setAutoCapitalized(true);

        assertEquals("Hello", composer.getPreferredWord());
        assertTrue(composer.isFirstCharCapitalized());
        assertTrue(composer.isAutoCapitalized());

        composer.reset();

        assertEquals(0, composer.size());
        assertNull(composer.getTypedWord());
        assertNull(composer.getPreferredWord());
        assertFalse(composer.isFirstCharCapitalized());
        assertFalse(composer.isAutoCapitalized());
    }

    @Test
    public void copyRetainsStateAndCanBeEditedIndependently() {
        WordComposer original = new WordComposer();
        original.add('C', new int[] {'C'});
        original.add('a', new int[] {'a'});
        original.setPreferredWord("Candidate");
        original.setFirstCharCapitalized(true);
        original.setAutoCapitalized(true);

        WordComposer copy = new WordComposer(original);
        assertEquals("Candidate", copy.getPreferredWord());

        copy.add('t', new int[] {'t'});
        copy.setPreferredWord(null);

        assertEquals("Ca", original.getTypedWord().toString());
        assertEquals("Candidate", original.getPreferredWord());
        assertEquals("Cat", copy.getTypedWord().toString());
        assertEquals("Cat", copy.getPreferredWord().toString());
        assertTrue(copy.isFirstCharCapitalized());
        assertTrue(copy.isAutoCapitalized());
    }
}
