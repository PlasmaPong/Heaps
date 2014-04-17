package HEAPINESS;

public class Driver {
	private static NodeHeap datHeapDoe;

	public static void main(String[] args){
		datHeapDoe = new NodeHeap();
		int i = 0;
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(1);
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(2);
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(3);
		
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(4);
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(5);
		/*
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(6);
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(7);
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(8);
		System.out.println("--------"+(++i));
		datHeapDoe.alternativeAdd(9);
		*/
		System.out.println("dsdsd");
		datHeapDoe.altToString();
		
	}
}
