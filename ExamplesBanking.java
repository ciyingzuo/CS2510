//import tester.*;
//
//// Bank Account Examples and Tests
//public class ExamplesBanking {
//	
//    public ExamplesBanking(){}
//    Account check0 = new Checking(1, 100, "First Checking Account", 20);
//    Account check1 = new Checking(1, 100, "First Checking Account", 20);
//    Account savings1 = new Savings(4, 200, "First Savings Account", 2.5);    
//    
//    // Tests the exceptions we expect to be thrown when
//    //   performing an "illegal" action.
//    public boolean testAmtAvailable(Tester t){
//    	  return
//      t.checkExpect(this.check1.amtAvailable(), 80) &&
//      t.checkExpect(this.savings1.amtAvailable(), 200);
//    }
//    
//    public boolean testSame(Tester t){
//        return
//    t.checkExpect(this.check0.same(check1), true);
//  }
//}
