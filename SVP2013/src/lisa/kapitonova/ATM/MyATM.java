package lisa.kapitonova.ATM; 
  
import java.lang.reflect.Array;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List; 
  
public class MyATM implements IATM { 
  
    private int[] coins = null; 
  
    private List<int[]> result; 
  
    @Override
    public void setCoins(int[] coins) throws Exception { 
        for (int i = 0; i < coins.length; i++) { 
            if (coins[i] <= 0) { 
                throw new Exception("Bad coins"); 
            } 
        } 
        this.coins = coins; 
    } 
      
    private int[] copyArray(int[] array) { 
        int[] a = new int[array.length]; 
        for(int i = 0; i < array.length; i++) { 
            a[i] = array[i]; 
        } 
        return a; 
    } 
  
    private void exchange(int sum, int i, int[] c) throws Exception { 
        if (sum >= coins[i]) { 
            c[i]++; 
        } else { 
            if (i == 0) { 
                result.add(c); 
            } 
            return; 
        } 
  
        for (int j = 0; i - j >= 0; j++) { 
            exchange(sum - coins[i], i - j, copyArray(c)); 
        } 
    } 
  
    public List<int[]> exchange(int moneyToExchange) throws Exception { 
        if (coins == null || coins.length == 0) { 
            throw new Exception("Coins were not set"); 
        } 
  
        result = new ArrayList<int[]>(); 
  
        for (int i = coins.length - 1; i >= 0; i--) { 
            exchange(moneyToExchange, i, new int[coins.length]); 
        }
        
        return result; 
    }
    
    
  
}