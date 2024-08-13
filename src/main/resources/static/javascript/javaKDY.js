var kakaoMapKey = kakaoMapKey || 'f21943e0271305ea9a7bb98bc1b976d2'; // 기본값 설정

// 전역 변수 선언
var markers = [];
var map;
var ps;
var infowindow;

// 페이지 로드 시 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {
    // 지도 컨테이너와 옵션 설정
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울 시청 좌표
            level: 3 // 지도 확대 레벨
        };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 장소 검색 객체 생성
    ps = new kakao.maps.services.Places();

    // 인포윈도우 생성
    infowindow = new kakao.maps.InfoWindow({zIndex:1});

    // 초기 검색 실행
    searchPlaces();
});

// 키워드 검색 함수
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }
    // 장소 검색 실행
    ps.keywordSearch(keyword, placesSearchCB);
}

// 장소 검색 콜백 함수
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        displayPlaces(data);
        displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

// 검색 결과 표시 함수
function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    removeAllChildNods(listEl);
    removeMarker();

    for ( var i=0; i<places.length; i++ ) {
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]);

        bounds.extend(placePosition);

        (function(marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });

            itemEl.onmouseover =  function () {
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    map.setBounds(bounds);
}

// 나머지 함수들 (getListItem, addMarker, removeMarker, displayPagination, displayInfowindow, removeAllChildNods)은
// 이전과 동일하게 유지하면 됩니다.

// 팝업 표시 함수
function showPopup(place) {
    document.getElementById('place-name').textContent = place.place_name;
    document.getElementById('place-address').textContent = place.address_name;
    document.getElementById('place-phone').textContent = place.phone;
    document.getElementById('popup').style.display = 'block';
}

// 팝업 닫기 함수
function closePopup() {
    document.getElementById('popup').style.display = 'none';
}

// 찜하기 버튼 이벤트 리스너
document.getElementById('like-button').addEventListener('click', function() {
    const placeName = document.getElementById('place-name').textContent;
    // TODO: 서버에 찜하기 요청 보내기
    fetch('/api/likes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            placeName: placeName,
            // 필요한 다른 데이터 추가
        }),
    })
        .then(response => response.json())
        .then(data => {
            alert('찜하기 완료!');
            // TODO: 필요한 경우 UI 업데이트
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('찜하기 실패. 다시 시도해주세요.');
        });
});