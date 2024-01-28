package com.example.a310frontend;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class application extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();
}