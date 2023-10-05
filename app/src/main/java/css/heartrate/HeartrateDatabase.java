package css.heartrate;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Heartrate.class}, version = 1, exportSchema = false)
public abstract class HeartrateDatabase extends RoomDatabase {

    public abstract HeartrateDao heartrateDao();

    private static volatile HeartrateDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static HeartrateDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HeartrateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    HeartrateDatabase.class, "heartrate_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
