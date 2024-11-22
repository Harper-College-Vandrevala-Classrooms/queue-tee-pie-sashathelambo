package com.csc;

public class QueueTees<T extends Cutie> {
    private T[] queue;
    private int front;
    private int rear;
    private int size;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public QueueTees(int capacity) {
        this.capacity = capacity;
        this.queue = (T[]) new Cutie[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(T cutie) {
        if (size == capacity) {
            System.out.println("Queue is full! Cannot add more cuties.");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = cutie;
        size++;
    }

    public T dequeue() {
        if (size == 0) {
            System.out.println("Queue is empty! No cuties to remove.");
            return null;
        }
        T cutie = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;
        return cutie;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            queue[i] = null;
        }
        front = 0;
        rear = -1;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public static void main(String[] args) {
        QueueTees<Cutie> cutieQueue = new QueueTees<>(3);
        
        Cutie puppy = new Cutie() {
            public String description() {
                return "A little puppy with big, sad eyes";
            }
            public Integer cutenessRating() {
                return 11;
            }
        };
        
        cutieQueue.enqueue(puppy);
        System.out.println("Queue size: " + cutieQueue.size()); // Should print 1
        
        cutieQueue.clear();
        System.out.println("Queue size after clear: " + cutieQueue.size()); // Should print 0
    }
}
