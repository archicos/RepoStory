// Generated by view binder compiler. Do not edit!
package com.archico.storyapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.archico.storyapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textview.MaterialTextView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDetailStoryBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final LinearLayout container;

  @NonNull
  public final ImageView imgStory;

  @NonNull
  public final ShimmerFrameLayout layShimmer;

  @NonNull
  public final MaterialTextView tvStoryDesc;

  @NonNull
  public final MaterialTextView tvStoryTitle;

  private ActivityDetailStoryBinding(@NonNull ScrollView rootView, @NonNull LinearLayout container,
      @NonNull ImageView imgStory, @NonNull ShimmerFrameLayout layShimmer,
      @NonNull MaterialTextView tvStoryDesc, @NonNull MaterialTextView tvStoryTitle) {
    this.rootView = rootView;
    this.container = container;
    this.imgStory = imgStory;
    this.layShimmer = layShimmer;
    this.tvStoryDesc = tvStoryDesc;
    this.tvStoryTitle = tvStoryTitle;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDetailStoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDetailStoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_detail_story, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDetailStoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.container;
      LinearLayout container = ViewBindings.findChildViewById(rootView, id);
      if (container == null) {
        break missingId;
      }

      id = R.id.img_story;
      ImageView imgStory = ViewBindings.findChildViewById(rootView, id);
      if (imgStory == null) {
        break missingId;
      }

      id = R.id.lay_shimmer;
      ShimmerFrameLayout layShimmer = ViewBindings.findChildViewById(rootView, id);
      if (layShimmer == null) {
        break missingId;
      }

      id = R.id.tv_story_desc;
      MaterialTextView tvStoryDesc = ViewBindings.findChildViewById(rootView, id);
      if (tvStoryDesc == null) {
        break missingId;
      }

      id = R.id.tv_story_title;
      MaterialTextView tvStoryTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvStoryTitle == null) {
        break missingId;
      }

      return new ActivityDetailStoryBinding((ScrollView) rootView, container, imgStory, layShimmer,
          tvStoryDesc, tvStoryTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
