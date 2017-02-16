/*
   Информационно-вычислительный центр космодрома Байконур
*/

package experement;

import static java.lang.Thread.sleep;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Администратор
 */
public class ThreadInvokeAllExperement {
    
  //-------------------Logger---------------------------------------------------
  
  //-------------------Constants------------------------------------------------

  //-------------------Fields---------------------------------------------------

  //-------------------Constructors---------------------------------------------

  //-------------------Getters and setters--------------------------------------

  //-------------------Methods--------------------------------------------------
    public static void start() throws InterruptedException {
        List<Callable<Long>> tasks = new LinkedList<>();
        for(int i = 0; i < 20; i++)
        tasks.add(new Callable<Long>() {

            @Override
            public Long call() throws Exception {
                return Thread.currentThread().getId();
            }
        });
        tasks.add(10,new Callable<Long>() {

            @Override
            public Long call() throws Exception {
                List<String> s = null;
                s.get(0);
                return Thread.currentThread().getId();
            }
        });
        ExecutorService exec = Executors.newFixedThreadPool(5);
        List<Future<Long>> future = exec.invokeAll(tasks,1000,TimeUnit.MILLISECONDS);
        for(Future<Long> f : future){
            
            try {
                System.out.println(f.get());
            } catch (ExecutionException ex) {
               System.out.println(ex.getMessage());
            } catch (InterruptedException ex) {
              System.out.println(ex.getMessage());
            }catch(CancellationException ex){
                 System.out.println(ex.getMessage());
            }
        }
        
    }
}