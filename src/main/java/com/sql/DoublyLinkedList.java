package com.sql;



import java.util.NoSuchElementException;

public class DoublyLinkedList {

    private DLLNode head, last;
    private int size = 0;

    public void addFirst(Object data) {
        DLLNode newNode = new DLLNode();
        newNode.data = data;
        if(size==0) {
            head = newNode;
            last = head;
        } else {
            newNode.nextNode = head;
            head.previousNode = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(Object data) {
        DLLNode newNode = new DLLNode();
        newNode.data = data;
        if(size==0) {
            head = newNode;
        } else {
            last.nextNode = newNode;
            newNode.previousNode = last;
        }
        last = newNode;
        size++;
    }

    public void removeFirst() {
        if(size <= 1) {
            head = null;
            last = null;
        } else {
            DLLNode oldHead = head;
            head = oldHead.nextNode;
            oldHead.nextNode = null;
            head.previousNode = null;
        }
        size--;
    }

    public void removeLast() {
        if(size <= 1) {
            head = null;
            last = null;
        } else {
            last = last.previousNode;
            last.nextNode.previousNode = null;
            last.nextNode = null;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public void clear() {
        DLLNode currentNode = last;
        DLLNode tempNode;
        while(currentNode != null) {
            tempNode = currentNode.previousNode;
            currentNode.nextNode = null;
            currentNode.previousNode = null;
            currentNode.data = null;
            currentNode = tempNode;
        }
        last = null;
        head = null;
        size = 0;
    }

    protected class DLLNode {
        protected DLLNode nextNode, previousNode;
        protected Object data;
    }

    public DLLIterator iterator() {
        return new DLLIterator();
    }

    public class DLLIterator {

        private DLLNode currentPreviousNode = null;
        private DLLNode currentNextNode = head;

        public boolean hasNext() {
            if(currentNextNode == null) {
                return false;
            } else {
                return(currentNextNode != null);
            }
        }

        public boolean hasPrevious() {
            if(currentPreviousNode == null) {
                return false;
            } else {
                return (currentPreviousNode != null);
            }
        }

        public Object next() {
            if(currentNextNode == null) throw new NoSuchElementException("Attempt to retrieve next value from " +
                    "DoublyLinkedList after all values have already been retrieved. Verify hasNext method returns true " +
                    "before calling next method.");
            Object data = currentNextNode.data;
            DLLNode tempNode = currentNextNode;
            currentNextNode = currentNextNode.nextNode;
            currentPreviousNode = tempNode;
            return data;
        }

        public Object previous() {
            if(currentPreviousNode == null) throw new NoSuchElementException("Attempt to retrieve previous value from " +
                    "head node of DoublyLinkedList. Verify hasPrevious method returns true " +
                    "before calling previous method.");
            Object data = currentPreviousNode.data;
            DLLNode tempNode = currentPreviousNode;
            currentPreviousNode = currentPreviousNode.previousNode;
            currentNextNode = tempNode;
            return data;
        }

        public void resetToBeginning() {
            currentNextNode = head;
            currentPreviousNode = null;
        }

        public void resetToEnd() {
            currentNextNode = null;
            currentPreviousNode = last;
        }
    }
}