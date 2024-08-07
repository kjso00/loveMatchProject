package com.ohgiraffers.lovematchproject.date.dateexception;

/**
 * 사용자의 데이트 찜하기 할당량이 초과되었을 때 발생하는 예외
 */
public class DateQuotaExceededException extends DateException {

    /**
     * 새로운 DateQuotaExceededException을 구성
     */
    public DateQuotaExceededException() {
        super("일일 또는 전체 데이트 찜하기 한도를 초과했습니다.");
    }
}