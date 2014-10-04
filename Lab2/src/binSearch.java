import java.util.Scanner;


public class binSearch {
	public int bruteForceSearch(int val, int [] array){
		int result = -1;
		for(int i=0; i<array.length; i++){
			if(val==array[i])
				result = 1;
		}
		return result;
	}
	
	static int count = 0;
	
	public static int binarySearch(int val, int [] array, int low, int up){
		if(low > up)
			return -1;
		count++;
		int pos = (low + up)/2;
		if(array[pos]==val){
			return pos;
		}
		else if(array[pos] < val){
			return binarySearch(val,array,pos+1,up);
		}
		else{
			return binarySearch(val,array,0,pos-1);
		}
	}
	
	public static void main(String [] args){
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter size of an array: ");

		while(!scan.hasNextInt()){
			System.out.println("Error: Please enter a valid integer. Please try again.");
			scan.nextLine();
		}
		int size = scan.nextInt();
		while(size<1){
			System.out.println("Error: Please enter an integer greater than 0. Please try again.");
			scan.nextLine();
			size = scan.nextInt();
		}
		
		
		randArray myArray = new randArray(size);
		int [] intArray = myArray.array;
		int value = 0;
		System.out.println("Please enter -1 when you wish to quit!");
		
		while(value!=-1){
			System.out.println("Please enter an integer you wish to find: ");
			value = scan.nextInt();
			if(value!=-1){
				count = 0;
				int res = binarySearch(value,intArray,0,intArray.length-1);
				if(res==-1){
					System.out.println(value + " does not exist within the array.");
				}
				else if(res!=-1){
					System.out.println(value + " exists in the array at index " + res + ", after " + count + " searches!");
				}
			}
		}
		
		scan.close();
	}
}

