public class Synchronization {
    public static void main(String[] args){
        //first();
        //second();

    }


    private static void first() {
        class CommonResource{
            int x = 0;
        }

        class CountThread implements Runnable{
            CommonResource res;
            CountThread(CommonResource res){
                this.res = res;
            }

            @Override
            public void run() {
                res.x = 1;
                for (int i = 1; i < 5; i++){
                    System.out.printf("%s %d \n", Thread.currentThread().getName(), res.x++);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        System.out.printf("%s is interrupt \n", Thread.currentThread().getName());
                    }
                }
            }
        }

        CommonResource commonResource = new CommonResource();
        for (int i = 1; i < 6; i++){
            Thread thread = new Thread(new CountThread(commonResource));
            thread.setName("Thread" + i);
            thread.start();
        }
    }

    private static void second() {
        class CommonResource{
            int x = 0;
        }

        class SynchronizedRunCountThread implements Runnable{
            final CommonResource res;
            SynchronizedRunCountThread(CommonResource res){
                this.res = res;
            }

            @Override
            public void run() {
                synchronized (res){
                    res.x = 1;
                    for (int i = 1; i < 5; i++) {
                        System.out.printf("%s %d \n", Thread.currentThread().getName(), res.x++);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.out.printf("%s is interrupted \n", Thread.currentThread().getName());
                        }
                    }
                }
            }
        }


        CommonResource res = new CommonResource();
        for (int i = 1; i < 6; i++){
            Thread thread = new Thread(new SynchronizedRunCountThread(res));
            thread.setName("SynchThread " + i);
            thread.start();
        }
    }

}



class SynhCommonResource{
    int x = 0;

    synchronized void increment(){
        x = 1;
        for (int i = 1; i < 5; i++){
            System.out.printf("%s %d \n", Thread.currentThread().getName(), x++);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

        }
    }
}

class SynchCountThread implements Runnable{
    SynhCommonResource res;
    SynchCountThread(SynhCommonResource res){
        this.res = res;
    }


    @Override
    public void run() {
        res.increment();
    }
}