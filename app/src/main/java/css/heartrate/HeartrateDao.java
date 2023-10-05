package css.heartrate;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HeartrateDao {
    @Insert
    void insert(Heartrate heartrate);

    @Delete
    void delete(Heartrate heartrate);

    @Query("SELECT * FROM Heartrate")
    LiveData<List<Heartrate>> getAll();
}
