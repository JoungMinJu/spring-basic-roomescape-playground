package roomescape.reservation;

public enum ReservationStatus {
    RESERVED("예약"),
    CANCELED("취소");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
