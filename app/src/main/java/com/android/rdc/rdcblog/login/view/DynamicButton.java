package com.android.rdc.rdcblog.login.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.Button;

import com.android.rdc.rdcblog.R;

public class DynamicButton extends Button {
    public static final String TAG = "DynamicButton";

    //默认值
    public static final int DEFUALT_DYB_CORNER_RADIUS = 2;


    /**
     * 控件的高度
     */
    protected int mHeight;
    /**
     * 控件的宽度
     */
    protected int mWidth;

    protected int startWidth=0;

    /**
     * 控件的背景色
     */
    protected int mColor;

    /**
     * 控件按住时的背景
     */
    protected int mPressedColor;
    /**
     * 圆角半径
     */
    protected float mCornerRadius;
    /**
     * 边缘宽度
     */
    protected int mStrokeWidth;
    /**
     * 边缘颜色
     */
    protected int mStrokeColor;
    /**
     * 是否正在执行动画
     */
    protected boolean bAnimatoring = false;
    /**
     * 正常时的背景
     */
    private CustomGradientDrawable mNormalDrawable;
    /**
     * 按下时的背景
     */
    private CustomGradientDrawable mPressedDrawable;


    public DynamicButton(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public DynamicButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public DynamicButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr);
    }






    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        mWidth = getWidth();
        if(startWidth==0){
            startWidth=mWidth;
        }
    }

    public void initView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.DynamicButton, defStyleAttr, 0);
        mColor = array.getColor(R.styleable.DynamicButton_dybtn_color, 0);
        mPressedColor = array.getColor(R.styleable.DynamicButton_dybtn_pressed_color, 0);
        mCornerRadius = array.getDimensionPixelOffset(R.styleable.DynamicButton_dybtn_corner_radius, dp2px(DEFUALT_DYB_CORNER_RADIUS));


        mNormalDrawable = createDrawable(mColor, mCornerRadius);
        mPressedDrawable = createDrawable(mPressedColor, mCornerRadius);


        StateListDrawable mListDrawable = new StateListDrawable();
        mListDrawable.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
        mListDrawable.addState(new int[]{android.R.attr.state_focused}, mPressedDrawable);
        mListDrawable.addState(new int[]{android.R.attr.state_selected}, mPressedDrawable);
        mListDrawable.addState(new int[]{}, mNormalDrawable);
        setBackground(mListDrawable);

        array.recycle();
    }


    /**
     * 创建背景图片
     *
     * @param mColor
     * @param mCornerRadius
     * @return
     */
    public CustomGradientDrawable createDrawable(int mColor, float mCornerRadius) {
        CustomGradientDrawable drawable = new CustomGradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(mColor);
        drawable.setRadius(mCornerRadius);
        return drawable;
    }

    /**
     * 设置背景
     *
     * @param mDrawable
     */
    public void setBackGroundCompat(Drawable mDrawable) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(mDrawable);
        } else {
            setBackground(mDrawable);
        }
    }

    /**
     * 开始变换Button的形状
     *
     * @param params
     */
    public void startChange(@NonNull PropertyParam params) {

        if (!bAnimatoring) {
            //没有正在执行动画
            mPressedDrawable.setColor(params.mPressedColor);
            mPressedDrawable.setCornerRadius(params.mCornerRadius);
            if (params.duration > 0) {
                changeWithAnimation(params);
            }
            mColor = params.mColor;
            mCornerRadius = params.mCornerRadius;
        }
    }



    /**
     * 带有动画的渐变
     *
     * @param param
     */
    public void changeWithAnimation(final PropertyParam param) {

        bAnimatoring = true;
        setText(param.text);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        final AnimatorBuilder.AnimatorParams mAnimatorParams = AnimatorBuilder.AnimatorParams.build(this);
        mAnimatorParams.height(mHeight, param.mHeight)
                .width(mWidth, param.mWidth)
                .cornerRadius(mCornerRadius, param.mCornerRadius)
                .color(mColor, param.mColor)
                .duration(param.duration)
                .listener(new AnimatorBuilder.AnimatorListener() {
                    @Override
                    public void onAnimatorEnd() {


                    }
                });
        AnimatorBuilder.startAnimator(mAnimatorParams,dimen(R.dimen.mb_height_56));
        bAnimatoring=false;
    }

    public void loginAnim(final DynamicButton btnMorph) {
        DynamicButton.PropertyParam circle = DynamicButton.PropertyParam.build()
                .duration(250)
                .setCornerRadius(dimen(R.dimen.mb_height_56))
                .setWidth(dimen(R.dimen.mb_height_56))
                .setHeight(dimen(R.dimen.mb_height_56))
                .setColor(color(R.color.colorAccent))
                .setPressedColor(color(R.color.colorAccent_dark));
        btnMorph.startChange(circle);
        btnMorph.setClickable(false);
    }


    public void failedAnim(final DynamicButton btn){
        DynamicButton.PropertyParam rectf=DynamicButton.PropertyParam.build()
                .duration(250)
                .setCornerRadius(dimen(R.dimen.mb_height_56))
                .setWidth(startWidth)
                .setHeight(dimen(R.dimen.mb_height_56))
                .setColor(color(R.color.colorAccent))
                .setPressedColor(color(R.color.colorAccent_dark))
                .text("登录");
        btn.startChange(rectf);
    }

    public void signUpFailedAnim(final DynamicButton btn){
        DynamicButton.PropertyParam rectf=DynamicButton.PropertyParam.build()
                .duration(250)
                .setCornerRadius(dimen(R.dimen.mb_height_56))
                .setWidth(startWidth)
                .setHeight(dimen(R.dimen.mb_height_56))
                .setColor(color(R.color.colorAccent))
                .setPressedColor(color(R.color.colorAccent_dark))
                .text("注册");
        btn.startChange(rectf);
    }


    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public Drawable drawable(int resId){
        return getResources().getDrawable(resId);
    }



    /**
     * 获取正常状态下的背景图片
     *
     * @return
     */
    public CustomGradientDrawable getNormalDrawable() {
        return mNormalDrawable;
    }


    /**
     * 使用Build模式构建该按钮的属性
     */
    public static class PropertyParam {

        public int mHeight;
        public int mWidth;
        public int mColor;
        public int mPressedColor;
        public float mCornerRadius;
        public long duration;

        public String text;
        public Drawable icon;


        public static PropertyParam build() {
            return new PropertyParam();
        }

        public PropertyParam setHeight(int mHeight) {
            this.mHeight = mHeight;
            return this;
        }

        public PropertyParam setWidth(int mWidth) {
            this.mWidth = mWidth;
            return this;
        }

        public PropertyParam setColor(int mColor) {
            this.mColor = mColor;
            return this;
        }

        public PropertyParam setCornerRadius(int mCornerRadius) {
            this.mCornerRadius = mCornerRadius;
            return this;
        }


        public PropertyParam setPressedColor(int mPressedColor) {
            this.mPressedColor = mPressedColor;
            return this;
        }

        public PropertyParam duration(long duration) {
            this.duration = duration;
            return this;
        }

        public PropertyParam text(String text) {
            this.text = text;
            return this;
        }

        public PropertyParam icon(Drawable icon) {
            this.icon = icon;
            return this;
        }
    }

    /**
     * dp转换px
     * @param dp
     * @return
     */
    public int dp2px(int dp) {
        return (int) (this.getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
