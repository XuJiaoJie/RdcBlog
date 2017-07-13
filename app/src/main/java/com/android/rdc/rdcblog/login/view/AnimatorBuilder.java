package com.android.rdc.rdcblog.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 定义属性动画的开始值和结束值
 * Created by tom on 15/10/31.
 */
public class AnimatorBuilder {


	public static void startAnimator(final AnimatorParams params,int normalWidth) {

		params.mButton.setClickable(false);
		int changWidth=normalWidth;
		ValueAnimator widthAnimator = ValueAnimator.ofInt(params.fromWidth, params.toWidth);
		widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				ViewGroup.LayoutParams lp = params.mButton.getLayoutParams();
				lp.width = (Integer) animation.getAnimatedValue();
				params.mButton.setLayoutParams(lp);
			}
		});

		CustomGradientDrawable drawable = (CustomGradientDrawable) params.mButton.getNormalDrawable();
		ObjectAnimator colorAnimaor = ObjectAnimator.ofInt(drawable, "color", params.fromColor, params.toColor);
		colorAnimaor.setEvaluator(new ArgbEvaluator());


		AnimatorSet animators = new AnimatorSet();
		animators.setDuration(params.duration);

		if(params.fromWidth>params.toWidth){
			changWidth=params.fromWidth;
		}
		/*Log.d("width","		"+params.fromWidth);
		Log.d("width","		"+params.toWidth);
		Log.d("width","		"+changWidth);*/
			ObjectAnimator xAnimator = ObjectAnimator.ofFloat(params.mButton, "X", changWidth/2);
			animators.play(widthAnimator).with(colorAnimaor).with(xAnimator);

		//animators.play(widthAnimator).with(colorAnimaor);


		animators.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				params.mListener.onAnimatorEnd();
				if(params.fromWidth-params.toWidth<=0){
					TranslateAnimation animationX=new TranslateAnimation(0,10,0,0);
							animationX.setInterpolator(new AccelerateInterpolator());
							animationX.setDuration(5);
							animationX.setRepeatCount(5);
						    animationX.setRepeatMode(Animation.REVERSE);
							params.mButton.startAnimation(animationX);
					animationX.setAnimationListener(new Animation.AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							params.mButton.setClickable(true);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {

						}
					});

				}
			}
		});
		animators.start();

	}



	public static class AnimatorParams {

		private float fromCornerRadius;
		private float toCornerRadius;


		private int fromHeight;
		private int toHeight;

		private int fromWidth;
		private int toWidth;

		private int fromColor;
		private int toColor;


		private long duration;


		private DynamicButton mButton;
		private AnimatorListener mListener;

		public AnimatorParams(DynamicButton mButton) {
			this.mButton = mButton;
		}

		public static AnimatorParams build(DynamicButton mButton) {
			return new AnimatorParams(mButton);
		}

		public AnimatorParams height(int fromHeight, int toHeight) {
			this.fromHeight = fromHeight;
			this.toHeight = toHeight;
			return this;
		}

		public AnimatorParams cornerRadius(float fromCornerRadius, float toCornerRadius) {
			this.fromCornerRadius = fromCornerRadius;
			this.toCornerRadius = toCornerRadius;
			return this;
		}


		public AnimatorParams width(int fromWidth, int toWidth) {
			this.fromWidth = fromWidth;
			this.toWidth = toWidth;
			return this;
		}


		public AnimatorParams duration(long duration) {
			this.duration = duration;
			return this;
		}

		public AnimatorParams listener(AnimatorListener listener) {
			this.mListener = listener;
			return this;
		}

		public AnimatorParams color(int fromColor, int toColor) {
			this.fromColor = fromColor;
			this.toColor = toColor;
			return this;
		}

	}


	public interface AnimatorListener {
		public void onAnimatorEnd();
	}
}
