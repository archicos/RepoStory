// Generated by view binder compiler. Do not edit!
package com.archico.storyapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.archico.storyapp.R;
import com.archico.storyapp.customview.MyEditText;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton btnRegister;

  @NonNull
  public final ConstraintLayout container;

  @NonNull
  public final MyEditText edRegisterPassword;

  @NonNull
  public final TextInputEditText etRegisterEmail;

  @NonNull
  public final TextInputEditText etRegisterUsername;

  @NonNull
  public final MaterialTextView hyplLogin;

  @NonNull
  public final LinearLayout tfEmail;

  @NonNull
  public final LinearLayout tfPassword;

  @NonNull
  public final LinearLayout tfUsername;

  @NonNull
  public final MaterialTextView tvTitle;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton btnRegister, @NonNull ConstraintLayout container,
      @NonNull MyEditText edRegisterPassword, @NonNull TextInputEditText etRegisterEmail,
      @NonNull TextInputEditText etRegisterUsername, @NonNull MaterialTextView hyplLogin,
      @NonNull LinearLayout tfEmail, @NonNull LinearLayout tfPassword,
      @NonNull LinearLayout tfUsername, @NonNull MaterialTextView tvTitle) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.container = container;
    this.edRegisterPassword = edRegisterPassword;
    this.etRegisterEmail = etRegisterEmail;
    this.etRegisterUsername = etRegisterUsername;
    this.hyplLogin = hyplLogin;
    this.tfEmail = tfEmail;
    this.tfPassword = tfPassword;
    this.tfUsername = tfUsername;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      MaterialButton btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.container;
      ConstraintLayout container = ViewBindings.findChildViewById(rootView, id);
      if (container == null) {
        break missingId;
      }

      id = R.id.ed_register_password;
      MyEditText edRegisterPassword = ViewBindings.findChildViewById(rootView, id);
      if (edRegisterPassword == null) {
        break missingId;
      }

      id = R.id.et_register_email;
      TextInputEditText etRegisterEmail = ViewBindings.findChildViewById(rootView, id);
      if (etRegisterEmail == null) {
        break missingId;
      }

      id = R.id.et_register_username;
      TextInputEditText etRegisterUsername = ViewBindings.findChildViewById(rootView, id);
      if (etRegisterUsername == null) {
        break missingId;
      }

      id = R.id.hypl_login;
      MaterialTextView hyplLogin = ViewBindings.findChildViewById(rootView, id);
      if (hyplLogin == null) {
        break missingId;
      }

      id = R.id.tf_email;
      LinearLayout tfEmail = ViewBindings.findChildViewById(rootView, id);
      if (tfEmail == null) {
        break missingId;
      }

      id = R.id.tf_password;
      LinearLayout tfPassword = ViewBindings.findChildViewById(rootView, id);
      if (tfPassword == null) {
        break missingId;
      }

      id = R.id.tf_username;
      LinearLayout tfUsername = ViewBindings.findChildViewById(rootView, id);
      if (tfUsername == null) {
        break missingId;
      }

      id = R.id.tv_title;
      MaterialTextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, btnRegister, container,
          edRegisterPassword, etRegisterEmail, etRegisterUsername, hyplLogin, tfEmail, tfPassword,
          tfUsername, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
