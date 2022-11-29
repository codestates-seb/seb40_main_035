import { atom } from 'recoil';
// import { recoilPersist } from 'recoil-persist';
// const { persistAtom } = recoilPersist();

// 기술 스택 탭 메뉴 클릭 시, 활성화 탭 식별 위한 상태
export const activeIdxState = atom({
  key: 'activeIdxState',
  default: 0,
});

// 기술 스택 보기란 메뉴 클릭 시, 활성화 탭 식별 위한 상태
export const activeMenuState = atom({
  key: 'activeMenuState',
  default: 0,
});
// 선택 or 입력된 기술 스택 목록 상태
export const selectedSkillstacksState = atom({
  key: 'selectedSkillstacksState',
  default: [],
});

// 입력된 프론트엔드 참여 인원 상태
export const feNumberState = atom({
  key: 'feNumberState',
  default: 0,
});

// 입력된 백엔드 참여 인원 상태
export const beNumberState = atom({
  key: 'beNumberState',
  default: 0,
});

// 프로젝트 시작 날짜
export const startDateState = atom({
  key: 'startDateState',
  default: null,
});

// 프로젝트 종료 날짜
export const endDateState = atom({
  key: 'endDateState',
  default: null,
});

// 선택된 관심 분야 상태
export const selectedInterestsState = atom({
  key: 'selectedInterestState',
  default: [],
});

// 사용자의 관심 분야 목록 상태
export const interestViewState = atom({
  key: 'interestViewState',
  default: [],
});

// 사용자의 기술 스택 목록 상태
export const skillStackViewState = atom({
  key: 'skillStackViewState',
  default: [
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
  ],
});

// 유저 프로필 기본 정보
export const userProfileState = atom({
  key: 'userProfileState',
  default: {},
});

// 로그인된 유저의 정보 (유저 아이디)
export const currentUserState = atom({
  key: 'isLoginState',
  default: {
    memberId: 15,
  },
});

// 입력된 해시태그 목록 상태
export const inputHashTagsState = atom({
  key: 'inputHashTagsState',
  default: [],
});

// 게시글 목록
export const articlesListState = atom({
  key: 'articlesListState',
  default: [],
});

// 사용자가 모집한 프로젝트 목록 상태
export const recruitedArticlesState = atom({
  key: 'recruitedArticlesState',
  default: [],
});

// 사용자가 좋아요한 프로젝트 목록 상태
export const likedArticlesState = atom({
  key: 'likedArticlesState',
  default: [],
});

// 모달 오픈 상태
export const modalOpenState = atom({
  key: 'modalOpenState',
  default: false,
});

// 게시글 작성 시 제목 value 상태
export const inputTitleState = atom({
  key: 'inputTitleState',
  default: '',
});

// 게시글 작성 시 본문 value 상태
export const inputBodyState = atom({
  key: 'inputBodyState',
  default: '',
});

// inputTitle 유효성검사 상태
export const inputTitleCheckState = atom({
  key: 'inputTitleCheckState',
  default: true,
});

// inputBody 유효성검사 상태
export const inputBodyCheckState = atom({
  key: 'inputBodyCheckState',
  default: true,
});

// startDate 유효성검사 상태
export const startDateCheckState = atom({
  key: 'startDateCheckState',
  default: true,
});

// endDate 유효성검사 상태
export const endDateCheckState = atom({
  key: 'endDateCheckState',
  default: true,
});

// beNumber 유효성검사 상태
export const beNumberCheckState = atom({
  key: 'beNumberCheckState',
  default: true,
});

// feNumber 유효성검사 상태
export const feNumberCheckState = atom({
  key: 'feNumberCheckState',
  default: true,
});

// selectedInterests 유효성검사 상태
export const interestsCheckState = atom({
  key: 'interestsCheckState',
  default: true,
});

// selectedSkillstacks 유효성검사 상태
export const skillstacksCheckState = atom({
  key: 'skillstacksCheckState',
  default: true,
});

// hasghtag 유효성검사 상태
export const hashtagsCheckState = atom({
  key: 'hashtagsCheckState',
  default: true,
});

// 실행순서
export const nextState = atom({
  key: 'nextState',
  default: 1,
});

// 모집 중/ 완료 상태
export const isCompletedState = atom({
  key: 'isCompletedState',
  default: false,
});
