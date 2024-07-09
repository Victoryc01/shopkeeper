package org.example;

import java.util.Random;


public class PancakesNonConcurrent {

    private static final int MAX_PANCAKES_PER_SLOT = 12;
    private static final int MAX_PANCAKES_PER_USER = 5;
    private static final int SLOT_DURATION_MS = 30000;

    public int totalPancakesMade = 0;
    public int totalPancakesEaten = 0;
    public int totalWastedPancakes = 0;
    public int totalUnmetOrders = 0;


    public void start() {
        while (true) {
            simulateSlot();
            try {
                Thread.sleep(SLOT_DURATION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateSlot() {
        Random random = new Random();

        int pancakesMade = random.nextInt(MAX_PANCAKES_PER_SLOT + 1);
        totalPancakesMade += pancakesMade;

        int totalPancakesRequested = 0;
        for (int i = 0; i < 3; i++) {
            int pancakesForUser = random.nextInt(MAX_PANCAKES_PER_USER + 1);
            totalPancakesRequested += pancakesForUser;
        }

        int pancakesEaten;
        int wastedPancakes;
        int unmetPancakeOrders;
        if (totalPancakesRequested <= pancakesMade) {
            pancakesEaten = totalPancakesRequested;
            wastedPancakes = pancakesMade - totalPancakesRequested;
            unmetPancakeOrders = 0;
        } else {
            pancakesEaten = pancakesMade;
            wastedPancakes = 0;
            unmetPancakeOrders = totalPancakesRequested - pancakesMade;
        }

        totalPancakesEaten += pancakesEaten;
        totalWastedPancakes += wastedPancakes;
        totalUnmetOrders += unmetPancakeOrders;

        System.out.println("Slot Start Time: " + System.currentTimeMillis());
        System.out.println("Pancakes Made: " + pancakesMade);
        System.out.println("Pancakes Eaten: " + pancakesEaten);
        System.out.println("Wasted Pancakes: " + wastedPancakes);
        System.out.println("Unmet Pancake Orders: " + unmetPancakeOrders);
        System.out.println("Slot End Time: " + System.currentTimeMillis());
        System.out.println();
    }
}
