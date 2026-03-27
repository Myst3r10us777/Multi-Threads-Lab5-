import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Vvedite kol potokov: ");

        int threadsCount = scanner.nextInt();

        List<MyThread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            System.out.print("\nVvedite kol vichisleniy potoka " + (i + 1) + ": ");
            int totalSteps = scanner.nextInt();
            threads.add(new MyThread(i + 1, totalSteps));
        }
        System.out.println('\n');

        for (MyThread thread : threads) {
            thread.start();
        }

        while (true){
            boolean flag = true;

            for (MyThread thread : threads) {
                thread.printProgress();

                if (!thread.isFinished()){
                    flag = false;
                }
            }
            System.out.println('\n');
            if (flag){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }

        for (MyThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}