package com.pato.gadsleaderboard;

import com.pato.gadsleaderboard.model.LearnerHours;
import com.pato.gadsleaderboard.model.LearnerSkillIq;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GadsApiInterface {
    // contains methods we call on GadsApi. e.g list hours , list skill iq.

    // return a List of Learner Hours.
    @GET("api/hours")
    Call<List<LearnerHours>> getLeanerHours();

    // get learner skill_iq returns a List of Learner Skill_IQ.
    @GET("api/skilliq")
    Call<List<LearnerSkillIq>> getSkillIq();

}
