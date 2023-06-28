package com.vsl.test_mvvm_retrofit.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vsl.test_mvvm_retrofit.activitys.MainActivity;
import com.vsl.test_mvvm_retrofit.constant.APIServices;
import com.vsl.test_mvvm_retrofit.constant.Constant;
import com.vsl.test_mvvm_retrofit.constant.Resource;
import com.vsl.test_mvvm_retrofit.constant.RetroInstance;
import com.vsl.test_mvvm_retrofit.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentViewModel extends ViewModel {

    private final MutableLiveData<Resource<List<Student>>> studentGetData;
    private final MutableLiveData<Resource<Student>> studentIdData;
    private final MutableLiveData<Resource<Student>> studentPostData;
    private final MutableLiveData<Resource<Student>> studentPatchData;
    private final MutableLiveData<Resource<Student>> studentDeleteData;

    public StudentViewModel() {
        studentIdData = new MutableLiveData<>();
        studentGetData = new MutableLiveData<>();
        studentPostData = new MutableLiveData<>();
        studentPatchData = new MutableLiveData<>();
        studentDeleteData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<List<Student>>> getStudentListObserver() {
        return studentGetData;
    }

    public MutableLiveData<Resource<Student>> GetStudentIdObserver() {
        return studentIdData;
    }

    public MutableLiveData<Resource<Student>> postStudentListObserver() {
        return studentPostData;
    }

    public MutableLiveData<Resource<Student>> patchStudentListObserver() {
        return studentPatchData;
    }

    public MutableLiveData<Resource<Student>> deleteStudentListObserver() {
        return studentDeleteData;
    }

    //Get---------------------------------------------------------------------------------------------------------------------------------------------
    public void makeApiCall() {

        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<List<Student>> call = apiServices.getStudentData();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {

                try {
                    if (response.body().isEmpty()) {
                        studentGetData.setValue(Resource.error(Constant.SOMETHING_WRONG, null));
                    } else {
                        List<Student> body = response.body();
                        studentGetData.setValue(Resource.success(body));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                studentGetData.setValue(Resource.error(t.getMessage(), null));
                Log.e("Error :", t.getMessage());
            }
        });
    }


    //Get{id}---------------------------------------------------------------------------------------------------------------------------------------------
    public void makeApiCallById(int dataEntityId) {

        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<Student> call = apiServices.getStudentIdData(dataEntityId);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                try {
                    if (response.body() == null) {
                        studentIdData.setValue(Resource.error(Constant.SOMETHING_WRONG, null));
                    } else {
                        Student body = response.body();
                        studentIdData.setValue(Resource.success(body));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                studentIdData.setValue(Resource.error(t.getMessage(), null));
                Log.e("Error :", t.getMessage());
            }
        });
    }

    //Post---------------------------------------------------------------------------------------------------------------------------------------------
    public void makeApiAdd(Student student) {

        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<Student> call = apiServices.addStudentData(student);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {

                try {
                    if (response.body() != null) {
                        Student body = response.body();
                        studentPostData.setValue(Resource.success(body));
                    } else {
                        studentPostData.setValue(Resource.error(Constant.SOMETHING_WRONG, null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                studentPostData.setValue(Resource.error(t.getMessage(), null));
                Log.e("Error :", t.getMessage().toString());
            }
        });
    }


    //Patch---------------------------------------------------------------------------------------------------------------------------------------------
    public void makeApiPatch(int dataEntityId, Student student) {

        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<Student> call = apiServices.patchStudentData(dataEntityId, student);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {

                try {
                    if (response.body() != null) {
                        Student body = response.body();
                        studentPatchData.setValue(Resource.success(body));
                    } else {
                        studentPatchData.setValue(Resource.error(Constant.SOMETHING_WRONG, null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                studentPatchData.setValue(Resource.error(t.getMessage(), null));
                Log.e("Error :", t.getMessage().toString());
            }
        });
    }


    //Delete
    public void makeApiDelete(int dataEntityId) {

        APIServices apiServices = RetroInstance.getRetroClient().create(APIServices.class);
        Call<Student> call = apiServices.deleteStudentData(dataEntityId);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {

                try {
                    if (response.body() != null) {
                        Student body = response.body();
                        studentDeleteData.setValue(Resource.success(body));
                    } else {
                        studentDeleteData.setValue(Resource.error(Constant.SOMETHING_WRONG, null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                studentDeleteData.setValue(Resource.error(t.getMessage(), null));
                Log.e("Error :", t.getMessage().toString());
            }
        });
    }
}
