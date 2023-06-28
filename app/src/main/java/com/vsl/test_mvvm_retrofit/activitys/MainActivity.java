package com.vsl.test_mvvm_retrofit.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vsl.test_mvvm_retrofit.adapters.MyAdapter;
import com.vsl.test_mvvm_retrofit.constant.AndroidExtension;
import com.vsl.test_mvvm_retrofit.constant.Constant;
import com.vsl.test_mvvm_retrofit.constant.Resource;
import com.vsl.test_mvvm_retrofit.databinding.ActivityMainBinding;
import com.vsl.test_mvvm_retrofit.model.Student;
import com.vsl.test_mvvm_retrofit.viewmodels.StudentViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Student> studentList;
    StudentViewModel listViewModel;
    MyAdapter adapter;
    String addId, addCompName, addCompEmail, addEmail, addPass, addPhone;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //RECYCLER VIEW
        binding.recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MyAdapter(studentList);
        binding.recView.setAdapter(adapter);

        if (!AndroidExtension.isOnline(MainActivity.this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }

        listViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        //Get---------------------------------------------------------------------------------------------------------------------------------------------
        binding.GetData.setOnClickListener(v -> {
            listViewModel.getStudentListObserver().observe(this, finalData -> {
                switch (finalData.status) {
                    case SUCCESS:
                        loadDetail(finalData.data);
                        break;
                    case LOADING:
//                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
                        break;
                    case ERROR:
                        Toast.makeText(this, Constant.SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
                        break;
                }
            });

            listViewModel.makeApiCall();
        });

        //Get{id}---------------------------------------------------------------------------------------------------------------------------------------------
        binding.GetIdDate.setOnClickListener(v -> {
            StringDataChecking();
            if (addId.isEmpty()) {
                binding.id.setError("Enter id first");
                binding.id.requestFocus();
            } else {
                listViewModel.GetStudentIdObserver().observe(this, new Observer<Resource<Student>>() {
                    @Override
                    public void onChanged(Resource<Student> studentResource) {
                        if (studentResource.data != null) {
                            binding.companyName.setText(studentResource.data.getCompany_name());
                            binding.companyEmail.setText(studentResource.data.getCompany_email());
                            binding.email.setText(studentResource.data.getEmail());
                            binding.password.setText(studentResource.data.getPassword());
                            binding.phone.setText(studentResource.data.getPhone());
//                            Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, Constant.SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                listViewModel.makeApiCallById(Integer.parseInt(addId));
            }

        });

        //Post---------------------------------------------------------------------------------------------------------------------------------------------
        binding.PostDate.setOnClickListener(v -> {
            StringDataChecking();

            if (addId.isEmpty()) {
                binding.id.setError("Enter Id!");
                binding.id.requestFocus();
            } else if (addCompName.isEmpty()) {
                binding.companyName.setError("Enter Company Name!");
                binding.companyName.requestFocus();
            } else if (addCompEmail.isEmpty()) {
                binding.companyEmail.setError("Enter Company Email!");
                binding.companyEmail.requestFocus();
            } else if (addEmail.isEmpty()) {
                binding.email.setError("Enter email!");
                binding.email.requestFocus();
            } else if (addPass.isEmpty()) {
                binding.password.setError("Enter password!");
                binding.password.requestFocus();
            } else if (addPhone.isEmpty()) {
                binding.phone.setError("Enter phone!");
                binding.phone.requestFocus();
            } else {
                Student student = new Student(Integer.parseInt(addId), addCompName, addCompEmail, addEmail, addPass, addPhone);
                listViewModel.postStudentListObserver().observe(this, finalData -> {
                    switch (finalData.status) {
                        case SUCCESS:
                            Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
                            break;
                        case LOADING:
//                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            Toast.makeText(this, Constant.SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
                listViewModel.makeApiAdd(student);
            }
        });

        //Patch---------------------------------------------------------------------------------------------------------------------------------------------
        binding.PatchData.setOnClickListener(v -> {
            StringDataChecking();
            if (addCompName.isEmpty()) {
                addCompName = null;
            }
            if (addCompEmail.isEmpty()) {
                addCompEmail = null;
            }
            if (addEmail.isEmpty()) {
                addEmail = null;
            }
            if (addPass.isEmpty()) {
                addPass = null;
            }
            if (addPhone.isEmpty()) {
                addPhone = null;
            }
            if (addId.isEmpty()) {
                binding.id.setError("Enter id first");
            } else {
                Student student = new Student(Integer.parseInt(addId), addCompName, addCompEmail, addEmail, addPass, addPhone);
                listViewModel.patchStudentListObserver().observe(this, new Observer<Resource<Student>>() {
                    @Override
                    public void onChanged(Resource<Student> studentResource) {
                        if (studentResource != null) {
                            Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, Constant.SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                listViewModel.makeApiPatch(Integer.parseInt(addId), student);
            }
        });


        binding.DeleteData.setOnClickListener(v -> {
            StringDataChecking();

            if (addId.isEmpty()) {
                binding.id.setError("Enter id first");
            } else {
                listViewModel.deleteStudentListObserver().observe(this, new Observer<Resource<Student>>() {
                    @Override
                    public void onChanged(Resource<Student> studentResource) {
                        if (studentResource != null) {
                            Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, Constant.SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                listViewModel.makeApiDelete(Integer.parseInt(addId));
            }
        });

    }

    private void loadDetail(List<Student> data) {
        studentList = data;
        adapter.updateStudentList(data);
        binding.noData.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, Constant.SUCCESS, Toast.LENGTH_SHORT).show();
    }

    private void StringDataChecking() {
        addId = binding.id.getText().toString();
        addCompName = binding.companyName.getText().toString();
        addCompEmail = binding.companyEmail.getText().toString();
        addEmail = binding.email.getText().toString();
        addPass = binding.password.getText().toString();
        addPhone = binding.phone.getText().toString();
    }
}


//        https://www.youtube.com/playlist?list=PLirRGafa75rSMDp5bORq_eHjMLKqJ2EYO
//        Grid View
//        https://www.youtube.com/watch?v=EovaoepBQQ8
//        POST Method
//        https://www.youtube.com/watch?v=ytHBE179sZs