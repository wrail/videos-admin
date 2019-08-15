package org.n3r.idwroker;

public interface WorkerIdStrategy {
    void initialize();

    long availableWorkerId();

    void release();
}
