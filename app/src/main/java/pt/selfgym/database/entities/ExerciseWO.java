package pt.selfgym.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "exerciseswo", foreignKeys = {@ForeignKey(entity = Exercise.class,
        parentColumns = "exerciseId",
        childColumns = "exercise_id",
        onDelete = CASCADE
), @ForeignKey(entity = Workout.class,
        parentColumns = "workoutId",
        childColumns = "workoutexwo_id",
        onDelete = CASCADE
), @ForeignKey(entity = Circuit.class,
        parentColumns = "circuitId",
        childColumns = "circuitexwo_id",
        onDelete = CASCADE
)})
public class ExerciseWO {

    @PrimaryKey(autoGenerate = true)
    public Long exerciseWOId;

    //FK of exercises
    @ColumnInfo(name = "exercise_id")
    @NonNull
    public Long exercise_id;

    //FK of workouts
    @ColumnInfo(name = "workoutexwo_id")
    @NonNull
    public Long workoutexwo_id;

    //FK of circuit if it belongs (it can be null)
    @ColumnInfo(name = "circuitexwo_id")
    public Long circuitexwo_id;

    @ColumnInfo(name = "order_exwo")
    @NonNull
    public Long order_exwo;

}