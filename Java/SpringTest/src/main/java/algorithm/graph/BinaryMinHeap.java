package algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data structure to support following operations
 * extracMin - O(logn)
 * addToHeap - O(logn)
 * containsKey - O(1)
 * decreaseKey - O(logn)
 * getKeyWeight - O(1)
 *
 * It is a combination of binary heap and hash map
 *
 */
public class BinaryMinHeap<T> {

    private List<Node> bHeap = new ArrayList<>();
	private Map<T, Integer> idxMap = new HashMap<>();
        
    public class Node {
        int weight;
        T key;
    }

    /**
     * Checks where the key exists in heap or not
     */
    public boolean containsData(T key){
        return idxMap.containsKey(key);
    }

    /**
     * Add key and its weight to they heap
     */
    public void add(int weight,T key) {
        Node node = new Node();
        node.weight = weight;
        node.key = key;
        bHeap.add(node);
        int size = bHeap.size();
        int current = size - 1;
        int parentIndex = (current - 1) / 2;
        idxMap.put(node.key, current);

        while (parentIndex >= 0) {
            Node parentNode = bHeap.get(parentIndex);
            Node currentNode = bHeap.get(current);
            if (parentNode.weight > currentNode.weight) {
                swap(parentNode,currentNode);
                updateIdxMap(parentNode.key,currentNode.key,parentIndex,current);
                current = parentIndex;
                parentIndex = (parentIndex - 1) / 2;
            } else {
                break;
            }
        }
    }

    /**
     * Get the heap min without extracting the key
     */
    public T min(){
        return bHeap.get(0).key;
    }

    /**
     * Checks with heap is empty or not
     */
    public boolean empty(){
        return bHeap.size() == 0;
    }

    /**
     * Decreases the weight of given key to newWeight
     */
    public void decrease(T data, int newWeight){
        Integer idx = idxMap.get(data);
        bHeap.get(idx).weight = newWeight;
        int parent = (idx -1 )/2;
        while(parent >= 0){
            if(bHeap.get(parent).weight > bHeap.get(idx).weight){
                swap(bHeap.get(parent), bHeap.get(idx));
                updateIdxMap(bHeap.get(parent).key,bHeap.get(idx).key,parent,idx);
                idx = parent;
                parent = (parent-1)/2;
            }else{
                break;
            }
        }
    }

    /**
     * Get the weight of given key
     */
    public Integer getWeight(T key) {
        Integer idx = idxMap.get(key);
        if( idx == null ) {
            return null;
        } else {
            return bHeap.get(idx).weight;
        }
    }

    /**
     * Returns the min node of the heap
     */
    public Node extractMinNode() {
        int size = bHeap.size() -1;
        Node minNode = new Node();
        minNode.key = bHeap.get(0).key;
        minNode.weight = bHeap.get(0).weight;

        int lastNodeWeight = bHeap.get(size).weight;
        bHeap.get(0).weight = lastNodeWeight;
        bHeap.get(0).key = bHeap.get(size).key;
        idxMap.remove(minNode.key);
        idxMap.remove(bHeap.get(0));
        idxMap.put(bHeap.get(0).key, 0);
        bHeap.remove(size);

        int currentIndex = 0;
        size--;
        while(true){
            int left = 2*currentIndex + 1;
            int right = 2*currentIndex + 2;
            if(left > size){
                break;
            }
            if(right > size){
                right = left;
            }
            int smallerIndex = bHeap.get(left).weight <= bHeap.get(right).weight ? left : right;
            if(bHeap.get(currentIndex).weight > bHeap.get(smallerIndex).weight){
                swap(bHeap.get(currentIndex), bHeap.get(smallerIndex));
                updateIdxMap(bHeap.get(currentIndex).key,bHeap.get(smallerIndex).key,currentIndex,smallerIndex);
                currentIndex = smallerIndex;
            }else{
                break;
            }
        }
        return minNode;
    }
    /**
     * Extract min value key from the heap
     */
    public T extractMin(){
        Node node = extractMinNode();
        return node.key;
    }

    private void printIdxMap(){
        System.out.println(idxMap);
    }

    private void swap(Node node1,Node node2){
        int weight = node1.weight;
        T data = node1.key;
        
        node1.key = node2.key;
        node1.weight = node2.weight;
        
        node2.key = data;
        node2.weight = weight;
    }

    private void updateIdxMap(T data1, T data2, int pos1, int pos2){
        idxMap.remove(data1);
        idxMap.remove(data2);
        idxMap.put(data1, pos1);
        idxMap.put(data2, pos2);
         
    }
    
    public void printHeap(){
        for(Node n : bHeap){
            System.out.println(n.weight + " " + n.key);
        }
    }
    
    public static void main(String args[]){
        BinaryMinHeap<String> heap = new BinaryMinHeap<String>();
        heap.add(3, "Tushar");
        heap.add(4, "Ani");
        heap.add(8, "Vijay");
        heap.add(10, "Pramila");
        heap.add(5, "Roy");
        heap.add(6, "NTF");
        heap.add(2,"AFR");
        heap.decrease("Pramila", 1);
        heap.printHeap();
        heap.printIdxMap();
    }
}