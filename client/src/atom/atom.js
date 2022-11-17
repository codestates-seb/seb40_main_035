import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const asideFocusState = atom({
  key: 'asideFocusState',
  default: '/',
  effects_UNSTABLE: [persistAtom],
});

export const startDateState = atom({
  key: 'startDateState',
  default: null,
});

export const endDateState = atom({
  key: 'endDateState',
  default: null,
});
