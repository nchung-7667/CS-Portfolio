package Drones;

import CommonUtils.BetterStack;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages everything regarding the requesting of items in our game.
 * Will be integrated with the other drone classes.
 *
 * You may only use java.util.List, java.util.ArrayList, java.io.* and java.util.Scanner
 * from the standard library.  Any other containers used must be ones you created.
 */
public class ItemRequestManager implements ItemRequestManagerInterface {
    /**
     * Get the retrieval times as per the specifications
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and index of the original request, per the specifications
     */
    @Override
    public ArrayList<ItemRetrievalTimes> getRetrievalTimes(String filename) {
        try {
            // as all of the inputs are on the same line, it is actually more efficient to use scanner's nextInt since
            // with BufferedReader you would have to read in the entire line (possibly 10m integers long) at once
            Scanner scan = new Scanner(new FileReader(filename));
            int numOfRequests = scan.nextInt();
            int timeToStorage = scan.nextInt();
            int[] requestTimes = new int[numOfRequests];
            for (int i = 0; i < numOfRequests; i++) {
                requestTimes[i] = scan.nextInt();
            }
            ArrayList<ItemRetrievalTimes> retrievalTimes = new ArrayList<>();
            long globalTime = -1;
            BetterStack<Long> distanceToStorage = new BetterStack<>();
            BetterStack<Long> packageDistance = new BetterStack<>();
            BetterStack<Integer> currentRequest = new BetterStack<>();
            int indexOfNextRequest = 0;
            boolean requestPresent = false;
            boolean allRequests = false;
            int positionOfDrone = 0;
            int maxPositionOfDrone = 2 * timeToStorage;
            boolean pickingUp = false;
            while (retrievalTimes.size() < numOfRequests) { //While not all requests are done
                globalTime++;
                if (requestPresent) {   //If there is a request move drone
                    if (pickingUp) {
                        if (positionOfDrone > timeToStorage) {
                            positionOfDrone--;
                        }
                        else if (positionOfDrone < timeToStorage){
                            positionOfDrone++;
                        }
                        if ((maxPositionOfDrone - packageDistance.peek()) == positionOfDrone) {
                            //Drone has picked up package
                            pickingUp = false;
                        }
                    }
                    else {  //Drone is delivering package
                        positionOfDrone++;
                    }
                }
                if ((positionOfDrone == maxPositionOfDrone) && requestPresent) { //package delivered
                    distanceToStorage.pop();
                    packageDistance.pop();
                    retrievalTimes.add(new ItemRetrievalTimes(currentRequest.pop(), globalTime));
                    if (distanceToStorage.isEmpty()) { //Check if there is a request present
                        requestPresent = false;
                    }
                    else {
                        if (distanceToStorage.peek() != 0) { //Reset distance if next package not moved from storage
                            distanceToStorage.pop();
                            distanceToStorage.push((long) timeToStorage);
                        }
                    }
                    pickingUp = true;
                }
                while (!allRequests && globalTime == requestTimes[indexOfNextRequest]) {
                    currentRequest.push(indexOfNextRequest);
                    indexOfNextRequest++;
                    if (indexOfNextRequest == numOfRequests) {
                        allRequests = true;
                    }
                    if (positionOfDrone > timeToStorage) {
                        if (!packageDistance.isEmpty() && !pickingUp) {   //If a drone is carrying a package back
                            packageDistance.pop();
                            packageDistance.push((long) 2 * timeToStorage - positionOfDrone);
                        }
                        distanceToStorage.push((long) positionOfDrone - timeToStorage);
                        pickingUp = true;
                    }
                    else if (positionOfDrone == timeToStorage) {
                        distanceToStorage.push(0L);
                        pickingUp = false;
                    }
                    else {
                        distanceToStorage.push((long) timeToStorage - positionOfDrone);
                        pickingUp = true;
                    }
                    packageDistance.push((long) timeToStorage);
                    requestPresent = true;
                }
            }
            return retrievalTimes;
        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }

        return null;
    }
}
