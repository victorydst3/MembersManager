package com.asiantech.membersmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asiantech.membersmanager.abstracts.BaseFragment;
import com.asiantech.membersmanager.activity.LoginActivity_;
import com.asiantech.membersmanager.dialog.DialogChooseImage;
import com.asiantech.membersmanager.fragment.DetailHotNotificationFragment_;
import com.asiantech.membersmanager.fragment.DrawerFragment;
import com.asiantech.membersmanager.fragment.DrawerFragment_;
import com.asiantech.membersmanager.fragment.FavoriteFragment_;
import com.asiantech.membersmanager.fragment.HelpAndFeedBackFragment_;
import com.asiantech.membersmanager.fragment.HomeFragment_;
import com.asiantech.membersmanager.fragment.NotificationDetailFragment_;
import com.asiantech.membersmanager.fragment.ProfileFragment_;
import com.asiantech.membersmanager.fragment.TimeSheetFragment_;
import com.asiantech.membersmanager.fragment.VacationDayFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by VinhHlb on 12-03-2015.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements DrawerFragment
        .FragmentDrawerListener, BaseFragment.OnBaseFragmentListener {
    public static final int TYPE_BACK = 0;
    public static final int TYPE_HOME = 1;
    public static final int TYPE_EDIT = 2;
    public static final int TYPE_SETTING = 3;
    public static final int TYPE_CLOSE = 4;
    public static final int TYPE_DONE = 5;
    public static final int TYPE_DETAILS = 7;
    public static final int TYPE_SENT = 6;
    public static final int TYPE_DELETE = 8;
    public static final int TYPE_FAVORITE = 9;
    private DrawerFragment mDrawerFragment;
    @ViewById(R.id.toolbar)
    Toolbar mToolBar;
    @ViewById(R.id.imgFavoriteToolBar)
    ImageView imgFavoriteToolBar;
    @ViewById(R.id.img_right)
    ImageView mImgRight;
    @ViewById(R.id.img_left_button)
    ImageView mImgLeft;
    @ViewById(R.id.tv_title)
    TextView mTvTItle;
    @ViewById(R.id.tv_numdetele)
    TextView mtvNumDelete;
    private Boolean mCheckFavorite;
    private Fragment mContent;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(mToolBar);
        initView();
        initListener();
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        changeFragmentWithoutAnim(new HomeFragment_(), false);
        setTitle(getString(R.string.title_home));
    }

    private void initView() {
        mDrawerFragment = (DrawerFragment_)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolBar);
    }

    private void initListener() {
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment_();
                title = getString(R.string.title_home);
                mDrawerFragment.isPosClick(0);
                break;
            case 1:
                fragment = new FavoriteFragment_();
                title = getString(R.string.title_favorite);
                mDrawerFragment.isPosClick(1);
                break;
            case 2:
                fragment = new TimeSheetFragment_();
                title = getString(R.string.time_sheet);
                mDrawerFragment.isPosClick(2);
                break;
            case 3:
                fragment = new VacationDayFragment_();
                title = getString(R.string.vacation_day);
                mDrawerFragment.isPosClick(3);
                break;
            case 4:
                fragment = new HelpAndFeedBackFragment_();
                title = getString(R.string.help_feedback);
                mDrawerFragment.isPosClick(4);
                break;
            case 5:
                LoginActivity_.intent(MainActivity.this).start();
                finish();
                break;
            default:
                break;
        }
        keepHomeFragment();
        if (fragment != null) {
            changeFragment(fragment, false);
            setTitle(title);
        }
    }

    private void keepHomeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                if (i > 0) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    @Override
    public void setTitleHeader(String title) {
        setTitle(title);
    }

    @Click(R.id.img_right)
    void onEdit() {
        if (mContent instanceof ProfileFragment_) {
            if (!((ProfileFragment_) mContent).isEditing) {
                ((ProfileFragment_) mContent).clickEdit();
            } else {
                ((ProfileFragment_) mContent).clickDone();
            }
        } else if (mContent instanceof VacationDayFragment_) {
            ((VacationDayFragment_) mContent).clickSentMail();
        } else if (mContent instanceof FavoriteFragment_) {
            ((FavoriteFragment_) mContent).onDelete();
        }
    }

    @Click(R.id.imgFavoriteToolBar)
    void onFavorite() {
        if (mContent instanceof NotificationDetailFragment_) {
            if (mCheckFavorite) {
                imgFavoriteToolBar.setImageResource(R.drawable.ic_unfavorite);
                mCheckFavorite = false;
                ((NotificationDetailFragment_) mContent).changeFavorite();
            } else {
                imgFavoriteToolBar.setImageResource(R.drawable.ic_favorite);
                mCheckFavorite = true;
                ((NotificationDetailFragment_) mContent).changeFavorite();
            }
        }
        if (mContent instanceof DetailHotNotificationFragment_) {
            if (mCheckFavorite) {
                imgFavoriteToolBar.setImageResource(R.drawable.ic_unfavorite);
                mCheckFavorite = false;
                ((DetailHotNotificationFragment_) mContent).changeFavorite();
            } else {
                imgFavoriteToolBar.setImageResource(R.drawable.ic_favorite);
                mCheckFavorite = true;
                ((DetailHotNotificationFragment_) mContent).changeFavorite();
            }
        }
    }

    @Override
    public void setTypeHeader(int type) {
        switch (type) {
            case TYPE_BACK:
                mImgLeft.setVisibility(View.GONE);
                mtvNumDelete.setVisibility(View.GONE);
                mImgRight.setVisibility(View.GONE);
                imgFavoriteToolBar.setVisibility(View.GONE);
                mTvTItle.setVisibility(View.VISIBLE);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
                break;
            case TYPE_CLOSE:
                break;
            case TYPE_EDIT:
                mImgLeft.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                mTvTItle.setVisibility(View.VISIBLE);
                mtvNumDelete.setVisibility(View.GONE);
                imgFavoriteToolBar.setVisibility(View.GONE);
                mImgRight.setImageResource(R.drawable.ic_mode_edit);
                break;

            case TYPE_SETTING:
                break;
            case TYPE_DONE:
                mImgLeft.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                mtvNumDelete.setVisibility(View.GONE);
                mTvTItle.setVisibility(View.VISIBLE);
                imgFavoriteToolBar.setVisibility(View.GONE);
                mImgRight.setImageResource(R.drawable.ic_done_white);
                break;
            case TYPE_DETAILS:
                mImgLeft.setVisibility(View.GONE);
                mImgRight.setVisibility(View.GONE);
                mTvTItle.setVisibility(View.VISIBLE);
                mtvNumDelete.setVisibility(View.GONE);
                imgFavoriteToolBar.setVisibility(View.VISIBLE);

                if (mContent instanceof NotificationDetailFragment_) {
                    mCheckFavorite = ((NotificationDetailFragment_) mContent).checkFavorite();
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    }
                }
                if (mContent instanceof DetailHotNotificationFragment_) {
                    mCheckFavorite = ((DetailHotNotificationFragment_) mContent).checkFavorite();
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    }
                }

                if (mCheckFavorite) {
                    imgFavoriteToolBar.setImageResource(R.drawable.ic_favorite);
                } else {
                    imgFavoriteToolBar.setImageResource(R.drawable.ic_unfavorite);
                }
                break;
            case TYPE_DELETE:
                imgFavoriteToolBar.setVisibility(View.GONE);
                mtvNumDelete.setVisibility(View.VISIBLE);
                mImgLeft.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                mTvTItle.setVisibility(View.VISIBLE);
                mImgRight.setImageResource(R.drawable.ic_delete_white);
                break;
            case TYPE_SENT:
                imgFavoriteToolBar.setVisibility(View.GONE);
                mImgLeft.setVisibility(View.GONE);
                mImgRight.setVisibility(View.VISIBLE);
                mTvTItle.setVisibility(View.VISIBLE);
                mImgRight.setImageResource(R.drawable.ic_send_white);
                mtvNumDelete.setVisibility(View.GONE);
                break;
            case TYPE_FAVORITE:
                mImgLeft.setVisibility(View.GONE);
                mtvNumDelete.setVisibility(View.GONE);
                mImgRight.setVisibility(View.GONE);
                imgFavoriteToolBar.setVisibility(View.GONE);
                mTvTItle.setVisibility(View.VISIBLE);
                break;
            case TYPE_HOME:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                initView();
                mImgLeft.setVisibility(View.GONE);
                mtvNumDelete.setVisibility(View.GONE);
                mImgRight.setVisibility(View.GONE);
                imgFavoriteToolBar.setVisibility(View.GONE);
                mTvTItle.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void updateTvRight(int num) {
        mtvNumDelete.setText(String.valueOf(num));
    }

    // Change fragment with animation
    public void changeFragment(Fragment fragment, boolean isBack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.container_body, fragment);
        mContent = fragment;
        //Add to back stack
        if (!isBack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    // Change fragment without animation
    public void changeFragmentWithoutAnim(Fragment fragment, boolean isBack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        //Add to back stack
        if (!isBack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    private void setTitle(String title) {
        mTvTItle.setText(title);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerFragment != null && mDrawerFragment.isShowing()) {
            mDrawerFragment.closeDrawer();
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() != 1) {
                super.onBackPressed();
                mContent = getSupportFragmentManager().findFragmentById(R.id.container_body);
                if (mContent instanceof HomeFragment_) {
                    mDrawerFragment.isPosClick(0);
                    mDrawerFragment.notifyDatasetChange();
                }
                if (mContent instanceof FavoriteFragment_) {
                    mDrawerFragment.isPosClick(1);
                    mDrawerFragment.notifyDatasetChange();
                }
                if (mContent instanceof TimeSheetFragment_) {
                    mDrawerFragment.isPosClick(2);
                    mDrawerFragment.notifyDatasetChange();
                }
                if (mContent instanceof VacationDayFragment_) {
                    mDrawerFragment.isPosClick(3);
                    mDrawerFragment.notifyDatasetChange();
                }
                if (mContent instanceof HelpAndFeedBackFragment_) {
                    mDrawerFragment.isPosClick(4);
                    mDrawerFragment.notifyDatasetChange();
                }
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mContent instanceof ProfileFragment_) {
            switch (requestCode) {
                case DialogChooseImage.SELECT_PHOTO:
                    if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        if (((ProfileFragment_) mContent).isEditing) {
                            setTypeHeader(TYPE_DONE);
                            ((ProfileFragment_) mContent).setNewAvatar(uri, null, false);
                        } else {
                            setTypeHeader(TYPE_EDIT);
                        }
                    }
                    break;
                case DialogChooseImage.TAKE_PICTURE:
                    if (resultCode == RESULT_OK && data != null &&
                            data.getExtras() != null &&
                            data.getExtras().get("data") != null) {
                        try {
                            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            if (thumbnail != null) {
                                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                                File destination = new File(Environment.getExternalStorageDirectory(),
                                        System.currentTimeMillis() + ".jpg");
                                FileOutputStream fo;
                                try {
                                    destination.createNewFile();
                                    fo = new FileOutputStream(destination);
                                    fo.write(bytes.toByteArray());
                                    fo.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (((ProfileFragment_) mContent).isEditing) {
                                    setTypeHeader(TYPE_DONE);
                                    ((ProfileFragment_) mContent).setNewAvatar(null, thumbnail, true);
                                } else {
                                    setTypeHeader(TYPE_EDIT);
                                }
                            }
                        } catch (OutOfMemoryError oom) {
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}