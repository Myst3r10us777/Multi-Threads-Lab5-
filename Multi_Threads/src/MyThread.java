public class MyThread extends Thread {
    private final int threadNumber;
    private final int totalSteps;
    private volatile int currentStep = 0;
    private volatile boolean finished = false;
    private long startTime;
    private long endTime;

    public MyThread(int threadNumber, int totalSteps) {
        this.threadNumber = threadNumber;
        this.totalSteps = totalSteps;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();

        for (int i = 0; i < totalSteps; i++) {
            currentStep++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        endTime = System.currentTimeMillis();
        finished = true;
    }

    public void printProgress() {
        int procent = (int) ((currentStep * 100.0) / totalSteps);
        int barLength = 10;
        int filledLength = (int) (barLength * currentStep / totalSteps );

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append("=");
            } else if (i == filledLength && procent < 100) {
                bar.append(">");
            } else {
                bar.append("·");
            }
        }
        bar.append("]");

        String timeInfo;
        if (finished) {
            double elapsedTime = (endTime - startTime);
            timeInfo = String.format("time: %.2fms (Completed)", elapsedTime);
        } else {
            double elapsedTime = (System.currentTimeMillis() - startTime);
            timeInfo = String.format("time: %.2fms (Going)", elapsedTime);
        }

        System.out.printf("%d) | ID: %d | %s %3d%% | %s%n",
                threadNumber,
                getId(),
                bar.toString(),
                procent,
                timeInfo);
    }

    public boolean isFinished() {
        return finished;
    }

}