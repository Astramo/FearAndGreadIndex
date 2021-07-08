package com.example.myrxjava.api;


import com.example.myrxjava.MainActivity;

import dagger.Component;

@Component
public interface ViewModelComponent {
    void inject(MainActivity mainActivity);
}
