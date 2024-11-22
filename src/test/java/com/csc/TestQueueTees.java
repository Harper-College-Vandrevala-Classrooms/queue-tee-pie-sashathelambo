package com.csc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestQueueTees {
    private QueueTees<TestCutie> queue;
    private static final int QUEUE_CAPACITY = 3;

    private class TestCutie implements Cutie {
        private final String desc;
        private final Integer rating;

        public TestCutie(String desc, Integer rating) {
            this.desc = desc;
            this.rating = rating;
        }

        @Override
        public String description() {
            return desc;
        }

        @Override
        public Integer cutenessRating() {
            return rating;
        }
    }

    @BeforeEach
    void setUp() {
        queue = new QueueTees<>(QUEUE_CAPACITY);
    }

    @Test
    void testNewQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testEnqueueAndDequeue() {
        TestCutie cutie = new TestCutie("Test Cutie", 7);
        queue.enqueue(cutie);
        
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        
        TestCutie dequeuedCutie = queue.dequeue();
        assertEquals(cutie.description(), dequeuedCutie.description());
        assertEquals(cutie.cutenessRating(), dequeuedCutie.cutenessRating());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testQueueReachesCapacity() {
        for (int i = 0; i < QUEUE_CAPACITY; i++) {
            queue.enqueue(new TestCutie("Cutie " + i, i));
        }
        
        assertTrue(queue.isFull());
        assertEquals(QUEUE_CAPACITY, queue.size());
    }

    @Test
    void testCircularBehavior() {
        TestCutie cutie1 = new TestCutie("First", 1);
        TestCutie cutie2 = new TestCutie("Second", 2);
        TestCutie cutie3 = new TestCutie("Third", 3);
        TestCutie cutie4 = new TestCutie("Fourth", 4);

        queue.enqueue(cutie1);
        queue.enqueue(cutie2);
        queue.enqueue(cutie3);

        assertEquals(cutie1.description(), queue.dequeue().description());
        queue.enqueue(cutie4);

        assertEquals(QUEUE_CAPACITY, queue.size());
    }

    @Test
    void testDequeueEmptyQueue() {
        assertNull(queue.dequeue());
        assertEquals(0, queue.size());
    }

    @Test
    void testEnqueueFullQueue() {
        for (int i = 0; i < QUEUE_CAPACITY; i++) {
            queue.enqueue(new TestCutie("Cutie " + i, i));
        }

        TestCutie extraCutie = new TestCutie("Extra", 10);
        queue.enqueue(extraCutie);

        assertEquals(QUEUE_CAPACITY, queue.size());
    }

    @Test
    void testMultipleEnqueueDequeue() {
        TestCutie cutie1 = new TestCutie("First", 1);
        TestCutie cutie2 = new TestCutie("Second", 2);

        queue.enqueue(cutie1);
        queue.enqueue(cutie2);
        assertEquals(2, queue.size());

        assertEquals(cutie1.description(), queue.dequeue().description());
        assertEquals(1, queue.size());

        assertEquals(cutie2.description(), queue.dequeue().description());
        assertEquals(0, queue.size());
    }

    @Test
    void testClearQueue() {
        TestCutie cutie1 = new TestCutie("First", 1);
        TestCutie cutie2 = new TestCutie("Second", 2);
        queue.enqueue(cutie1);
        queue.enqueue(cutie2);
        
        assertEquals(2, queue.size());
        
        queue.clear();
        
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
        assertNull(queue.dequeue());
    }

    @Test
    void testClearAndReuseQueue() {
        TestCutie cutie1 = new TestCutie("First", 1);
        TestCutie cutie2 = new TestCutie("Second", 2);
        
        queue.enqueue(cutie1);
        queue.clear();
        queue.enqueue(cutie2);
        
        assertEquals(1, queue.size());
        assertEquals(cutie2.description(), queue.dequeue().description());
    }
}
