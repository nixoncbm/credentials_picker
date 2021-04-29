package com.nixon.credentials_picker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener;

import static android.app.Activity.RESULT_OK;

public class CredentialsPickerPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, ActivityResultListener {

  private MethodChannel channel;
  private static final String CHANNEL = "com.nixon.credential_picker";
  private static final int CODE_REQUEST_PHONE = 437;
  private CredentialsClient credentialsClient;
  private Context context;
  private Activity activity;
  private ActivityPluginBinding activityBinding;
  private Result resultRequest;
  private static final String TAG = "Log picker credential";

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  private boolean isGooglePlayServicesAvailable() {
    int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
    return status == ConnectionResult.SUCCESS;
  }

  private void pickPhone() {
    if (!isGooglePlayServicesAvailable()) {
      System.out.println("No tiene servicios de google");
      resultRequest.notImplemented();
      return;
    }
    HintRequest hintRequest = new HintRequest.Builder().setHintPickerConfig(
            new CredentialPickerConfig.Builder().setShowCancelButton(true).build()
    ).setIdTokenRequested(true).setPhoneNumberIdentifierSupported(true).build();

    pick(hintRequest);
  }

  private void pickEmail() {
    if (!isGooglePlayServicesAvailable()) {
      System.out.println("No tiene servicios de google");
      resultRequest.notImplemented();
      return;
    }
    HintRequest hintRequest = new HintRequest.Builder().setHintPickerConfig(
            new CredentialPickerConfig.Builder().setShowCancelButton(true).build()
    ).setIdTokenRequested(true).setEmailAddressIdentifierSupported(true).build();

    pick(hintRequest);
  }

  private void pick(HintRequest hintRequest){
    PendingIntent intent = credentialsClient.getHintPickerIntent(hintRequest);
    try {
      activity.startIntentSenderForResult(intent.getIntentSender(), CODE_REQUEST_PHONE, null, 0, 0, 0, null);
    }catch (Exception e){
      resultRequest.error("Exception", "Error request phone", e.getMessage());
    }
  }

  private void detachActivity() {
    activityBinding.removeActivityResultListener(this);
    activityBinding = null;
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    channel.setMethodCallHandler(this);
    activity = binding.getActivity();
    credentialsClient = Credentials.getClient(binding.getActivity());
    context = binding.getActivity().getApplicationContext();
    activityBinding = binding;
    activityBinding.addActivityResultListener(this);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity();
    detachActivity();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    onAttachedToActivity(binding);
  }

  @Override
  public void onDetachedFromActivity() {
    detachActivity();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    resultRequest = result;
    if (call.method.equals("pickPhone")) {
      pickPhone();
    } else if (call.method.equals("pickEmail")) {
      pickEmail();
    } else {
      result.notImplemented();
    }
  }



  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "Account Picker on Activity Result");
    if(requestCode == CODE_REQUEST_PHONE){
      if(resultCode == RESULT_OK){
        Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
        if(credential != null){
          HashMap<Object, Object> map = new HashMap<Object, Object>();
          map.put("id", credential.getId());
          map.put("name", credential.getName());
//          map.put("profilePictureUri", credential.getProfilePictureUri());
          map.put("password", credential.getPassword());
          map.put("accountType", credential.getAccountType());
          map.put("givenName", credential.getGivenName());
          map.put("familyName", credential.getFamilyName());
          resultRequest.success(map);
          return true;
        }
      } else {
        resultRequest.success(null);
      }
    }
    return false;
  }


}
