package com.april.cars;

public class CarsNotFoundException extends RuntimeException
{
    public CarsNotFoundException (Long id)
    {
        super("Could not find Car");
    }
}