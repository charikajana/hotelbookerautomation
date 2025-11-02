package com.sabre.hotelbooker.utils;

/**
 * Global test execution state manager for handling critical failures
 */
public class TestExecutionState {
    
    private static ThreadLocal<Boolean> criticalFailureOccurred = new ThreadLocal<>();
    
    /**
     * Mark that a critical failure has occurred to skip subsequent steps
     */
    public static void markCriticalFailure() {
        criticalFailureOccurred.set(true);
    }
    
    /**
     * Check if a critical failure has occurred
     */
    public static boolean hasCriticalFailureOccurred() {
        Boolean failed = criticalFailureOccurred.get();
        return failed != null && failed;
    }
    
    /**
     * Reset the critical failure state (called at the start of each scenario)
     */
    public static void resetCriticalFailureState() {
        criticalFailureOccurred.set(false);
    }
    
    /**
     * Clean up thread local (called at the end of each scenario)
     */
    public static void cleanup() {
        criticalFailureOccurred.remove();
    }
}