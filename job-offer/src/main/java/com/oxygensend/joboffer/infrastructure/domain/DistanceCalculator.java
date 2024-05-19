package com.oxygensend.joboffer.infrastructure.domain;

class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371; // EARTH RADIUS IN KM
    public static final double THE_LONGEST_DISTANCE_BETWEEN_CITIES_IN_POLAND = 1089; // IN KM

    private DistanceCalculator() {
    }

    public static double calculateDistanceBasedOnVincentyFormula(double lat1, double lon1, double lat2, double lon2) {
        // Exit if coordinate is missing
        if (lat1 == 0 || lon1 == 0 || lat2 == 0 || lon2 == 0) {
            return THE_LONGEST_DISTANCE_BETWEEN_CITIES_IN_POLAND;
        }

        double latFrom = Math.toRadians(lat1);
        double lonFrom = Math.toRadians(lon1);
        double latTo = Math.toRadians(lat2);
        double lonTo = Math.toRadians(lon2);

        double lonDelta = lonFrom - lonTo;
        double a = Math.pow(Math.cos(latTo) * Math.sin(lonDelta), 2) +
                Math.pow(Math.cos(latFrom) * Math.sin(latTo) -
                                 Math.sin(latFrom) * Math.cos(latTo) * Math.cos(lonDelta), 2);
        double b = Math.sin(latFrom) * Math.sin(latTo) +
                Math.cos(latFrom) * Math.cos(latTo) * Math.cos(lonDelta);

        double angle = Math.atan2(Math.sqrt(a), b);

        return angle * EARTH_RADIUS;
    }
}
