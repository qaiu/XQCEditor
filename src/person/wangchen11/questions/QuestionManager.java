package person.wangchen11.questions;

import java.util.ArrayList;

import person.wangchen11.gnuccompiler.GNUCCompiler;

import android.content.Context;

public class QuestionManager {
	private static QuestionManager mQuestionManager = null;
	
	private static final String ASSETS_PATH = "questions/";
	
	private ArrayList<QuestionGroup> mAllLevelQuestions = new ArrayList<QuestionGroup>();
	
	private QuestionManager(Context context) {
		mAllLevelQuestions.add(new QuestionGroup(context,ASSETS_PATH,"1.",4,"入门训练"));
		mAllLevelQuestions.add(new QuestionGroup(context,ASSETS_PATH,"1.",4,"入门训练"));
		mAllLevelQuestions.add(new QuestionGroup(context,ASSETS_PATH,"1.",4,"入门训练"));
		mAllLevelQuestions.add(new QuestionGroup(context,ASSETS_PATH,"1.",4,"入门训练"));
	}
	public static void init(Context context){
		if(mQuestionManager==null)
			mQuestionManager = new QuestionManager(context);
	}
	
	public static QuestionManager instance(){
		return mQuestionManager;
	}
	
	public QuestionGroup getQuestionGroupByLevel(int level){
		return mAllLevelQuestions.get(level);
	}
	
	public int getQuestionLevelCount(){
		return mAllLevelQuestions.size();
	}
	
	public QuestionGroup getQuestionGroup(Question question){
		for(QuestionGroup questionGroup:mAllLevelQuestions){
			if(questionGroup.getQuestionIndex(question)>=0)
				return questionGroup;
		}
		return null;
	}
	
	public Question getNextQuestion(Question question){
		QuestionGroup questionGroup = getQuestionGroup(question);
		if(questionGroup==null)
			return null;
		int index = questionGroup.getQuestionIndex(question);
		if(index+1 >= questionGroup.getQuestions().size()){
			int questionGroupIndex = mAllLevelQuestions.indexOf(questionGroup);
			if(questionGroupIndex+1 >= mAllLevelQuestions.size())
				return null;
			return mAllLevelQuestions.get(questionGroupIndex+1).getQuestions().get(0);
		}
			
		return questionGroup.getQuestions().get(index+1);
	}
	
	public Question getPreQuestion(Question question){
		QuestionGroup questionGroup = getQuestionGroup(question);
		if(questionGroup==null)
			return null;
		int index = questionGroup.getQuestionIndex(question);
		if(index-1 < 0){
			int questionGroupIndex = mAllLevelQuestions.indexOf(questionGroup);
			if(questionGroupIndex-1 < 0)
				return null;
			return mAllLevelQuestions.get(questionGroupIndex-1).getQuestions().get(0);
		}
		
		return questionGroup.getQuestions().get(index-1);
	}
	
	public String getQuestionCodeFile(Question question){
		QuestionGroup questionGroup = getQuestionGroup(question);
		if(questionGroup==null)
			return null;
		int questionGroupIndex = mAllLevelQuestions.indexOf(questionGroup);
		int questionIndex = questionGroup.getQuestionIndex(question);
		
		return GNUCCompiler.getSystemDir()+"/Answers/answer"+String.format("%03d", questionGroupIndex+1)+"_"+String.format("%03d", questionIndex+1)+".cpp";
	}
	
}
