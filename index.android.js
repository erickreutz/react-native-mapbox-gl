'use strict'

var React = require('react-native');
var {
  NativeMethodsMixin,
  PropTypes,
  NativeModules,
  requireNativeComponent
} = React;

var ReactMapView = requireNativeComponent('RCTMapbox', {
    name: 'RCTMapbox',
    propTypes: {
      accessToken: PropTypes.string.isRequired,
      annotations: PropTypes.arrayOf(React.PropTypes.shape({
        title: PropTypes.string,
        subtitle: PropTypes.string,
        coordinates: PropTypes.arrayOf(),
        alpha: PropTypes.number,
        fillColor: PropTypes.string,
        strokeColor: PropTypes.string,
        strokeWidth: PropTypes.number
      })),
      centerCoordinate: PropTypes.shape({
        latitude: PropTypes.number.isRequired,
        longitude: PropTypes.number.isRequired
      }),
      debugActive: PropTypes.bool,
      direction: PropTypes.number,
      rotationEnabled: PropTypes.bool,
      scrollEnabled: PropTypes.bool,
      showsUserLocation: PropTypes.bool,
      styleUrl: PropTypes.string,
      UserLocationTrackingMode: PropTypes.oneOf(['NONE', 'FOLLOW']),
      zoomEnabled: PropTypes.bool,
      zoomLevel: PropTypes.number,
      onRegionChange: PropTypes.func,
      onUserLocationUpdate: PropTypes.func,
      // Fix for https://github.com/mapbox/react-native-mapbox-gl/issues/118
      scaleY: PropTypes.number,
      scaleX: PropTypes.number,
      translateY: PropTypes.number,
      translateX: PropTypes.number,
      rotation: PropTypes.number
    },
    nativeOnly: {
      onChange: true,
      onUserLocationUpdate: true
    },
    defaultProps() {
      return {
        centerCoordinate: {
          latitude: 0,
          longitude: 0
        },
        debugActive: false,
        direction: 0,
        rotationEnabled: true,
        scrollEnabled: true,
        showsUserLocation: true,
        styleUrl: 'asset://styles/streets-v8.json',
        UserLocationTrackingMode: 'NONE',
        zoomEnabled: true,
        zoomLevel: 0
      };
    }
});

var ReactMapViewWrapper = React.createClass({
  mixins: [NativeMethodsMixin],
  
  _onChange(event) {
    this.props.onRegionChange && this.props.onRegionChange(event.nativeEvent);
  },
  _onUserLocationUpdate(event) {
    this.props.onUserLocationUpdate && this.props.onUserLocationUpdate(event.nativeEvent);
  },
  render() {
    return <ReactMapView
      {...this.props}
      onChange={this._onChange}
      onUserLocationUpdate={this._onUserLocationUpdate} />
  }
});

module.exports = ReactMapViewWrapper;
