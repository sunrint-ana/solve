package net.yellowstrawberry.solveana.solve.test;

import java.util.concurrent.*;

public class TimeLimitedCodeBlock {
    private static final int THREADS = 2;
    private static int using = 0;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    private static synchronized boolean use() {
        if(using < THREADS) {using++; return true;}
        return false;
    }

    public static long runWithTimeout(final Runnable runnable, long timeout, TimeUnit timeUnit) throws Exception {
        return runWithTimeout(() -> {
            runnable.run();
            return null;
        }, timeout, timeUnit);
    }

    public static <T> long runWithTimeout(Callable<T> callable, long timeout, TimeUnit timeUnit) throws Exception {
        if(using >= THREADS) {
            while (use()) {
                Thread.sleep(100);
            }
        }

        final Future<T> future = executor.submit(callable);
        long s = System.currentTimeMillis();
        try {
            future.get(timeout, timeUnit);
            using--;
            return System.currentTimeMillis()-s;
        }
        catch (TimeoutException e) {
            using--;
            future.cancel(true);
            throw e;
        }
        catch (ExecutionException e) {
            using--;
            Throwable t = e.getCause();
            if (t instanceof Error) {
                throw (Error) t;
            } else if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                throw new IllegalStateException(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
