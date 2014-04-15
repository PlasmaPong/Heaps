package HEAPINESS;

public class NodeHeap<V extends Comparable<V>> implements Heap<V> {

	MODE mode;
	private Node head;

	public NodeHeap(){
		mode = NodeHeap.MODE.MAX;
		head = null;
	}
	public void add(V value) {
		/**
		 * Adds a value to the based on dat good shit nigga!
		 */
		if(head==null){
			head = new Node(value);
		}
		else{
			Node temp=head;
			while(temp.getRightChild()!=null){//deal with null rightChild case for each iteration
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
			}
			else{
				temp.setRightChild(new Node(value));
			}
		}
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
	private void siftDown(Node start) {
		// TODO Auto-generated method stub
		Node temp = start;
		boolean swapped;
		if(start.leftDepth()!=1){
			do{
				swapped = false;
				if(temp.getRightChild()!=null){//compare children, compare the highest to the parent@temp
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
		}
		return false;
	}
	private void swapValues(Node node1, Node node2) {
		/**
		 * make temporary node
		 * @node2 the one you want to change with head/to be deleted
		 * @node1 the head generally(or at least for this case)
		 * swap by setting last element's children equal to heads children
		 * and then setting head to the temp.getChild() from the other method.
		 */
		V value = node1.getValue();
		node1.setValue(node2.getValue());
		node2.setValue(value);

	}
	@Override
	public V[] toArray(){
		// TODO Auto-generated method stub
		return null;
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
				node.setParent(this);
			}
		}
		Node getRightChild(){
			return this.rightChild;
		}
		void setRightChild(Node node){
			this.rightChild = node;
			if(node!=null){
				node.setParent(this);
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setMode(HEAPINESS.Heap.MODE mode) {
		// TODO Auto-generated method stub

	}
}
