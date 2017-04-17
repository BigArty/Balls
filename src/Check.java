
public class Check extends Thread {
    Check(){
        start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
        if(Energy.working==0){
            System.out.println("Error while start.");
            System.exit(0);
        }
    }
}
