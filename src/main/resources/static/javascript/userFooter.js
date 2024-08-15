async function fetchUserInfo() {
    try {
        const response = await fetch('/logininfo/userinfo'); // 서버에 사용자 정보 요청함. 세션id는 쿠키를 통해 전달됨
        if (!response.ok) { // 응답이 없으면
            throw new Error('Failed to fetch user info'); // 에러발생
        }
        return await response.json(); // json으로 응답받음
    } catch (error) { // 에러발생하면
        console.error('Error fetching user info:', error); // 에러메세지 출력
        return null; // null값 반환
    }
}

async function renderPage() {
    const userInfo = await fetchUserInfo(); // 가져온 사용자 정보를 userInfo에 저장
    // HTML에서 id가 "user-info"인 div 요소를 가져옵니다.
    const userInfoDiv = document.getElementById('user-info'); // html에서 user-info를 가져옴(div id=user-info)

    if(userInfo) {
        console.log('User Info:', userInfo); // debug
        console.log('username:', userInfo.name); // debug
        console.log('authority:', userInfo.authority); // debug

        if (userInfo.authority === 'USER') { // 사용자 권한이 USER 면
            userInfoDiv.innerHTML = `<p>${userInfo.name}</p>`;
        } else if (userInfo.authority === 'ADMIN') { // 관리자가 로그인 했을 경우
            userInfoDiv.innerHTML = `<button onclick="location.href='/login/admin'" class="default-btn" style="width: 100px;">${userInfo.name}<br>관리자</button>`;
        }
    } else {
        userInfoDiv.innerHTML = `<p>로그인 정보가 없습니다.</p>`;
    }
}
document.addEventListener('DOMContentLoaded', renderPage);