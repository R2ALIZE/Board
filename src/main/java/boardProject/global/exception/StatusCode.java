package boardProject.global.exception;

import lombok.Getter;

@Getter
public enum StatusCode {

    /**
     // 1xx: Informational - 요청이 수신되어 처리 중임을 나타냅니다.
     100 // Continue: 요청의 일부를 받은 상태이며, 나머지를 계속 보내도 됨
     101 // Switching Protocols: 서버가 요청에 따라 프로토콜 전환을 수행 중
     102 // Processing: 서버가 요청을 받았으며 처리가 진행 중임을 나타냄 (웹DAV)


     // 2xx: Success - 요청이 성공적으로 처리되었음을 나타냅니다.
     200 // OK: 요청이 성공적으로 처리됨
     201 // Created: 요청이 성공적으로 처리되었으며, 새로운 리소스가 생성됨
     202 // Accepted: 요청이 수락되었으나, 아직 처리가 완료되지 않음
     203 // Non-Authoritative Information: 프록시 서버를 통해 제공된 응답으로 원본 서버와 다를 수 있음
     204 // No Content: 요청이 성공적으로 처리되었으나, 반환할 콘텐츠가 없음
     205 // Reset Content: 요청이 성공적으로 처리되었으며, 콘텐츠를 초기화할 것을 권장함
     206 // Partial Content: 부분 요청이 성공적으로 처리됨


     // 3xx: Redirection - 요청 완료를 위해 추가 동작이 필요함을 나타냅니다.
     300 // Multiple Choices: 여러 응답이 가능함을 나타냄
     301 // Moved Permanently: 요청한 리소스가 새로운 URL로 이동했음
     302 // Found: 요청한 리소스가 임시적으로 다른 URL에 있음
     303 // See Other: 클라이언트가 다른 URL로 GET 요청을 해야 함
     304 // Not Modified: 요청한 리소스가 수정되지 않았음 (캐싱 관련)
     307 // Temporary Redirect: 요청한 리소스가 임시적으로 다른 URL에 있음 (같은 HTTP 메소드 사용)
     308 // Permanent Redirect: 요청한 리소스가 영구적으로 다른 URL로 이동함 (같은 HTTP 메소드 사용)


     // 4xx: Client Error - 클라이언트 측 오류를 나타냅니다.
     400 // Bad Request: 잘못된 요청으로 서버가 요청을 이해하지 못함
     401 // Unauthorized: 인증이 필요하며, 인증이 유효하지 않음
     402 // Payment Required: 결제 요구 (현재 거의 사용되지 않음)
     403 // Forbidden: 서버가 요청을 이해했으나, 권한이 없어 거부됨
     404 // Not Found: 요청한 리소스를 찾을 수 없음
     405 // Method Not Allowed: 요청한 메소드가 허용되지 않음
     406 // Not Acceptable: 클라이언트의 요청이 허용되지 않는 형태로 되어 있음
     407 // Proxy Authentication Required: 프록시 인증이 필요함
     408 // Request Timeout: 클라이언트의 요청 시간이 초과됨
     409 // Conflict: 요청이 현재 서버 상태와 충돌됨
     410 // Gone: 요청한 리소스가 영구적으로 삭제됨
     411 // Length Required: 요청에 Content-Length 헤더가 필요함
     412 // Precondition Failed: 사전 조건이 실패하여 요청이 거부됨
     413 // Payload Too Large: 요청한 데이터의 크기가 너무 큼
     414 // URI Too Long: 요청 URI가 너무 길어 서버가 처리하지 못함
     415 // Unsupported Media Type: 지원되지 않는 미디어 타입임
     416 // Range Not Satisfiable: 요청한 범위가 유효하지 않음
     417 // Expectation Failed: 요청의 Expect 헤더 요구사항이 충족되지 않음
     418 // I'm a teapot: 1998년 만우절 RFC로 정의된 에러 코드 (농담)
     421 // Misdirected Request: 요청이 적절하지 않은 서버로 전송됨
     426 // Upgrade Required: 클라이언트가 프로토콜을 업그레이드해야 함
     428 // Precondition Required: 조건이 필요함
     429 // Too Many Requests: 클라이언트가 너무 많은 요청을 보냄
     431 // Request Header Fields Too Large: 헤더 필드가 너무 큼
     451 // Unavailable For Legal Reasons: 법적 이유로 접근이 제한됨


     // 5xx: Server Error - 서버 측 오류를 나타냅니다.
     500 // Internal Server Error: 서버 오류로 요청을 처리할 수 없음
     501 // Not Implemented: 서버가 요청한 기능을 지원하지 않음
     502 // Bad Gateway: 게이트웨이나 프록시 서버에서 유효하지 않은 응답을 받음
     503 // Service Unavailable: 서버가 일시적으로 과부하 또는 유지 보수 중임
     504 // Gateway Timeout: 게이트웨이 또는 프록시 서버에서 응답 시간이 초과됨
     505 // HTTP Version Not Supported: 서버가 HTTP 버전을 지원하지 않음
     507 // Insufficient Storage: 서버에 저장 공간이 부족함 (웹DAV)
     508 // Loop Detected: 서버가 루프를 감지했음 (웹DAV)
     510 // Not Extended: 요청에 필요한 확장이 누락됨
     511 // Network Authentication Required: 네트워크 인증이 필요함
     **/

    // Success Code ------------------------------------------------------------




    /** COMMON **/
    SELECT_SUCCESS(200,"S001","SELECT SUCCESS!"),
    UPDATE_SUCCESS(200,"S002","UPDATE SUCCESS!"),
    INSERT_SUCCESS(201,"S003","INSERT SUCCESS!"),
    DELETE_SUCCESS(204,"S004","DELETE SUCCESS!"),



    /** AUTH **/


    REGISTRATION_SUCCESS(200,"S006","REGISTRATION_SUCCESS"),
    JWT_REFRESH_SUCCESS(200,"S007","JWR REFRESH_SUCCESS"),
    LOGIN_SUCCESS(200,"S005","LOGIN SUCCESS"),
    LOGOUT_SUCCESS(200,"S006","LOGOUT SUCCESS"),
    WITHDRAWAL_SUCCESS(200,"S007","WITHDRAWAL_SUCCESS"),




    // Success Code END ---------------------------------------------------------




    // Error Code ---------------------------------------------------------------

    //// Servlet filters

    FILTER_EXCEPTION(500,"E029","EXCEPTION OCCURS DURING FILTER PROCESSING"),





    INVALID_REQUEST(400,"E004", "INVALID REQUEST! PLEASE CHECK HTTP REQUEST BODY"),


    //// Article
    ARTICLE_NOT_EXIST(404,"E001","REQUESTED ARTICLE NOT EXIST IN DB"),
    ARTICLE_REPO_EMPTY(404,"E002","ARTICLE REPOSITORY EMPTY"),



    //// Member

    MEMBER_NOT_EXIST(404,"E003","REQUESTED MEMBER NOT EXIST IN DB"),




    //// Comment
    COMMENT_NOT_EXIST(404,"E005","REQUESTED COMMENT NOT EXIST IN DB"),
    COMMENT_REPO_EMPTY(404,"E006","COMMENT REPOSITORY EMPTY"),


    // Auth

    /** JWT **/

    HEADER_NOT_FOUND(403,"E025","(JWT) REQUIRED HEADER NOT FOUND IN REQUEST"),
    INVALID_TOKEN_SIGNATURE(403,"E023","INVALID JWT TOKEN SIGNATURE"),
    INVALID_TOKEN_FORMAT (403,"E024","INVALID JWT TOKEN FORMAT"),
    TOKEN_EXPIRED (403,"E017","JWT TOKEN EXPIRED"),
    TOKEN_NOT_FOUND(403,"E019","JWT TOKEN NOT FOUND IN HEADER"),
    TOKEN_BLACKLISTED(403,"E025","JWT TOKEN BLACKLISTED"),


    /** AUTHORIZATION **/

    AUTHORIZATION_FAILED(401,"E026","AUTHORIZATION PROCESS FAILED"),
    INAPPROPRIATE_AUTHORITY(403,"E027","INAPPROPRIATE AUTHORITY TO ACCESS"),


    /** AUTHENTICATION **/

    AUTHENTICATION_FAILED(401,"E027","AUTHENTICATION PROCESS FAILED"),
    AUTHENTICATION_REQUIRED(403,"E028","AUTHENTICATION IS REQUIRED TO ACCESS"),


    /** ENCRYPTION **/

    HASHING_STRATEGY_NOT_SET (500,"E015","HASHING STRATEGY NOT SET"),
    ENCRYPTION_STRATEGY_NOT_SET(500,"E016","ENCRYPTION STRATEGY NOT SET"),


    /** SECRET KEY **/

    KEY_GENERATE_FAIL (500,"E011","FAIL TO GENERATE KEY"),
    KEY_TYPE_NOT_FOUND (500, "E013"," INVALID KEY TYPE"),
    KEY_NOT_EXIST (404, "E007", "KEY NOT EXISTS IN KEYSTORE"),
    KEY_SAVED_WRONG_KEYSTORE(500,
                                "E012",
                                "SYMMETRIC KEY -> JCEKS, OTHERS -> PKCS12"),
    KEY_NOT_LOADED(500,"E014","KEY NOT LOADED ON MEMORY"),


    /** KEYSTORE **/

    KEYSTORE_NOT_EXIST (404, "E008", "KEYSTORE NOT EXIST"),
    KEYSTORE_DELETE_FAIL (500, "E009","FAIL TO DELETE KEYSTORE"),
    KEYSTORE_NOT_LOADED(500,"E010","KEYSTORE NOT LOADED ON MEMORY"),


    //
    INDISCERNIBLE_EXCEPTION (500,"E999", "INDISCERNIBLE EXCEPTION HAPPENED"),


    // Error Code END ------------------------------------------------------------

    ;



    private final int status;

    private final String divisionCode;

    private final String message;

    StatusCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }





}
