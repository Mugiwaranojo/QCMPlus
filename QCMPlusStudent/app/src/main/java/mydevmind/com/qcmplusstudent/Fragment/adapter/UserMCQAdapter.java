package mydevmind.com.qcmplusstudent.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.model.Option;
import mydevmind.com.qcmplusstudent.model.Question;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * Created by Joan on 01/08/2014.
 */
public class UserMCQAdapter extends BaseExpandableListAdapter {

    private Context context;
    private UserMCQ userMCQ;

    public UserMCQAdapter(Context context, UserMCQ userMCQ) {
        this.context = context;
        this.userMCQ= userMCQ;
    }

    @Override
    public int getGroupCount() {
        return userMCQ.getMcq().getQuestions().size();
    }

    @Override
    public int getChildrenCount(int i) {
        return userMCQ.getMcq().getQuestions().get(i).getOptions().size();
    }

    @Override
    public Object getGroup(int i) {
        return userMCQ.getMcq().getQuestions().get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return userMCQ.getMcq().getQuestions().get(i).getOptions().get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i*i2;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= inflater.inflate(R.layout.layout_list_question, null);
        Question question= (Question) getGroup(i);
        TextView textViewStatement= (TextView)v.findViewById(R.id.textViewListQuestion);
        textViewStatement.setText(question.getStatement());
        if(!userHasGoodAnswerQuestion(question)){
            ImageView imageView= (ImageView) v.findViewById(R.id.imageViewListQuestion);
            imageView.setImageResource(android.R.drawable.ic_menu_delete);
        }
        return v;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= inflater.inflate(R.layout.layout_list_option, null);
        Question question= (Question) getGroup(i);
        Option option= (Option)getChild(i, i2);
        TextView textViewStatement= (TextView)v.findViewById(R.id.textViewListQuestion);
        textViewStatement.setText(option.getStatement());
        ImageView imageView= (ImageView) v.findViewById(R.id.imageViewListQuestion);
        if(isUserAnswer(question, option) && !question.validOption().getStatement().equals(option.getStatement())){
            imageView.setImageResource(android.R.drawable.ic_menu_delete);
        }else if(!question.validOption().getStatement().equals(option.getStatement())){
            imageView.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    private boolean userHasGoodAnswerQuestion(Question question){
        for(UserAnswer userAnswer: userMCQ.getUserAnswers()){
            if(userAnswer.getQuestion().getObjectId().equals(question.getObjectId())){
                if(userAnswer.getScore()==1){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean isUserAnswer(Question question, Option option){
        for(UserAnswer userAnswer: userMCQ.getUserAnswers()){
            if(userAnswer.getQuestion().getObjectId().equals(question.getObjectId())){
                if(userAnswer.getAnswer().getStatement().equals(option)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
