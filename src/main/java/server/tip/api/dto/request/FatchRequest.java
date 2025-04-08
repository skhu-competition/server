package server.tip.api.dto.request;

public record FatchRequest(
        String title,
        String content,
        String image
) {
}
