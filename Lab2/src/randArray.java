import java.util.Arrays;
import java.util.Random;


public class randArray {
	
	public int [] array;
	
	public randArray(int size){
		int [] newArray = new int[size];
		for(int i=0; i<size; i++){
			Random rand = new Random();
			newArray[i] = rand.nextInt(100);
		}
		Arrays.sort(newArray);
		for(int i=0; i<size; i++){
			System.out.println(newArray[i]);
		}
		array = newArray;
	}
}
