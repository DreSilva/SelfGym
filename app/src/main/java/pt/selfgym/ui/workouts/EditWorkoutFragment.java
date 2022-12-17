package pt.selfgym.ui.workouts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import pt.selfgym.Interfaces.ActivityInterface;
import pt.selfgym.R;
import pt.selfgym.SharedViewModel;
import pt.selfgym.dtos.CircuitDTO;
import pt.selfgym.dtos.ExerciseDTO;
import pt.selfgym.dtos.ExerciseWODTO;
import pt.selfgym.dtos.SetsDTO;
import pt.selfgym.dtos.WorkoutDTO;


public class EditWorkoutFragment extends Fragment {

    private ActivityInterface activityInterface;
    private SharedViewModel mViewModel;
    private EditAdapter adapter;
    private EditText editTextNote, observations;
    private View view;
    private int id;


    public EditWorkoutFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityInterface = (ActivityInterface) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.edit_workout_fragment, container, false);
        setHasOptionsMenu(true);


        this.mViewModel = new ViewModelProvider(activityInterface.getMainActivity()).get(SharedViewModel.class);

        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }

        //TODO: get workout by id
        //String note = mViewModel.getNoteContentById(id);

        WorkoutDTO workout = new WorkoutDTO(1, "olá" , "hell", "full body");
        ExerciseWODTO exercise1 = new ExerciseWODTO(1,1,0.0,1,1,0,new ExerciseDTO(1,"hell","sodqodmpaod", "sidno"));
        ExerciseWODTO exercise2 = new ExerciseWODTO(2,2,0.0,1,0,new ExerciseDTO(1,"hell","sodqodmpaod", "sidno"),1);
        ExerciseWODTO exercise3 = new ExerciseWODTO(3,3,0.0,1,0, new ExerciseDTO(1,"hell","sodqodmpaod", "sidno"), new ArrayList<SetsDTO>());
        ExerciseWODTO exercise4 = new ExerciseWODTO(4,4,0.0 ,0, new ExerciseDTO(1,"hell","sodqodmpaod", "sidno"), 1, new ArrayList<SetsDTO>());
        CircuitDTO circuit = new CircuitDTO(5,5,0, new ArrayList<ExerciseWODTO>());
        ArrayList<Object> workoutComposition = new ArrayList<Object>();
        workoutComposition.add(exercise1);
        workoutComposition.add(exercise2);
        workoutComposition.add(exercise3);
        workoutComposition.add(exercise4);
        workoutComposition.add(circuit);
        workout.setWorkoutComposition(workoutComposition);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.exercises);
        adapter = new EditAdapter(workout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(adapter);

        observations = (EditText) view.findViewById(R.id.textAreaObservations);
        observations.setText(workout.getObservation());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    activityInterface.changeFrag(new WorkoutFragment());
                    return true;
                }
                return false;
            }
        } );

        BottomNavigationView navBar = (BottomNavigationView) activityInterface.getMainActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_workout_menu, menu);
        MenuItem saveItem = menu.findItem(R.id.savemenu);
        MenuItem exitItem = menu.findItem(R.id.closemenu);
        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                //TODO: Save workout
                activityInterface.changeFrag(new WorkoutFragment());
                return false;
            }
        });
        exitItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                activityInterface.changeFrag(new WorkoutFragment());
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

}