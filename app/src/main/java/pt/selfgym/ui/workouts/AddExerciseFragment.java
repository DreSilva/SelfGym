package pt.selfgym.ui.workouts;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import pt.selfgym.Interfaces.ActivityInterface;
import pt.selfgym.Interfaces.WorkoutsInterface;
import pt.selfgym.R;
import pt.selfgym.SharedViewModel;
import pt.selfgym.dtos.ExerciseDTO;

public class AddExerciseFragment extends Fragment implements WorkoutsInterface {

    private SharedViewModel mViewModel;
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
        setHasOptionsMenu(true);

        //TODO: get exercise list
        ArrayList<ExerciseDTO> exerciseList = new ArrayList<ExerciseDTO>();
        exerciseList.add(new ExerciseDTO("exercise1","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","full body"));
        exerciseList.add(new ExerciseDTO("exercise2","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","full body"));
        exerciseList.add(new ExerciseDTO("exercise3","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","upper body"));
        exerciseList.add(new ExerciseDTO("exercise4","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","upper body"));
        exerciseList.add(new ExerciseDTO("exercise5","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","upper body"));
        exerciseList.add(new ExerciseDTO("exercise6","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","lower body"));
        exerciseList.add(new ExerciseDTO("exercise7","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","lower body"));
        exerciseList.add(new ExerciseDTO("exercise8","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","push"));
        exerciseList.add(new ExerciseDTO("exercise9","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","push"));
        exerciseList.add(new ExerciseDTO("exercise10","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","pull"));
        exerciseList.add(new ExerciseDTO("exercise11","https://www.google.com/search?q=bicep+curl&rlz=1C1FCXM_pt-PTPT957PT957&sxsrf=ALiCzsbTQSTSmWDdXKdSJsZt8Qrun7ToRA:1671573299125&tbm=isch&source=iu&ictx=1&vet=1&fir=VcpWJuh3TkCk2M%252C42_Skd25L6nC7M%252C_%253BHeGNSM-f6U23eM%252CJBuNV4EtKKQBxM%252C_%253BS9QhI7mQOxvpfM%252Cj2vncYSJRz-qrM%252C_%253BOGpgdTPIlUH_lM%252C8X3fLFkuI09VMM%252C_%253BH134j2AtgTAHzM%252CgspqDti2NR4EbM%252C_%253BpTnrpVoZxW_Q6M%252CyMXkHJtHzf97YM%252C_&usg=AI4_-kRN3rMHR782fvEyHREOTzGkDuzi2Q&sa=X&ved=2ahUKEwjhgdfll4n8AhWt87sIHQpRDckQ_h16BAhTEAE#imgrc=S9QhI7mQOxvpfM","pull"));






        ArrayList<ExerciseDTO> upperBodyList = new ArrayList<ExerciseDTO>();
        ArrayList<ExerciseDTO> lowerBodyList = new ArrayList<ExerciseDTO>();
        ArrayList<ExerciseDTO> pushList = new ArrayList<ExerciseDTO>();
        ArrayList<ExerciseDTO> pullList = new ArrayList<ExerciseDTO>();

        for (ExerciseDTO e: exerciseList) {
            if (e.getType() == "upper body" || e.getType() == "push" || e.getType() =="pull") {
                upperBodyList.add(e);
            }
            if (e.getType() == "lower body") {
                lowerBodyList.add(e);
            }
            if (e.getType() == "push") {
                pushList.add(e);
            }
            if (e.getType() == "pull") {
                pullList.add(e);
            }
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewExercise);
        adapter = new PickExerciseAdapter(exerciseList, this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(inflater.getContext(),2));
        recyclerView.setAdapter(adapter);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabLayout);
        tabs.addTab(new TabLayout.Tab().setText("full body"));
        tabs.addTab(new TabLayout.Tab().setText("upper body"));
        tabs.addTab(new TabLayout.Tab().setText("lower body"));
        tabs.addTab(new TabLayout.Tab().setText("push"));
        tabs.addTab(new TabLayout.Tab().setText("pull"));
        tabs.getTabAt(0).select();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabs.getTabAt(0).isSelected()){
                    adapter.setExerciseList(exerciseList);
                } else if (tabs.getTabAt(0).isSelected()){
                    adapter.setExerciseList(upperBodyList);
                } else if (tabs.getTabAt(0).isSelected()){
                    adapter.setExerciseList(lowerBodyList);
                } else if (tabs.getTabAt(0).isSelected()){
                    adapter.setExerciseList(pushList);
                } else {
                    adapter.setExerciseList(pullList);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //do nothing
            }
        });

        return view;
    }


    @Override
    public void onItemClick(int position, View v) {

        //activityInterface.changeFrag(fr);
    }

    @Override
    public void onLongItemClick(int position, View v) {
        //do nothing
    }
}