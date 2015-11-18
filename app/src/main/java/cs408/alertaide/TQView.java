package cs408.alertaide;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Negatu on 11/15/15.
 */
class TQView extends LinearLayout {

    private JSONObject myData;
    private Context myContext;
    private AAView myQuestionView;
    private LinearLayout myAnswersView;
    private ArrayList<AnswerButton> myAnswerButtons;
    private LinearLayout.LayoutParams answersPadding;
    private String myQuestion;
    private String mySelectedAnswer;
    private int myPrevIndex;

    public TQView(Context context, JSONObject tq){
        super(context);
        myContext = context;
        this.setOrientation(VERTICAL);
        this.setBackgroundColor(Color.parseColor("#FFFFFF"));
        myData = tq;

        myAnswersView = new LinearLayout(myContext);
        myAnswersView.setOrientation(HORIZONTAL);
        myAnswersView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        answersPadding = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        answersPadding.setMargins(25, 10, 25, 10);

        myAnswerButtons = new ArrayList<>();
        myPrevIndex = -1;

        createMyView();
    }

    private void createMyView(){
        try {
            myQuestion = myData.getString("question");
            myQuestionView = new AAView(myContext, myQuestion, 1);
        } catch (Exception e){
            myQuestionView = new AAView(myContext, "Missing Question",1);
        }
        this.addView(myQuestionView);

        try {
            JSONArray answerOptions = myData.getJSONArray("answer_options");
            for (int i=0; i<answerOptions.length(); i++){
                AnswerButton button = new AnswerButton(myContext, answerOptions.getString(i), i);
                myAnswerButtons.add(button);
                myAnswersView.addView(button, answersPadding);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnswerButton b = (AnswerButton) v;
                        setClicked(b.getIndex());
                    }
                });
            }
        } catch (Exception e) {
            AAView missingAnswers = new AAView(myContext, "Missing Answers", 1);
            myAnswersView.addView(missingAnswers);
        }
        this.addView(myAnswersView);
    }

    public String getQuestion(){
        return myQuestion;
    }

    public String getAnswer(){
        return mySelectedAnswer;
    }

    public int getIndex(){
        return myPrevIndex;
    }

    private void setClicked(int index){
        AnswerButton clickedButton = myAnswerButtons.get(index);
        mySelectedAnswer = clickedButton.getValue();
        if(myPrevIndex != -1){
            myAnswerButtons.get(myPrevIndex).undoClicked();
        }
        clickedButton.setClicked();
        myPrevIndex = index;
    }


}
