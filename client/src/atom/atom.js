import { atom } from 'recoil';
// import { recoilPersist } from 'recoil-persist';

// const { persistAtom } = recoilPersist();

export const selectedTagsState = atom({
  key: 'selectedTagsState',
  default: [],
});
