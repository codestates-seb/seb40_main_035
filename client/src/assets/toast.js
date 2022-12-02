import Swal from 'sweetalert2';

export const notiSuccess = (message) => {
  Swal.fire({
    text: message,
    icon: 'success',
    confirmButtonText: '확인',
  });
};
export const notiError = (message) => {
  Swal.fire({
    text: message,
    icon: 'error',
    confirmButtonText: '확인',
  });
};

export const notiConfirm = (message) => {
  Swal.fire({
    title: message,
    icon: 'question',
    showCancelButton: true,
    closeOnConfirm: false,
    closeOnCancel: true,
    confirmButtonText: '확인',
    denyButtonText: '취소',
  });
};

export const notiInfo = (message) => {
  Swal.fire({
    text: message,
    icon: 'info',
    confirmButtonText: '확인',
  });
};
