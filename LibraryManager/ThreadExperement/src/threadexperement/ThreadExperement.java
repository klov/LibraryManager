/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadexperement;

/**
 *
 * @author Администратор
 */
public class ThreadExperement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
       TestHarness th = new TestHarness();
       long nanosecond = th.timeTasks(5, new Runnable() {

           @Override
           public void run() {
               int s = 0;
               for(int i = 0 ; i < 20 ; i++){
               s +=i;
               }
               System.out.println(s);
           }
       });
       System.out.println(nanosecond);
    }
    
}
