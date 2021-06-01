package edu.uw.tcss450.chatapp_group1.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class NewRequestCountViewModel extends ViewModel {
    private MutableLiveData<Integer> mNewRequestCount;

    public NewRequestCountViewModel() {
        mNewRequestCount = new MutableLiveData<>();
        mNewRequestCount.setValue(0);
    }

    public void addRequestCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewRequestCount.observe(owner, observer);
    }

    public void increment() {
        mNewRequestCount.setValue(mNewRequestCount.getValue() + 1);
    }

    public void reset() {
        mNewRequestCount.setValue(0);
    }
}
