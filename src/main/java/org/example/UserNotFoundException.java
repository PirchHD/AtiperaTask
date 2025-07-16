package org.example;

public class UserNotFoundException extends RuntimeException {
    private final int status;

    public UserNotFoundException(String message, int status)
    {
        super(message);
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }
}
