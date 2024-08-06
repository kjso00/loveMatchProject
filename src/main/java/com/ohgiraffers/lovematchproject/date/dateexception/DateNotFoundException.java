package com.ohgiraffers.lovematchproject.date.dateexception;

/**
 * 요청한 데이트 정보를 찾을 수 없을 때 발생하는 예외
 */
public class DateNotFoundException extends DateException {

    /**
     * 지정된 ID로 데이트 정보를 찾을 수 없을 때 새로운 DateNotFoundException을 구성
     *
     * @param dateId 찾을 수 없는 데이트의 ID
     */
    public DateNotFoundException(Long dateId) {
        super(String.format("ID가 %d인 데이트 정보를 찾을 수 없습니다.", dateId));
    }
}