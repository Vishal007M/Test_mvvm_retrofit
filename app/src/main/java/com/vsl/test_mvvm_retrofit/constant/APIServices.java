package com.vsl.test_mvvm_retrofit.constant;

import com.vsl.test_mvvm_retrofit.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {

    @GET("api/view")
    Call<List<Student>> getStudentData();

    @GET("api/view/{dataEntityId}")
    Call<Student> getStudentIdData(@Path("dataEntityId") int dataEntityId);

    @POST("api/add")
    Call<Student> addStudentData(@Body Student add);

    @PATCH("api/reset/{dataEntityId}")
    Call<Student> patchStudentData(@Path("dataEntityId") int dataEntityId, @Body Student Data);

    @DELETE("api/delete/{dataEntityId}")
    Call<Student> deleteStudentData(@Path("dataEntityId") int dataEntityId);


}
