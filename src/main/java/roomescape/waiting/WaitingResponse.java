package roomescape.waiting;


public class WaitingResponse {

    private Long id;
    private String theme;
    private String date;
    private String time;
    private String status;

    private WaitingResponse() {
    }

    private WaitingResponse(Long id, String name, String date, String time) {
        this.id = id;
        this.theme = name;
        this.date = date;
        this.time = time;
    }

    private WaitingResponse(Long id, String name, String date, String time, String status) {
        this.id = id;
        this.theme = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public static WaitingResponse from(Waiting waiting) {
        return new WaitingResponse(
            waiting.getId(),
            waiting.getTheme().getName(),
            waiting.getDate(),
            waiting.getTime().getValue()
        );
    }

    public static WaitingResponse from(WaitingWithRank waitingWithRank) {
        Waiting waiting = waitingWithRank.getWaiting();
        long rank = waitingWithRank.getRank();

        return new WaitingResponse(
            waiting.getId(),
            waiting.getTheme().getName(),
            waiting.getDate(),
            waiting.getTime().getValue(),
            (rank + 1) + "번째 예약대기"
        );
    }

    public Long getId() {
        return id;
    }

    public String getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
