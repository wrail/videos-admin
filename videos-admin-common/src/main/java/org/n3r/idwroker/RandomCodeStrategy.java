package org.n3r.idwroker;

public interface RandomCodeStrategy {
    void init();

    int prefix();

    int next();

    void release();
}
