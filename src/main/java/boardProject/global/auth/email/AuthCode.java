package boardProject.global.auth.email;

public record AuthCode(String authCode, long lastRequestTime) {}
