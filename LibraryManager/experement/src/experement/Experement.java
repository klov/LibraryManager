/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experement;

import static experement.ThreadInvokeAllExperement.start;

/**
 *
 * @author Администратор
 */
public class Experement {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {

        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        new ReaderThread().start();
//        ready = false;
//        number = 42;
//        ready = true;
//        List<String> l = new ArrayList<String>();
//        l.add("list");
//        Object o =  l;
//        System.out.println(o.getClass().getName());
//        List<Integer> in = new LinkedList<>();
//        A a = new A();
//        System.out.println(A.s);
//
//        byte[] a = {23,32,12,1,25,4};
//        System.out.println(a.toString());
//         PrimeProducer pp =  new PrimeProducer();
//         pp.start();
//         sleep(100);
//         pp.interrupt();
          start();
    }

}
