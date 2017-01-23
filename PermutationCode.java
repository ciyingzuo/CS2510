import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for  encoding
 * and decoding of the messages that use this code.
 */
public class PermutationCode {
    /** The original list of characters to be encoded */
    ArrayList<Character> alphabet = 
        new ArrayList<Character>(Arrays.asList(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                't', 'u', 'v', 'w', 'x', 'y', 'z'));

    ArrayList<Character> code = new ArrayList<Character>(26);

    /** A random number generator */
    Random rand = new Random();

    /**
     * Create a new instance of the encoder/decoder with a new permutation code 
     */
    PermutationCode() {
        this.code = this.initEncoder();
    }

    /**
     * Create a new instance of the encoder/decoder with the given code 
     */
    PermutationCode(ArrayList<Character> code) {
        this.code = code;
    }

    /** Initialize the encoding permutation of the characters */
    ArrayList<Character> initEncoder() {
        ArrayList<Character> alphabetcopy = 
                new ArrayList<Character>(Arrays.asList(
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                        't', 'u', 'v', 'w', 'x', 'y', 'z'));
        for (int i = 0; i < 26; i++) {
            Character a = alphabetcopy.get(i);
            int x = rand.nextInt(26);
            Character b = alphabetcopy.get(x);
            alphabetcopy.set(x, a);
            alphabetcopy.set(i, b);
        }
        return alphabetcopy;
    }

    /**
     * produce an encoded <code>String</code> from the given <code>String</code>
     * @param source the <code>String</code> to encode
     * @return the secretly encoded <String>
     */
    String encode(String source) {
        String m = "";
        for (int i = 0; i < source.length(); i++) {
            m = m + this.code.get((int)source.charAt(i) - 97).toString();
        }
        return m;
    }

    /**
     * produce an decoded <code>String</code> from the given <code>String</code>
     * @param source the <code>String</code> to decode
     * @return the revealed <String>
     */
    String decode(String code) {
        String m = "";
        for (int i = 0; i < code.length(); i++) {
            m = m + (alphabet.get(this.find(code.charAt(i)))).toString();
        }
        return m;
    }

    // find the specific object and return it's position.
    public int find(Character c) {
        int result = 0;
        for (int i = 0;  i < 26; i++) {
            if (this.code.get(i) == c)
            { result = i; }
        }
        return result;
    }
}

class ExamplesPerm {
    ArrayList<Character> alphabetcopy1 = 
            new ArrayList<Character>(Arrays.asList(
                    'b', 'c', 'd', 'e', 'f', 'a', 'g', 'h', 'i', 'j', 
                    'k', 'u', 'm', 'q', 'o', 'p', 'n', 'r', 's', 
                    't', 'l', 'v', 'w', 'x', 'y', 'z'));

    boolean test(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.encode("abc"), "bcd");
    }
    boolean test1(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.decode("bcd"), "abc");
    }
    
    boolean test2(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.find('c'), 1);
    }
    
    boolean test3(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.code.get(25), 'z');
    }
    
    boolean test4(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.code.get(0), 'b');
    }
    
    boolean test5(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.encode("mnpq"), "mqpn");
    }
    boolean test6(Tester t) {
        PermutationCode perm = new PermutationCode(this.alphabetcopy1);
        return t.checkExpect(perm.decode("mqpn"), "mnpq");
    }
}