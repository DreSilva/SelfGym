package pt.selfgym.ui.workouts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import pt.selfgym.Interfaces.ActivityInterface;
import pt.selfgym.R;
import pt.selfgym.dtos.CircuitDTO;
import pt.selfgym.dtos.ExerciseDTO;
import pt.selfgym.dtos.ExerciseWODTO;
import pt.selfgym.dtos.SetsDTO;
import pt.selfgym.dtos.WorkoutDTO;

public class EditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private WorkoutDTO workout;
    private LayoutInflater layoutInflater;
    private ArrayList<RecyclerView.ViewHolder> holdersList;
    private ViewGroup parent;
    private ActivityInterface activityInterface;
    private Context context;

    public EditAdapter(WorkoutDTO workout, Context context) {
        this.workout = workout;
        this.holdersList = new ArrayList<RecyclerView.ViewHolder>();
        this.activityInterface = (ActivityInterface) context;
        this.context = context;
    }

    public WorkoutDTO getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutDTO workout) {
        this.workout = workout;
        holdersList = new ArrayList<RecyclerView.ViewHolder>();
        notifyDataSetChanged();
    }

    class ViewHolderFixedSetsReps extends RecyclerView.ViewHolder {
        public EditText reps, weight, sets, rest;
        public TextView name;
        public ImageButton settings;

        public ViewHolderFixedSetsReps(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.reps = (EditText) itemView.findViewById(R.id.repsinput);
            this.sets = (EditText) itemView.findViewById(R.id.setsinput);
            this.weight = (EditText) itemView.findViewById(R.id.weightinput);
            this.rest = (EditText) itemView.findViewById(R.id.restinput);
            this.settings = (ImageButton) itemView.findViewById(R.id.settings);
        }
    }

    class ViewHolderFixedSetsTime extends RecyclerView.ViewHolder {
        public TextView name;
        public EditText duration, weight, sets, rest;
        public ImageButton settings;

        public ViewHolderFixedSetsTime(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name2);
            this.duration = (EditText) itemView.findViewById(R.id.durationinput2);
            this.sets = (EditText) itemView.findViewById(R.id.setsinput2);
            this.weight = (EditText) itemView.findViewById(R.id.weightinput2);
            this.rest = (EditText) itemView.findViewById(R.id.restinput2);
            this.settings = (ImageButton) itemView.findViewById(R.id.settings2);
        }
    }

    class ViewHolderVariableSetsReps extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView sets;
        ImageButton addSet, deleteSet, settings;

        public ViewHolderVariableSetsReps(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name3);
            this.sets = (RecyclerView) itemView.findViewById(R.id.setList3);
            this.addSet = (ImageButton) itemView.findViewById(R.id.addSet3);
            this.deleteSet = (ImageButton) itemView.findViewById(R.id.deleteSet3);
            this.settings = (ImageButton) itemView.findViewById(R.id.settings3);
        }
    }

    class ViewHolderVariableSetsTime extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView sets;
        ImageButton addSet, deleteSet, settings;

        public ViewHolderVariableSetsTime(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name4);
            this.sets = (RecyclerView) itemView.findViewById(R.id.setList4);
            this.addSet = (ImageButton) itemView.findViewById(R.id.addSet4);
            this.deleteSet = (ImageButton) itemView.findViewById(R.id.deleteSet4);
            this.settings = (ImageButton) itemView.findViewById(R.id.settings4);
        }
    }

    class ViewHolderCircuit extends RecyclerView.ViewHolder {
        public EditText laps, rest;
        public RecyclerView exs;

        public ViewHolderCircuit(View itemView) {
            super(itemView);
            this.laps = (EditText) itemView.findViewById(R.id.lapsinput);
            this.rest = (EditText) itemView.findViewById(R.id.restinputcircuit);
            this.exs = (RecyclerView) itemView.findViewById(R.id.exercisesCircuit);
        }
    }

    class ViewHolderSet extends RecyclerView.ViewHolder {
        public TextView set;
        public EditText weight, variable, rest;

        public ViewHolderSet(View itemView) {
            super(itemView);
            this.set = (TextView) itemView.findViewById(R.id.setSets);
            this.rest = (EditText) itemView.findViewById(R.id.restInputSets);
            this.variable = (EditText) itemView.findViewById(R.id.variableInputSets);
            this.weight = (EditText) itemView.findViewById(R.id.weightInputSets);

        }
    }

    @Override
    public int getItemCount() {
        return workout.getWorkoutComposition() != null ? workout.getWorkoutComposition().size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (workout.getWorkoutComposition().get(position) instanceof ExerciseWODTO) {
            ExerciseWODTO exerciseWODTO = (ExerciseWODTO) workout.getWorkoutComposition().get(position);
            if (exerciseWODTO.isFixedSetsReps()) {
                return 0;
            } else if (exerciseWODTO.isFixedSetsTime()) {
                return 1;
            } else if (exerciseWODTO.isVariableSetsReps()) {
                return 2;
            } else if (exerciseWODTO.isVariableSetsTime()) {
                return 3;
            }
        } else if (workout.getWorkoutComposition().get(position) instanceof CircuitDTO) {
            return 4;
        }
        return 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        this.parent = parent;
        View listItem;
        switch (viewType) {
            case 0:
                listItem = layoutInflater.inflate(R.layout.list_item_fixed_sets_reps_exercise, parent, false);
                return new ViewHolderFixedSetsReps(listItem);
            case 1:
                listItem = layoutInflater.inflate(R.layout.list_item_fixed_sets_time_exercise, parent, false);
                return new ViewHolderFixedSetsTime(listItem);
            case 2:
                listItem = layoutInflater.inflate(R.layout.list_item_variable_sets_reps_exercise, parent, false);
                return new ViewHolderVariableSetsReps(listItem);
            case 3:
                listItem = layoutInflater.inflate(R.layout.list_item_variable_sets_time_exercise, parent, false);
                return new ViewHolderVariableSetsTime(listItem);
            case 4:
                listItem = layoutInflater.inflate(R.layout.list_item_circuit, parent, false);
                return new ViewHolderCircuit(listItem);
        }
        listItem = layoutInflater.inflate(R.layout.list_item_set, parent, false);
        return new ViewHolderSet(listItem);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (workout.getWorkoutComposition().get(position) instanceof ExerciseWODTO) {
            ExerciseWODTO exerciseWODTO = (ExerciseWODTO) workout.getWorkoutComposition().get(position);
            if (exerciseWODTO.isFixedSetsReps()) {
                ViewHolderFixedSetsReps viewHolder1 = (ViewHolderFixedSetsReps) holder;
                viewHolder1.rest.setText(exerciseWODTO.getRest() + "");
                viewHolder1.weight.setText(exerciseWODTO.getWeight() + "");
                viewHolder1.reps.setText(exerciseWODTO.getReps() + "");
                viewHolder1.sets.setText(exerciseWODTO.getSets() + "");
                viewHolder1.name.setText(exerciseWODTO.getExercise().getName());
                viewHolder1.settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewExSettingsPopup(position);
                    }
                });
                holdersList.add(viewHolder1);

            } else if (exerciseWODTO.isFixedSetsTime()) {
                ViewHolderFixedSetsTime viewHolder2 = (ViewHolderFixedSetsTime) holder;
                viewHolder2.rest.setText(exerciseWODTO.getRest() + "");
                viewHolder2.weight.setText(exerciseWODTO.getWeight() + "");
                viewHolder2.duration.setText(exerciseWODTO.getDuration() + "");
                viewHolder2.sets.setText(exerciseWODTO.getSets() + "");
                viewHolder2.name.setText(exerciseWODTO.getExercise().getName());
                viewHolder2.settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewExSettingsPopup(position);
                    }
                });
                holdersList.add(viewHolder2);

            } else if (exerciseWODTO.isVariableSetsReps()) {
                ViewHolderVariableSetsReps viewHolder3 = (ViewHolderVariableSetsReps) holder;
                viewHolder3.name.setText(exerciseWODTO.getExercise().getName() + "");

                WorkoutDTO workoutSets = new WorkoutDTO();
                ArrayList<Object> exList = new ArrayList<Object>();
                for (SetsDTO e : ((ExerciseWODTO) workout.getWorkoutComposition().get(position)).getSetsList()) {
                    exList.add(e);
                }
                workoutSets.setWorkoutComposition(exList);

                EditAdapter adapter = new EditAdapter(workoutSets, context);
                viewHolder3.sets.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
                viewHolder3.sets.setAdapter(adapter);

                viewHolder3.addSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Object> newSets = new ArrayList<Object>();
                        for (RecyclerView.ViewHolder v : ((EditAdapter) Objects.requireNonNull(viewHolder3.sets.getAdapter())).holdersList) {
                            ViewHolderSet vs = (ViewHolderSet) v;
//                            try {
                            newSets.add(new SetsDTO(Integer.parseInt(vs.variable.getText().toString()), Integer.parseInt(vs.rest.getText().toString()), Double.parseDouble(vs.weight.getText().toString()), newSets.size() + 1));
//                            }
//                            catch (Exception e)
//                            {
////                                Log.w("error",e.getLocalizedMessage());
//                                newSets.add(new SetsDTO(0, 0,0.0, newSets.size() + 1));
//
//                            }
                        }

                        newSets.add(new SetsDTO(0, 0, 0, newSets.size() + 1));
                        workoutSets.setWorkoutComposition(newSets);
                        EditAdapter adapter = new EditAdapter(workoutSets , context);
                        viewHolder3.sets.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
                        viewHolder3.sets.setAdapter(adapter);
                    }
                });
                viewHolder3.deleteSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Object> newSets = new ArrayList<Object>();
                        for (RecyclerView.ViewHolder v : ((EditAdapter) viewHolder3.sets.getAdapter()).holdersList) {
                            ViewHolderSet vs = (ViewHolderSet) v;
                            newSets.add(new SetsDTO(Integer.parseInt(vs.variable.getText().toString()), Integer.parseInt(vs.rest.getText().toString()), Double.parseDouble(vs.weight.getText().toString()), newSets.size() + 1));
                        }

                        if (newSets.size() > 1) {
                            newSets.remove(newSets.size() - 1);
                            workoutSets.setWorkoutComposition(newSets);
                            EditAdapter adapter = new EditAdapter(workoutSets, context);
                            viewHolder3.sets.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
                            viewHolder3.sets.setAdapter(adapter);
                        }
                    }
                });
                viewHolder3.settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewExSettingsPopup(position);
                    }
                });

                holdersList.add(viewHolder3);

            } else if (exerciseWODTO.isVariableSetsTime()) {
                ViewHolderVariableSetsTime viewHolder4 = (ViewHolderVariableSetsTime) holder;
                viewHolder4.name.setText(exerciseWODTO.getExercise().getName() + "");

                WorkoutDTO workoutSets = new WorkoutDTO("set", "set", "set");
                ArrayList<Object> exList = new ArrayList<Object>();
                for (SetsDTO e : ((ExerciseWODTO) workout.getWorkoutComposition().get(position)).getSetsList()) {
                    exList.add(e);
                }
                workoutSets.setWorkoutComposition(exList);

                EditAdapter adapter = new EditAdapter(workoutSets, context);
                viewHolder4.sets.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
                viewHolder4.sets.setAdapter(adapter);

                viewHolder4.addSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Object> newSets = new ArrayList<Object>();
                        for (RecyclerView.ViewHolder v : ((EditAdapter) Objects.requireNonNull(viewHolder4.sets.getAdapter())).holdersList) {
                            ViewHolderSet vs = (ViewHolderSet) v;
//                            try {
                            newSets.add(new SetsDTO(Integer.parseInt(vs.variable.getText().toString()), Integer.parseInt(vs.rest.getText().toString()), Double.parseDouble(vs.weight.getText().toString()), newSets.size() + 1));
//                            }
//                            catch (Exception e)
//                            {
////                                Log.w("error",e.getLocalizedMessage());
//                                newSets.add(new SetsDTO(0, 0,0.0, newSets.size() + 1));
//
//                            }
                        }

                        newSets.add(new SetsDTO(0, 0, 0, newSets.size() + 1));
                        workoutSets.setWorkoutComposition(newSets);
                        EditAdapter adapter = new EditAdapter(workoutSets, context);
                        viewHolder4.sets.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
                        viewHolder4.sets.setAdapter(adapter);
                    }
                });
                viewHolder4.deleteSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<Object> newSets = new ArrayList<Object>();
                        for (RecyclerView.ViewHolder v : ((EditAdapter) viewHolder4.sets.getAdapter()).holdersList) {
                            ViewHolderSet vs = (ViewHolderSet) v;
                            newSets.add(new SetsDTO(Integer.parseInt(vs.variable.getText().toString()), Integer.parseInt(vs.rest.getText().toString()), Double.parseDouble(vs.weight.getText().toString()), newSets.size() + 1));
                        }

                        if (newSets.size() > 1) {
                            newSets.remove(newSets.size() - 1);
                            workoutSets.setWorkoutComposition(newSets);
                            EditAdapter adapter = new EditAdapter(workoutSets, context);
                            viewHolder4.sets.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
                            viewHolder4.sets.setAdapter(adapter);
                        }
                    }
                });
                viewHolder4.settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewExSettingsPopup(position);
                    }
                });
                holdersList.add(viewHolder4);
            }
        } else if (workout.getWorkoutComposition().get(position) instanceof CircuitDTO) {
            CircuitDTO circuitDTO = (CircuitDTO) workout.getWorkoutComposition().get(position);
            ViewHolderCircuit viewHolder5 = (ViewHolderCircuit) holder;
            viewHolder5.laps.setText(circuitDTO.getLaps() + "");
            viewHolder5.rest.setText(circuitDTO.getRest() + "");

            WorkoutDTO workoutCircuit = new WorkoutDTO("circuit", "circuit", "circuit");
            ArrayList<Object> exList = new ArrayList<Object>();
            for (ExerciseWODTO e : ((CircuitDTO) workout.getWorkoutComposition().get(position)).getExerciseList()) {
                exList.add(e);
            }
            workoutCircuit.setWorkoutComposition(exList);

            EditAdapter adapter = new EditAdapter(workoutCircuit, context);
            viewHolder5.exs.setLayoutManager(new LinearLayoutManager(layoutInflater.getContext()));
            viewHolder5.exs.setAdapter(adapter);
            holdersList.add(viewHolder5);

        } else {
            ViewHolderSet viewHolder6 = (ViewHolderSet) holder;
            viewHolder6.weight.setText(((SetsDTO) workout.getWorkoutComposition().get(position)).getWeight() + "");
            viewHolder6.set.setText(((SetsDTO) workout.getWorkoutComposition().get(position)).getOrder_set() + "");
            viewHolder6.rest.setText(((SetsDTO) workout.getWorkoutComposition().get(position)).getRest() + "");
            viewHolder6.variable.setText(((SetsDTO) workout.getWorkoutComposition().get(position)).getVariable() + "");
            holdersList.add(viewHolder6);
        }
    }

    public WorkoutDTO saveWorkoutChanges(WorkoutDTO workoutDTO) {
        ArrayList<Object> composition = workout.getWorkoutComposition();
        WorkoutDTO newWorkout = new WorkoutDTO(workout.getName(), workout.getObservation(), workout.getType());
//        newWorkout.setId(workoutDTO.getId());
        for (int i = 0; i < holdersList.size(); i++) {
            if (holdersList.get(i) instanceof ViewHolderFixedSetsReps) {
                int order = ((ExerciseWODTO) composition.get(i)).getOrder();
                double weight = Double.parseDouble(((ViewHolderFixedSetsReps) holdersList.get(i)).weight.getText().toString());
                int sets = Integer.parseInt(((ViewHolderFixedSetsReps) holdersList.get(i)).sets.getText().toString());
                int reps = Integer.parseInt(((ViewHolderFixedSetsReps) holdersList.get(i)).reps.getText().toString());
                int rest = Integer.parseInt(((ViewHolderFixedSetsReps) holdersList.get(i)).rest.getText().toString());
                ExerciseDTO exercise = ((ExerciseWODTO) composition.get(i)).getExercise();
                ExerciseWODTO ex = new ExerciseWODTO(order, weight, sets, reps, rest, exercise);
//                ex.setId(((ExerciseWODTO) workoutDTO.getWorkoutComposition().get(i)).getId());
                newWorkout.addToWorkoutComposition(ex);

            } else if (holdersList.get(i) instanceof ViewHolderFixedSetsTime) {
                int order = ((ExerciseWODTO) composition.get(i)).getOrder();
                double weight = Double.parseDouble(((ViewHolderFixedSetsTime) holdersList.get(i)).weight.getText().toString());
                int sets = Integer.parseInt(((ViewHolderFixedSetsTime) holdersList.get(i)).sets.getText().toString());
                int rest = Integer.parseInt(((ViewHolderFixedSetsTime) holdersList.get(i)).rest.getText().toString());
                ExerciseDTO exercise = ((ExerciseWODTO) composition.get(i)).getExercise();
                int duration = Integer.parseInt(((ViewHolderFixedSetsTime) holdersList.get(i)).duration.getText().toString());
                ExerciseWODTO ex = new ExerciseWODTO(order, weight, sets, rest, exercise, duration);
//                ex.setId(((ExerciseWODTO) workoutDTO.getWorkoutComposition().get(i)).getId());
                newWorkout.addToWorkoutComposition(ex);

            } else if (holdersList.get(i) instanceof ViewHolderVariableSetsReps) {
                int order = ((ExerciseWODTO) composition.get(i)).getOrder();
                int reps = ((ExerciseWODTO) composition.get(i)).getReps();
                ExerciseDTO exercise = ((ExerciseWODTO) composition.get(i)).getExercise();
                EditAdapter setsAdapter = (EditAdapter) ((ViewHolderVariableSetsReps) holdersList.get(i)).sets.getAdapter();
                WorkoutDTO setsWorkout = setsAdapter.saveWorkoutChanges(workoutDTO);
                ArrayList<SetsDTO> setsList = new ArrayList<SetsDTO>();
                for (Object o : setsWorkout.getWorkoutComposition()) {
                    SetsDTO s = (SetsDTO) o;
//                    for(SetsDTO setsDTO : ((ExerciseWODTO) workoutDTO.getWorkoutComposition().get(i)).getSetsList())
//                    {
//                        if(setsDTO.getOrder_set() == s.getOrder_set())
//                            s.setId(setsDTO.getId());
//                    }
                    setsList.add(s);
                }
                ExerciseWODTO ex = new ExerciseWODTO(order, reps, exercise, setsList);
//                ex.setId(((ExerciseWODTO) workoutDTO.getWorkoutComposition().get(i)).getId());
                newWorkout.addToWorkoutComposition(ex);

            } else if (holdersList.get(i) instanceof ViewHolderVariableSetsTime) {
                int order = ((ExerciseWODTO) composition.get(i)).getOrder();
                int duration = ((ExerciseWODTO) composition.get(i)).getDuration();
                ExerciseDTO exercise = ((ExerciseWODTO) composition.get(i)).getExercise();
                EditAdapter setsAdapter = (EditAdapter) ((ViewHolderVariableSetsTime) holdersList.get(i)).sets.getAdapter();
                WorkoutDTO setsWorkout = setsAdapter.saveWorkoutChanges(workoutDTO);
                ArrayList<SetsDTO> setsList = new ArrayList<SetsDTO>();
                for (Object o : setsWorkout.getWorkoutComposition()) {
                    SetsDTO s = (SetsDTO) o;
//                    for(SetsDTO setsDTO : ((ExerciseWODTO) workoutDTO.getWorkoutComposition().get(i)).getSetsList())
//                    {
//                        if(setsDTO.getOrder_set() == s.getOrder_set())
//                            s.setId(setsDTO.getId());
//                    }
                    setsList.add(s);
                }
                ExerciseWODTO ex = new ExerciseWODTO(order, exercise, duration, setsList);
//                ex.setId(((ExerciseWODTO) workoutDTO.getWorkoutComposition().get(i)).getId());
                newWorkout.addToWorkoutComposition(ex);

            } else if (holdersList.get(i) instanceof ViewHolderCircuit) {
                int rest = Integer.parseInt(((ViewHolderCircuit) holdersList.get(i)).rest.getText().toString());
                int laps = Integer.parseInt(((ViewHolderCircuit) holdersList.get(i)).laps.getText().toString());
                EditAdapter exsAdapter = (EditAdapter) ((ViewHolderCircuit) holdersList.get(i)).exs.getAdapter();
                WorkoutDTO exsWorkout = exsAdapter.saveWorkoutChanges(workoutDTO);
                ArrayList<ExerciseWODTO> exerciseList = new ArrayList<ExerciseWODTO>();
                for (Object o : exsWorkout.getWorkoutComposition()) {
                    ExerciseWODTO e = (ExerciseWODTO) o;
                    exerciseList.add(e);
                }
                CircuitDTO ex = new CircuitDTO(laps, rest, exerciseList);
                newWorkout.addToWorkoutComposition(ex);

            } else {
                int order = ((SetsDTO) composition.get(i)).getOrder_set();
                double weight = Double.parseDouble(((ViewHolderSet) holdersList.get(i)).weight.getText().toString());
                int rest = Integer.parseInt(((ViewHolderSet) holdersList.get(i)).rest.getText().toString());
                int variable = Integer.parseInt(((ViewHolderSet) holdersList.get(i)).variable.getText().toString());
                SetsDTO set = new SetsDTO(variable, rest, weight, order);
                newWorkout.addToWorkoutComposition(set);
            }
        }
        return newWorkout;
    }

    public void NewExSettingsPopup(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityInterface.getMainActivity());
        final View NewExSettingsPopUp = layoutInflater.inflate(R.layout.popup_edit_exercises, null);

        Button deleteExercise = (Button) NewExSettingsPopUp.findViewById(R.id.deleteButton);
        Button cancel = (Button) NewExSettingsPopUp.findViewById(R.id.cancelButton);
        Button saveChanges = (Button) NewExSettingsPopUp.findViewById(R.id.saveExChanges);

        Spinner RepsTime = (Spinner) NewExSettingsPopUp.findViewById(R.id.RepsTimeSpinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(activityInterface.getMainActivity(), android.R.layout.simple_spinner_item, Arrays.asList("Reps", "Time"));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RepsTime.setAdapter(typeAdapter);
        if (((ExerciseWODTO) workout.getWorkoutComposition().get(position)).isFixedSetsReps() || ((ExerciseWODTO) workout.getWorkoutComposition().get(position)).isVariableSetsReps()) {
            RepsTime.setSelection(0);
        } else {
            RepsTime.setSelection(1);
        }

        Spinner FixedVariable = (Spinner) NewExSettingsPopUp.findViewById(R.id.FixedVariableSpinner);
        ArrayAdapter<String> typeAdapter2 = new ArrayAdapter<>(activityInterface.getMainActivity(), android.R.layout.simple_spinner_item, Arrays.asList("Fixed", "Variable"));
        typeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FixedVariable.setAdapter(typeAdapter2);
        if (((ExerciseWODTO) workout.getWorkoutComposition().get(position)).isFixedSetsReps() || ((ExerciseWODTO) workout.getWorkoutComposition().get(position)).isFixedSetsTime()) {
            FixedVariable.setSelection(0);
        } else {
            FixedVariable.setSelection(1);
        }

        dialogBuilder.setView(NewExSettingsPopUp);
        Dialog dialog = dialogBuilder.create();
        dialog.show();

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDTO newWorkout = workout;
                ExerciseWODTO composition = (ExerciseWODTO) workout.getWorkoutComposition().get(position);

                if ( RepsTime.getSelectedItem().toString() == "Reps" && FixedVariable.getSelectedItem() == "Fixed") {
                    newWorkout.getWorkoutComposition().set(position, new ExerciseWODTO( composition.getOrder(), 0.0, 0, 1, 0, composition.getExercise()));
                } else if ( RepsTime.getSelectedItem().toString() == "Time" && FixedVariable.getSelectedItem() == "Fixed") {
                    newWorkout.getWorkoutComposition().set(position, new ExerciseWODTO(composition.getOrder(), 0.0, 0, 0, composition.getExercise(),1));
                } else if ( RepsTime.getSelectedItem().toString() == "Reps" && FixedVariable.getSelectedItem() == "Variable") {
                    newWorkout.getWorkoutComposition().set(position, new ExerciseWODTO( composition.getOrder() ,1, composition.getExercise(),  new ArrayList<SetsDTO>()));
                } else {
                    newWorkout.getWorkoutComposition().set(position, new ExerciseWODTO( composition.getOrder() , composition.getExercise(), 1,  new ArrayList<SetsDTO>()));
                }
                setWorkout(newWorkout);
                dialog.dismiss();
            }
        });

        deleteExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workout.getWorkoutComposition().remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

