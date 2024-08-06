// 페이지가 로드되면 실행되는 이벤트 리스너
document.addEventListener('DOMContentLoaded', function() {
    // DOM 요소 선택
    const categoryButtons = document.querySelectorAll('.category-btn');
    const subcategoryContainer = document.querySelector('.subcategory-container');
    const contentList = document.querySelector('.content-list');
    const locationSearch = document.getElementById('location-search');
    const nearbyBtn = document.getElementById('nearby-btn');

    // 카테고리별 서브카테고리 정의
    const subcategories = {
        view: ['연극', '공연', '전시회'],
        enjoy: ['테마파크', '원데이클래스', '사주/타로'],
        eat: ['한식', '일식&양식', '카페']
    };

    // 카테고리 버튼에 이벤트 리스너 추가
    categoryButtons.forEach(button => {
        button.addEventListener('click', function() {
            const category = this.dataset.category;
            showSubcategories(category);
            fetchContents(category);
        });
    });

    // 서브카테고리 표시 함수
    function showSubcategories(category) {
        subcategoryContainer.innerHTML = '';
        subcategories[category].forEach(sub => {
            const button = document.createElement('button');
            button.textContent = sub;
            button.addEventListener('click', () => fetchContents(category, sub));
            subcategoryContainer.appendChild(button);
        });
    }

    // 컨텐츠 가져오기 함수 (API 호출 필요)
    function fetchContents(category, subcategory = null) {
        // TODO: 실제 API 호출 구현
        // 현재는 더미 데이터 사용
        const dummyData = [
            { name: '장소 1', image: 'path/to/image1.jpg', rating: 4.5 },
            { name: '장소 2', image: 'path/to/image2.jpg', rating: 4.2 },
            { name: '장소 3', image: 'path/to/image3.jpg', rating: 4.8 },
            { name: '장소 4', image: 'path/to/image4.jpg', rating: 4.0 },
            { name: '장소 5', image: 'path/to/image5.jpg', rating: 4.7 },
        ];

        displayContents(dummyData);
    }

    // 컨텐츠 표시 함수
    function displayContents(data) {
        contentList.innerHTML = '';
        data.forEach(item => {
            const div = document.createElement('div');
            div.className = 'content-item';
            div.innerHTML = `
                <img src="${item.image}" alt="${item.name}">
                <h3>${item.name}</h3>
                <p>평점: ${item.rating}</p>
            `;
            contentList.appendChild(div);
        });
    }

    // 지역 검색 이벤트 리스너
    locationSearch.addEventListener('input', function() {
        // TODO: API 호출하여 검색 결과 가져오기
        console.log('Searching for:', this.value);
    });

    // 내 주변 버튼 이벤트 리스너
    nearbyBtn.addEventListener('click', function() {
        // TODO: 사용자의 위치를 가져오고 API 호출하기
        console.log('Fetching nearby places');
    });
});