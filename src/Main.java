import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // first();
        // second();
        // secondByLambda();

    }

    private static void first() throws InterruptedException {
        System.out.println("Main thread started...");

        ArrayList<Thread> t = new ArrayList<Thread>();
        int num = 22;
        for (int i = 0; i < num; i++) {
            t.add(new JThread("JThread" + i));
            t.get(i).start();
        }

        for (Thread thread : t) {
            thread.join();
        }

        System.out.println("Main thread finished...");
    }

    private static void second(){
        System.out.print("Main thread started...\n");
        Thread myThread = new Thread(new MyRun(), "MyThread");
        myThread.start();
        System.out.print("Main thread finished...\n");
    }

    private static void secondByLambda(){
        System.out.println("Main thread started...");
        Runnable r = () -> {
            System.out.printf("%s started...\n", Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s finished...\n", Thread.currentThread().getName());
        };
        Thread t = new Thread(r, "My Thread");
        t.start();
        System.out.println("Main thread finished...");
    }


    public static void name(Thread tre){
        System.out.println(tre.getName());
    }

}

class JThread extends Thread{
    JThread(String name){
        super(name);
    }

    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            Thread.sleep(500);
        }
        catch (InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());

    }
}

class MyRun implements Runnable{

    public void run(){
        System.out.printf("%s started...\n", Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s finished...\n", Thread.currentThread().getName());

    }
}