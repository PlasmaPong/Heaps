package HEAPINESS;

import java.lang.reflect.Array;

public class NodeHeap<V extends Comparable<V>> implements Heap<V> {

	MODE mode;
	private Node head;
	int lastNode = 0;

	public NodeHeap(){
		mode = NodeHeap.MODE.MAX;
		head = null;
	}
	public void add(V value) {
		/**
		 * Adds a value to the based on dat good shit nigga!
		 * compares the depth of the subtrees to check if they are filled or not
		 * then does so for each subtree until it deals with cases of the last children. 
		 */
		Node temp=head;
		if(head==null){
			head = new Node(value);
		}
		else{
			while(temp.getRightChild()!=null){
				if(temp.getLeftChild().leftDepth()>temp.getRightChild().leftDepth()){
					//case for filled left tree
					if(temp.getLeftChild().leftDepth()==temp.getLeftChild().rightDepth()){
						temp=temp.getRightChild();
						while(temp.getLeftChild()!=null){
							temp=temp.getLeftChild();
						}
						temp.setLeftChild(new Node(value));
						return;
					}
					temp = temp.getLeftChild();
				}
				//leftDepth==rightDepth case
				else{
					if(temp.getRightChild().leftDepth()==temp.getRightChild().rightDepth()){
						while(temp.getLeftChild()!=null){
							temp=temp.getLeftChild();
						}
						temp.setLeftChild(new Node(value));
						return;
					}
					temp=temp.getRightChild();
				}
			}
			if(temp.getLeftChild()==null){//case for when neither exist at the start
				temp.setLeftChild(new Node(value));
				temp = temp.getLeftChild();
			}
			else{
				temp.setRightChild(new Node(value));
				temp=temp.getRightChild();
			}
			siftUp(temp);
		}
		lastNode++;
	}	
	private void siftUp(Node node) {
		// TODO Auto-generated method stub
		boolean swapped  = true;
		Node temp = node;
		
		do{
			if(temp.getParent()==null){
				swapped = false;
			}else{
				if(compareValues(temp.getParent(), temp)){
					swapValues(temp.getParent(), temp);
					temp = temp.getParent();
				}else{
					swapped=false;
				}
			}
		}while(swapped);
	}
	@Override
	public V remove(){
		/**
		 * deal with:
		 * null right child for the head
		 * filled left tree
		 * when both trees are full
		 * when head is the only node
		 * TODO CAREFUL OF SPECIAL CASES INVOLVING INNERMOST  WHILE LOOP
		 * There could be problems with checking the rightmost grandchild of the node
		 */
		V value = null;
		//Node lastTemp = null;
		Node temp = head;
		if(head.getLeftChild()==null){
			value = head.getValue();//careful with the return type
			head = null;
		}
		else{
			while(temp.getRightChild()!=null){//scales the tree to find the end/deals with right null child
				if(temp.getLeftChild().leftDepth() > temp.getRightChild().leftDepth()){
					temp = temp.getLeftChild();
				}
				else{
					temp = temp.getRightChild();
				}
			}
			if(temp.getLeftChild()!=null){
				temp = temp.getLeftChild();
				temp.getParent().setLeftChild(null);
				value = head.getValue();
				temp.setRightChild(head.getRightChild());
				temp.setLeftChild(head.getRightChild());
				head = temp;
				temp.setParent(null);
			}else{
				temp.getParent().setRightChild(null);
				value =  head.getValue();
				temp.setRightChild(head.getRightChild());
				temp.setLeftChild(head.getRightChild());
				head = temp;
				temp.setParent(null);
			}
			siftDown(head);
			//be careful with the return type.
		}
		return value;
		/**

		 * creates the connections to the last and disconnects the original head
		 * 
		 */
		//tempInt = (Integer) head.getValue();
		//temp.getRightChild().setRightChild(head.getRightChild());
		//temp.getLeftChild().setLeftChild(head.getLeftChild());
		//head = temp.getRightChild();
		//head.setParent(null);
		//siftDown(head); IDEAS
	}
	public void heapify(){
		/**
		 * make into list, heapify a la ArrayHeap
		 */
		V[] array = toArray();
		for(int i = (toArray().length-1)/2; i>=0;i--){
			siftDown(i, array);
		}
	}
	private void siftDown(int index, V[] array) {
		int lastIndex = array.length-1;
		assert (index >= 0 && index <= lastIndex);

		if (index <= ((lastIndex - 1) / 2)) {
			int ch1Index;
			int ch2Index;
			int swapIndex = 0;
			boolean swapped;

			do {
				swapped = false;
				ch1Index = 2 * index + 1;

				if (ch1Index <= lastIndex) {
					ch2Index = ch1Index + 1;

					swapIndex = ch2Index <= lastIndex
							&& compareValues(ch2Index, ch1Index, array) ? ch2Index
									: ch1Index;
				}
				/**
				 * SEE IF ERRORS WILL CLEAR UP AFTER MAKING toArray!!!!!!!!
				 */
				if (compareValues(swapIndex, index, array)) {
					swapValues(swapIndex, index, array);
					index = swapIndex;
					swapped = true;
				}
			} while (swapped);
		}
	}
	private void swapValues(int index1, int index2, V[] array) {
		Object value = array[index1];
		array[index1] = array[index2];
		array[index2] = (V) value;
	}

	private boolean compareValues(int ch2Index, int ch1Index, V[] array) {
		if (mode == Heap.MODE.MAX) {
			return (array[ch2Index]).compareTo(array[ch1Index]) > 0;
		} else {
			return (array[ch2Index]).compareTo(array[ch1Index]) < 0;
		}
	}
	private void siftDown(Node start) {
		/**
		 * compare children, compare the highest to the parent.  Swap if necessary
		 * if the children and parent fulfill the heap property, then the sift fulfills the heap property
		 */
		Node temp = start;
		boolean swapped;
		if(start.leftDepth()!=1){
			do{
				swapped = false;
				if(temp.getRightChild()!=null){
					if(compareValues(temp.getRightChild(), temp.getLeftChild())){
						if(compareValues(temp.getRightChild(), temp)){
							swapValues(temp, temp.getRightChild());
							swapped = true;
							temp = temp.getRightChild();
						}
					}else{
						if(compareValues(temp.getLeftChild(), temp)){
							swapValues(temp, temp.getLeftChild());
							swapped = true;
							temp = temp.getLeftChild();
						}
					}
				}
				else{
					if(compareValues(temp.getLeftChild(), temp)){
						swapValues(temp, temp.getLeftChild());
						temp = temp.getLeftChild();
					}
				}

			}while(swapped);
		}
	}
	private boolean compareValues(Node temp, Node child) {
		if(mode == NodeHeap.MODE.MAX){
			return ((temp.getValue()).compareTo(child.getValue())>0);
		}else{
			return !((temp.getValue()).compareTo(child.getValue())>0);
		}
	}

	private void swapValues(Node node1, Node node2) {
		/**
		 * make temporary node
		 * @node2 the one you want to change with head/to be deleted
		 * @node1 the head generally(or at least for this case)
		 * swap by setting last element's children equal to heads children
		 * and then setting head to the temp.getChild() from the other method.
		 */
		System.out.println("Swapped");
		V value = node1.getValue();
		node1.setValue(node2.getValue());
		node2.setValue(value);

	}
	public V[] toArray(){
		/**
		 * TODO set values for each index.
		 * WARNING: COPY PASTE FROM BELOW METHOD CAUTION WITH IMPLEMENTATION
		 */
		V[] array = (V[]) Array.newInstance(head.getValue().getClass(), lastNode);//ASK
		Node temp;
		for(int i = 0; i <= (lastNode-1);++i){
			temp = head;
			String binary = Integer.toString(i, 2);
			for(int j = 1;j <= (binary.length()-1);++j){
				if(binary.charAt(j)=='0'){
					temp=temp.getLeftChild();
				}else{
					temp=temp.getRightChild();
				}
			}
			//System.out.println(i);
			array[i]=temp.getValue();
			//System.out.println(i);
		}
		return array;
	}
	///////////////////////////////////////////////////////////////////////////////
	/////////////////////SUPER COOOOOOOOOOOOOOOOOOOOOOOOOOOOL//////////////////////
	///////////////////////////////////////////////////////////////////////////////
	public void alternativeAdd(V value){
		/**
		 * read down until the last part of the binary number representation of the tree
		 * check case for left null and right null child
		 * somebody told me to try representing it as a binary number, so i tried this.
		 */
		Node temp = head;
		if(head!=null){
			String binary = Integer.toString(lastNode+1, 2);
			System.out.println(binary);
			for(int i = 1;i <= (binary.length()-2);++i){
				if(binary.charAt(i)=='0'){
					temp=temp.getLeftChild();
				}else{
					temp=temp.getRightChild();
				}
			}
			//System.out.println("before assignemnt");
			if(temp.getLeftChild()==null){
				System.out.println("Array before new node " + toString());
				temp.setLeftChild(new Node(value));
				lastNode++;
				System.out.println("value is "+ value);
				System.out.println("parent value is " + temp.getValue());
				System.out.println("Array before the sift "+ toString());
				siftUp(temp.getLeftChild());
				System.out.println("Array after the sift " + toString());
				//System.out.println("value is "+ value);
				//System.out.println("parent value is " + temp.getValue());
			}
			else{
				System.out.println("Array before new node " +toString());
				temp.setRightChild(new Node(value));
				lastNode++;
				System.out.println("value is "+ value);
				System.out.println("parent value is " + temp.getValue());
				System.out.println("Array before the sift "+toString());
				siftUp(temp.getRightChild());
				System.out.println("Array after the sift " +toString());
				//System.out.println("value is "+ value);
				//System.out.println("parent value is " + temp.getValue());

			}
		}else{
			//System.out.println("Array before new node " +toString());
			System.out.println(lastNode);
			head=new Node(value);
			lastNode++;
			System.out.println("value is "+ value);
			
		}
		//lastNode++;LAST NODE INCREMENT GOES HERE I THNK
	}
	@Override
	public void fromArray(V[] array){
		// TODO Auto-generated method stub
	}
	@Override
	public V[] getSortedContents(){
		// TODO Auto-generated method stub
		return null;
	}
	private class Node{
		private Node leftChild;
		private Node rightChild;
		private Node parent;
		private V value;

		Node(V value){
			leftChild = null;
			rightChild = null;
			parent = null;
			this.value = value;
		}
		V getValue(){
			return this.value;
		}
		void setValue(V value){
			this.value = value;
		}
		Node getLeftChild(){
			return this.leftChild;
		}
		void setLeftChild(Node node){
			this.leftChild = node;
			if(node!=null){
				this.leftChild.setParent(this);
			}
		}
		Node getRightChild(){
			return this.rightChild;
		}
		void setRightChild(Node node){
			this.rightChild = node;
			if(node!=null){
				this.leftChild.setParent(this);
			}
		}
		Node getParent(){
			return this.parent;
		}
		void setParent(Node node){
			this.parent = node;
		}
		//method for doing a check of depth for measurement and comparison
		int leftDepth(){
			if(leftChild == null){
				return 1;
			}
			return (1+leftChild.leftDepth());
		}
		int rightDepth(){
			if(rightChild == null){
				return 1;
			}
			return (1 +rightChild.rightDepth());
		}
	}
	@Override
	public HEAPINESS.Heap.MODE getMode() {
		// TODO Autogenerated method stub
		return null;
	}
	@Override
	public void setMode(HEAPINESS.Heap.MODE mode) {
		// TODO Auto-generated method stub

	}
	public String toString(){
		V[] array = toArray();
		String str = "";
		for(int i=0;i<=array.length-1;++i){
			str+=(" "+array[i]);
		}
		return str;
	}
	public void altToString(){
		System.out.println(head.getLeftChild().getRightChild().getValue());//toArray is wrong!!!!!
	}
}

