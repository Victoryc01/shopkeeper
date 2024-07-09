package org.example;

public class Main {
    public static void main(String[] args) {

        PancakesNonConcurrent panNo = new PancakesNonConcurrent();
        panNo.start();

        PancakesConcurrent pan = new PancakesConcurrent();
        pan.start();
    }
}

/* Observations
* The non-concurrent version is easier to implement and understand but
* is not suitable for scaling to more complex or numerous concurrent tasks.
*
* The concurrent version provides a more realistic simulation of concurrent users,
* handling the tasks more efficiently and effectively.
*/