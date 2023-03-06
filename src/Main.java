import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // first();
        // second();
        // secondByLambda();
        // third();
        thirdByInterrupter();
    }

    private static void first() throws InterruptedException {
        int countOfThreads = 22;
        ArrayList<Thread> threadsList = new ArrayList<>();

        System.out.println("Main thread started...");

        for (int i = 0; i < countOfThreads; i++) {
            threadsList.add(new JThread("JThread" + i));
            threadsList.get(i).start();
        }

        for (Thread thread : threadsList) {
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

    private static void third(){
        System.out.println("Main started...");
        MyThread thread = new MyThread();
        new Thread(thread, "MyThread").start();

        try {
            Thread.sleep(1100);
            thread.disable();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main finished... \n");
    }

    private static void thirdByInterrupter(){
        System.out.println("Main thread started...");
        MyJThread thread = new MyJThread("MyJThread");
        thread.start();

        try {
            Thread.sleep(150);
            thread.interrupt();
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main thread finished...");
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

class MyThread implements Runnable{
    private boolean isActive;

    MyThread(){
        isActive = true;
    }

    public void disable(){
        isActive = false;
    }
    @Override
    public void run() {
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        int counter = 1;
        while (isActive){
            System.out.println("Loop: " + counter++);
            try{
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println("Thread has been interrupted");
            }
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}

class MyJThread extends Thread{
    MyJThread(String name){
        super(name);
    }

    @Override
    public void run() {
        System.out.printf("%s started... \n", Thread.currentThread().getName());

        int counter = 1;

        try {
            while(!isInterrupted()) {
                System.out.println("Loop: " + counter++);
                Thread.sleep(30);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " has been interrupted");
        }


        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}