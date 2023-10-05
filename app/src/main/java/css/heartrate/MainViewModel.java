package css.heartrate;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private HeartrateRepository heartrateRepository;

    private LiveData<List<Heartrate>> heartrateList;

    public MainViewModel (Application application) {
        super(application);
        heartrateRepository = new HeartrateRepository(application);
        heartrateList = heartrateRepository.getAll();
    }

    public void insert(Integer heartrate, Integer age) {
        Heartrate hr = new Heartrate(heartrate, age);
        heartrateRepository.insert(hr);
    }

    public LiveData<List<Heartrate>> getAllHeartrates () {
        heartrateList = heartrateRepository.getAll();
        return heartrateList;
    }
}
