package com.ohgiraffers.lovematchproject.date.dateexception;

/**
 * 유효하지 않은 데이트 작업을 수행하려 할 때 발생하는 예외
 */
public class InvalidDateOperationException extends DateException {

    /**
     * 지정된 상세 메시지로 새로운 InvalidDateOperationException을 구성
     *
     * @param dateMessage 상세 메시지
     */
    public InvalidDateOperationException(String dateMessage) {
        super(dateMessage);
    }
}