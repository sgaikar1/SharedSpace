package com.emagicindia.realeastate.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;

import com.emagic.libraries.layouts.SweetAlertDialog;
import com.emagicindia.realeastate.model.AdressParameters;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.emagicindia.realeastate.MainActivity.citytext;

/**
 * Created by dell on 8/6/2016.
 */

public class AppUtils {
    private static final int REQUEST_PERMISSION_SETTING = 962;

    private static Location location;
    private static LocationManager googlePlayServicesHelper;
    private static List<String> addressparameters;
    private static SweetAlertDialog locationPermissionDialogue;
    private static AdressParameters adressParameters;
    private static ArrayList<AdressParameters> adressParameterses = new ArrayList<>();
    private static SweetAlertDialog contactPermissionDialogue;

    public static List<AdressParameters> getCityAndSubLocality(final Context mContext, final Geocoder geocoder) {
        addressparameters = new ArrayList<String>(2);
        googlePlayServicesHelper = new LocationManager(mContext.getApplicationContext());

        if(isGooglePlayServicesAvailable(mContext)){

           if( googlePlayServicesHelper.isLocationEnabled()) {

               googlePlayServicesHelper.startUpdate(new LocationListener() {
                   @Override
                   public void onLocationChanged(Location location) {
                       makeUseOfNewLocation(location);
                       googlePlayServicesHelper.stopUpdate();

                       if (location != null) {
                           System.out.println(location.getLatitude() + " : " + location.getLongitude());
                           List<Address> addresses = null;

                           try {
                               addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                           if (addresses != null) {
                               String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                               citytext = addresses.get(0).getLocality();
                               String state = addresses.get(0).getAdminArea();
                               String country = addresses.get(0).getCountryName();
                               String postalCode = addresses.get(0).getPostalCode();
                               String knownName = addresses.get(0).getFeatureName();
                               String subLocality = addresses.get(0).getSubLocality();
//                          Toast.makeText(mContext, "City: " + citytext + "\nsubLocality" + subLocality, Toast.LENGTH_LONG).show();
                               System.out.println("City: " + citytext + "\nsubLocality" + subLocality);
                               adressParameters = new AdressParameters(citytext, subLocality);
                               for (int i = 0; i < addresses.size(); i++) {
                                   adressParameterses.add(adressParameters);
                               }
                           } else {
                               System.out.println("Geocoder doesnt return any address");
                           }
                       }
                   }
               });
           }
            else {
                    LocationService locationService = new LocationService(mContext);
                    locationService.showSettingsAlert();
           }
        }else {
            LocationService locationService = new LocationService(mContext);
            location = locationService.getLocation();
            if(location!=null) {
                System.out.println(location.getLatitude() + " : " + location.getLongitude());
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses!=null) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    citytext = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    String subLocality = addresses.get(0).getSubLocality();
//                Toast.makeText(mContext, "City: " + citytext + "\nsubLocality" + subLocality, Toast.LENGTH_LONG).show();
                    System.out.println("City: " + citytext + "\nsubLocality" + subLocality);
                    adressParameters = new AdressParameters(citytext, subLocality);
//                    adressParameters.setSubLocality(subLocality);
//                    adressParameters.setCity(citytext);
                    for (int i = 0; i < addresses.size(); i++) {
                        adressParameterses.add(adressParameters);
                    }
                }else{
                    System.out.println("Geocoder doesnt return any address");
                }
            }
        }

        return adressParameterses;

    }


    public static void showNoLocationPermissionDialogue(final Context mContext) {
        locationPermissionDialogue = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        locationPermissionDialogue.setCancelable(false);
        locationPermissionDialogue.setTitleText("Permission not granted")
                .setContentText("Do you want to go to the settings menu to grant permission?")
                .setCancelText("No")
                .setConfirmText("Settings")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        ((Activity)mContext).startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        locationPermissionDialogue.dismiss();
//                        ((Activity)context).finish();
                    }
                })
                .show();
    }

    private static boolean isGooglePlayServicesAvailable(Context mContext) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            return false;
        }
    }

    public static void makeUseOfNewLocation(Location location) {
        if (location != null) {
           AppUtils.location = location;
        }
    }

    public static void showNoContactPermissionDialogue(final Context mContext) {
        contactPermissionDialogue = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        contactPermissionDialogue.setCancelable(false);
        contactPermissionDialogue.setTitleText("Permission not granted")
                .setContentText("Do you want to go to the settings menu to grant permission?")
                .setCancelText("No")
                .setConfirmText("Settings")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        ((Activity) mContext).startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        contactPermissionDialogue.dismiss();
//                        ((Activity)context).finish();
                    }
                })
                .show();
    }
}
