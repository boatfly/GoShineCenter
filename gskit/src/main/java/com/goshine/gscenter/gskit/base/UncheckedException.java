package com.goshine.gscenter.gskit.base;

public class UncheckedException extends RuntimeException {
    private static final long serialVersionUID = 4140223302171577502L;

    public UncheckedException(Throwable wrapped) {
        super(wrapped);
    }

    @Override
    public String getMessage() {
        return super.getCause().getMessage();
    }
}
