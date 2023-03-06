import java.util.concurrent.Exchanger;

public class ExchangerAbout {
    public static void main(String[] args){
        class PutThread implements Runnable{
            Exchanger<String> ex;
            String message;

            PutThread(Exchanger<String> ex){
                this.ex = ex;
                message = "Hello JAVA!;";
            }
           @Override
            public void run(){
                try{
                    message = ex.exchange(message);
                    System.out.println("PutThread has received: " + message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        class GetThread implements Runnable{
            Exchanger<String> ex;
            String message;

            GetThread(Exchanger<String> ex){
                this.ex = ex;
                message = "Hello World!";
            }


            @Override
            public void run() {
                try{
                    message = ex.exchange(message);
                    System.out.println("GetThread has received: " + message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Exchanger<String> ex = new Exchanger<String>();
        new Thread(new PutThread(ex)).start();
        new Thread(new GetThread(ex)).start();
    }

}
