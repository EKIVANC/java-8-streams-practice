package Interview_Q1;

import java.util.Stack;


class Solution {

  private Stack<Integer> numStack;
  private Stack<Transaction> tranStack;

  public Solution() {
    // write your code in Java SE 8
    numStack = new Stack<>();
    tranStack = new Stack<>();
  }

  public void push(int value) {
    numStack.push(value);
  }

  public int top() {
    try {
      return numStack.peek();
    }
    catch (java.util.EmptyStackException ex ){
      // it is better to use a logger library here ! I never log in this way actually
      System.out.println("stack is empty PEEK operation!");
    }
    return 0;
  }

  public void pop() {
    try {
      numStack.pop();
    }
    catch (java.util.EmptyStackException ex ){
      // it is better to use a logger library here ! I never log in this way actually
      System.out.println("stack is empty POP operation!");
    }
  }

  public void begin() {
    tranStack.push(new Transaction(numStack.size()));
  }

  public boolean rollback() {
    boolean isTranPoped = false;
    try {
      Transaction popped = tranStack.pop();
      isTranPoped = true;
      for (int i = popped.sIndex; i <= numStack.size()  ; i++) {
        numStack.pop();
      }
    }
    catch (java.util.EmptyStackException ex ){
      // it is better to use a logger library here ! I never log in this way actually
      System.out.println("Transaction stack is empty Rollbacl operation!");
    }
    return isTranPoped;
  }

  public boolean commit() {
    try {
      tranStack.pop();
      return true;
    }
    catch (java.util.EmptyStackException ex ){
      // it is better to use a logger library here ! I never log in this way actually
      System.out.println("Transaction stack is empty Rollbacl operation!");
    }
    return false;
  }

  private final class Transaction {
    private  Integer sIndex;
       Transaction(Integer sIndex) {
          this.sIndex = sIndex;
      }
  }

  public static void test() {
    // Define your tests here
    Solution sol = new Solution();
    sol.push(42);
    assert sol.top() == 42 : "top() should be 42";
  }
}

public class Runner {
    public static void main(String[] args) throws Exception{
      Solution sol = new Solution();
      sol.push(4);
      sol.begin();                    // start transaction 1
      sol.push(7);                    // stack: [4,7]
      sol.begin();                    // start transaction 2
      sol.push(2);                    // stack: [4,7,2]
      if (sol.rollback()!= true){
        throw new Exception("");
      }  // rollback transaction 2


      if (sol.top() != 7){ //   stack: [4,7]
        throw new Exception("");
      }



      sol.begin();                    // start transaction 3
      sol.push(10);                   // stack: [4,7,10]

      if (sol.commit() != true) {    // transaction 3 is committed
        throw new Exception("");
      }

      if (sol.top()!= 10) {
        throw new Exception("");
      }
      if (sol.rollback() != true){// rollback transaction 1
        throw new Exception("");
      }
      if (sol.top() != 4) {// stack: [4]
        throw new Exception("");
      }
      if (sol.commit() != false) {// there is no open transaction
        throw new Exception("");
      }
    }
}
