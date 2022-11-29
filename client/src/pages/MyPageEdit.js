import { useRecoilValue } from 'recoil';
import styled from 'styled-components';
import { userProfileState } from '../atom/atom';
import InterestSelect from '../components/InterestSelect';
import LevelSelect from '../components/LevelSelect';
import MiniButton from '../components/MiniButton';
import SkillStackSelect from '../components/SkillStackSelect';
import avatar from '../assets/image/userAvatar.png';

const MypageEditContainer = styled.div`
  background-color: var(--purple-light);
  width: 100vw;
  height: 100vh;
`;
const Wrapper = styled.section`
  margin-top: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  .save-btn {
    margin-top: 30px;
    width: 600px;
    display: flex;
    justify-content: flex-end;
  }
`;
const MyInfoContainer = styled.form`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 600px;
  height: 250px;
  background-color: white;
  border-radius: 8px;

  .user-info {
    display: flex;
    flex-direction: column;
    font-size: 15px;
    width: 300px;
  }
  label {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
  }
  .user-img {
    padding: 30px;
    img {
      width: 130px;
      height: 130px;
    }
  }
  .level-select-view {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  input {
    width: 200px;
    font-size: 13px;
    padding: 15px;
    outline: none;
    height: 27px;
    border-radius: 8px;
    background-color: var(--purple-light);
    border: 0;
    /* border: 1px solid var(--purple-medium); */
    transition: 300ms ease-in-out;
    white-space: nowrap;

    &:hover,
    &:focus,
    &:active {
      border-color: var(--purple);
    }

    &:focus,
    &:active {
      box-shadow: 0px 0px 0px 4px var(--purple-medium);
    }
  }

  .github-info {
    margin-left: 50px;
  }
`;
const ViewContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
  margin-top: 25px;
  width: 600px;
  height: auto;
  background-color: white;
  border-radius: 8px;
  padding: 30px 50px;

  span {
    margin-bottom: 25px;
    font-size: 15px;
  }
`;

const MyPageEdit = () => {
  const profileData = useRecoilValue(userProfileState);
  return (
    <MypageEditContainer>
      <Wrapper>
        <MyInfoContainer>
          <div className="user-img">
            <img src={avatar} alt={`${profileData.name}'의 프로필 이미지`} />
          </div>
          <div className="user-info">
            <label htmlFor="user-nickname">
              <span>닉네임</span>
              <input id="user-nickname" className="input-user-nickname"></input>
            </label>
            <label htmlFor="user-description">
              <span>한 줄 소개</span>
              <input
                id="user-description"
                className="input-user-decription"
              ></input>
            </label>
            <label htmlFor="level-select-view">
              <span>숙련도</span>
              <LevelSelect />
            </label>

            <label htmlFor="github-url">
              <span>깃허브</span>
              <input id="github-url" className="github-info"></input>
            </label>
          </div>
        </MyInfoContainer>
        {/* 기술 선택란 */}
        <ViewContainer>
          <span>사용할 기술 스택을 태그로 표현해 주세요.</span>
          <SkillStackSelect />
        </ViewContainer>
        {/* 관심 분야 선택란 */}
        <ViewContainer>
          <span>관심 분야를 선택해 주세요.</span>
          <InterestSelect />
        </ViewContainer>
        <div className="save-btn">
          <MiniButton text="저장하기" />
        </div>
      </Wrapper>
    </MypageEditContainer>
  );
};

export default MyPageEdit;
