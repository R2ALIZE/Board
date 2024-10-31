package boardProject.global.exception;

import lombok.Getter;

@Getter
public enum StatusCode {

    // Success Code ------------------------------------------------------------

    SELECT_SUCCESS(200,"S001","SELECT SUCCESS!"),
    UPDATE_SUCCESS(200,"S002","UPDATE SUCCESS!"),
    INSERT_SUCCESS(201,"S003","INSERT SUCCESS!"),
    DELETE_SUCCESS(204,"S004","DELETE SUCCESS!"),




    // Success Code END ---------------------------------------------------------




    // Error Code ---------------------------------------------------------------

    INVALID_REQUEST(400,"E004", "INVALID REQUEST! PLEASE CHECK HTTP REQUEST BODY"),


    //// Article
    ARTICLE_NOT_EXIST(404,"E001","REQUESTED ARTICLE NOT EXIST IN DB"),
    ARTICLE_REPO_EMPTY(404,"E002","ARTICLE REPOSITORY EMPTY"),



    //// Member

    ACCOUNT_NOT_EXIST(404,"E003","REQUESTED ACCOUNT NOT EXIST IN DB"),




    //// Comment
    COMMENT_NOT_EXIST(404,"E005","REQUESTED COMMENT NOT EXIST IN DB"),
    COMMENT_REPO_EMPTY(404,"E006","COMMENT REPOSITORY EMPTY"),


    //// Auth

    HASHING_STRATEGY_NOT_SET (500,"E015","HASHING STRATEGY NOT SET"),

    ENCRYPTION_STRATEGY_NOT_SET(500,"E016","ENCRYPTION STRATEGY NOT SET"),




    KEY_GENERATE_FAIL (500,"E011","FAIL TO GENERATE KEY"),

    KEY_TYPE_NOT_FOUND (500, "E013"," INVALID KEY TYPE"),

    KEY_NOT_EXIST (404, "E007", "KEY NOT EXISTS IN KEYSTORE"),

    KEY_SAVED_WRONG_KEYSTORE(500,
                                "E012",
                                "SYMMETRIC KEY -> JCEKS, OTHERS -> PKCS12"),

    KEY_NOT_LOADED(500,"E014","KEY NOT LOADED ON MEMORY"),









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
