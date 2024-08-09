// DOM이 로드되면 실행
document.addEventListener('DOMContentLoaded', function() {
    // DOM 요소 선택
    const categoryButtons = document.querySelectorAll('.category-btn');
    const subcategoryButtons = document.querySelector('.subcategory-buttons');
    const dateList = document.getElementById('date-list');
    const keywordSearch = document.getElementById('keyword-search');
    const nearbyBtn = document.getElementById('nearby-btn');
    const locationBtn = document.getElementById('location-btn');
    const searchBtn = document.getElementById('search-btn');

    // 카테고리 버튼 이벤트 리스너
    categoryButtons.forEach(button => {
        button.addEventListener('click', function() {
            const category = this.dataset.category;
            updateSubcategories(category);
            fetchDates(category);
        });
    });

    // 키워드 검색 이벤트 리스너
    keywordSearch.addEventListener('input', debounce(function() {
        searchByKeyword(this.value);
    }, 300));

    // 내 주변 버튼 이벤트 리스너
    nearbyBtn.addEventListener('click', searchNearby);

    // 지역 설정 버튼 이벤트 리스너
    locationBtn.addEventListener('click', openLocationModal);

    // 검색 버튼 이벤트 리스너
    searchBtn.addEventListener('click', performSearch);

    // 서브카테고리 업데이트 함수
    function updateSubcategories(category) {
        // API를 호출하여 해당 카테고리의 서브카테고리를 가져옴
        fetch(`/api/subcategories?category=${category}`)
            .then(response => response.json())
            .then(data => {
                subcategoryButtons.innerHTML = data.map(sub =>
                    `<button class="subcategory-btn" data-subcategory="${sub.code}">${sub.name}</button>`
                ).join('');
                // 서브카테고리 버튼에 이벤트 리스너 추가
                document.querySelectorAll('.subcategory-btn').forEach(btn => {
                    btn.addEventListener('click', () => fetchDates(category, btn.dataset.subcategory));
                });
            });
    }

    // 데이트 장소 가져오기 함수
    function fetchDates(category, subcategory = null, page = 1) {
        let url = `/api/dates?category=${category}&page=${page}`;
        if (subcategory) url += `&subcategory=${subcategory}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                displayDates(data.content);
                updatePagination(data.totalPages, page);
            })
            .catch(error => {
                console.error('데이트 장소 가져오기 오류:', error);
                dateList.innerHTML = '<p>데이트 장소를 불러오는 데 실패했습니다. 다시 시도해주세요.</p>';
            });
    }

    // 데이트 장소 표시 함수
    function displayDates(dates) {
        dateList.innerHTML = dates.map(date => `
            <div class="date-item">
                <img src="${date.imageUrl}" alt="${date.name}">
                <h3>${date.name}</h3>
                <p>평점: ${date.averageRating}</p>
                <button onclick="showDateDetails(${date.id})">자세히 보기</button>
            </div>
        `).join('');
    }

    // 페이지네이션 업데이트 함수
    function updatePagination(totalPages, currentPage) {
        // 페이지네이션 로직 구현
    }

    // 키워드로 검색 함수
    function searchByKeyword(keyword) {
        fetch(`/api/dates/search?keyword=${encodeURIComponent(keyword)}`)
            .then(response => response.json())
            .then(data => displayDates(data));
    }

    // 내 주변 검색 함수
    function searchNearby() {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(position => {
                const { latitude, longitude } = position.coords;
                fetch(`/api/dates/nearby?lat=${latitude}&lon=${longitude}`)
                    .then(response => response.json())
                    .then(data => displayDates(data));
            }, error => {
                console.error('위치 정보 가져오기 실패:', error);
                alert('위치 정보를 가져올 수 없습니다. 위치 서비스를 활성화해주세요.');
            });
        } else {
            alert('이 브라우저에서는 위치 서비스를 지원하지 않습니다.');
        }
    }

    // 지역 설정 모달 열기 함수
    function openLocationModal() {
        // 지역 설정 모달 구현
    }

    // 검색 수행 함수
    function performSearch() {
        // 현재 선택된 카테고리, 서브카테고리, 키워드 등을 조합하여 검색 수행
    }

    // 디바운스 함수
    function debounce(func, delay) {
        let timeoutId;
        return function (...args) {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => func.apply(this, args), delay);
        };
    }
});

// 데이트 장소 상세 정보 표시 함수
function showDateDetails(dateId) {
    window.location.href = `/dates/${dateId}`;
}