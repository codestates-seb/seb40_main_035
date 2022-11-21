import { atom } from 'recoil';
// import { recoilPersist } from 'recoil-persist';
// const { persistAtom } = recoilPersist();

// 기술 스택 탭 메뉴 클릭 시, 활성화 탭 식별 위한 상태
export const activeIdxState = atom({
  key: 'activeIdxState',
  default: 0,
});

// 선택 or 입력된 기술 스택 목록 상태
export const selectedTagsState = atom({
  key: 'selectedTagsState',
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
  default: [
    {
      interestId: 1,
      name: '교육',
    },
    {
      interestId: 2,
      name: '에너지/친환경',
    },
  ],
});

export const userProfileState = atom({
  key: 'userProfileState',
  default: {
    memberId: 1,
    email: 'hgd@gmail.com',
    password: 'hgd1234!',
    name: '홍길동',
    description: '길동이입니다',
    level: '학생',
    github: 'github.com/honggildong',
  },
});

export const currentUserState = atom({
  key: 'isLoginState',
  default: {
    memberId: 1,
  },
});
