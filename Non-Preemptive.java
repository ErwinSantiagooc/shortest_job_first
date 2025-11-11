import java.util.*;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int startTime;
    int finishTime;
    int waitingTime;
    int turnaroundTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class SJF_NonPreemptive {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // Read process data
        for (int i = 0; i < n; i++) {
            System.out.print("Process name: ");
            String name = sc.next();
            System.out.print("Arrival time: ");
            int arrival = sc.nextInt();
            System.out.print("Burst time: ");
            int burst = sc.nextInt();

            processes.add(new Process(name, arrival, burst));
        }

        // Sort by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        List<Process> completed = new ArrayList<>();

        while (!processes.isEmpty()) {
            // Find processes that have arrived
            List<Process> available = new ArrayList<>();
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime)
                    available.add(p);
            }

            // If no process has arrived yet, move time forward
            if (available.isEmpty()) {
                currentTime++;
                continue;
            }

            // Select the process with the shortest burst time
            Process next = Collections.min(available, Comparator.comparingInt(p -> p.burstTime));
            processes.remove(next);

            // Calculate times
            next.startTime = currentTime;
            next.finishTime = currentTime + next.burstTime;
            next.turnaroundTime = next.finishTime - next.arrivalTime;
            next.waitingTime = next.turnaroundTime - next.burstTime;

            currentTime = next.finishTime;
            completed.add(next);
        }

        // Display results
        System.out.println("\nSJF (Non-Preemptive) Scheduling Results:");
        System.out.println("Process | Arrival | Burst | Start | Finish | Waiting | Turnaround");
        for (Process p : completed) {
            System.out.printf("%7s | %7d | %5d | %5d | %6d | %7d | %10d\n",
                    p.name, p.arrivalTime, p.burstTime, p.startTime, p.finishTime, p.waitingTime, p.turnaroundTime);
        }

        // Calculate averages
        double avgWaiting = completed.stream().mapToInt(p -> p.waitingTime).average().orElse(0);
        double avgTurnaround = completed.stream().mapToInt(p -> p.turnaroundTime).average().orElse(0);

        System.out.printf("\nAverage waiting time: %.2f\n", avgWaiting);
        System.out.printf("Average turnaround time: %.2f\n", avgTurnaround);
    }
}
