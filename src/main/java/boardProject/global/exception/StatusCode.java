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


    //// Article
    ARTICLE_NOT_EXIST(404,"E001","REQUESTED ARTICLE NOT EXIST IN DB"),
    ARTICLE_REPO_EMPTY(404,"E002","ARTICLE REPOSITORY EMPTY"),



    //// Account

    ACCOUNT_NOT_EXIST(404,"E003","REQUESTED ACCOUNT NOT EXIST IN DB"),




    //// Comment



    //// Auth


    //
    INDISCERNIBLE_EXCEPTION (500,"E999", "INDISCERNIBLE EXCEPTION HAPPENED")


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
