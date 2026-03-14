package Drones;

import CommonUtils.BetterQueue;

import java.io.*;
import java.util.ArrayList;

/**
 * Manages everything regarding the cleaning of swords in our game.
 * Will be integrated with the other drone classes.
 *
 * You may only use java.util.List, java.util.ArrayList, and java.io.* from
 * the standard library.  Any other containers used must be ones you created.
 */
public class CleanSwordManager implements CleanSwordManagerInterface {
    /**
     * Gets the cleaning times per the specifications.
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and times it took to fill them, as per the specifications
     */
    @Override
    public ArrayList<CleanSwordTimes> getCleaningTimes(String filename) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            String line = bf.readLine();
            String[] inputs = line.split(" ");
            int numOfSwords = Integer.parseInt(inputs[0]);
            int numOfRequests = Integer.parseInt(inputs[1]);
            long timeOfCycle = Long.parseLong(inputs[2]);
            BetterQueue<Long> queue = new BetterQueue<>();
            BetterQueue<Long> cleanStartTime = new BetterQueue<>();
            long total = 0;
            for (int i = 0; i < numOfSwords; i++) {
                line = bf.readLine();
                queue.add(Long.parseLong(line));
                if (i == 0) {
                    cleanStartTime.add(0L);
                }
                else {
                    cleanStartTime.add(total);
                }
                //System.out.println("enqueue orignal Time: " + total);
                total += Long.parseLong(line);

            }
            long totalTime = 0;
            ArrayList<CleanSwordTimes> times = new ArrayList<>();

            for (int i = 0; i < numOfRequests; i++) {
                line = bf.readLine();
                long request = Long.parseLong(line);
                long time = queue.remove();
                queue.add(timeOfCycle);
                long enqueueTime = cleanStartTime.remove();
                //System.out.println(i + "\n Enqueue Time: " + enqueueTime + "\ntime: " + time +"\ntotal Time: " +
                // totalTime +  "\n");
                if (totalTime < enqueueTime + time) {
                    //System.out.println("LESS");
                    totalTime = enqueueTime + time;
                    //System.out.println("MODIFIED: " + totalTime);
                }
                if (totalTime < request) {
                    totalTime = request;
                }
                if (totalTime > total) {
                    //System.out.println("REACHED1");
                    total = totalTime;
                    cleanStartTime.add(totalTime);
                }
                else {
                    //System.out.println("REACHED2");
                    cleanStartTime.add(total);
                }
                total += timeOfCycle;
                times.add(new CleanSwordTimes(totalTime, totalTime - request));
            }
            return times;
        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
