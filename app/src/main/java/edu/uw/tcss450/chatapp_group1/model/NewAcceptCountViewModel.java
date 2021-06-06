package edu.uw.tcss450.chatapp_group1.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class NewAcceptCountViewModel extends ViewModel {
    private MutableLiveData<Integer> mNewAccetCount;
    public NewAcceptCountViewModel() {
        mNewAccetCount = new MutableLiveData<>();
        mNewAccetCount.setValue(0);
    }

    public void addAcceptCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewAccetCount.observe(owner, observer);
    }

    public void increment() {
        mNewAccetCount.setValue(mNewAccetCount.getValue() + 1);
    }

    public void reset() {
        mNewAccetCount.setValue(0);
    }
}
