package lisa.kapitonova.ATM;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		int[] coins = {1,3,5,10};
		int value = 10;

		IATM atm = new MyATM();
		atm.setCoins(coins);
		List<int[]> results = atm.exchange(value);

		for (int[] r : results) {
			System.out.println(Arrays.toString(r));
		}
		
		System.out.println("END");
	}
}