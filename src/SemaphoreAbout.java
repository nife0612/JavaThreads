import java.util.concurrent.Semaphore;

public class SemaphoreAbout {
    public static void main(String[] args){
        //first();
        //second();

    }

    static void first(){
        class CommonResourceSem{
            int x = 0;
        }

        class CountThreadSem implements Runnable{
            CommonResourceSem res;
            Semaphore sem;


            CountThreadSem(CommonResourceSem res, Semaphore sem){
                this.res = res;
                this.sem = sem;

            }

            @Override
            public void run() {
                try {
                    sem.acquire();
                    System.out.println(Thread.currentThread().getName() + " wait an access");
                    res.x = 1;
                    for (int i = 1; i < 6; i++){
                        System.out.printf(Thread.currentThread().getName() + ": " + res.x + '\n');
                        res.x++;
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {throw new RuntimeException(e);}
                System.out.println(Thread.currentThread().getName() + " release an access");
                sem.release();

            }
        }

        Semaphore sem = new Semaphore(1);
        CommonResourceSem res = new CommonResourceSem();
        new Thread(new CountThreadSem(res, sem), "CountThread 1").start();
        new Thread(new CountThreadSem(res, sem), "CountThread 2").start();
        new Thread(new CountThreadSem(res, sem), "CountThread 3").start();

    }

    static void second(){

        class Philosopher extends Thread{
            Semaphore sem;
            int count = 0;
            int id;

            Philosopher(Semaphore sem, int id){
                this.sem = sem;
                this.id = id;
            }

            @Override
            public void run() {
                try{
                    while(count < 3) {
                        sem.acquire();
                        System.out.println("Philosopher " + id + " sits down at the table.");
                        sleep(500);

                        count++;


                        System.out.println("Philosopher " + id + " leaves the table.");
                        sem.release();
                        sleep(500);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }


        }

        Semaphore sem = new Semaphore(2);
        for (int i = 1; i < 6; i++){
            new Philosopher(sem, i).start();
        }
    }



}

