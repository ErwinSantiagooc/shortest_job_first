import java.util.*;

class ProcessSRTF {
    String name;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int startTime = -1;
    int finishTime;
    int waitingTime;
    int turnaroundTime;

    public ProcessSRTF(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SJF_Preemptive {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        List<ProcessSRTF> processes = new ArrayList<>();

        // Input process data
        for (int i = 0; i < n; i++) {
            System.out.print("Process name: ");
            String name = sc.next();
            System.out.print("Arrival time: ");
            int arrival = sc.nextInt();
            System.out.print("Burst time: ");
            int burst = sc.nextInt();
            processes.add(new ProcessSRTF(name, arrival, burst));
        }

        int time = 0, completed = 0;
        List<ProcessSRTF> readyQueue = new ArrayList<>();

        // Simulate the scheduler
        while (completed < n) {
            // Add processes that arrive at this time
            for (ProcessSRTF p : processes) {
                if (p.arrivalTime == time)
                    readyQueue.add(p);
            }

            if (!readyQueue.isEmpty()) {
                // Sort ready queue by remaining time (shortest first)
                readyQueue.sort(Comparator.comparingInt(p -> p.remainingTime));
                ProcessSRTF current = readyQueue.get(0);

                // Record the first time the process starts
                if (current.startTime == -1)
                    current.startTime = time;

                // Execute process for one unit of time
                current.remainingTime--;
                time++;

                // If process finishes, calculate stats
                if (current.remainingTime == 0) {
                    current.finishTime = time;
                    current.turnaroundTime = current.finishTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
                    completed++;
                    readyQueue.remove(current);
                }
            } else {
                time++; // No process ready, advance time
            }
        }

        // Display results
        System.out.println("\nSJF (Preemptive - SRTF) Scheduling Results:");
        System.out.println("Process | Arrival | Burst | Start | Finish | Waiting | Turnaround");
        for (ProcessSRTF p : processes) {
            System.out.printf("%7s | %7d | %5d | %5d | %6d | %7d | %10d\n",
                    p.name, p.arrivalTime, p.burstTime, p.startTime, p.finishTime, p.waitingTime, p.turnaroundTime);
        }
    }
}
