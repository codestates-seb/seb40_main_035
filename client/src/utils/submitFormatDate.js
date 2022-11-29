// 게시글 작성에 보내는 데이터 날짜 형식 변경
// 객체로 저장된 날짜형식을 yyyymmdd의 포멧으로 변경
const submitFormatDate = (date) => {
  date = String(date);
  date = new Date(date);
  let year = date.getFullYear(); //yyyy
  let month = 1 + date.getMonth(); //mm
  month = month >= 10 ? month : '0' + month; //month 두자리로 저장
  let day = date.getDate(); //dd
  day = day >= 10 ? day : '0' + day; //day 두자리로 저장
  return `${year + '' + month + '' + day}`; //yyyymmdd
};

export default submitFormatDate;
