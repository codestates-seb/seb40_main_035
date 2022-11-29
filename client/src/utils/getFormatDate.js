//데이터 날짜 형식 변경해 이전 정보 받아오기 위한 날짜 형식 변경
const getFormatDate = (specific) => {
  let year = Number(specific.slice(0, 4));
  let month = Number(specific.slice(4, 6));
  if (month === 1) {
    month = 12;
  } else {
    month--;
  }
  let day = Number(specific.slice(6));

  return new Date(year, month, day);
};

export default getFormatDate;
