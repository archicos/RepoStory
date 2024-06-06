// Generated by view binder compiler. Do not edit!
package com.archico.storyapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.StackView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.archico.storyapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ImageBannerWidgetBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final TextView bannerText;

  @NonNull
  public final TextView emptyView;

  @NonNull
  public final StackView stackView;

  private ImageBannerWidgetBinding(@NonNull FrameLayout rootView, @NonNull TextView bannerText,
      @NonNull TextView emptyView, @NonNull StackView stackView) {
    this.rootView = rootView;
    this.bannerText = bannerText;
    this.emptyView = emptyView;
    this.stackView = stackView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ImageBannerWidgetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ImageBannerWidgetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.image_banner_widget, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ImageBannerWidgetBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.banner_text;
      TextView bannerText = ViewBindings.findChildViewById(rootView, id);
      if (bannerText == null) {
        break missingId;
      }

      id = R.id.empty_view;
      TextView emptyView = ViewBindings.findChildViewById(rootView, id);
      if (emptyView == null) {
        break missingId;
      }

      id = R.id.stack_view;
      StackView stackView = ViewBindings.findChildViewById(rootView, id);
      if (stackView == null) {
        break missingId;
      }

      return new ImageBannerWidgetBinding((FrameLayout) rootView, bannerText, emptyView, stackView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
