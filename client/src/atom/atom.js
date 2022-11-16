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
