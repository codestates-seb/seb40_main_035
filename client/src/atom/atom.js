import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const asideFocusState = atom({
  key: 'asideFocusState',
  default: '/',
  effects_UNSTABLE: [persistAtom],
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
  effects_UNSTABLE: [persistAtom],
});
