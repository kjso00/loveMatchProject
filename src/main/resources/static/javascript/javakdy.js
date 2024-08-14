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
    var keyword = document.getElementById('keyword').value.trim();
    if (!keyword) {
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
    } else if (status === kakao.maps.services.Status.ERROR) {
        console.error('검색 중 오류가 발생했습니다.', {
            data: data,
            status: status,
            pagination: pagination
        });
        alert('검색 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
    }
}

// 검색 결과 표시 함수
function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    removeAllChildNodes(listEl);
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

// 검색결과 항목을 Element로 반환하는 함수입니다


// 검색된 장소의 목록 항목을 HTML 요소로 만들어 반환
function getListItem(index, places) {
    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
            '<div class="info">' +
            '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone  + '</span>' +
        '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 지도의 특정 위치에 마커를 추가
function addMarker(position, idx, title) {
    var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
        imageSize = new kakao.maps.Size(36, 37),
        imgOptions =  {
            spriteSize: new kakao.maps.Size(36, 691),
            spriteOrigin: new kakao.maps.Point(0, (idx*46)+10),
            offset: new kakao.maps.Point(13, 37)
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position,
            image: markerImage
        });

    marker.setMap(map);
    markers.push(marker);

    return marker;
}

//현재 지도에 표시된 모든 마커를 제거

function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색 결과가 여러 페이지로 나눠져 있을 경우, 페이지 번호를 표시하고 이동할 수 있도록 함
function displayPagination(pagination) {
    function displayPagination(pagination) {
        var paginationEl = document.getElementById('pagination'),
            fragment = document.createDocumentFragment(),
            i;

        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild(paginationEl.lastChild);
        }

        for (i=1; i<=pagination.last; i++) {
            var el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;

            if (i === pagination.current) {
                el.className = 'on';
            } else {
                el.onclick = (function(i) {
                    return function() {
                        pagination.gotoPage(i);
                    }
                })(i);
            }

            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    }

}

// 마커 위에 정보창을 표시

function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 주어진 요소의 모든 자식 노드를 제거
function removeAllChildNodes(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}



// 팝업 표시 함수
document.addEventListener("DOMContentLoaded", function() {
    var mapContainer = document.getElementById('map');
    var keywordInput = document.getElementById('keyword');
    var likeButton = document.getElementById('like-button');
    var mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울 시청 좌표
        level: 3 // 지도 확대 레벨
    };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 장소 검색 객체 생성
    ps = new kakao.maps.services.Places();

    // 인포윈도우 생성
    infowindow = new kakao.maps.InfoWindow({zIndex:1});

    // 찜하기 버튼 이벤트 리스너
    likeButton.addEventListener('click', function() {
        const placeName = document.getElementById('place-name').textContent;
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

    // 초기 검색 실행
    searchPlaces();
});