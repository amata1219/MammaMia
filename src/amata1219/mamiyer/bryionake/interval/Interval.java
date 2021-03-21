package amata1219.mamiyer.bryionake.interval;

public class Interval<N extends Number & Comparable<N>> {

    private final Endpoint<N> lowerEndpoint, upperEndpoint;

    public Interval(Endpoint<N> lowerEndpoint, Endpoint<N> upperEndpoint) {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
    }

    public boolean contains(N value) {
        return lowerEndpoint.hasBoundaryValueBelow(value) && upperEndpoint.hasBoundaryValueAbove(value);
    }

}
