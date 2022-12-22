package pt.selfgym.ui.workouts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import pt.selfgym.database.AppDatabase;
import pt.selfgym.dtos.CircuitDTO;
import pt.selfgym.dtos.ExerciseDTO;
import pt.selfgym.dtos.ExerciseWODTO;
import pt.selfgym.dtos.WorkoutDTO;

public class WorkoutViewModel extends AndroidViewModel {

    private AppDatabase mDb;
    private final MutableLiveData<WorkoutDTO> workout = new MutableLiveData<WorkoutDTO>();
    private final MutableLiveData<List<CircuitDTO>> circuits = new MutableLiveData<List<CircuitDTO>>();
    private final MutableLiveData<List<ExerciseWODTO>> exerciseswo = new MutableLiveData<List<ExerciseWODTO>>();

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
    }

    public WorkoutDTO getWorkout() {
        return workout.getValue();
    }

    public void setWorkout(WorkoutDTO workoutDTO) {
        workout.setValue(workoutDTO);
    }
    public List<CircuitDTO> getCircuits() {
        return circuits.getValue();
    }

    public List<ExerciseWODTO> getExerciseswo() {
        return exerciseswo.getValue();
    }

    public void newWorkout() {
        this.workout.setValue(new WorkoutDTO("", "", "full body"));
        this.exerciseswo.setValue(new ArrayList<ExerciseWODTO>());
        this.circuits.setValue(new ArrayList<CircuitDTO>());
    }

    public void updateworkoutparams(String name, String type, String obs) {
        this.workout.getValue().setName(name);
        this.workout.getValue().setType(type);
        this.workout.getValue().setObservation(obs);
    }

    public void addExercisetoWorkout(ExerciseDTO exerciseDTO) {
        List<ExerciseWODTO> exercisewoDTOList = exerciseswo.getValue();
        ExerciseWODTO exerciseWODTO;
        if (exercisewoDTOList == null) {
            exercisewoDTOList = new ArrayList<ExerciseWODTO>();
            exerciseWODTO = new ExerciseWODTO(1, 0, 0, 0, 0, exerciseDTO);
            exercisewoDTOList.add(exerciseWODTO);
            exerciseswo.setValue(exercisewoDTOList);

        } else {
            AtomicBoolean trigger = new AtomicBoolean(false);
            exercisewoDTOList.forEach(exwo -> {
                if (exwo.getExercise().getId() == exerciseDTO.getId())
                    trigger.set(true);
            });
            if (!trigger.get()) {
                exerciseWODTO = new ExerciseWODTO(exerciseswo.getValue().size() + 1, 0, 0, 0, 0, exerciseDTO);
                exercisewoDTOList.add(exerciseWODTO);
                workout.getValue().addToWorkoutComposition(exerciseWODTO);
                exerciseswo.setValue(exercisewoDTOList);
            }
        }
//        mDb = AppDatabase.getInstance(getApplication().getApplicationContext());
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                //how to get all workouts
//                Mapper mapper = new Mapper();
//                ExerciseDTO exerciseDTO = mapper.toDTO(mDb.DAO().getExercisebyName(exercisename),ExerciseDTO.class);
//
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<ExerciseDTO> exerciseDTOList = exercisestempofwo.getValue();
//                        if (exerciseDTOList == null) {
//                            exerciseDTOList = new ArrayList<ExerciseDTO>();
//                        }
//                        exerciseDTOList.add(exerciseDTO);
//                        exercisestempofwo.setValue(exerciseDTOList);
//                    }
//                });
//            }
//        });
    }


}
