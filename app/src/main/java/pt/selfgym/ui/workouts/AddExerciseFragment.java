package pt.selfgym.ui.workouts;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import pt.selfgym.Interfaces.ActivityInterface;
import pt.selfgym.Interfaces.ButtonsInterface;
import pt.selfgym.Interfaces.WorkoutsInterface;
import pt.selfgym.R;
import pt.selfgym.SharedViewModel;
import pt.selfgym.dtos.ExerciseDTO;

public class AddExerciseFragment extends Fragment implements ButtonsInterface {

    private SharedViewModel mViewModel;
    private WorkoutViewModel workoutViewModel;
    private PickExerciseAdapter adapter;
    private ActivityInterface activityInterface;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private int id;

    public AddExerciseFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityInterface = (ActivityInterface) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_exercise_fragment, container, false);

        this.workoutViewModel = new ViewModelProvider(activityInterface.getMainActivity()).get(WorkoutViewModel.class);
        this.mViewModel = new ViewModelProvider(activityInterface.getMainActivity()).get(SharedViewModel.class);



        mViewModel.getExercises().observe(getViewLifecycleOwner(), exerciseDTOS -> {

            ArrayList<ExerciseDTO> upperBodyList = new ArrayList<ExerciseDTO>();
            ArrayList<ExerciseDTO> lowerBodyList = new ArrayList<ExerciseDTO>();
            ArrayList<ExerciseDTO> pushList = new ArrayList<ExerciseDTO>();
            ArrayList<ExerciseDTO> pullList = new ArrayList<ExerciseDTO>();

            for (ExerciseDTO e : exerciseDTOS) {
                if (e.getType().equals("upper body") || e.getType().equals("push") || e.getType().equals("pull")) {
                    upperBodyList.add(e);
                }
                if (e.getType().equals("lower body")) {
                    lowerBodyList.add(e);
                }
                if (e.getType().equals("push")) {

                    pushList.add(e);
                }
                if (e.getType().equals("pull")) {
                    pullList.add(e);
                }
            }

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewExercise);
            adapter = new PickExerciseAdapter(exerciseDTOS, this);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new GridLayoutManager(inflater.getContext(), 2));
            recyclerView.setAdapter(adapter);


            TabLayout tabs = (TabLayout) view.findViewById(R.id.tabLayout);

            for (int i = 0; i < 5; i++) {
                TabLayout.Tab tab = tabs.newTab();
                if (tab != null) {
                    TextView customView = new TextView(getContext());
                    customView.setTextAppearance(R.style.MyTabTextAppearance);
                    if (i == 0) {
                        customView.setText("full body");
                    } else if (i == 1) {
                        customView.setText("upper body");
                    } else if (i == 2) {
                        customView.setText("lower body");
                    } else if (i == 3) {
                        customView.setText("push");
                    } else if (i == 4) {
                        customView.setText("pull");
                    }
                    customView.setGravity(Gravity.CENTER);
                    tab.setCustomView(customView);
                    tabs.addTab(tab);
                }
            }

            tabs.getTabAt(0).select();
            View customView = tabs.getTabAt(0).getCustomView();
            ((TextView) customView).setTextColor(Color.parseColor("#9B30FF"));

            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tabs.getTabAt(0).isSelected()) {
                        adapter.setExerciseList(exerciseDTOS);
                    } else if (tabs.getTabAt(1).isSelected()) {
                        adapter.setExerciseList(upperBodyList);
                    } else if (tabs.getTabAt(2).isSelected()) {
                        adapter.setExerciseList(lowerBodyList);
                    } else if (tabs.getTabAt(3).isSelected()) {
                        adapter.setExerciseList(pushList);
                    } else {
                        adapter.setExerciseList(pullList);
                    }

                    View customView = tab.getCustomView();
                    ((TextView) customView).setTextColor(Color.parseColor("#9B30FF"));


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View customView = tab.getCustomView();
                    ((TextView) customView).setTextColor(Color.parseColor("#9E9E9E"));
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    //do nothing
                }
            });
        });
        return view;
    }


    @Override
    public void onItemClick(int position, View v) {
        ExerciseDTO exerciseDTO = adapter.getExerciseList().get(position);
        workoutViewModel.addExercisetoWorkout(exerciseDTO);
        activityInterface.getMainActivity().changeFrag(new EditWorkoutFragment(),null);
//        NavBackStackEntry backStackEntry = navController.getPreviousBackStackEntry();
//        EditWorkoutFragment previousFragment = (EditWorkoutFragment) navController.findDestination(backStackEntry.getId());
//        previousFragment.setExerciseToBeAdded(name);

//        navController.navigateUp();
    }

    @Override
    public void onLongItemClick(int position, View v) {
        //do nothing
    }

}
