package org.example;

import java.util.Random;
import java.util.concurrent.*;

public class PancakesConcurrent {
    private static final int MAX_PANCAKES_PER_SLOT = 12;
    private static final int MAX_PANCAKES_PER_USER = 5;
    private static final int SLOT_DURATION_MS = 30;

    public int totalPancakesMade = 0;
    public int totalPancakesEaten = 0;
    public int totalWastedPancakes = 0;
    public int totalUnmetOrders = 0;

//    public void start() {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(() -> simulateSlot(), 0, SLOT_DURATION_MS, TimeUnit.SECONDS);
//    }

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

        CompletableFuture<Integer> user1 = CompletableFuture.supplyAsync(() -> random.nextInt(MAX_PANCAKES_PER_USER + 1));
        CompletableFuture<Integer> user2 = CompletableFuture.supplyAsync(() -> random.nextInt(MAX_PANCAKES_PER_USER + 1));
        CompletableFuture<Integer> user3 = CompletableFuture.supplyAsync(() -> random.nextInt(MAX_PANCAKES_PER_USER + 1));

        int totalPancakesRequested = 0;
        try {
            totalPancakesRequested += user1.get();
            totalPancakesRequested += user2.get();
            totalPancakesRequested += user3.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
