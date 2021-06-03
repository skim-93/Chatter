package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class LocationViewModel extends AndroidViewModel {
    private MutableLiveData<Location> mLocation;
    /** Toggle the zipcode search bar. False = Hidden, True = Visible */
    private MutableLiveData<Boolean> mToggleZipcode;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        mLocation = new MediatorLiveData<>();
    }

    public void addLocationObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Location> observer) {
        mLocation.observe(owner, observer);
    }

    public void setLocation(final Location location) {
        if (!location.equals(mLocation.getValue())) {
            mLocation.setValue(location);
        }
    }

    public Location getCurrentLocation() {
        return new Location(mLocation.getValue());
    }
}
