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
    INPUT_INVALID(400,"E001","INVALID INPUT DATA TYPE"),
    ARTICLE_NOT_EXIST(404,"E002","REQUESTED ARTICLE NOT EXIST IN DB")



    //// Member




    //// Comment



    //// Auth





    // Error Code END ------------------------------------------------------------

    ;



    private final int httpStatus;

    private final String divisionCode;

    private final String message;

    StatusCode(int httpStatus, String divisionCode, String message) {
        this.httpStatus = httpStatus;
        this.divisionCode = divisionCode;
        this.message = message;
    }





}
