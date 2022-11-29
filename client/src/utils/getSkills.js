//기술스택 보기란 이전 정보 받아오기 위한 날짜 형식 변경
const getSkills = (res) => {
  let arrSkills = [
    {
      tabTitle: '프론트엔드',
      tabCont: [],
    },
    {
      tabTitle: '백엔드',
      tabCont: [],
    },
    {
      tabTitle: '기타',
      tabCont: [],
    },
  ];

  for (let el of res) {
    console.log(el);
    if (el.skillSort === '프론트엔드') {
      arrSkills[0].tabCont.push({
        skillId: arrSkills[0].tabCont + 1,
        name: el.name,
      });
    }
    if (el.skillSort === '백엔드') {
      arrSkills[1].tabCont.push({
        skillId: arrSkills[1].tabCont + 1,
        name: el.name,
      });
    }
    if (el.skillSort === '기타') {
      arrSkills[2].tabCont.push({
        skillId: arrSkills[2].tabCont + 1,
        name: el.name,
      });
    }
  }

  return arrSkills;
};

export default getSkills;
