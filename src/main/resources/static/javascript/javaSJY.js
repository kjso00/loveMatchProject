// 공지사항 등록 폼 제출 처리
document.getElementById('noticeForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼 제출 시 페이지 새로고침 방지

    const noticeTitle = document.getElementById('noticeTitle').value.trim();
    const noticeContent = document.getElementById('noticeContent').value.trim();

    if (noticeTitle === '' || noticeContent === '') {
        alert('제목과 내용을 입력해주세요.');
        return;
    }

    const noticeData = {
        noticeTitle: noticeTitle,
        noticeContent: noticeContent
    };

    fetch('/insert', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noticeData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('공지사항이 성공적으로 등록되었습니다.');
                window.location.href = '/notice/admin/noticelist'; // 공지사항 목록 페이지로 리다이렉트
            } else {
                alert(data.message || '공지사항 등록에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error:', error));
});

// 공지사항 삭제 처리
function deleteNotice(id) {
    if (!confirm('정말 이 공지사항을 삭제하시겠습니까?')) {
        return;
    }

    fetch(`/notice/admin/noticelist/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ _method: 'DELETE' })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('공지사항이 삭제되었습니다.');
                window.location.reload(); // 페이지 새로고침
            } else {
                alert(data.message || '공지사항 삭제에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error:', error));
}

// 공지사항 수정 처리
document.getElementById('editNoticeForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼 제출 시 페이지 새로고침 방지

    const noticeId = document.getElementById('noticeId').value;
    const noticeTitle = document.getElementById('editNoticeTitle').value.trim();
    const noticeContent = document.getElementById('editNoticeContent').value.trim();

    if (noticeTitle === '' || noticeContent === '') {
        alert('제목과 내용을 입력해주세요.');
        return;
    }

    const noticeData = {
        noticeTitle: noticeTitle,
        noticeContent: noticeContent
    };

    fetch(`/notice/admin/noticelist/editpost/${noticeId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noticeData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('공지사항이 성공적으로 수정되었습니다.');
                window.location.href = `/notice/admin/noticelist/${noticeId}`; // 수정된 공지사항 상세 페이지로 이동
            } else {
                alert(data.message || '공지사항 수정에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error:', error));
});



