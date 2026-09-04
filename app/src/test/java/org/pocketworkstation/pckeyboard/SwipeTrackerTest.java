/*
 * Copyright (C) 2026
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.pocketworkstation.pckeyboard;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SwipeTrackerTest {

    private static final float DELTA = 0.0001f;

    @Test
    public void ringBufferWrapsAndKeepsNewestPointsInOrder() {
        SwipeTracker.EventRingBuffer buffer = new SwipeTracker.EventRingBuffer(3);

        buffer.add(1.0f, 11.0f, 101L);
        buffer.add(2.0f, 12.0f, 102L);
        buffer.add(3.0f, 13.0f, 103L);
        buffer.add(4.0f, 14.0f, 104L);

        assertEquals(3, buffer.size());
        assertPoint(buffer, 0, 2.0f, 12.0f, 102L);
        assertPoint(buffer, 1, 3.0f, 13.0f, 103L);
        assertPoint(buffer, 2, 4.0f, 14.0f, 104L);

        buffer.dropOldest();
        assertEquals(2, buffer.size());
        assertPoint(buffer, 0, 3.0f, 13.0f, 103L);

        buffer.clear();
        assertEquals(0, buffer.size());
    }

    @Test
    public void velocityUsesAllSamplesAndRequestedUnits() {
        SwipeTracker tracker = new SwipeTracker();
        tracker.mBuffer.add(0.0f, 0.0f, 100L);
        tracker.mBuffer.add(10.0f, 20.0f, 110L);
        tracker.mBuffer.add(30.0f, 10.0f, 120L);

        tracker.computeCurrentVelocity(1000);

        assertEquals(1250.0f, tracker.getXVelocity(), DELTA);
        assertEquals(1250.0f, tracker.getYVelocity(), DELTA);
    }

    @Test
    public void velocityIsClampedInBothDirections() {
        SwipeTracker tracker = new SwipeTracker();
        tracker.mBuffer.add(20.0f, -20.0f, 0L);
        tracker.mBuffer.add(0.0f, 20.0f, 10L);

        tracker.computeCurrentVelocity(1000, 500.0f);

        assertEquals(-500.0f, tracker.getXVelocity(), DELTA);
        assertEquals(500.0f, tracker.getYVelocity(), DELTA);
    }

    @Test
    public void velocityIgnoresSamplesWithNoElapsedTime() {
        SwipeTracker tracker = new SwipeTracker();
        tracker.mBuffer.add(0.0f, 0.0f, 10L);
        tracker.mBuffer.add(100.0f, 100.0f, 10L);

        tracker.computeCurrentVelocity(1000);

        assertEquals(0.0f, tracker.getXVelocity(), DELTA);
        assertEquals(0.0f, tracker.getYVelocity(), DELTA);
    }

    private static void assertPoint(SwipeTracker.EventRingBuffer buffer, int position,
            float x, float y, long time) {
        assertEquals(x, buffer.getX(position), DELTA);
        assertEquals(y, buffer.getY(position), DELTA);
        assertEquals(time, buffer.getTime(position));
    }
}
