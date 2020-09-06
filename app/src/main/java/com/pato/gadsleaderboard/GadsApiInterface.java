package com.pato.gadsleaderboard;

import com.pato.gadsleaderboard.model.LearnerHours;
import com.pato.gadsleaderboard.model.LearnerSkillIq;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface GadsApiInterface {
    // contains methods we call on GadsApi. e.g list hours , list skill iq.

    // return a List of Learner Hours.
    @GET("api/hours")
    Call<List<LearnerHours>> getLeanerHours();

    // get learner skill_iq returns a List of Learner Skill_IQ.
    @GET("api/skilliq")
    Call<List<LearnerSkillIq>> getSkillIq();

    // Post project to google form => required to replace entire BASE_URL.
    @FormUrlEncoded
    @POST()
    Call<Void> postProject(
            @Url String dynamic_URL,
            @Field("entry.1824927963") String email,
            @Field("entry.1877115667") String fName,
            @Field("entry.2006916086") String lName,
            @Field("entry.284483984") String gitRepoUrl

    );

}
