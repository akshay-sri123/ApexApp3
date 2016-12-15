package com.example.ApexApp3;

/**
 * Created by akshay on 12/14/16.
 */
import com.datatorrent.api.InputOperator;

/**
 * @since 3.1.0
 */

public interface InputGenerator<T> extends InputOperator
{
    public OutputPort<T> getOutputPort();
}