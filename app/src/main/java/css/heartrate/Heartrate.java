package css.heartrate;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.sql.Date;

@Entity
public class Heartrate {
    public Integer pulse;       // actual rate in beats per minute
    public Integer age;            // age when heart rate measurement was taken
    public Double maxHeartRate;    // calculated maximum rate based on age
    public Double percent;         // percent of maximum rate and a precent
    //public Date date;
    public Integer range;          // which range this heart rate is in, used as index into arrays below

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    // ---- The following values don't need to be stored in the database, so the SQL to ignore them
    // TODO: These should really be stored somewhere else, like an XML or JSON file such as /res/values/strings.xml
    @Ignore
    final String rangeNames[] = {"Resting", "Moderate", "Endurance", "Aerobic","Anaerobic","Red zone"};
    @Ignore
    final String rangeDescriptions[] = {"In active or resting", "Weight maintenance and warm up", "Fitness and fat burning", "Cardio training and endurance","Hardcore interval training","Maximum Effort"};
    @Ignore
    final Double rangeBounds[] = {.50, .60, .70, .80, .90, 1.00};  // These must be in ascending order


    public Heartrate(Integer pulse, Integer age) {
        this.pulse = pulse;
        this.age = age;
        calcHeartRange();
    }

    /**
     *  Calculate the maximum heartrate and precent of max using the CDC guidelines
     *  Uses the age and pulse to do this calculation
     *  @return range -- the range index (usually 0 - 5) used for the index into the arrays above
     */
    public Integer calcHeartRange() {
        maxHeartRate = 220.0 - age;        // from  http://www.cdc.gov/physicalactivity/basics/measuring/heartrate.htm
        percent = pulse / maxHeartRate;
        for (int i=0; i<rangeNames.length; i++) {
            if ( percent < rangeBounds[i] ) {
                // heartrate is in this range
                range = i;
                return range;          // break out of this loop
            }
        }
        return rangeNames.length-1;                      // this should never happen
    }

    /**
     * @return the name for this range such as Aerobic
     */
    public String getRangeName() {
        calcHeartRange();
        return rangeNames[range];
    }

    /**
     * @return the longer description for this range such as "Fitness and fat burning"
     */
    public String getRangeDescrtiption() {
        calcHeartRange();
        return rangeDescriptions[range];
    }

    /**
     * @return the heartrate as a descriptive string that can be displayed
     */
    public String toString() {
        return "Pulse of " + pulse + " - Cardio level: " + getRangeName();
    }


}