package sist.com.chapter;

public class ArrayEx7 {
	public static void main(String[] args) {
		int[] numArr = new int[10];
		
		for(int i=0; i<numArr.length; i++) {
			System.out.print(numArr[i]);
		}
		System.out.println();
		
		for(int i=0; i<100; i++) {
			int n=(int)(Math.random()*10);//0~9중의 한값을 임의로 얻는다.
			int tmp = numArr[0];
			numArr[0]=numArr[n];
			numArr[n]=tmp; //numArr[0]과
		}
		for(int i=0; i<numArr.length; i++) {
			System.out.print(numArr[i]);
		}
	}
}
