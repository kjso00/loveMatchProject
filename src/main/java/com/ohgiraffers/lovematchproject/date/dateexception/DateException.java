package com.ohgiraffers.lovematchproject.date.dateexception;

/**
 * 데이트 관련 예외의 기본 클래스
 * 모든 데이트 관련 사용자 정의 예외는 이 클래스를 상속받음
 */
public class DateException extends RuntimeException {

    /**
     * 지정된 상세 메시지를 가진 새로운 DateException을 구성함
     *
     * @param dateMessage 상세 메시지
     */
    public DateException(String dateMessage) {
        super(dateMessage);
    }

    /**
     * 지정된 상세 메시지와 원인을 가진 새로운 DateException을 구성함
     *
     * @param dateMessage 상세 메시지
     * @param dateCause 원인
     */
    public DateException(String dateMessage, Throwable dateCause) {
        super(dateMessage, dateCause);
    }
}