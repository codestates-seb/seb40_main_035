/* eslint-disable react/no-unknown-property */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/label-has-associated-control */
import styled from 'styled-components';
import { useState, useEffect } from 'react';
import axios from 'axios';
import DefaultButton from '../components/DefaultButton';
import InterestSelect from '../components/InterestSelect';
import LevelSelect from '../components/LevelSelect';
import MiniButton from '../components/MiniButton';
import SkillStackSelect from '../components/SkillStackSelect';
import Message from '../components/Message';
import Label from '../components/Label';
import LineInput from '../components/LineInput';
import { AiOutlineCheck } from 'react-icons/ai';
import { useRecoilState, useRecoilValue } from 'recoil';
import {
  selectedLevelState,
  selectedSkillstacksState,
  selectedInterestsState,
  skillstacksCheckState,
  interestsCheckState,
} from '../atom/atom';
import { notiError, notiInfo, notiSuccess, notiToast } from '../assets/toast';

const Container = styled.div`
  min-height: calc(100vh - 62px);
  width: 100%;

  h1 {
    padding: 67px 0 77px;
    text-align: center;
  }

  .form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    grid-template-rows: auto;
    gap: 100px;

    .rightside {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    }

    .input-wrapper {
      display: flex;

      button,
      .confirmed-icon {
        margin-left: 25px;
      }

      .confirmed-icon {
        padding-top: 5px;
      }
    }

    .select-section {
      margin-bottom: 30px;

      .option-notice {
        font-size: 13px;
        font-weight: 400;
      }
    }
  }

  .submit-btn {
    margin-top: 60px;
    float: right;
  }

  .github-url {
    width: 100%;
    color: var(--black);
    line-height: 10px;
    padding-top: 12px;
    border-bottom: 1px solid var(--purple);
    font-size: 14px;
  }
`;

const SignUp = () => {
  const [userId, setUserId] = useState('');
  const [userIdErr, setUserIdErr] = useState(null); // id ??????(????????? ?????? ?????????)
  const [idIsChecked, setIdChecked] = useState(false); // id ???????????? + ???????????? ??????

  const [verificationCode, setVerificationCode] = useState('');
  const [verificationCodeErr, setVerificationCodeErr] = useState(null);
  const [codeIsChecked, setCodeChecked] = useState(false); // ?????? ?????? ??????

  const [nickName, setNickName] = useState('');
  const [nickNameErr, setNickNameErr] = useState(null);
  const [nameIsChecked, setNameChecked] = useState(false); // ????????? ?????? ?????? ??????

  const [password, setPassword] = useState('');
  const [passwordErr, setPasswordErr] = useState(false);

  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordCheckErr, setPasswordCheckErr] = useState(false);

  const [selectedLevel, setSelectedLevel] = useRecoilState(selectedLevelState);
  const [levelErr, setLevelErr] = useState(false);

  const [selectedSkillStacks, setSelectedSkillStacks] = useRecoilState(
    selectedSkillstacksState,
  );
  const [skillstacksCheck, setSkillstacksCheck] = useRecoilState(
    skillstacksCheckState,
  );

  const [selectedInterest, setSelectedInterest] = useRecoilState(
    selectedInterestsState,
  );
  const [interestsCheck, setInterestsCheck] =
    useRecoilState(interestsCheckState);

  const [github, setGithub] = useState('');
  const [githubIsChecked, setGithubChecked] = useState(false);

  useEffect(() => {
    setSelectedLevel('');
    setSelectedSkillStacks([]);
    setSkillstacksCheck(true);
    setSelectedInterest([]);
    setInterestsCheck(true);
  }, []);

  // ?????? ??????
  // ?????????
  const onIdChange = (id) => {
    setUserId(id);
    if (idIsChecked) {
      onIdValidation();
      setIdChecked(false);

      setVerificationCode('');
      setCodeChecked(false);
      setVerificationCodeErr(true);
    }
  };
  // ?????????
  const onNameChange = (name) => {
    setNickName(name);
    if (nameIsChecked) {
      onNickNameValidation();
      setNameChecked(false);
    }
  };
  // ????????????
  const onPasswordChange = (pw) => {
    setPassword(pw);
    if (pw.length >= 8) {
      const regex =
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
      setPasswordErr(!regex.test(pw));
    }
  };
  // ???????????? ??????
  const onPasswordCheckChange = (pwCheck) => {
    setPasswordCheck(pwCheck);
    onPasswordCheckValidation();
  };

  // ????????? ??????
  // ?????????
  const onIdValidation = () => {
    if (userId.length) {
      const email = new RegExp('[a-z0-9]+@[a-z]+.[a-z]{2,3}');
      setUserIdErr(!email.test(userId));
    }
  };
  // ????????????
  const onVerificationValidation = () => {
    setVerificationCodeErr(!verificationCode.length);
  };
  // ?????????
  const onNickNameValidation = () => {
    if (nickName.length) {
      setNickNameErr(!(nickName.length >= 1));
    }
  };
  // ????????????
  const onPasswordValidation = () => {
    if (password.length) {
      const regex =
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
      setPasswordErr(!regex.test(password));
    }
  };
  // ???????????? ??????
  const onPasswordCheckValidation = () => {
    if (passwordCheck.length) {
      setPasswordCheckErr(password !== passwordCheck);
    }
  };

  // ??????/?????? ??????
  // ????????? ????????????
  const onCheckIdBtn = () => {
    onIdValidation();
    if (!userIdErr && userId.length > 1) {
      axios
        .post(`/members/signup/email-send`, {
          email: userId,
        })
        .then(() => {
          notiSuccess('???????????? ???????????? ??????????????? ?????????????????????');
          setIdChecked(true);
        })
        .catch((err) => {
          if (err.response.data.status === 409) {
            notiError(err.response.data.message);
          } else {
            console.log(err);
          }
        });
    } else {
      notiToast('????????? ????????? ?????? ??????????????????', 'error');
    }
  };
  // ???????????? ????????????
  const onCheckVerificationBtn = () => {
    axios
      .post(`/members/signup/email-auth`, {
        email: userId,
        code: verificationCode,
      })
      .then(() => {
        setVerificationCodeErr(false);
        setCodeChecked(true);
      })
      .catch(() => {
        notiToast('??????????????? ???????????? ????????????.', 'error');
        setVerificationCodeErr(true);
        setCodeChecked(false);
      });
  };
  // ????????? ????????????
  const onCheckNickNameBtn = () => {
    if (nickName.length) {
      axios
        .post(`/members/signup/verify-name`, {
          name: nickName,
        })
        .then(() => {
          setNameChecked(true);
        })
        .catch((err) => {
          notiError(err.response.data.message);
          setNameChecked(false);
          setNickNameErr(true);
        });
    } else {
      notiToast('1??? ?????? ??????????????????', 'error');
    }
  };
  // ????????? ????????????
  const onConnectGithub = () => {
    const githubPopup = window.open(
      process.env.REACT_APP_OAUTH_GITHUB_URL,
      '????????? ?????????',
      'width=600px,height=500px,scrollbars=yes',
    );
    githubPopup.addEventListener('unload', () => {
      const githubURL = window.localStorage.getItem('githubURL');
      if (githubURL) {
        setGithub(githubURL);
        setGithubChecked(true);
      } else {
        const Authorization = window.localStorage.getItem('Authorization');
        if (Authorization) notiInfo('?????? ????????? ????????? ???????????????.');
      }
    });
  };
  // ????????? ?????? ??????
  const onDisconnectGithub = () => {
    window.localStorage.removeItem('githubURL');
    setGithub('');
    setGithubChecked(false);
  };
  // ?????? ?????? ??????
  const onCheckAll = () => {
    onIdValidation();
    onNickNameValidation();
    onPasswordValidation();
    onPasswordCheckValidation();

    setSkillstacksCheck(selectedSkillStacks.length >= 1);
    setInterestsCheck(selectedInterest.length >= 1);
    setLevelErr(selectedLevel == null);
  };
  // ?????? ??????
  const onSignUp = () => {
    onCheckAll();

    if (
      idIsChecked &&
      codeIsChecked &&
      nameIsChecked &&
      !passwordErr &&
      !passwordCheckErr &&
      !levelErr &&
      skillstacksCheck &&
      interestsCheck
    ) {
      const memberSkills = selectedSkillStacks.map((el) => {
        return { skillName: el.name };
      });

      const memberInterests = selectedInterest.map((el) => {
        return { interestName: el };
      });

      const signUpInfo = {
        email: userId,
        name: nickName,
        password,
        passwordCheck,
        level: selectedLevel,
        memberInterests,
        memberSkills,
      };
      if (github.length > 1) signUpInfo.github = github;

      axios
        .post('/members/signup', signUpInfo)
        .then(() => {
          notiSuccess('?????????????????????! ????????????????????? ???????????? ????????? ?????????!');
          window.location = '/';
          window.localStorage.removeItem('githubURL');
        })
        .catch((err) => {
          if (err.response.data.status === 409) {
            notiError(err.response.data.message);
          }
          console.error(err);
        });
    } else {
      notiError('?????? ????????? ??????????????????!');
    }
  };

  return (
    <Container>
      <h1>????????????</h1>
      <div className="form-grid">
        <div className="leftside">
          <Label htmlFor="user-id">?????????</Label>
          <div className="input-wrapper">
            <LineInput
              id="user-id"
              message={
                idIsChecked
                  ? ''
                  : userIdErr
                  ? '????????? ????????? ?????? ??????????????????.'
                  : '???????????? ????????? ???????????? ??????????????????.'
              }
              value={userId}
              onChange={onIdChange}
              onBlur={onIdValidation}
              isError={userIdErr}
              type="email"
            />
            {!idIsChecked ? (
              <MiniButton text="????????????" onClick={onCheckIdBtn} />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="verification-code">????????????</Label>
          <div className="input-wrapper">
            <LineInput
              id="verification-code"
              message={
                !idIsChecked
                  ? '????????? ????????? ?????? ??????????????????.'
                  : codeIsChecked
                  ? ''
                  : '???????????? ????????? ????????? ??????????????????.'
              }
              value={verificationCode}
              onChange={setVerificationCode}
              onBlur={onVerificationValidation}
              isError={verificationCodeErr}
              disabled={!idIsChecked}
            />
            {!codeIsChecked ? (
              <MiniButton
                text="????????????"
                onClick={onCheckVerificationBtn}
                disabled={!idIsChecked}
              />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="nickname">?????????</Label>
          <div className="input-wrapper">
            <LineInput
              id="nickname"
              message={
                nameIsChecked
                  ? ''
                  : !nickNameErr
                  ? '???????????? ??????????????????.'
                  : nickName.length < 1
                  ? '1??? ?????? ??????????????????.'
                  : '????????? ??? ?????? ??????????????????.'
              }
              value={nickName}
              onChange={onNameChange}
              onBlur={onNickNameValidation}
              isError={nickNameErr}
            />
            {!nameIsChecked ? (
              <MiniButton text="????????????" onClick={onCheckNickNameBtn} />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="password">????????????</Label>
          <div className="input-wrapper">
            <LineInput
              id="password"
              message={
                passwordErr
                  ? '??????, ??????, ??????????????? ????????? 8??? ?????? ??????????????????.'
                  : password.length >= 8
                  ? ''
                  : '8?????? ????????? ??????, ??????, ???????????? ??????????????? ?????????.'
              }
              value={password}
              onChange={onPasswordChange}
              onBlur={onPasswordValidation}
              isError={passwordErr}
              type="password"
            />
            {password.length >= 8 && !passwordErr && (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="password-check">???????????? ??????</Label>
          <div className="input-wrapper">
            <LineInput
              id="password-check"
              message={
                passwordCheck.length < 1
                  ? '??????????????? ?????? ??? ??? ??????????????????.'
                  : password === passwordCheck
                  ? ''
                  : '??????????????? ???????????? ????????????.'
              }
              value={passwordCheck}
              onChange={onPasswordCheckChange}
              onBlur={onPasswordCheckValidation}
              isError={passwordCheckErr}
              type="password"
            />
            {passwordCheck.length >= 8 && password === passwordCheck && (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="level">?????????</Label>
          <LevelSelect id="level" />
          {levelErr && <Message isError={levelErr} text="?????? ???????????????." />}
        </div>
        <div className="rightside">
          <div className="select-section">
            <Label htmlFor="skill-stack">
              ???????????? ?????? ????????? ??????????????????
            </Label>
            {!skillstacksCheck && (
              <Message isError={true} text="1??? ?????? ??????????????????." />
            )}
            <SkillStackSelect id="skill-stack" />
          </div>
          <div className="select-section">
            <Label htmlFor="interest">?????? ????????? ??????????????????</Label>
            {!interestsCheck && (
              <Message isError={true} text="1??? ?????? ??????????????????." />
            )}
            <InterestSelect id="interest" />
          </div>
          <div className="select-section">
            <Label htmlFor="github">
              ?????????<span className="option-notice">(??????)</span>
            </Label>
            <div className="input-wrapper">
              <p className="github-url" id="github">
                {githubIsChecked ? github : '????????? ????????? ??????????????????!'}
              </p>
              {githubIsChecked ? (
                <>
                  <div className="confirmed-icon">
                    <AiOutlineCheck fill="var(--purple)" />
                  </div>
                  <MiniButton
                    text="????????????"
                    onClick={onDisconnectGithub}
                    color="var(--purple-light)"
                    bgColor="var(--purple)"
                  />
                </>
              ) : (
                <MiniButton text="????????????" onClick={onConnectGithub} />
              )}
            </div>
          </div>
        </div>
      </div>
      <DefaultButton
        text="????????????"
        className="submit-btn"
        onClick={onSignUp}
      />
    </Container>
  );
};

export default SignUp;
