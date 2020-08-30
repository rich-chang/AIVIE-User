package com.aivie.aivie.user.presentation.therapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aivie.aivie.user.R;

import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> questionTitle = new ArrayList<>();
    ArrayList<String> questionContent = new ArrayList<>();
    ArrayList<String> answer01 = new ArrayList<>();
    ArrayList<String> answer02 = new ArrayList<>();
    ArrayList<String> answer03 = new ArrayList<>();
    ArrayList<String> answer04 = new ArrayList<>();
    Integer currentTopicIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        findViewById(R.id.buttonPrevious).setOnClickListener(this);
        findViewById(R.id.buttonNext).setOnClickListener(this);

        initQuestionnaire();
        displayTopic(currentTopicIndex);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonPrevious:
                currentTopicIndex = currentTopicIndex - 1;
                displayTopic(currentTopicIndex);
                break;
            case R.id.buttonNext:
                if (currentTopicIndex == questionTitle.size()-1) {
                    saveResult();
                } else {
                    currentTopicIndex = currentTopicIndex + 1;
                    displayTopic((currentTopicIndex));
                }
                break;
        }
    }

    void displayTopic(int index) {
        Log.i("richc", "currentTopicIndex: " + currentTopicIndex);

        if (currentTopicIndex == -1) return;

        String title = questionTitle.get(index) + " " + (currentTopicIndex + 1) + "/" + questionTitle.size();
        ((TextView)findViewById(R.id.textView_qTitle)).setText(title);
        ((TextView)findViewById(R.id.textView_qContent)).setText(questionContent.get(index));
        ((TextView)findViewById(R.id.radioButtonAnswer01)).setText(answer01.get(index));
        ((TextView)findViewById(R.id.radioButtonAnswer02)).setText(answer02.get(index));
        ((TextView)findViewById(R.id.radioButtonAnswer03)).setText(answer03.get(index));
        ((TextView)findViewById(R.id.radioButtonAnswer04)).setText(answer04.get(index));

        ((RadioButton)findViewById(R.id.radioButtonAnswer01)).setChecked(true);
        displayButton();
    }

    void displayButton() {

        // Previous button
        if (currentTopicIndex <= 0) {
            findViewById(R.id.buttonPrevious).setEnabled(false);
        } else {
            findViewById(R.id.buttonPrevious).setEnabled(true);
        }

        // Next button
        if (currentTopicIndex == questionTitle.size()-1) {
            ((Button)findViewById(R.id.buttonNext)).setText("FINISH");
        } else {
            findViewById(R.id.buttonNext).setEnabled(true);
            ((Button)findViewById(R.id.buttonNext)).setText("NEXT");
        }
    }

    void saveResult() {
        // Placeholder
        Toast.makeText(this, "Result saved!", Toast.LENGTH_SHORT).show();

        //startActivity(new Intent(this, PatientReportActivity.class));
        finish();
    }

    void initQuestionnaire() {

        currentTopicIndex = 0;

        // topic-01
        questionTitle.add("Apparent sadness");
        questionContent.add("Despondency, gloom, and despair (more than just ordinary transient low spirits), reflected in speech, facial expression, and posture; rate by depth and inability to brighten up");
        answer01.add("No sadness");
        answer02.add("Looks dispirited but brightens up without difficulty");
        answer03.add("Appears sad and unhappy most of the time");
        answer04.add("Looks miserable all the time; extremely despondent");

        // topic-02
        questionTitle.add("Reported sadness");
        questionContent.add("Reports of depressed mood, regardless of whether it is reflected in appearance or not; includes low spirits, despondency, or the feeling of being beyond help and without hope");
        answer01.add("Occasional sadness in keeping with circumstances");
        answer02.add("Sad or low but brightens up without difficulty");
        answer03.add("Pervasive feelings of sadness or gloominess; mood still influenced by external circumstances");
        answer04.add("Continuous or unvarying sadness, misery, or despondency");

        // topic-03
        questionTitle.add("Inner tension");
        questionContent.add("feelings of ill-defined discomfort, edginess, inner turmoil, mental tension mounting to either panic, dread, or anguish; rate by intensity, frequency, duration, and extent of reassurance called for");
        answer01.add("Placid; only fleeting inner tension");
        answer02.add("Occasional feelings of edginess and ill-defined discomfort");
        answer03.add("Continuous feelings of inner tension or intermittent panic which the patient can only master with some difficulty");
        answer04.add("Unrelenting dread or anguish; overwhelming panic");

        // topic-04
        questionTitle.add("Reduced sleep");
        questionContent.add("Experience of reduced duration or depth of sleep compared to the patient's own normal pattern when well");
        answer01.add("Sleeps as normal");
        answer02.add("Slight difficulty dropping off to sleep, or slightly reduced, light, or fitful sleep");
        answer03.add("Sleep reduced or broken by at least two hours");
        answer04.add("<2-3 hours of sleep");

        // topic-05
        questionTitle.add("Reduced appetite");
        questionContent.add("Feeling of loss of appetite; rate by loss of desire for food or the need to force oneself to eat");
        answer01.add("Normal or increased appetite");
        answer02.add("Slightly reduced appetite");
        answer03.add("No appetite; food is tasteless");
        answer04.add("Needs persuasion to eat");

        // topic-06
        questionTitle.add("Concentration difficulty");
        questionContent.add("Difficulties in collecting one’s thoughts mounting to incapacitating lack of concentration; rate by intensity, frequency, and degree of incapacity produced");
        answer01.add("No difficulties in concentrating");
        answer02.add("Occasional difficulties in collecting one’s thoughts");
        answer03.add("Difficulties in concentrating and sustaining thought which reduces ability to read or hold a conversation");
        answer04.add("Unable to read or converse without great initiative");

        // topic-07
        questionTitle.add("Lassitude");
        questionContent.add("Difficulty getting started or slowness initiating and performing everyday activities");
        answer01.add("Hardly any difficulty getting started in daily activities; no sluggishness");
        answer02.add("Difficulty in starting activities");
        answer03.add("Difficulty starting simple routine activities, which are carried out with effort");
        answer04.add("Complete lassitude; unable to do anything without help");

        // topic-08
        questionTitle.add("Inability to feel");
        questionContent.add("Subjective experience of reduced interest in the surroundings or activities that normally give pleasure; the ability to react with adequate emotion to circumstances or people is reduced");
        answer01.add("Normal interest in surroundings/other people");
        answer02.add("Reduced ability to enjoy usual interests");
        answer03.add("Loss of interest in surroundings; loss of feelings for friends/acquaintances");
        answer04.add("Emotionally paralyzed, unable to feel anger, grief, or pleasure; complete failure to feel for close relatives and friends");

        // topic-09
        questionTitle.add("Pessimistic thoughts");
        questionContent.add("Thoughts of guilt, inferiority, self reproach, sinfulness, remorse, and ruin");
        answer01.add("No pessimistic thoughts");
        answer02.add("Fluctuating ideas of failure, self reproach, or self depreciation");
        answer03.add("Persistent self accusations or definite but still rational ideas of guilt; increasingly pessimistic about the future");
        answer04.add("Delusions of ruin, remorse, or irredeemable sin; absurd and unshakable self accusations");

        // topic-10
        questionTitle.add("Suicidal thoughts");
        questionContent.add("Feeling that life is not worth living, that a natural death would be welcome, suicidal thoughts, and the preparations for suicide; suicidal attempts should not in themselves influence the rating");
        answer01.add("Enjoys life");
        answer02.add("Weary of life; only fleeting suicidal thoughts");
        answer03.add("Feels better off dead; suicidal thoughts common and considered as possible solution but no specific plans/intention");
        answer04.add("Explicit plans for suicide; active preparations");
    }

}
