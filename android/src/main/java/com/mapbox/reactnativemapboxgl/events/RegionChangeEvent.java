package com.mapbox.reactnativemapboxgl.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class RegionChangeEvent extends Event<RegionChangeEvent> {

  public static final String EVENT_NAME = "topChange";

  private final LatLng mLatLng;
  private final double mZoomLevel;

  public RegionChangeEvent(int viewId, long timestampMs, double zoomLevel, LatLng latLng) {
    super(viewId, timestampMs);
    mZoomLevel = zoomLevel;
    mLatLng = latLng;
  }

  @Override
  public String getEventName() {
    return EVENT_NAME;
  }

  public double getZoomLevel() {
    return mZoomLevel;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {
    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }

  private WritableMap serializeEventData() {
    WritableMap eventData = Arguments.createMap();
    eventData.putDouble("zoomLevel", getZoomLevel());
    eventData.putDouble("latitude", mLatLng.getLatitude());
    eventData.putDouble("longitude", mLatLng.getLongitude());
    return eventData;
  }
}
