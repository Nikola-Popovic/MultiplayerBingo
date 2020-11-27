import Util from "../util";

// Need it named with capital L to not conflict Geolocation class
export default class GeoLocation {
    private readonly _longitude: number;
    private readonly _latitude: number;

    constructor(longitude: number, latitude: number) {
        this._longitude = longitude;
        this._latitude = latitude;
    }

    // in m
    distanceToLocation(location: GeoLocation): number {
        return Util.distance(this._longitude, this._latitude, location._longitude, location._latitude);
    }

    distanceTo(longitude: number, latitude: number): number {
        return Util.distance(this._longitude, this._latitude, longitude, latitude);
    }
}
