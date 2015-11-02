package com.mapbox.reactnativemapboxgl;

import javax.annotation.Nullable;

import java.util.Map;

import android.os.SystemClock;
import android.graphics.Color;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.UIManagerModule;
import com.mapbox.mapboxsdk.constants.MyLocationTracking;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.facebook.react.uimanager.events.EventDispatcher;

import com.mapbox.reactnativemapboxgl.events.RegionChangeEvent;
import com.mapbox.reactnativemapboxgl.events.UserLocationUpdateEvent;

import java.util.Map;

public class ReactNativeMapboxGLManager extends SimpleViewManager<MapView> {
    public static final String REACT_CLASS = "RCTMapbox";

    public static final String PROP_ACCESS_TOKEN = "accessToken";
    public static final String PROP_ANNOTATIONS = "annotations";
    public static final String PROP_CENTER_COORDINATE = "centerCoordinate";
    public static final String PROP_DEBUG_ACTIVE = "debugActive";
    public static final String PROP_DIRECTION = "direction";
    public static final String PROP_ROTATION_ENABLED = "rotationEnabled";
    public static final String PROP_SCROLL_ENABLED = "scrollEnabled";
    public static final String PROP_SHOWS_USER_LOCATON = "showsUserLocation";
    public static final String PROP_STYLE_URL = "styleUrl";
    public static final String PROP_ZOOM_ENABLED = "zoomEnabled";
    public static final String PROP_ZOOM_LEVEL = "zoomLevel";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public MapView createViewInstance(ThemedReactContext context) {
        MapView mv = new MapView(context, "pk.foo");
        mv.onCreate(null);
        return mv;
    }

    @ReactProp(name = PROP_ACCESS_TOKEN)
    public void setAccessToken(MapView view, @Nullable String accessToken) {
      view.setAccessToken(accessToken);
    }

    @ReactProp(name = PROP_CENTER_COORDINATE)
    public void setCenterCoordinate(MapView view, @Nullable ReadableMap coordinate) {
      double latitude = coordinate.getDouble("latitude");
      double longitude = coordinate.getDouble("longitude");
      view.setCenterCoordinate(new LatLng(latitude, longitude));
    }

    @ReactProp(name = PROP_DEBUG_ACTIVE, defaultBoolean = false)
    public void setDebugActive(MapView view, boolean debugActive) {
        view.setDebugActive(debugActive);
    }

    @ReactProp(name = PROP_DIRECTION, defaultFloat = 0.0f)
    public void setDirection(MapView view, float direction) {
        view.setDirection(direction);
    }

    @ReactProp(name = PROP_ROTATION_ENABLED, defaultBoolean = true)
    public void setRotateEnabled(MapView view, boolean rotationEnabled) {
        view.setRotateEnabled(rotationEnabled);
    }

    @ReactProp(name = PROP_SHOWS_USER_LOCATON, defaultBoolean = false)
    public void setShowsUserLocation(MapView view, boolean showsUserLocation) {
        view.setMyLocationEnabled(showsUserLocation);
    }

    @ReactProp(name = PROP_STYLE_URL)
    public void setStyleUrl(MapView view, @Nullable String styleUrl) {
        view.setStyleUrl(styleUrl);
    }

    @ReactProp(name = PROP_ZOOM_ENABLED, defaultBoolean = true)
    public void setZoomEnabled(MapView view, boolean zoomEnabled) {
        view.setZoomEnabled(zoomEnabled);
    }

    @ReactProp(name = PROP_ZOOM_LEVEL, defaultFloat = 12.0f)
    public void setZoomLevel(MapView view, float zoomLevel) {
        view.setZoomLevel(zoomLevel);
    }

    @ReactProp(name = PROP_SCROLL_ENABLED, defaultBoolean = true)
    public void setScrollEnabled(MapView view, boolean scrollEnabled) {
        view.setScrollEnabled(scrollEnabled);
    }

    @Override
    public @Nullable Map getExportedCustomDirectEventTypeConstants() {
      return MapBuilder.of(
          RegionChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onChange"),
          UserLocationUpdateEvent.EVENT_NAME, MapBuilder.of("registrationName", "onUserLocationUpdate")
      );
    }

    @Override
    protected void addEventEmitters(final ThemedReactContext reactContext, final MapView view) {
      final EventDispatcher eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();

      view.addOnMapChangedListener(new MapView.OnMapChangedListener() {
          @Override
          public void onMapChanged(int change) {
              if (change == MapView.REGION_DID_CHANGE || change == MapView.REGION_DID_CHANGE_ANIMATED) {
                  eventDispatcher.dispatchEvent(
                    new RegionChangeEvent(view.getId(), SystemClock.uptimeMillis(), view.getZoomLevel(), view.getCenterCoordinate())
                  );
              }
          }
      });

      view.setOnMyLocationChangeListener(new MapView.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(android.location.Location location) {
          eventDispatcher.dispatchEvent(
            new UserLocationUpdateEvent(view.getId(), SystemClock.uptimeMillis(), view.getMyLocation())
          );
        }
      });
    }
}
