public class WaitAndNotify {
    public static void main(String[] args){
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
class Store{
    public int product = 0;

    synchronized void get(){
        while(product < 1){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        product--;
        System.out.println("The consumer buy one product.");
        System.out.println("The products on the store: " + product);
        notify();
    }

    synchronized void put(){
        while (product > 2){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        product++;
        System.out.println("The producer create one product.");
        System.out.println("The products on the store: " + product);
        notify();
    }
}

class Producer implements Runnable {
    private final Store store;
    Producer(Store store){
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++){
            store.put();
        }
    }
}

class Consumer implements Runnable{
    private final Store store;
    Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 1; i < 6; i++){
            store.get();
        }
    }
}