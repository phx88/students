package lisa.kapitonova.ATM;

import java.util.List;

public interface IATM {
	
	void setCoins(int[] coins) throws Exception;
	
	List<int[]> exchange(int value) throws Exception;
}
