package com.android.rdc.rdcblog.bloglist.view.activity;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity{

    protected static final int EDGE_FLAG = SwipeBackLayout.EDGE_LEFT;
    protected SwipeBackLayout mSwipeBackLayout;

    /**
     * 侧滑结束activity
     */
    public void initSwipeBackLayout(){
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(EDGE_FLAG);
    }

}
