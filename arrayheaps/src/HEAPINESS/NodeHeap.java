package HEAPINESS;

public class NodeHeap<V extends Comparable<V>> implements Heap<V> {
	
	private Node head;
	
	public NodeHeap(){
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
		Node lastTemp;
		Node temp = head;
		while(temp.getRightChild()!=null){
			if(temp.getLeftChild().leftDepth()>temp.getRightChild().rightDepth()){
				temp=temp.getLeftChild();
				if(temp.getLeftChild().leftDepth()==temp.getLeftChild().rightDepth()){
					while(temp.getRightChild().getRightChild()!=null){
						temp=temp.getRightChild();
					}
					/**
					 * headtemp >>> head
					 * head >>> last
					 * last >>> headtemp
					 * set root's children.parent to 
					 */
					lastTemp=temp.getRightChild();
					head.getRightChild().setParent(lastTemp);//these two cut off the parent reference to head
					head.getLeftChild().setParent(lastTemp);
					head.setParent(temp);
					temp.setRightChild(head);
				}
			}
		}
		siftDown(head);
		return null;
	}
	private void siftDown(Node head2) {
		// TODO Auto-generated method stub
		
	}
	private void swapValues(Node node1, Node node2) {
		/**
		 * make temporary node
		 * @node2 the one you want to change with head/to be deleted
		 * @node1 the head generally(or at least for this case)
		 * swap by setting last element's children equal to heads children
		 * and then setting head to the temp.getChild() from the other method.
		 */
		Node temp = node1;
		node1=node2;
		node2.setRightChild(temp.getRightChild());
		node2
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
		}
		Node getRightChild(){
			return this.rightChild;
		}
		void setRightChild(Node node){
			this.rightChild = node;
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
}
