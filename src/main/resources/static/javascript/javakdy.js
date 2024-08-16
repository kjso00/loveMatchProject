// 전역 변수 선언
var markers = []; // markers 배열을 선언
var map, ps, infowindow, placeOverlay, currCategory = ''; // 지도, 장소 검색 객체, 인포윈도우, 커스텀 오버레이, 현재 선택된 카테고리
let currentPlaces = [];


// DOM이 완전히 로드된 후 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {
    // 지도와 관련된 설정
    var mapContainer = document.getElementById('map'); // 지도를 표시할 div 요소
    var mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심 좌표 (서울 시청)
        level: 3 // 지도의 확대 레벨
    };

    // 지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);
    // 장소 검색 객체 생성
    ps = new kakao.maps.services.Places();
    // 인포윈도우 생성
    infowindow = new kakao.maps.InfoWindow({zIndex:1});
    // 커스텀 오버레이 생성
    placeOverlay = new kakao.maps.CustomOverlay({zIndex:1});

    // 카테고리 클릭 이벤트 등록
    addCategoryClickEvent();
    displayFavorites();

});




// 통합 검색 함수
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.trim()) {
        alert('키워드를 입력해주세요!');
        return;
    }
    ps.keywordSearch(keyword, placesSearchCB, {category_group_code: currCategory});
}

// 카테고리 클릭 이벤트 등록 함수
function addCategoryClickEvent() {
    var category = document.getElementById('category');
    if (category) {
        var children = category.children;
        for (var i = 0; i < children.length; i++) {
            children[i].onclick = onClickCategory;
        }
    } else {
        console.error('Category container not found');
    }
}

// 카테고리 클릭 핸들러
function onClickCategory() {
    var id = this.id;
    var className = this.className;

    if (className === 'on') {
        currCategory = '';
    } else {
        currCategory = id;
        var keyword = document.getElementById('keyword').value;
        if (!keyword.trim()) {
            alert('키워드를 입력해주세요!');
            return;
        }
    }

    changeCategoryClass(this);
    searchPlaces();
}

// 검색 완료 후 결과 처리
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        currentPlaces = data;
        console.log('currentPlaces updated:', currentPlaces);
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

// 검색 버튼에 이벤트 리스너 추가
var searchButton = document.getElementById('search-button');
if (searchButton) {
    searchButton.addEventListener('click', searchPlaces); // 검색 버튼 클릭 시 검색 함수 호출
} else {
    console.error('Search button not found'); // 검색 버튼을 찾지 못한 경우 에러 로그
}

// 검색 결과 목록과 마커를 표출하는 함수
function displayPlaces(places) {
    console.log('displayPlaces 함수 호출됨', places.length);
    currentPlaces = places; // 이 라인 추가
    var listEl = document.getElementById('placesList');
    var menuEl = document.getElementById('menu_wrap');
    var fragment = document.createDocumentFragment();
    var bounds = new kakao.maps.LatLngBounds();

    // 검색 결과 목록에 추가된 항목들을 제거
    removeAllChildNodes(listEl);

    // 지도에 표시되고 있는 마커를 제거
    removeMarker();

    for (var i = 0; i < places.length; i++) {
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        var marker = addMarker(placePosition, i, places[i]);
        var itemEl = getListItem(i, places[i]);

        bounds.extend(placePosition);

        (function(marker, place) {
            kakao.maps.event.addListener(marker, 'click', function() {
                displayPlaceInfo(place);
            });

            itemEl.onclick = function() {
                displayPlaceInfo(place);
                map.panTo(new kakao.maps.LatLng(place.y, place.x));
            };
        })(marker, places[i]);

        fragment.appendChild(itemEl);
    }

    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    map.setBounds(bounds);
}

// 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    var searchButton = document.getElementById('search-button');
    var categoryButtons = document.getElementsByClassName('category-btn');
    for (var i = 0; i < categoryButtons.length; i++) {
        categoryButtons[i].addEventListener('click', onClickCategory);
    }
    if (searchButton) {
        searchButton.addEventListener('click', searchPlaces);
    }
});

    // 페이지 네비게이션 표시
    if (pagination) {
        displayPagination(pagination);

}

// 검색결과 항목을 Element로 반환하는 함수

function getListItem(index, places) {
    var el = document.createElement('li'), // 리스트 아이템 생성
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
            '<div class="info">' +
            '   <h5>' + places.place_name + '</h5>'; // 장소 이름

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' + // 도로명 주소
            '   <span class="jibun gray">' + places.address_name + '</span>'; // 지번 주소
    } else {
        itemStr += '    <span>' + places.address_name + '</span>'; // 지번 주소
    }

    itemStr += '  <span class="tel">' + places.phone + '</span>' + // 전화번호
        '</div>';

    el.innerHTML = itemStr;
    el.className = 'item'; // 리스트 아이템에 클래스 추가

    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수
function addMarker(position, idx, place) {
    var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 URL
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지 크기
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지 크기
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 이미지 내 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions), // 마커 이미지 객체 생성
        marker = new kakao.maps.Marker({
            position: position, // 마커 위치
            image: markerImage
        });

    marker.setMap(map); // 지도에 마커 표출
    markers.push(marker); // 마커 배열에 추가

    // 마커 클릭 이벤트 추가
    kakao.maps.event.addListener(marker, 'click', function() {
        displayPlaceInfo(place);
    });

    return marker;
}

// 현재 지도에 표시된 모든 마커를 제거하는 함수
function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null); // 지도에서 마커 제거
    }
    markers = []; // 마커 배열 초기화
}

// 검색 결과가 여러 페이지로 나눠져 있을 경우 페이지 번호를 표시하고 이동할 수 있도록 함
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'), // 페이지 네비게이션을 표시할 엘리먼트
        fragment = document.createDocumentFragment(), // 문서 조각
        i;

    // 페이지 번호를 표시할 li 엘리먼트 생성
    for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;
        el.className = i === pagination.current ? 'on' : '';
        el.onclick = (function(i) {
            return function() {
                pagination.gotoPage(i); // 페이지 이동
            };
        })(i);

        fragment.appendChild(el);
    }

    // 기존 페이지 번호를 제거하고 새 페이지 번호를 추가
    removeAllChildNodes(paginationEl);
    paginationEl.appendChild(fragment);
}

// 인포윈도우를 표시하는 함수
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>'; // 인포윈도우 내용
    infowindow.setContent(content); // 인포윈도우 내용 설정
    infowindow.open(map, marker); // 인포윈도우 열기
}

// 장소 정보 표시 함수 구현
function displayPlaceInfo(place) {
    var content = '<div class="infowindow-content">' +
        '<h5>' + place.place_name + '</h5>' +
        '<p>' + place.address_name + '</p>' +
        '<p>' + place.phone + '</p>' +
        '<button onclick="toggleFavorite(\'' + place.id + '\')" data-place-id="' + place.id + '">' +
        (isFavorite(place.id) ? '찜 해제' : '찜하기') +
        '</button>' +
        '<br><a href="https://map.kakao.com/link/map/' + place.place_name + ',' + place.y + ',' + place.x + '" style="color:blue" target="_blank">큰지도보기</a> ' +
        '<a href="https://map.kakao.com/link/to/' + place.place_name + ',' + place.y + ',' + place.x + '" style="color:blue" target="_blank">길찾기</a>' +
        '<button onclick="infowindow.close()">닫기</button>' +
        '</div>';

    infowindow.setContent(content);
    infowindow.setPosition(new kakao.maps.LatLng(place.y, place.x));
    infowindow.open(map);
}

    // 인포윈도우 닫기 버튼 추가
    var closeBtn = document.createElement('button');
    closeBtn.innerHTML = '닫기';
    closeBtn.onclick = function() {
        infowindow.close();
    };
    infowindow.setContent(infowindow.getContent() + closeBtn.outerHTML);




function toggleFavorite(placeId) {
    let favorites = localStorage.getItem('favorites');
    favorites = favorites ? favorites.split(',') : [];

    let index = favorites.indexOf(placeId);
    if (index > -1) {
        favorites.splice(index, 1); // 찜 제거
        console.log('찜 제거:', placeId);
    } else {
        favorites.push(placeId); // 찜 추가
        console.log('찜 추가:', placeId);
    }

    function displayFavorites() {
        let favorites = localStorage.getItem('favorites');
        favorites = favorites ? favorites.split(',') : [];

        let favoritesList = document.getElementById('favoritesList');
        favoritesList.innerHTML = '';
        favorites.forEach(placeId => {
            let place = currentPlaces.find(p => p.id === placeId);
            if (place) {
                let li = document.createElement('li');
                li.textContent = place.place_name;
                favoritesList.appendChild(li);
            }
        });
    }

    localStorage.setItem('favorites', favorites.join(','));
    updateFavoriteButton(placeId);

    // 인포윈도우 내용 업데이트
    var content = infowindow.getContent();
    content = content.replace(
        isFavorite(placeId) ? '찜하기' : '찜 해제',
        isFavorite(placeId) ? '찜 해제' : '찜하기'
    );
    infowindow.setContent(content);
}

// 새로운 함수 추가
function isFavorite(placeId) {
    let favorites = localStorage.getItem('favorites');
    favorites = favorites ? favorites.split(',') : [];
    return favorites.includes(placeId);
}


function updateFavoriteButton(placeId) {
    let button = document.querySelector(`button[data-place-id="${placeId}"]`);
    if (button) {
        button.textContent = isFavorite(placeId) ? '찜 해제' : '찜하기';
    }
    displayFavorites();
}


// 검색 결과 목록을 모두 제거하는 함수
function removeAllChildNodes(parent) {
    if (parent && parent.hasChildNodes()) {
        while (parent.firstChild) {
            parent.removeChild(parent.firstChild);
        }
    }
}

// 선택된 카테고리의 스타일을 변경하는 함수
function changeCategoryClass(el) {
    var category = document.getElementById('category'),
        children = category.children;

    for (var i = 0; i < children.length; i++) {
        children[i].className = ''; // 모든 카테고리의 클래스 제거
    }

    if (el) {
        el.className = 'on'; // 선택된 카테고리 스타일 적용
    }
}
