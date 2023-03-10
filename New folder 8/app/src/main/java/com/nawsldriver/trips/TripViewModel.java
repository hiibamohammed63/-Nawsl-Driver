package com.nawsldriver.trips;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nawsldriver.database.AppDatabase;
import com.nawsldriver.database.TripDetails;
import com.nawsldriver.database.TripDetailsDao;

import java.util.List;

public class TripViewModel extends AndroidViewModel {
    private LiveData<List<TripDetails>> tripsLiveData;

    public TripViewModel(@NonNull Application application) {
        super(application);
        TripDetailsDao tripRequestsDao = AppDatabase.getDatabase(application).tripDetailsDao();
        tripsLiveData = tripRequestsDao.getCompletedTrips();
    }

    LiveData<List<TripDetails>> getCompletedTrips() {
        return tripsLiveData;
    }
}
