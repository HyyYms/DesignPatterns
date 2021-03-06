package com.company;

import sun.misc.Cache;

import java.util.HashMap;

/**
 * @program: LRU缓存机制
 * @description:
 * @author: Mr.Huang
 * @create: 2020-10-26 21:03
 **/
public class LRUCache {

    private class CacheNode {
        private CacheNode prev;
        private CacheNode next;
        private int key;
        private int value;

        public CacheNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    private int capacity;
    private HashMap<Integer, CacheNode> valNodeMap = new HashMap<>();
    private CacheNode head = new CacheNode(-1, -1);
    private CacheNode tail = new CacheNode(-1, -1);


    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!valNodeMap.containsKey(key)) {
            return -1;
        }
        CacheNode current = valNodeMap.get(key);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        moveToTail(current);
        return valNodeMap.get(key).value;
    }

    public void put(int key, int value) {
        if (get(key) != -1) {
            valNodeMap.get(key).value = value;
            return;
        }
        if (valNodeMap.size() == capacity) {
            valNodeMap.remove(head.next.key);
            head.next = head.next.next;
            head.next.prev = head;
        }
        CacheNode node = new CacheNode(key, value);
        valNodeMap.put(key, node);
        moveToTail(node);
    }

    private void moveToTail(CacheNode node) {
        node.prev = tail.prev;
        tail.prev = node;
        node.next = tail;
        node.prev.next = node;
    }
}
