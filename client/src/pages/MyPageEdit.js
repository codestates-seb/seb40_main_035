/* eslint-disable no-unused-vars */
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import styled from 'styled-components';
import {
  currentUserState,
  selectedInterestsState,
  selectedLevelState,
  selectedSkillstacksState,
  userProfileState,
} from '../atom/atom';
import InterestSelect from '../components/InterestSelect';
import LevelSelect from '../components/LevelSelect';
import MiniButton from '../components/MiniButton';
import SkillStackSelect from '../components/SkillStackSelect';
import avatar from '../assets/image/userAvatar.png';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import getEditSkills from '../utils/getEditSkills';

const MypageEditContainer = styled.div`
  background-color: var(--purple-light);
  /* width: 100%; */
  height: auto;
`;
const Wrapper = styled.section`
  margin-top: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  .save-btn {
    margin-top: 20px;
    width: 650px;
    display: flex;
    justify-content: flex-end;
    margin-bottom: 50px;
  }
`;
const MyInfoContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 650px;
  height: 220px;
  background-color: white;
  border-radius: 8px;

  .user-info {
    display: flex;
    flex-direction: column;
    font-size: 15px;
    width: 345px;
  }

  label {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 5px;
    margin-bottom: 5px;
  }
  .user-img {
    padding-right: 50px;
    img {
      width: 130px;
      height: 130px;
    }
  }

  input {
    width: 250px;
    font-size: 13px;
    padding: 10px;
    outline: none;
    height: 30px;
    border-radius: 8px;
    background-color: var(--purple-light);
    border: 1px solid var(--purple-medium);
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
  width: 650px;
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
  // 로그인 후, 응답으로 받아 오는 멤버아이디
  // const currentUser = useRecoilValue(currentUserState);

  //프로필카드 상태
  const [profileData, setProfileData] = useRecoilState(userProfileState);
  // 기술스택 상태
  const [selectedSkillstacks, setSelectedSkillstacks] = useRecoilState(
    selectedSkillstacksState,
  );
  // 관심분야 상태
  const [selectedInterests, setSelectedInterests] = useRecoilState(
    selectedInterestsState,
  );
  // 프로필 정보 상태
  const [profileBody, setProfileBody] = useState({});
  const { id } = useParams();
  const navaigate = useNavigate();
  const [selectedLevel, setSelectedLevel] = useRecoilState(selectedLevelState);

  useEffect(() => {
    // 마이페이지 프로필 정보 불러오기
    axios.get(`/members/${id}`).then((res) => {
      // 기술스택 상태
      setSelectedSkillstacks(getEditSkills(res.data.data.skills));
      // 관심분야 상태
      let profileInterests = [];
      for (const el of res.data.data.interests) {
        profileInterests.push(el.name);
      }
      setSelectedInterests(profileInterests);
      setSelectedLevel(res.data.data.level);
      // 유저 프로필 정보 상태
      setProfileBody({
        memberId: res.data.data.memberId,
        name: res.data.data.name,
        description: res.data.data.description,
        level: res.data.data.level,
        github: res.data.data.github,
      });
    });
  }, []);

  // 닉네임 수정 이벤트 핸들러
  const onChangeName = (e) => {
    setProfileBody((cur) => {
      const newProfileBody = { ...cur };
      newProfileBody.name = e.target.value;
      return newProfileBody;
    });
  };
  // 한 줄 소개 수정 이벤트 핸들러
  const onChangeDescription = (e) => {
    setProfileBody((cur) => {
      const newProfileBody = { ...cur };
      newProfileBody.description = e.target.value;
      return newProfileBody;
    });
  };
  // 깃허브 수정 이벤트 핸들러
  const onChangeGithub = (e) => {
    setProfileBody((cur) => {
      const newProfileBody = { ...cur };
      newProfileBody.github = e.target.value;
      return newProfileBody;
    });
  };

  // 수정 PATCH 요청
  const onUpload = () => {
    // 로그인한 유저
    // axios.patch(`/members/${currentUser.memberId}`, {
    // headers: {
    //   Authorization: '',
    // },
    let token = '';
    // 선택된 기술 스택 목록을 POST 데이터 형식으로 변경
    let selectedSkillstacksSubmit = selectedSkillstacks.map((el) => {
      let obj = { skillName: el.name };
      return obj;
    });
    axios
      .patch(
        `/members/${id}`,
        {
          memberId: profileBody.memberId,
          name: profileBody.name,
          level: selectedLevel,
          description: profileBody.description,
          github: profileBody.github,
          memberInterests: selectedInterests,
          memberSkills: selectedSkillstacksSubmit,
        },
        {
          headers: { Authorization: token },
        },
      )
      .then((res) => {
        // 저장 후 마이페이지로 이동
        navaigate(`/mypage/${id}`);
      });
  };
  return (
    <MypageEditContainer>
      <Wrapper>
        {/* 유저 프로필 정보란 */}
        <MyInfoContainer>
          <div className="user-img">
            <img src={avatar} alt={`${profileData.name}'의 프로필 이미지`} />
          </div>
          <div className="user-info">
            <label htmlFor="user-nickname">
              <span>닉네임</span>
              <input
                id="user-nickname"
                className="input-user-nickname"
                value={profileBody.name || ''}
                onChange={onChangeName}
              ></input>
            </label>
            <label htmlFor="user-description">
              <span>한 줄 소개</span>
              <input
                id="user-description"
                className="input-user-decription"
                value={profileBody.description || ''}
                onChange={onChangeDescription}
              ></input>
            </label>
            <div className="level">
              <label htmlFor="level-select-view">
                <span>숙련도</span>
                <LevelSelect />
              </label>
            </div>
            <label htmlFor="github-url">
              <span>깃허브</span>
              <input
                id="github-url"
                className="github-info"
                value={profileBody.github || ''}
                onChange={onChangeGithub}
              ></input>
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
          <MiniButton text="저장하기" onClick={onUpload} />
        </div>
      </Wrapper>
    </MypageEditContainer>
  );
};

export default MyPageEdit;
