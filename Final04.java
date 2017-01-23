import tester.*;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Arrays;

public class Final04 {

}

class ArrayUtils {
    int sum(ArrayList<Integer> arr) {
        int sum = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
        }
        return sum;
    }

    boolean pps(ArrayList<Integer> arr) {
        int sum = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
            if (sum < 0) {
                break;
            }
        }
        if (sum < 0) {
            return false;
        } else
            return true;
    }

    boolean isSorted(ArrayList<String> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            String a = arr.get(i);
            String b = arr.get(i + 1);
            if (a.compareTo(b) > 0)
                return false;
        }
        return true;
    }

    boolean sequence(ArrayList<Integer> arr, ArrayList<Integer> s) {
        for (int i = 0; i < arr.size(); i++) {
            for (int n = 0; n < s.size(); n++){
                if (arr.get(i + n) != s.get(n)) {
                    break;
                }
                if (arr.get(i + n) == s.get(n) && n == s.size() - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}

class example {
    ArrayList<Integer> a = new ArrayList<Integer>(Arrays.asList(1, 2, 3, -10, 5));
    ArrayList<Integer> aa = new ArrayList<Integer>(Arrays.asList(2, 3, -10));
    ArrayList<String> b = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "D"));

    boolean test(Tester t) {
        return t.checkExpect(new ArrayUtils().pps(a), false) && t.checkExpect(new ArrayUtils().isSorted(b), true)
                && t.checkExpect(new ArrayUtils().sequence(a, aa), true);
    }
}