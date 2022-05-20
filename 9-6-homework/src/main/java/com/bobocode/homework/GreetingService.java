package com.bobocode.homework;

public class GreetingService {
    public void hello() {
        System.out.println("hello");
    }

    @LogInvocation
    public void gloryToUkraine() {
        System.out.println("Слава Україні!");
    }
}
