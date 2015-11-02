package com.mapbox.reactnativemapboxgl.events;

import android.location.Location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class UserLocationUpdateEvent extends Event<UserLocationUpdateEvent> {

  public static final String EVENT_NAME = "topUserLocationUpdate";

  private final Location mLocation;

  public UserLocationUpdateEvent(int viewId, long timestampMs, Location location) {
    super(viewId, timestampMs);
    mLocation = location;
  }

  @Override
  public String getEventName() {
    return EVENT_NAME;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {
    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }

  private WritableMap serializeEventData() {
    WritableMap eventData = Arguments.createMap();
    eventData.putDouble("latitude", mLocation.getLatitude());
    eventData.putDouble("longitude", mLocation.getLongitude());
    return eventData;
  }
}
