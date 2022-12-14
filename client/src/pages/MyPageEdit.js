/* eslint-disable no-unused-vars */
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import styled from 'styled-components';
import {
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
import { notiError, notiInfo, notiSuccess, notiToast } from '../assets/toast';

const MypageEditContainer = styled.div`
  background-color: var(--purple-light);
  min-height: calc(100vh - 62px);
`;
const Wrapper = styled.section`
  margin-top: 40px;
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
    flex-wrap: wrap;
    align-items: center;
    margin-top: 5px;
    margin-bottom: 5px;
    white-space: nowrap;
  }

  .user-img {
    padding-right: 50px;
    img {
      width: 130px;
      height: 130px;
    }
  }

  input {
    width: 100%;
    max-width: 250px;
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

  .github-wrapper {
    display: flex;
    align-items: center;

    input {
      margin-left: -5px;
    }

    button {
      margin-left: 10px;
    }
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
  //??????????????? ??????
  const [profileData, setProfileData] = useRecoilState(userProfileState);
  // ???????????? ??????
  const [selectedSkillstacks, setSelectedSkillstacks] = useRecoilState(
    selectedSkillstacksState,
  );
  // ???????????? ??????
  const [selectedInterests, setSelectedInterests] = useRecoilState(
    selectedInterestsState,
  );
  // ????????? ?????? ??????
  const [profileBody, setProfileBody] = useState({});
  const { id } = useParams();
  const navaigate = useNavigate();
  const [selectedLevel, setSelectedLevel] = useRecoilState(selectedLevelState);

  useEffect(() => {
    // ??????????????? ????????? ?????? ????????????
    axios.get(`/members/${id}`).then((res) => {
      // ???????????? ??????
      setSelectedSkillstacks(getEditSkills(res.data.data.skills));
      // ???????????? ??????
      let profileInterests = [];
      for (const el of res.data.data.interests) {
        profileInterests.push(el.name);
      }
      setSelectedInterests(profileInterests);
      setSelectedLevel(res.data.data.level);
      // ?????? ????????? ?????? ??????
      setProfileBody({
        memberId: res.data.data.memberId,
        name: res.data.data.name,
        description: res.data.data.description,
        level: res.data.data.level,
        github: res.data.data.github,
      });
    });
  }, []);

  // ????????? ?????? ????????? ?????????
  const onChangeName = (e) => {
    setProfileBody((cur) => {
      const newProfileBody = { ...cur };
      newProfileBody.name = e.target.value;
      return newProfileBody;
    });
  };
  // ??? ??? ?????? ?????? ????????? ?????????
  const onChangeDescription = (e) => {
    setProfileBody((cur) => {
      const newProfileBody = { ...cur };
      newProfileBody.description = e.target.value;
      return newProfileBody;
    });
  };
  // ????????? ?????? ????????? ?????????
  const onConnectGithub = () => {
    const githubPopup = window.open(
      process.env.REACT_APP_OAUTH_GITHUB_URL,
      '????????? ?????????',
      'width=600px,height=500px,scrollbars=yes',
    );
    githubPopup.addEventListener('unload', () => {
      const githubURL = window.localStorage.getItem('githubURL');
      if (githubURL) {
        setProfileBody({ ...profileBody, github: githubURL });
      } else {
        const Authorization = window.localStorage.getItem('Authorization');
        if (Authorization) notiInfo('?????? ????????? ????????? ???????????????.');
      }
    });
  };
  const onDisconnectGithub = () => {
    axios
      .get(`/members/${id}/delete-github`, {
        headers: { Authorization: localStorage.getItem('Authorization') },
      })
      .then(() => {
        setProfileBody({ ...profileBody, github: '' });
      })
      .catch((err) => {
        console.error(err);
        notiToast('????????? ??????????????????????');
      });
  };
  // ?????? PATCH ??????
  const onUpload = () => {
    // ????????? ?????? ?????? ????????? POST ????????? ???????????? ??????
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
          headers: { Authorization: localStorage.getItem('Authorization') },
        },
      )
      .then((res) => {
        notiSuccess('??????????????? ?????????????????????.');
        navaigate(`/mypage/${id}`);
      })
      .catch((err) => notiError('??????????????? ??????????????????!'));
  };
  return (
    <MypageEditContainer>
      <Wrapper>
        {/* ?????? ????????? ????????? */}
        <MyInfoContainer>
          <div className="user-img">
            <img src={avatar} alt={`${profileData.name}'??? ????????? ?????????`} />
          </div>
          <div className="user-info">
            <label htmlFor="user-nickname">
              <span>?????????</span>
              <input
                id="user-nickname"
                className="input-user-nickname"
                value={profileBody.name || ''}
                onChange={onChangeName}
              ></input>
            </label>
            <label htmlFor="user-description">
              <span>??? ??? ??????</span>
              <input
                id="user-description"
                className="input-user-decription"
                value={profileBody.description || ''}
                onChange={onChangeDescription}
              ></input>
            </label>
            <div className="level">
              <label htmlFor="level-select-view">
                <span>?????????</span>
                <LevelSelect />
              </label>
            </div>
            <label htmlFor="github-url">
              <span>?????????</span>
              <div className="github-wrapper">
                <input
                  id="github-url"
                  value={profileBody.github || '????????? ????????? ????????????.'}
                  disabled
                ></input>
                {profileBody.github ? (
                  <MiniButton text="????????????" onClick={onDisconnectGithub} />
                ) : (
                  <MiniButton text="????????????" onClick={onConnectGithub} />
                )}
              </div>
            </label>
          </div>
        </MyInfoContainer>
        {/* ?????? ????????? */}
        <ViewContainer>
          <span>????????? ?????? ????????? ????????? ????????? ?????????.</span>
          <SkillStackSelect />
        </ViewContainer>
        {/* ?????? ?????? ????????? */}
        <ViewContainer>
          <span>?????? ????????? ????????? ?????????.</span>
          <InterestSelect />
        </ViewContainer>
        <div className="save-btn">
          <MiniButton text="????????????" onClick={onUpload} />
        </div>
      </Wrapper>
    </MypageEditContainer>
  );
};

export default MyPageEdit;
